package us.cownet.lamps.simple;

import us.cownet.lamps.LampPattern;

public class SimpleLampPattern implements LampPattern {
	public static final SimpleLampPattern ALL_OFF = new SimpleLampPattern(new byte[0]);

	private byte[] pattern;

	public SimpleLampPattern(byte[] pattern) {
		this.pattern = pattern;
	}

	@Override
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

	@Override
	public boolean getLamp(int col, int row) {
		return (pattern[col] & (1 << row)) != 0;
	}

	public void setLamp(int col, int row, boolean on) {
		if (on) {
			pattern[col] |= (1 << row);
		} else {
			pattern[col] &= ~(1 << row);
		}
	}

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
