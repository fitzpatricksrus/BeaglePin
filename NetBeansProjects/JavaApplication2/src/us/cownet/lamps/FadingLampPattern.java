package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.  One time deal.  Fade and then sit.
 */
public class FadingLampPattern extends ContainerLampPattern {
	//Here's the basic algorithm.
	//for (flipNdx : fadeSpeed) {
	//  for (cycle : fadeSpeed) {
	//    if (cycle >= flipNdx) {
	//      lights are on
	//    } else {
	//      lights are off
	//    }
	//  }
	// The time it takes to actually fade is fadeIterations**2.
	public enum FadeDirection {
		FADE_OFF,
		FADE_ON,
		BOUNCE
	};

	public FadingLampPattern(LampPattern pattern, FadeDirection direction, int fadeSpeed) {
		super(pattern);
		this.fadeSpeed = fadeSpeed;
		reset();
	}

	public void setPattern(LampPattern pattern, FadeDirection direction, int fadeSpeed) {
		super.setLampPattern(pattern);
		this.fadeSpeed = fadeSpeed;
		reset();
	}

	@Override
	public byte getColumn(int x) {
		if (cycle >= flipNdx) {
			byte col = super.getColumn(x);
			return col;
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
		//for (flipNdx : fadeSpeed) {
		//  for (cycle : fadeSpeed) {
		//    if (cycle >= flipNdx) {
		//      lights are on
		//    } else {
		//      lights are off
		//    }
		//  }

		super.endOfMatrixSync();
		if (flipNdx < fadeSpeed) {
			if (cycle < flipNdx) {
				cycle++;
			} else {
				flipNdx++;
				cycle = 0;
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
}
