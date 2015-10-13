package us.cownet.lamps;

public class GreyscaleLampPattern implements MatrixLampPattern {

	public GreyscaleLampPattern() {
	}

	public GreyscaleLampPattern(int[][] greyPattern) {
		setPattern(greyPattern);
	}

	public GreyscaleLampPattern(int[][] greyPattern, int grayscaleBits) {
		setPattern(greyPattern, grayscaleBits, 0);
	}

	public GreyscaleLampPattern(int[][] greyPattern, int grayscaleBits, int startPosition) {
		setPattern(greyPattern, grayscaleBits, startPosition);
	}

	@Override
	public byte getColumn(int x) {
		return patterns[index[cycleCount]].getColumn(x);
	}

	@Override
	public int getColCount() {
		return patterns[0].getColCount();
	}

	public void setStartPosition(int startPosition) {
		cycleStart = startPosition;
	}

	@Override
	public void attached() {
		cycleCount = cycleStart;
	}

	@Override
	public void endOfMatrixSync() {
		cycleCount = (cycleCount + 1) % (greyscaleCycleSize);
	}

	public final void setPattern(int[][] greyPattern) {
		setPattern(greyPattern, 8, 0);
	}

	public final void setPattern(int[][] greyPattern, int grayscaleBits, int startPosition) {
		this.grayscaleBits = grayscaleBits;
		this.cycleStart = startPosition;
		greyscaleCycleSize = (1 << grayscaleBits) - 1;
		mask = new int[grayscaleBits];
		index = new int[greyscaleCycleSize];
		patterns = new SimpleLampPattern[grayscaleBits];

		int next = 0;
		for (int i = 0; i < grayscaleBits; i++) {
			mask[i] = 1 << i;
			for (int j = 0; j < mask[i]; j++) {
				index[next++] = i;
			}
		}

		int colCount = greyPattern.length;
		int rowCount = greyPattern[0].length;
		for (int i = 0; i < grayscaleBits; i++) {
			patterns[i] = new SimpleLampPattern(new int[colCount]);
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					boolean isOn = (greyPattern[col][row] & mask[i]) != 0;
					patterns[i].setLamp(col, row, isOn);
				}
			}
		}
	}

	// position in the refresh cycle
	private int cycleCount;
	// position to start the cycle on attached()
	private int cycleStart;
	// how many bits of greyscale resolution
	private int grayscaleBits;
	// component lamp patters.  one for each greyscale bit
	private SimpleLampPattern patterns[];
	// how many ticks in greyscale cycle
	private int greyscaleCycleSize;
	// mask for each bit in the cycle
	private int mask[];
	// which pattern to use for each stage in the cycle
	private int index[];

	public static void main(String args[]) {
		System.out.println(">Testing GreyscaleLampPattern");

		final int patternValues[][] = {
			{
				1 << 7,
				1 << 6,
				1 << 5,
				1 << 4,
				1 << 0,
				1 << 1,
				1 << 2,
				1 << 3
			}
		};
		int colCount = patternValues.length;
		int rowCount = patternValues[0].length;
		int result[][] = new int[colCount][rowCount];

		GreyscaleLampPattern pattern = new GreyscaleLampPattern(patternValues, 8);
		pattern.attached();
		for (int i = 0; i < 255; i++) {
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					if (pattern.getLamp(col, row)) {
						result[col][row]++;
					}
				}
			}
			pattern.endOfMatrixSync();
		}
		pattern.detached();
		for (int col = 0; col < colCount; col++) {
			for (int row = 0; row < rowCount; row++) {
				System.out.println("" + col + "," + row + "  " + result[col][row]);
			}
		}

		System.out.println("<Testing GreyscaleLampPattern");
	}
}
