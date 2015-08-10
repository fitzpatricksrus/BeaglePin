package us.cownet.lamps;

import java.util.Arrays;

public class GreyscaleLampPattern implements LampPattern {

	public GreyscaleLampPattern() {
	}

	public GreyscaleLampPattern(int[][] greyPattern) {
		setPattern(greyPattern);
	}

	public GreyscaleLampPattern(int[][] greyPattern, int grayscaleBits) {
		setPattern(greyPattern, grayscaleBits);
	}

	@Override
	public byte getColumn(int x) {
		return patterns[index[cycleCount]].getColumn(x);
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
	public void endOfColumnSync() {
		cycleCount = (cycleCount + 1) % (greyscaleCycleSize);
	}

	@Override
	public void endOfMatrixSync() {
		cycleCount = (cycleCount + 1) % (greyscaleCycleSize);
	}

	@Override
	public void detached() {
	}

	public final void setPattern(int[][] greyPattern) {
		setPattern(greyPattern, 8);
	}

	public final void setPattern(int[][] greyPattern, int grayscaleBits) {
		this.grayscaleBits = grayscaleBits;
		greyscaleCycleSize = (1 << grayscaleBits) - 1;
		mask = new int[grayscaleBits];
		index = new int[greyscaleCycleSize];
		patterns = new SimpleLampPattern[grayscaleBits];

		int next = 0;
		for (int i = 0; i < grayscaleBits; i++) {
			mask[i] = 1 << i;
//			System.out.println(MASK[i]);
			for (int j = 0; j < mask[i]; j++) {
				index[next++] = i;
			}
		}
//		index[next++] = grayscaleBits - 1;
		System.out.println("mask: " + Arrays.toString(mask));
		System.out.println("index: " + Arrays.toString(index));

		int colCount = greyPattern.length;
		int rowCount = greyPattern[0].length;
		for (int i = 0; i < grayscaleBits; i++) {
			patterns[i] = new SimpleLampPattern(new int[colCount]);
			System.out.println("Bit plane " + i);
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					boolean isOn = (greyPattern[col][row] & mask[i]) != 0;
					patterns[i].setLamp(col, row, isOn);
					System.out.print(isOn ? "1" : "0");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	// position in the refresh cycle
	private int cycleCount;
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
			{1, 2, 4, 8},};
		int colCount = patternValues.length;
		int rowCount = patternValues[0].length;
		int result[][] = new int[colCount][rowCount];

		GreyscaleLampPattern pattern = new GreyscaleLampPattern(patternValues, 4);
		pattern.attached();
		for (int i = 0; i < 30; i++) {
			for (int col = 0; col < colCount; col++) {
				for (int row = 0; row < rowCount; row++) {
					if (pattern.getLamp(col, row)) {
						result[col][row]++;
					}
				}
			}
			pattern.endOfMatrixSync();
		}
		for (int col = 0; col < colCount; col++) {
			for (int row = 0; row < rowCount; row++) {
				System.out.println("" + col + "," + row + "  " + result[col][row]);
			}
		}

		System.out.println("<Testing GreyscaleLampPattern");
	}
}
