package us.cownet.lamps;

/*
 A lamp matrix where transitions between off/on happen slowly.  Bulbs each
 have thier own state and can rise and fall individually.

 todo:
 deal with patterns of size different from matrix size
 */
public class FadingLampMatrix extends CyclicLampMatrix {
	private int pixels[][];
	private int mask[];
	private int speed;

	public FadingLampMatrix(PinballOutputController controller, long ticks, int speed) {
		super(controller, ticks, speed);
		this.speed = speed;
		pixels = new int[controller.getColumnCount()][8];
		mask = new int[speed];
		for (int i = 0; i < 256; i++) {
			mask[i] = (int)Math.pow(2, Math.floor(Math.log(i) / Math.log(2)));
		}
	}

	@Override
	protected byte internalGetColumn(LampPattern pattern, int columnNumber) {
		byte value = 0;
		int bitMask = mask[getCycle()];

		for (int row = 0; row < 8; row++) {
			value <<= 1;
			if ((pixels[columnNumber][row] & bitMask) != 0) {
				value |= 1;
			}
		}

		return value;
	}

	@Override
	protected void internalSetPattern(LampPattern newPattern) {
		super.internalSetPattern(newPattern);
	}

	@Override
	protected void internalEndOfCycle() {
		LampPattern pattern = getDisplayedPattern();
		for (int col = 0; col < pixels[0].length; col++) {
			for (int row = 0; row < 8; row++) {
				if (pattern.getLamp(col, row)) {
					pixels[col][row] = Math.min(pixels[col][row] + 1, speed);
				} else {
					pixels[col][row] = Math.max(pixels[col][row] - 1, 0);
				}
			}
		}
	}

	public static void main(String args[]) {
		for (int i = 0; i < 256; i++) {
			System.out.println("" + i + " " + Math.pow(2, Math.floor(Math.log(i) / Math.log(2))));
		}
	}
}
