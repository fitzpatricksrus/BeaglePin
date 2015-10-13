package us.cownet.lamps;

public interface LinearLampMatrix extends LampPattern {
	public default byte getColumn(int x) {
		int colBase = x * 8;
		byte result = 0;
		for (int row = 0; row < 8; row++) {
			result <<= 1;
			if (getLamp(colBase + row)) {
				result |= 1;
			}
		}
		return result;
	}

	public default int getColCount() {
		return (getLampCount() + 7) / 8;
	}

	// preferred method to override
	public int getLampCount();

	public default boolean getLamp(int x, int y) {
		return getLamp(x << 3 + y);
	}

	// preferred method to override
	public boolean getLamp(int index);

}
