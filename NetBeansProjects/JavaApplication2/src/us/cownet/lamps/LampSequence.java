package us.cownet.lamps;

import java.util.ArrayList;

public class LampSequence implements MatrixLampPattern {
	private int ndx;
	private final ArrayList<LampPattern> patterns;
	private final Cycle cycle;

	private interface Cycle {
		public int getIndex();

		public void nextIndex();

		public boolean isDone();

		public void reset();
	}

	private class OneTime implements Cycle {
		@Override
		public int getIndex() {
			return Math.min(ndx, patterns.size() - 1);
		}

		@Override
		public void nextIndex() {
			ndx++;
		}

		@Override
		public boolean isDone() {
			return ndx >= patterns.size();
		}

		@Override
		public void reset() {
		}
	}

	private class OneTimeResetable extends OneTime {
		public void reset() {
			ndx = 0;
		}
	}

	private class Repeating implements Cycle {
		@Override
		public int getIndex() {
			return ndx;
		}

		@Override
		public void nextIndex() {
			ndx = (ndx + 1) % patterns.size();
		}

		@Override
		public boolean isDone() {
			return false;
		}

		@Override
		public void reset() {
			ndx = 0;
		}
	}

	private class Cylon implements Cycle {
		@Override
		public int getIndex() {
			return Math.abs(ndx);
		}

		@Override
		public void nextIndex() {
			ndx++;
			if (ndx >= patterns.size()) {
				reset();
			}
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
		ONETIME_RESETABLE,
		REPEATING,
		CYLON,
	}

	public LampSequence(CycleType cycle) {
		patterns = new ArrayList<>();
		ndx = 0;
		switch (cycle) {
			case ONETIME:
				this.cycle = new OneTime();
				break;
			case ONETIME_RESETABLE:
				this.cycle = new OneTimeResetable();
				break;
			case REPEATING:
				this.cycle = new Repeating();
				break;
			case CYLON:
				this.cycle = new Cylon();
				break;
			default:
				this.cycle = new OneTimeResetable();
				break;
		}
	}

	public LampSequence add(LampPattern pattern) {
		patterns.add(pattern);
		return this;
	}

	@Override
	public byte getColumn(int x) {
		return patterns.get(cycle.getIndex()).getColumn(x);
	}

	@Override
	public int getColCount() {
		return patterns.get(cycle.getIndex()).getColCount();
	}

	@Override
	public void attached() {
		cycle.reset();
		if (!cycle.isDone()) {
			patterns.get(cycle.getIndex()).attached();
		}
	}

	@Override
	public void endOfMatrixSync() {
		if (!cycle.isDone()) {
			LampPattern currentPattern = patterns.get(cycle.getIndex());
			currentPattern.endOfMatrixSync();
			if (currentPattern.isDone()) {
				patterns.get(cycle.getIndex()).detached();
				cycle.nextIndex();
				if (!cycle.isDone()) {
					patterns.get(cycle.getIndex()).attached();
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
			patterns.get(cycle.getIndex()).detached();
		}
		cycle.reset();
		if (!cycle.isDone()) {
			patterns.get(cycle.getIndex()).attached();
		}
	}

	@Override
	public void detached() {
		if (!cycle.isDone()) {
			patterns.get(cycle.getIndex()).detached();
		}
	}

}
