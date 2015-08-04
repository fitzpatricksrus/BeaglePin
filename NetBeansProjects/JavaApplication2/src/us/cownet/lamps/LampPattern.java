package us.cownet.lamps;

public interface LampPattern {
	public byte[] getPattern();

	public byte getColumn(int x);

	public boolean getLamp(int x, int y);

	public int getColCount();
}
