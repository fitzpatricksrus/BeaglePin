package us.cownet.lamps;

import java.util.ArrayList;
import us.cownet.lamps.simple.SimpleLampPattern;

public class CompositeOrLampPattern implements LampPattern {
	public CompositeOrLampPattern() {
		patterns = new ArrayList<>();
		compositePatternStorage = new byte[0];
		compositePattern = new SimpleLampPattern(compositePatternStorage);
	}

	public void addPattern(LampPattern pattern) {
		patterns.add(pattern);
		recomputeDerivedPattern();
	}

	public void RemovePattern(LampPattern pattern) {
		patterns.remove(pattern);
		recomputeDerivedPattern();
	}

	@Override
	public byte[] getPattern() {
		return compositePattern.getPattern();
	}

	@Override
	public byte getColumn(int col) {
		return compositePattern.getColumn(col);
	}

	@Override
	public boolean getLamp(int col, int row) {
		return compositePattern.getLamp(col, row);
	}

	@Override
	public int getColCount() {
		return compositePatternStorage.length;
	}

	private void recomputeDerivedPattern() {
		int colCount = 0;
		for (LampPattern pattern : patterns) {
			colCount = Math.max(colCount, pattern.getColCount());
		}
		if (colCount != compositePatternStorage.length) {
			// only reallocate storage if we need to.
			compositePatternStorage = new byte[colCount];	// already 0s.  It's Java
		}
		for (LampPattern pattern : patterns) {
			for (int i = 0; i < pattern.getColCount(); i++) {
				compositePatternStorage[i] |= pattern.getColumn(i);
			}
		}
		compositePattern.setPattern(compositePatternStorage);
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

	private ArrayList<LampPattern> patterns;
	private byte compositePatternStorage[];
	private SimpleLampPattern compositePattern;
}
