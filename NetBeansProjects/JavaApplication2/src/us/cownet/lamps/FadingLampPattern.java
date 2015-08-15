package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.  One time deal.  Fade and then sit.
 */
public class FadingLampPattern extends ContainerLampPattern {
	/*

	 Here's the basic algorithm.

	 for (flipNdx : fadeSpeed) {
	 for (cycle : fadeSpeed) {
	 if (cycle >= flipNdx) {
	 lights are on
	 } else {
	 lights are off
	 }
	 }
	 }

	 The time it takes to actually fade is fadeIterations**2.

	 Each PWM cycle is pwmSteps long and there are pwmSteps cycles before things complete.
	 */
	public enum FadeDirection {
		FADE_OFF,
		FADE_ON,
		BOUNCE
	};

	public FadingLampPattern(LampPattern pattern, FadeDirection direction, int pwmSteps) {
		super(pattern);
		reset();
	}

	public void setPattern(LampPattern pattern, FadeDirection direction, int pwmSteps) {
		super.setLampPattern(pattern);
		reset();
	}

	@Override
	public byte getColumn(int x) {
		if (cycle >= flipNdx) {
			return super.getColumn(x);
		} else {
			return 0;
		}
	}

	@Override
	public int getColCount() {
		return super.getColCount();
	}

	@Override
	public void attached() {
		super.attached();
		reset();
	}

	@Override
	public void endOfMatrixSync() {
		if (cycle >= flipNdx) {
			// end of this cycle
			if (flipNdx < fadeSpeed) {
				// not the end of the fade yet
				cycle = 0;
				flipNdx = flipNdx + 1;
			}
		}
	}

	public void reset() {
		flipNdx = 0;
		cycle = 0;
	}

	public boolean isDone() {
		return flipNdx >= fadeSpeed;
	}

	private FadeDirection fadeDirection;
	private int fadeSpeed;
	private int flipNdx;
	private int cycle;

	public static void main(String args[]) {

	}
}
