package us.cownet.lamps;

public interface LampPattern {
	public byte getColumn(int x);

	public int getColCount();

	public default boolean getLamp(int x, int y) {
		return (getColumn(x) & (1 << y)) != 0;
	}

	public default boolean getLamp(int index) {
		return getLamp(index >>> 3, index & 0b00000111);
	}

	public default void attached() {
	}

	public default void endOfColumnSync() {
	}

	public default void endOfMatrixSync() {
	}

	public default void detached() {
	}
}
