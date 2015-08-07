package us.cownet.lamps.tests;

import us.cownet.lamps.GreyscaleLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class GreyscaleLampMatrixTest implements Test {
	public GreyscaleLampMatrixTest(LampMatrix greyMatrix) {
		this.greyLampMatrix = greyMatrix;
	}

	private LampMatrix greyLampMatrix;

	private static final int patternValues[][] = {
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},
		{127, 127, 127, 127, 127, 127, 127, 127},};
	private static final int patternValues2[][] = {
		{0, 1, 3, 7, 15, 31, 63, 127},
		{0, 5, 10, 15, 20, 25, 30, 35},
		{40, 45, 50, 55, 60, 65, 70, 75},
		{80, 85, 90, 95, 100, 105, 110, 115},
		{120, 125, 130, 135, 140, 145, 150, 155},
		{160, 165, 170, 175, 180, 185, 190, 195},
		{200, 205, 210, 215, 220, 225, 230, 235},
		{240, 245, 250, 251, 252, 253, 254, 255}
	};

	private static final GreyscaleLampPattern pattern = new GreyscaleLampPattern(patternValues);

	@Override
	public void setup() {
		greyLampMatrix.setPattern(pattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

}
