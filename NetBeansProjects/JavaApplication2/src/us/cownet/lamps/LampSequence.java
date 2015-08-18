package us.cownet.lamps;

import java.util.ArrayList;

public class LampSequence extends LampPatternContainer implements LampPattern {

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
		if (ndx < patterns.size()) {
			return patterns.get(ndx).getColumn(x);
		} else {
			return 0;
		}
	}

	@Override
	public int getColCount() {
		if (ndx < patterns.size()) {
			return patterns.get(ndx).getColCount();
		} else {
			return 0;
		}
	}

	@Override
	public void attached() {
		reset();
	}

	@Override
	public void endOfColumnSync() {
		if (ndx < patterns.size()) {
			patterns.get(ndx).endOfColumnSync();
		}
	}

	@Override
	public void endOfMatrixSync() {
		if (ndx < patterns.size()) {
			patterns.get(ndx).endOfMatrixSync();
			patterns.get(ndx).detached();
			ndx++;
			if (ndx < patterns.size()) {
				patterns.get(ndx).attached();
			}
		}
	}

	@Override
	public boolean isDone() {
		return ndx < patterns.size();
	}

	@Override
	public void reset() {
		ndx = 0;
		if (ndx < patterns.size()) {
			patterns.get(ndx).attached();
		}
	}

	@Override
	public void detached() {
		if (ndx < patterns.size()) {
			patterns.get(ndx).detached();
		}
	}

	private int ndx;
	private ArrayList<LampPattern> patterns;

}
