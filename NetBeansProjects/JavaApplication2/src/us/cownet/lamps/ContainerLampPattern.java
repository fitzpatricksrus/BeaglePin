package us.cownet.lamps;

public class ContainerLampPattern implements LampPattern {
	public ContainerLampPattern(LampPattern sourcePattern) {
		this.sourcePattern = sourcePattern;
	}

	public byte getColumn(int x) {
		return sourcePattern.getColumn(x);
	}

	public int getColCount() {
		return sourcePattern.getColCount();
	}

	public boolean getLamp(int x, int y) {
		return sourcePattern.getLamp(x, y);
	}

	public void attached() {
		sourcePattern.attached();
	}

	public void endOfColumnSync() {
		sourcePattern.endOfColumnSync();
	}

	public void endOfMatrixSync() {
		sourcePattern.endOfMatrixSync();
	}

	public void detached() {
		sourcePattern.detached();
	}

	public LampPattern getPattern() {
		return sourcePattern;
	}

	public void setLampPattern(LampPattern newPattern) {
		// hey jf - you need to deal with attach/detach and all that crud here.
		sourcePattern = newPattern;
	}

	protected LampPattern sourcePattern;
}
