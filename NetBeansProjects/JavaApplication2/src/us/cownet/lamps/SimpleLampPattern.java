package us.cownet.lamps;

public class SimpleLampPattern implements LampPattern {
	private byte[] pattern;

	public SimpleLampPattern(byte[] pattern) {
		this.pattern = pattern;
	}

	public byte[] getPattern() {
		return pattern;
	}

	public void setPattern(byte[] newPattern) {
		pattern = newPattern;
	}

	@Override
	public byte getColumn(int col) {
		return pattern[col];
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
	public void sync() {
	}

	@Override
	public void detached() {
	}

}
