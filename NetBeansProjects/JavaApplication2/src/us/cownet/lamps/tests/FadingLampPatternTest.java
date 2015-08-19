package us.cownet.lamps.tests;

import us.cownet.lamps.FadingLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.lamps.SimpleLampPattern;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class FadingLampPatternTest implements Test {
	public FadingLampPatternTest(LampMatrix matrix) {
		this.matrix = matrix;
		sourcePattern = new SimpleLampPattern(patternValues);
		fadingPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_ON, 200);
	}

	@Override
	public void setup() {
		matrix.setPattern(fadingPattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

	private static final int patternValues[] = {
		0b10000001,
		0b01000010,
		0b00100100,
		0b00011000,
		0b00001000,
		0b00010100,
		0b00100010,
		0b01000001
	};

	private SimpleLampPattern sourcePattern;
	private FadingLampPattern fadingPattern;
	private final LampMatrix matrix;

}
