package us.cownet.lamps;

public interface GreyscaleLampPattern {
	public byte[][] getPattern();

	public byte getLamp(int x, int y);

	public byte getColCount();

}
