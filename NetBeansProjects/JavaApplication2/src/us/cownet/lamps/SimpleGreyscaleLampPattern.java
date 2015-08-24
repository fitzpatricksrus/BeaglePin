package us.cownet.lamps;

public class SimpleGreyscaleLampPattern extends BAMGreyscaleLampPattern {

	public SimpleGreyscaleLampPattern() {
	}

	public SimpleGreyscaleLampPattern(int[][] greyPattern) {
		setPattern(greyPattern);
	}

	public SimpleGreyscaleLampPattern(int[][] greyPattern, int grayscaleBits) {
		super(grayscaleBits);
		setPattern(greyPattern);
	}

	public SimpleGreyscaleLampPattern(int[][] greyPattern, int grayscaleBits, int startPosition) {
		super(grayscaleBits, startPosition);
		setPattern(greyPattern);
	}

	@Override
	public int getColCount() {
		return patterns[0].getColCount();
	}

	public byte greyscaleGetColumn(int column, int bit) {
		return patterns[bit].getColumn(column);
	}

	public void greyscaleEndOfSync() {
	}

	public final void setPattern(int[][] greyPattern) {
		patterns = new SimpleLampPattern[getGrayscaleBits()];

		int colCount = greyPattern.length;
		int rowCount = greyPattern[0].length;
		for (int i = 0; i < getGrayscaleBits(); i++) {
			patterns[i] = new SimpleLampPattern(new int[colCount]);
//			System.out.println("Bit plane " + i);
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					boolean isOn = (greyPattern[col][row] & getMask(i)) != 0;
					patterns[i].setLamp(col, row, isOn);
//					System.out.print(isOn ? "1" : "0");
				}
//				System.out.println();
			}
//			System.out.println();
		}
	}

	// component lamp patters.  one for each greyscale bit
	private SimpleLampPattern patterns[];

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

		SimpleGreyscaleLampPattern pattern = new SimpleGreyscaleLampPattern(patternValues, 8);
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
