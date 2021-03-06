package us.cownet.lamps.tests;

import us.cownet.lamps.FadingLampMatrix;
import us.cownet.lamps.LampMatrix;
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.SimpleMatrixLampPattern;
import us.cownet.testing.Test;
import us.cownet.timers.Ticker;
import us.cownet.timers.TimerUtil;

public class FadingLampMatrixTest implements Test {
	public FadingLampMatrixTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	private final LampMatrix matrix;

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
		0b01111110,
		0b10111101,
		0b11011011,
		0b11100111,
		0b11110111,
		0b11101011,
		0b11011101,
		0b10111110
	};

	private static final SimpleMatrixLampPattern pattern1 = new SimpleMatrixLampPattern(patternValues1);
	private static final SimpleMatrixLampPattern pattern2 = new SimpleMatrixLampPattern(patternValues2);
	private final Ticker ticker = new Ticker(200L * 1000L);

	@Override
	public void setup() {
		matrix.setPattern(pattern1);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
		if (ticker.isTime()) {
			if (matrix.getPattern() == pattern1) {
				matrix.setPattern(pattern2);
			} else {
				matrix.setPattern(pattern1);
			}
		}
	}

	public static LampMatrix createTestMatrix() {
		PinballOutputController controller = PinballOutputControllerTest.createTestController();
		FadingLampMatrix prefetchLampMatrix = new FadingLampMatrix(controller, 0, 127);

		return prefetchLampMatrix;
	}

	public static void main(String args[]) {
		new FadingLampMatrixTest(createTestMatrix()).execute(64);
	}
}
