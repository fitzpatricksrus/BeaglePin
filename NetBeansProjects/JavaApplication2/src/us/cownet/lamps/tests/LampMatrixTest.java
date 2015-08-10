package us.cownet.lamps.tests;

import us.cownet.lamps.LampMatrix;
import us.cownet.lamps.SimpleLampPattern;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class LampMatrixTest implements Test {
	public LampMatrixTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	private LampMatrix matrix;

	private static final int patternValues[] = {
		0b10101010,
		0b01010101
	};
	private static final int patternValues2[] = {
		0b10000001,
		0b01000010,
		0b00100100,
		0b00011000,
		0b00001000,
		0b00010100,
		0b00100010,
		0b01000001
	};

	private static final SimpleLampPattern pattern = new SimpleLampPattern(patternValues);

	@Override
	public void setup() {
		matrix.setPattern(pattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}
}
