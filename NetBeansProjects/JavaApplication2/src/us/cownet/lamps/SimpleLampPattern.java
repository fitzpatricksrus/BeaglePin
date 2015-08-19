package us.cownet.lamps;

public class SimpleLampPattern implements LampPattern {
	private int[] pattern;

	public SimpleLampPattern(int[] pattern) {
		this.pattern = pattern;
	}

	public int[] getPattern() {
		return pattern;
	}

	public void setPattern(int[] newPattern) {
		pattern = newPattern;
	}

	@Override
	public byte getColumn(int col) {
		return (byte)pattern[col];
	}

	public void setLamp(int col, int row, boolean on) {
		if (on) {
			pattern[col] |= (1 << row);
		} else {
			pattern[col] &= ~(1 << row);
		}
	}

	@Override
	public int getColCount() {
		return pattern.length;
	}

	@Override
	public void attached() {
	}

	@Override
	public void endOfMatrixSync() {
	}

	@Override
	public boolean isDone() {
		// repeat forever
		return false;
	}

	@Override
	public void reset() {
	}

	@Override
	public void detached() {
	}
}
