package us.cownet.lamps;

public class GreyscaleLampPattern implements LampPattern {

	private static final int GREYSCALE_BITS = 8;
	private static final int GREYSCALE_STAGES = (1 << GREYSCALE_BITS);

	//{0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
	private static final int MASK[] = new int[GREYSCALE_BITS];		//8
	private static final int INDEX[] = new int[GREYSCALE_STAGES];	//256

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
		System.out.println();
	}

	public GreyscaleLampPattern() {
	}

	public GreyscaleLampPattern(int[][] greyPattern) {
		setPattern(greyPattern);
	}

	@Override
	public byte getColumn(int x) {
		return patterns[INDEX[cycleCount]].getColumn(x);
	}

	@Override
	public int getColCount() {
		return patterns[0].getColCount();
	}

	@Override
	public void attached() {
		cycleCount = 0;
	}

	@Override
	public void sync() {
		cycleCount = (cycleCount + 1) % GREYSCALE_STAGES;
	}

	@Override
	public void detached() {
	}

	public final void setPattern(int[][] greyPattern) {
		int colCount = greyPattern.length;
		for (int i = 0; i < GREYSCALE_BITS; i++) {
			patterns[i] = new SimpleLampPattern(new byte[colCount]);
//			System.out.println("Bit plane " + i);
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < 8; row++) {
					boolean isOn = (greyPattern[col][row] & MASK[i]) != 0;
					patterns[i].setLamp(col, row, isOn);
//					System.out.print(isOn ? "1" : "0");
				}
//				System.out.println();
			}
//			System.out.println();
		}
	}

	private int cycleCount;
	private final SimpleLampPattern patterns[] = new SimpleLampPattern[GREYSCALE_BITS];

	public static void main(String args[]) {
		System.out.println(">Testing GreyscaleLampPattern");

		int patternData[][] = new int[32][8];
		int value = 0;
		for (int col = 0; col < 32; col++) {
			for (int row = 0; row < 8; row++) {
				patternData[col][row] = value++;
			}
		}

		GreyscaleLampPattern pattern = new GreyscaleLampPattern(patternData);

		pattern.attached();
		int iterations = 20;
		int result[][] = new int[32][8];
		for (int i = 0; i < 255 * iterations; i++) {
			for (int col = 0; col < 32; col++) {
				for (int row = 0; row < 8; row++) {
					if (pattern.getLamp(col, row)) {
						result[col][row]++;
					}
				}
			}
			pattern.sync();
		}

		value = 0;
		for (int col = 0; col < 32; col++) {
			for (int row = 0; row < 8; row++) {
				if (result[col][row] != value * iterations) {
					System.out.println("result[" + col + "][" + row + "] was "
							+ result[col][row] + " instead of " + value * iterations);
				}
				value++;
			}
		}
		System.out.println("<Testing GreyscaleLampPattern");
	}
}
