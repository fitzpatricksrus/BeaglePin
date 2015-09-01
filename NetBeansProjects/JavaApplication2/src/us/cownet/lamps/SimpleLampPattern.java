package us.cownet.lamps;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLampPattern implements LampPattern {
	private int[] pattern;

	public SimpleLampPattern(int columnCount) {
		this.pattern = new int[columnCount];
	}

	public SimpleLampPattern(int[] pattern) {
		this.pattern = pattern;
	}

	@Override
	public SimpleLampPattern clone() {
		try {
			SimpleLampPattern result = (SimpleLampPattern)super.clone();
			result.setPattern(getPattern().clone());
			return result;
		} catch (CloneNotSupportedException ex) {
			Logger.getLogger(SimpleLampPattern.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
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

	public void allOn() {
		int p[] = getPattern();
		for (int i = 0; i < p.length; i++) {
			p[i] = -1;
		}
	}

	public void allOff() {
		int p[] = getPattern();
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
		}
	}

	public void union(LampPattern other) {
		int colCount = Math.min(getColCount(), other.getColCount());
		for (int col = 0; col < colCount; col++) {
			for (int row = 0; row < 8; row++) {
				if (!getLamp(col, row)) {
					setLamp(col, row, other.getLamp(col, row));
				}
			}
		}
	}

	public void difference(LampPattern other) {
		// hey jf - this method doesn't deal with column counts correctly
		int colCount = Math.min(getColCount(), other.getColCount());
		for (int col = 0; col < colCount; col++) {
			for (int row = 0; row < 8; row++) {
				setLamp(col, row, getLamp(col, row) != other.getLamp(col, row));
			}
		}
	}
}
