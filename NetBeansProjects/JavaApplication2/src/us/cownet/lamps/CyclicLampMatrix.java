package us.cownet.lamps;

// Matrix that only allowed pattern changes after a set number of cycles.
public class CyclicLampMatrix extends SimpleLampMatrix {

	private final int cycleSize;
	private int currentCycle;

	public CyclicLampMatrix(PinballOutputController controller, long ticks, int cycleSize) {
		super(controller, ticks);
		this.cycleSize = cycleSize;
		this.currentCycle = 0;
	}

	@Override
	protected void internalSetPattern(LampPattern newPattern) {
		if (currentCycle == 0) {
			super.internalSetPattern(newPattern);
		}
	}

	@Override
	protected void internalEndOfMatrixSync(LampPattern pattern) {
		pattern.endOfMatrixSync();
		currentCycle = (currentCycle + 1) % cycleSize;
		if (currentCycle == 0) {
			internalEndOfCycle();
		}
	}

	protected int getCycle() {
		return currentCycle;
	}

	protected int getCycleSize() {
		return cycleSize;
	}

	protected void internalEndOfCycle() {
	}
}
