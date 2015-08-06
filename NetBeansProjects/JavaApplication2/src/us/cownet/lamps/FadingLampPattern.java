package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.
 */
public class FadingLampPattern implements LampPattern {

	public enum FadeDirection {
		FADE_TO_BLACK,
		FADE_TO_WHITE
	};

	public void setPattern(LampPattern pattern, FadeDirection direction, int speed) {
		sourcePattern = pattern;
		fadeDirection = direction;
		fadeSpeed = speed;
	}

	@Override
	public byte getColumn(int x) {
		if (currentValue > transitionValue) {
			return (fadeDirection == FadeDirection.FADE_TO_BLACK)
					? 0 : sourcePattern.getColumn(x);
		} else {
			return (fadeDirection == FadeDirection.FADE_TO_BLACK)
					? sourcePattern.getColumn(x) : 0;
		}
	}

	@Override
	public boolean getLamp(int x, int y) {
		return (getColumn(x) & (1 << y)) != 0;
	}

	@Override
	public int getColCount() {
		return sourcePattern.getColCount();
	}

	@Override
	public void attached() {
		currentValue = 0;
		transitionValue = fadeSpeed;
	}

	@Override
	public void sync() {
		currentValue = (currentValue + 1) % fadeSpeed;
		if (currentValue == 0) {
			transitionValue = Math.min(transitionValue - 1, 0);
		}
	}

	@Override
	public void detached() {
	}

	private LampPattern sourcePattern;
	private FadeDirection fadeDirection;
	private int fadeSpeed;
	private int currentValue;
	private int transitionValue;
}
