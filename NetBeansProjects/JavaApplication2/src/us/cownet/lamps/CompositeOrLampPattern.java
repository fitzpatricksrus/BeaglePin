package us.cownet.lamps;

import java.util.ArrayList;

public class CompositeOrLampPattern implements LampPattern {
	public CompositeOrLampPattern() {
		patterns = new ArrayList<>();
	}

	public void add(LampPattern pattern) {
		patterns.add(pattern);
	}

	public void remove(LampPattern pattern) {
		patterns.remove(pattern);
	}

	@Override
	public byte getColumn(int col) {
		byte result = 0;
		for (LampPattern pattern : patterns) {
			result |= pattern.getColumn(col);
		}
		return result;
	}

	@Override
	public int getColCount() {
		int result = 0;
		for (LampPattern p : patterns) {
			result = Math.max(result, p.getColCount());
		}
		return result;
	}

	@Override
	public void attached() {
		for (LampPattern pattern : patterns) {
			pattern.attached();
		}
	}

	@Override
	public void endOfMatrixSync() {
		for (LampPattern pattern : patterns) {
			pattern.endOfMatrixSync();
		}
	}

	@Override
	public boolean isDone() {
		for (LampPattern pattern : patterns) {
			if (!pattern.isDone()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void reset() {
		for (LampPattern pattern : patterns) {
			pattern.reset();
		}
	}

	@Override
	public void detached() {
		for (LampPattern pattern : patterns) {
			pattern.detached();
		}
	}

	private final ArrayList<LampPattern> patterns;
}
