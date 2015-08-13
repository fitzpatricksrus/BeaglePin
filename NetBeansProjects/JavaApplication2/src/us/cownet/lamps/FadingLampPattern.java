package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.  One time deal.  Fade and then sit.
 */
public class FadingLampPattern extends ContainerLampPattern {
	/*
	 Each PWM cycle is pwmSteps long and there are pwmSteps cycles before things complete.
	 */
	public enum FadeDirection {
		FADE_OFF,
		FADE_ON,
		BOUNCE
	};

	public FadingLampPattern(LampPattern pattern, FadeDirection direction, int pwmSteps) {
		super(null);
		setPattern(pattern, direction, pwmSteps);
	}

	public void setPattern(LampPattern pattern, FadeDirection direction, int pwmSteps) {
		this.invertingPattern = new TemporaryLampPattern(pattern, pwmSteps,
				direction == FadeDirection.FADE_OFF);
		sourcePattern = invertingPattern;
		this.fadeDirection = direction;
		this.pwmSteps = pwmSteps;
	}

	@Override
	public byte getColumn(int x) {
		return invertingPattern.getColumn(x);
	}

	@Override
	public int getColCount() {
		return invertingPattern.getColCount();
	}

	@Override
	public void attached() {
		reset();
	}

	@Override
	public void endOfMatrixSync() {
		invertingPattern.endOfMatrixSync();
		if (invertingPattern.isDone()) {
			if (pwmPosition < pwmSteps) {
				// keep stepping through this fade cycle
				pwmPosition += 1;
				invertingPattern.setDuration(pwmPosition);
				invertingPattern.reset();
			} else if (fadeDirection == FadeDirection.BOUNCE) {
				//done with fade cycle and we're supposed to bounce
				invertingPattern.setInvert(invertingPattern.getInvert());
				reset();
			} else {
				// we're done
			}
		}
	}

	public void reset() {
		pwmPosition = 0;
		invertingPattern.setDuration(0);
	}

	public boolean isDone() {
		return (fadeDirection != FadeDirection.BOUNCE) && (pwmPosition >= pwmSteps);
	}

	private TemporaryLampPattern invertingPattern;
	private FadeDirection fadeDirection;
	private int pwmSteps;
	private int pwmPosition;

	public static void main(String args[]) {

	}
}
