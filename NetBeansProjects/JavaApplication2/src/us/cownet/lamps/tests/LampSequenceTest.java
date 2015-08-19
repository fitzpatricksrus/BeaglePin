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

	private LampMatrix matrix;

	public LampSequenceTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	@Override
	public void setup() {
		SimpleLampPattern sourcePattern = new SimpleLampPattern(patternValues);
		FadingLampPattern fadeOnPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_ON, 200);
		FadingLampPattern fadeOffPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_OFF, 200);

		LampSequence sequence = new LampSequence(LampSequence.CycleType.REVERSING);
//		sequence.add(fadeOffPattern);
		sequence.add(fadeOnPattern);
		matrix.setPattern(sequence);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

}
