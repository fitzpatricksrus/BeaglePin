package us.cownet.lamps;

public class TemporaryLampPattern implements LampPattern {
	public TemporaryLampPattern(LampPattern sourcePattern, int duration, boolean tempOn) {
		this.sourcePattern = sourcePattern;
		this.duration = duration;
		this.tempOn = tempOn;
	}

	public void reset() {
		position = 0;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean getTempOn() {
		return tempOn;
	}

	public void setTempOn(boolean tempOn) {
		this.tempOn = tempOn;
	}

	@Override
	public byte getColumn(int x) {
		return (position < duration && tempOn) ? sourcePattern.getColumn(x) : 0;
	}

	@Override
	public int getColCount() {
		return sourcePattern.getColCount();
	}

	public void attached() {
		position = 0;
	}

	public void endOfMatrixSync() {
		position = Math.min(duration, position + 1);

	}

	private LampPattern sourcePattern;
	private int duration;
	private int position;
	private boolean tempOn;
}
