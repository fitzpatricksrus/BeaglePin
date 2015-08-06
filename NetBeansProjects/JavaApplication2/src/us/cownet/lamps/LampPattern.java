package us.cownet.lamps;

public interface LampPattern {
	public byte getColumn(int x);

	public boolean getLamp(int x, int y);

	public int getColCount();

	public void attached();

	public void sync();

	public void detached();
}
