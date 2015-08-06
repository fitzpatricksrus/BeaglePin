package us.cownet.lamps;

public interface LampPattern {
	public byte getColumn(int x);

	public default boolean getLamp(int x, int y) {
		return (getColumn(x) & (1 << y)) != 0;
	}

	public int getColCount();

	public void attached();

	public void sync();

	public void detached();
}
