package us.cownet.lamps;

import java.util.ArrayList;

public class LampSequence implements LampPattern {

	public LampSequence() {
		patterns = new ArrayList<>();
		ndx = 0;
	}

	public LampSequence add(LampPattern pattern) {
		patterns.add(pattern);
		return this;
	}

	@Override
	public byte getColumn(int x) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getColCount() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void reset() {

	}

	private int ndx;
	private ArrayList<LampPattern> patterns;

}
