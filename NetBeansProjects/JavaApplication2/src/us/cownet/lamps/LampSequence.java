package us.cownet.lamps;

import java.util.ArrayList;

public class LampSequence implements LampPattern {
	private int ndx;
	private final ArrayList<LampPattern> patterns;
	private final Cycle cycle;

	private interface Cycle {
		public int nextIndex();

		public boolean isDone();

		public void reset();
	}

	private class OneTime implements Cycle {
		@Override
		public int nextIndex() {
			return ndx++;
		}

		@Override
		public boolean isDone() {
			return ndx >= patterns.size();
		}

		public void reset() {
			ndx = 0;
		}
	}

	private class Repeating implements Cycle {
		@Override
		public int nextIndex() {
			ndx = (ndx + 1) % patterns.size();
			return ndx;
		}

		@Override
		public boolean isDone() {
			return false;
		}

		public void reset() {
			ndx = 0;
		}
	}

	private class Cylon implements Cycle {
		@Override
		public int nextIndex() {
			ndx++;
			if (ndx > patterns.size()) {
				reset();
			}
			return Math.abs(ndx);
		}

		@Override
		public boolean isDone() {
			return false;
		}

		@Override
		public void reset() {
			ndx = -patterns.size() + 1;
		}
	}

	public enum CycleType {
		ONETIME,
		REPEATING,
		REVERSING,
	}

	public LampSequence(CycleType cycle) {
		patterns = new ArrayList<>();
		ndx = 0;
		switch (cycle) {
			case ONETIME:
				this.cycle = new OneTime();
				break;
			case REPEATING:
				this.cycle = new Repeating();
				break;
			case REVERSING:
				this.cycle = new Cylon();
				break;
			default:
				this.cycle = new OneTime();
		}
	}

	public LampSequence add(LampPattern pattern) {
		patterns.add(pattern);
		return this;
	}

	@Override
	public byte getColumn(int x) {
		if (cycle.isDone()) {
			return 0;
		} else {
			return patterns.get(ndx).getColumn(x);
		}
	}

	@Override
	public int getColCount() {
		if (cycle.isDone()) {
			return 0;
		} else {
			return patterns.get(ndx).getColCount();
		}
	}

	@Override
	public void attached() {
		cycle.reset();
		if (!cycle.isDone()) {
			patterns.get(ndx).attached();
		}
	}

	@Override
	public void endOfMatrixSync() {
		if (!cycle.isDone()) {
			LampPattern currentPattern = patterns.get(ndx);
			currentPattern.endOfMatrixSync();
			if (currentPattern.isDone()) {
				patterns.get(ndx).detached();
				cycle.nextIndex();
				if (!cycle.isDone()) {
					patterns.get(ndx).attached();
				}
			}
		}
	}

	@Override
	public boolean isDone() {
		return cycle.isDone();
	}

	@Override
	public void reset() {
		if (!cycle.isDone()) {
			patterns.get(ndx).detached();
		}
		cycle.reset();
		if (!cycle.isDone()) {
			patterns.get(ndx).attached();
		}
	}

	@Override
	public void detached() {
		if (!cycle.isDone()) {
			patterns.get(ndx).detached();
		}
	}

}
