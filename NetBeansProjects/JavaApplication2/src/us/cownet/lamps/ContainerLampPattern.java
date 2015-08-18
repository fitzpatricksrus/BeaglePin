package us.cownet.lamps;

public class ContainerLampPattern implements LampPattern {
	public ContainerLampPattern(LampPattern sourcePattern) {
		this.sourcePattern = sourcePattern;
	}

	@Override
	public byte getColumn(int x) {
		return sourcePattern.getColumn(x);
	}

	@Override
	public int getColCount() {
		return sourcePattern.getColCount();
	}

	@Override
	public boolean getLamp(int x, int y) {
		return sourcePattern.getLamp(x, y);
	}

	@Override
	public void attached() {
		sourcePattern.attached();
	}

	@Override
	public void endOfMatrixSync() {
		sourcePattern.endOfMatrixSync();
	}

	@Override
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

	private LampPattern sourcePattern;
}
