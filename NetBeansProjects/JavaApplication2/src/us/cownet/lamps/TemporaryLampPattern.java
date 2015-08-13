package us.cownet.lamps;

/*
 A one shot deal.  Start either on or off, stay that way for some duration, and
 then change state.  Done.  Only the lamps that are in in the source are affected.
 It's just delay and invert.
 */
public class TemporaryLampPattern extends ContainerLampPattern {
	public TemporaryLampPattern(LampPattern sourcePattern, int duration, boolean startOn) {
		super(sourcePattern);
		this.duration = duration;
		this.startOn = startOn;
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

	public boolean getInvert() {
		return startOn;
	}

	public void setInvert(boolean invert) {
		this.startOn = invert;
	}

	@Override
	public byte getColumn(int x) {
		if (position < duration) {
			return startOn ? sourcePattern.getColumn(x) : 0;
		} else {
			// we're done with the cycle
			return startOn ? 0 : sourcePattern.getColumn(x);
		}
	}

	@Override
	public int getColCount() {
		return sourcePattern.getColCount();
	}

	public void attached() {
		position = 0;
	}

	public void endOfMatrixSync() {
		super.endOfMatrixSync();
		position = Math.min(duration, position + 1);
	}

	public boolean isDone() {
		return position >= duration;
	}

	private int duration;
	private int position;
	private boolean startOn;
}
