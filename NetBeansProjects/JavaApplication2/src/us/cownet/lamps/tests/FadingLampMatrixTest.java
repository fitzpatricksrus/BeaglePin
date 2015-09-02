package us.cownet.lamps.tests;

import us.cownet.lamps.*;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class FadingLampMatrixTest implements Test {
	public FadingLampMatrixTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	private LampMatrix matrix;

	private static final int patternValues1[] = {
		0b10000001,
		0b01000010,
		0b00100100,
		0b00011000,
		0b00001000,
		0b00010100,
		0b00100010,
		0b01000001
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

	private static final SimpleLampPattern pattern = new SimpleLampPattern(patternValues1);

	@Override
	public void setup() {
		matrix.setPattern(pattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

	public static LampMatrix createTestMatrix() {
		PinballOutputController controller = PinballOutputControllerTest.createTestController();
		SimpleLampMatrix lampMatrix = new SimpleLampMatrix(controller, 1);
		PrefetchSimpleLampMatrix prefetchLampMatrix = new PrefetchSimpleLampMatrix(controller, 1);

		return lampMatrix;
	}

	public static void main(String args[]) {
		new FadingLampMatrixTest(createTestMatrix()).execute();
	}
}
