package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.
 */
public class FadingLampPattern implements LampPattern {
	/*
	 Each PWM cycle is pwmSteps long and there are pwmSteps cycles before things complete.

	 */

	public enum FadeDirection {
		FADE_TO_BLACK,
		FADE_TO_WHITE,
		BOUNCE
	};

	public void setPattern(LampPattern pattern, FadeDirection direction, int pwmSteps) {
		this.sourcePattern = new TemporaryLampPattern(pattern, pwmSteps, direction != FadeDirection.FADE_TO_WHITE);
		this.fadeDirection = direction;
		this.pwmSteps = pwmSteps;
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
	public void attached() {
		pwmPhase = pwmSteps;
		pwmPosition = pwmSteps;
	}

	@Override
	public void endOfMatrixSync() {
		if (fadeDirection != FadeDirection.BOUNCE) {
		} else {
		}
	}

	private TemporaryLampPattern sourcePattern;
	private FadeDirection fadeDirection;
	private int pwmSteps;
	private int pwmPhase;
	private int pwmPosition;

	public static void main(String args[]) {

	}
}
