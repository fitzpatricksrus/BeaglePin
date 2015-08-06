package us.cownet.lamps;

public class GreyscaleLampPattern implements LampPattern {

	private static final int GREYSCALE_BITS = 8;

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

	@Override
	public byte getColumn(int x) {
	}

	@Override
	public int getColCount() {
	}

	@Override
	public void attached() {
	}

	@Override
	public void sync() {
	}

	@Override
	public void detached() {
	}

	private int tickCount;
}
