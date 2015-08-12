package us.cownet.lamps;

public interface LampPattern {
	public byte getColumn(int x);

	public int getColCount();

	public default boolean getLamp(int x, int y) {
		return (getColumn(x) & (1 << y)) != 0;
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
