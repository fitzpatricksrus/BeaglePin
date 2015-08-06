package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.
 */
public class FadingLampPattern implements LampPattern {

	@Override
	public byte[] getPattern() {
	}

	public void setPattern(LampPattern pattern) {
	}

	@Override
	public byte getColumn(int x) {
	}

	@Override
	public boolean getLamp(int x, int y) {
	}

	@Override
	public int getColCount() {
	}

	@Override
	public void attached() {
	}

	@Override
	public void sync() {
	}

	@Override
	public void detached() {
	}
}
