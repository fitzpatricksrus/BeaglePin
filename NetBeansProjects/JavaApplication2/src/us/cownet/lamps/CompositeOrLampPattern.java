package us.cownet.lamps;

import java.util.ArrayList;

public class CompositeOrLampPattern implements LampPattern {
	public CompositeOrLampPattern() {
	}

	public void addPattern(LampPattern pattern) {
		patterns.add(pattern);
		colCount = (byte)Math.max(colCount, pattern.getColCount());
	}

	@Override
	public byte[] getPattern() {
		return null;
	}

	public byte getColumn(int col) {
		int value = 0;
		for (int i = 0; i < colCount; i++) {
			value |= patterns.get(i).getPattern()[col];
		}
		return (byte)value;
	}

	@Override
	public boolean getLamp(int col, int row) {
		return false;
	}

	@Override
	public int getColCount() {
		return colCount;
	}

	private ArrayList<LampPattern> patterns;
	private byte colCount;
}
