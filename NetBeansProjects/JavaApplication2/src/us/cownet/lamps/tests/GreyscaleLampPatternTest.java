package us.cownet.lamps.tests;

import us.cownet.lamps.AbstractGreyscaleLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class GreyscaleLampPatternTest implements Test {
	public GreyscaleLampPatternTest(LampMatrix greyMatrix) {
		this.greyLampMatrix = greyMatrix;
	}

	private final LampMatrix greyLampMatrix;

	private static int patternValues[][] = {
		{0, 1, 3, 7, 15, 31, 63, 127},
		{0, 5, 10, 15, 20, 25, 30, 35},
		{40, 45, 50, 55, 60, 65, 70, 75},
		{80, 85, 90, 95, 100, 105, 110, 115},
		{120, 125, 130, 135, 140, 145, 150, 155},
		{160, 165, 170, 175, 180, 185, 190, 195},
		{200, 205, 210, 215, 220, 225, 230, 235},
		{240, 245, 250, 251, 252, 253, 254, 255}
	};

	@Override
	public void setup() {
		int pos = 0;
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				patternValues[col][row] = 4 * pos++;
			}
		}

//		AbstractGreyscaleLampPattern pattern = new AbstractGreyscaleLampPattern.Pattern(patternValues);
		AbstractGreyscaleLampPattern pattern = new AbstractGreyscaleLampPattern.Pattern2();
		greyLampMatrix.setPattern(pattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

	public static void main(String args[]) {
		new GreyscaleLampPatternTest(LampMatrixTest.createTestMatrix()).execute();
	}
}
