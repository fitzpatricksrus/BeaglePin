package us.cownet.lamps.tests;

import us.cownet.lamps.FadingLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.lamps.LampSequence;
import us.cownet.lamps.SimpleLampPattern;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class LampSequenceTest implements Test {

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

	private static final int patternValues1[] = {
		0b01111110,
		0b10111101,
		0b11011011,
		0b11100111,
		0b11110111,
		0b11101011,
		0b11011101,
		0b10111110,};

	private LampMatrix matrix;

	public LampSequenceTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	@Override
	public void setup() {
		SimpleLampPattern sourcePattern = new SimpleLampPattern(patternValues);
		FadingLampPattern fadeOnPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_ON, 255);
		FadingLampPattern fadeOffPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_OFF, 255);

		LampSequence innerSequence = new LampSequence(LampSequence.CycleType.ONETIME);
		innerSequence.add(fadeOnPattern);
		innerSequence.add(fadeOffPattern);

		SimpleLampPattern sourcePattern1 = new SimpleLampPattern(patternValues1);
		FadingLampPattern fadeOnPattern1 = new FadingLampPattern(sourcePattern1,
				FadingLampPattern.FadeDirection.FADE_ON, 255);
		FadingLampPattern fadeOffPattern1 = new FadingLampPattern(sourcePattern1,
				FadingLampPattern.FadeDirection.FADE_OFF, 255);

		LampSequence outerSequence = new LampSequence(LampSequence.CycleType.ONETIME);
		outerSequence.add(fadeOnPattern1);
		outerSequence.add(fadeOffPattern1);

		LampSequence mainSequence = new LampSequence(LampSequence.CycleType.REPEATING);
		mainSequence.add(innerSequence);
		mainSequence.add(outerSequence);

		matrix.setPattern(mainSequence);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

}
