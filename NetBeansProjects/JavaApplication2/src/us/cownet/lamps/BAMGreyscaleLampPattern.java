package us.cownet.lamps;

public abstract class BAMGreyscaleLampPattern implements LampPattern {

	public BAMGreyscaleLampPattern() {
		this(8);
	}

	public BAMGreyscaleLampPattern(int grayscaleBits) {
		this(8, 0);
	}

	public BAMGreyscaleLampPattern(int grayscaleBits, int startPosition) {
		this.grayscaleBits = grayscaleBits;
		this.cycleStart = startPosition;
		this.greyscaleCycleSize = (1 << grayscaleBits) - 1;
		mask = new int[grayscaleBits];
		index = new int[greyscaleCycleSize];
		int next = 0;
		for (int i = 0; i < grayscaleBits; i++) {
			mask[i] = 1 << i;
//			System.out.println(MASK[i]);
			for (int j = 0; j < mask[i]; j++) {
				index[next++] = i;
			}
		}
	}

	public void setStartPosition(int startPosition) {
		cycleStart = startPosition;
	}

	@Override
	public byte getColumn(int x) {
		return greyscaleGetColumn(x, index[cycleCount]);
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

	public abstract byte greyscaleGetColumn(int x, int bit);

	public abstract void greyscaleEndOfSync();

	public int getCycleCount() {
		return cycleCount;
	}

	public int getCycleStart() {
		return cycleStart;
	}

	public int getGrayscaleBits() {
		return grayscaleBits;
	}

	public int getGreyscaleCycleSize() {
		return greyscaleCycleSize;
	}

	public int getMask(int ndx) {
		return mask[ndx];
	}

	public int getIndex(int ndx) {
		return index[ndx];
	}

	// position in the refresh cycle
	private int cycleCount;
	// position to start the cycle on attached()
	private int cycleStart;
	// how many bits of greyscale resolution
	private int grayscaleBits;
	// how many ticks in greyscale cycle
	private int greyscaleCycleSize;
	// mask for each bit in the cycle
	private int mask[];
	// which pattern to use for each stage in the cycle
	private int index[];
}
