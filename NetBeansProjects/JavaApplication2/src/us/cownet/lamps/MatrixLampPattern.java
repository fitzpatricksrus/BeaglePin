package us.cownet.lamps;

public interface MatrixLampPattern extends LampPattern {
	public byte getColumn(int x);

	public int getColCount();

	// preferred method to override
	public default int getLampCount() {
		return getColCount() * 8;
	}

	public default boolean getLamp(int x, int y) {
		return (getColumn(x) & (1 << y)) != 0;
	}

	// preferred method to override
	public default boolean getLamp(int index) {
		// hey jf - this assumes an 8 light col size.
		return getLamp(index >>> 3, index & 0b00000111);
	}
}
