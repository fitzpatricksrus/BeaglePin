package us.cownet.lamps.simple;

import us.cownet.lamps.GreyscaleLampMatrix;
import us.cownet.lamps.GreyscaleLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.timers.Callback;

public class SimpleGreyscaleLampMatrix implements GreyscaleLampMatrix {
	private static final int GREYSCALE_BITS = 8;
	private LampMatrix matrix;
	private GreyscaleLampPattern pattern;
	private int tickNumber;
	private Callback callback;
	private SimpleLampPattern patterns[] = new SimpleLampPattern[GREYSCALE_BITS];

	//{0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
	private static final int MASK[] = new int[GREYSCALE_BITS];
	private static final int INDEX[] = new int[1 << GREYSCALE_BITS];

	static {
		int next = 0;
		for (int i = 0; i < GREYSCALE_BITS; i++) {
			MASK[i] = 1 << i;
//			System.out.println(MASK[i]);
			for (int j = 0; j < MASK[i]; j++) {
				INDEX[next++] = i;
			}
		}
		INDEX[next++] = GREYSCALE_BITS - 1;
//		System.out.println();
	}

	public SimpleGreyscaleLampMatrix(LampMatrix matrix) {
		this.matrix = matrix;
		tickNumber = 0;
		matrix.setSyncCallback(() -> tick());
	}

	public GreyscaleLampPattern getPattern() {
		return pattern;
	}

	public void setPattern(GreyscaleLampPattern lamps) {
		this.pattern = lamps;
		for (int i = 0; i < patterns.length; i++) {
			patterns[i] = new SimpleLampPattern(new byte[lamps.getColCount()]);
			for (int col = 0; col < pattern.getColCount(); col++) {
				for (int row = 0; row < 8; row++) {
					patterns[i].setLamp(col, row, (lamps.getLamp(col, row) & MASK[i]) != 0);
				}
			}
		}
		// we could reset tickNumber here, but that would have the effect
		// of rapidly changing patterns to be on artificially long since
		// tickNumber would always be low.  So, we just leave it as it is.
		matrix.setPattern(patterns[INDEX[tickNumber]]);
	}

	public void setSyncCallback(Callback callback) {
		this.callback = callback;
	}

	private void tick() {
		tickNumber = (tickNumber + 1) % (1 << GREYSCALE_BITS);
		if (tickNumber == 0 && callback != null) {
			callback.call();
		}
		matrix.setPattern(patterns[INDEX[tickNumber]]);
	}
}
