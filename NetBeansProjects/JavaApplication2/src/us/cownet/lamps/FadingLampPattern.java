package us.cownet.lamps;

/*
 Simply take the whole pattern and fade it up or down as a whole.  All lamps have the same
 brightness.  One time deal.  Fade and then sit.
 */
public class FadingLampPattern implements MatrixLampPattern {
	//Here's the basic algorithm.
	//	int fadeSpeed = 10;
	//	for (int flipNdx = 0; flipNdx <= fadeSpeed; flipNdx++) {
	//		for (int cycle = 0; cycle < fadeSpeed; cycle++) {
	//			if (cycle < flipNdx) {
	//				System.out.println("flipNdx: " + flipNdx + " cycle: " + cycle + " on");
	//			} else {
	//				System.out.println("flipNdx: " + flipNdx + " cycle: " + cycle + " off");
	//			}
	//		}
	//	}
	// The time it takes to actually fade is fadeIterations**2.
	public enum FadeDirection {
		FADE_OFF,
		FADE_ON,
	};

	public FadingLampPattern(LampPattern pattern, FadeDirection direction, int fadeSpeed) {
		sourcePattern = pattern;
		this.fadeSpeed = fadeSpeed;
		this.fadeDirection = direction;
		reset();
	}

	public void setPattern(LampPattern pattern, FadeDirection direction, int fadeSpeed) {
		sourcePattern = pattern;
		this.fadeSpeed = fadeSpeed;
		this.fadeDirection = direction;
		reset();
	}

	@Override
	public byte getColumn(int x) {
		//for (flipNdx : fadeSpeed) {
		//  for (cycle : fadeSpeed) {
		//    if (cycle < flipNdx) {
		//      lights are on
		//    } else {
		//      lights are off
		//    }
		//  }
		if (fadeDirection == FadeDirection.FADE_ON) {
			if (cycle < flipNdx) {
				return sourcePattern.getColumn(x);
			} else {
				return 0;
			}
		} else {
			if (cycle < flipNdx) {
				return 0;
			} else {
				return sourcePattern.getColumn(x);
			}
		}
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
		reset();
	}

	@Override
	public void endOfMatrixSync() {
		//for (flipNdx : fadeSpeed) {
		//  for (cycle : fadeSpeed) {
		//    if (cycle < flipNdx) {
		//      lights are on
		//    } else {
		//      lights are off
		//    }
		//  }

		sourcePattern.endOfMatrixSync();
		cycle++;
		if (cycle >= fadeSpeed) {
			flipNdx = Math.min(flipNdx + 1, fadeSpeed + 1);
			cycle = 0;
		}
	}

	@Override
	public void reset() {
		flipNdx = 0;
		cycle = 0;
	}

	@Override
	public void detached() {
		sourcePattern.detached();
	}

	@Override
	public boolean isDone() {
		return flipNdx > fadeSpeed;
	}

	private LampPattern sourcePattern;
	private FadeDirection fadeDirection;
	private int fadeSpeed;
	private int flipNdx;
	private int cycle;

	public static void main(String args[]) {
		/*
		 int fadeSpeed = 10;
		 for (int flipNdx = 0; flipNdx <= fadeSpeed + 2; flipNdx++) {
		 for (int cycle = 0; cycle < fadeSpeed; cycle++) {
		 if (cycle < flipNdx) {
		 System.out.println("flipNdx: " + flipNdx + " cycle: " + cycle + " on");
		 } else {
		 System.out.println("flipNdx: " + flipNdx + " cycle: " + cycle + " off");
		 }
		 }
		 }
		 */

		int patternData[] = {0b11111111};
		SimpleMatrixLampPattern simplePattern = new SimpleMatrixLampPattern(patternData);
		FadingLampPattern fadingPattern = new FadingLampPattern(simplePattern,
				FadeDirection.FADE_ON, 4);

		fadingPattern.attached();
		for (int i = 0; i < 4 * 7; i++) {
			byte col = fadingPattern.getColumn(0);
			System.out.println("cycle:" + i + "  col:" + col);
			fadingPattern.endOfMatrixSync();
		}

		System.out.println();
		fadingPattern = new FadingLampPattern(simplePattern,
				FadeDirection.FADE_OFF, 4);
		for (int i = 0; i < 4 * 7; i++) {
			byte col = fadingPattern.getColumn(0);
			System.out.println("cycle:" + i + "  col:" + col);
			fadingPattern.endOfMatrixSync();
		}
	}

}
