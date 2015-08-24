package us.cownet.lamps;

/*
 Base class that is geared toward patterns that change at endOfMatrixSync.
 Nothing is cached.  Everything is derived from the pattern data each time.
 The actual data is supplied by subclasses so it can be produced algorithmcally
 if needed.
 */
public abstract class AbstractGreyscaleLampPattern implements LampPattern {

	public AbstractGreyscaleLampPattern() {
		this(8);
	}

	public AbstractGreyscaleLampPattern(int grayscaleBits) {
		setPattern(grayscaleBits, 0);
	}

	public AbstractGreyscaleLampPattern(int grayscaleBits, int startPosition) {
		setPattern(grayscaleBits, startPosition);
	}

	@Override
	public byte getColumn(int x) {
		byte result = 0;
		for (int i = getColCount() - 1; i >= 0; i--) {
			result <<= 1;
			if (getLamp(x, i)) {
				result |= 1;
			}
		}
		return result;
	}

	@Override
	public boolean getLamp(int col, int row) {
		return (getLampValue(col, row) & mask[cycleCount]) != 0;
	}

	public boolean getLamp(int index) {
		return getLamp(index >>> 3, index & 0b00000111);
	}

	protected abstract int getLampValue(int col, int row);

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
		if (cycleCount == 0) {
			greyscaleEndOfSync();
		}
	}

	protected void greyscaleEndOfSync() {
	}

	protected final void setPattern(int grayscaleBits, int startPosition) {
		this.cycleStart = startPosition;
		greyscaleCycleSize = (1 << grayscaleBits) - 1;
		mask = new int[greyscaleCycleSize];

		int next = 0;
		for (int i = 0; i < grayscaleBits; i++) {
//			System.out.println(MASK[i]);
			int m = (1 << i);
			for (int j = 0; j < m; j++) {
				mask[next++] = m;
			}
		}
//		System.out.println("mask: " + Arrays.toString(mask));
//		System.out.println("index: " + Arrays.toString(index));
	}

	// position in the refresh cycle
	private int cycleCount;
	// position to start the cycle on attached()
	private int cycleStart;
	// how many ticks in greyscale cycle
	private int greyscaleCycleSize;
	// mask for each bit in the cycle
	private int mask[];

	public static class Pattern extends AbstractGreyscaleLampPattern {
		private int greyPattern[][];

		public Pattern(int[][] greyPattern) {
			this(greyPattern, 8);
		}

		public Pattern(int[][] greyPattern, int grayscaleBits) {
			this(greyPattern, grayscaleBits, 0);
		}

		public Pattern(int[][] greyPattern, int grayscaleBits, int startPosition) {
			this.greyPattern = greyPattern;
			setPattern(grayscaleBits, startPosition);
		}

		@Override
		public int getColCount() {
			return greyPattern[0].length;
		}

		@Override
		protected int getLampValue(int col, int row) {
			return greyPattern[col][row];
		}
	}

	public static class Pattern2 extends AbstractGreyscaleLampPattern {
		private int position = 0;

		public Pattern2() {
			this(8);
		}

		public Pattern2(int grayscaleBits) {
			this(grayscaleBits, 0);
		}

		public Pattern2(int grayscaleBits, int startPosition) {
			setPattern(grayscaleBits, startPosition);
		}

		@Override
		public int getColCount() {
			return 8;
		}

		@Override
		protected int getLampValue(int col, int row) {
			int ndx = col * 8 + row;	// 0 - 63
			int adj = Math.abs(((position + ndx) % 128) - 64);	// -64..64
//			if (ndx == 0) {
//				System.out.println(Math.min(adj * 4, 255));
//			}
			return Math.min(adj * 4, 255);
		}

		@Override
		protected void greyscaleEndOfSync() {
			position = (position + 1) % 128;
		}
	}

	public static void main(String[] args) {
		for (int position = 0; position < 256; position++) {
			System.out.print("" + position + "> ");
			for (int ndx = 0; ndx < 64; ndx++) {
				int adj = Math.abs(((position + ndx) % 128) - 64);	// -64..64
				System.out.print("" + adj + ",");
			}
			System.out.println();
		}
	}

}
