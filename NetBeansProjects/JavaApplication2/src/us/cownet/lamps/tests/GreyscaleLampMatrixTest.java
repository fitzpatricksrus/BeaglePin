package us.cownet.lamps.tests;

import us.cownet.lamps.GreyscaleLampMatrix;
import us.cownet.lamps.GreyscaleLampPattern;
import us.cownet.lamps.simple.SimpleGreyscaleLampPattern;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class GreyscaleLampMatrixTest implements Test {
	public GreyscaleLampMatrixTest(GreyscaleLampMatrix greyMatrix) {
		this.greyLampMatrix = greyMatrix;
	}

	private GreyscaleLampMatrix greyLampMatrix;

	private static final byte patternValues[][] = new byte[8][8]; /*{
	 {1, 3, 7, 15, 31, 63, 127, (byte)255},
	 {0, 10, 20, 30, 45, 60, 70, 80},
	 {30, 40, 50, 60, 70, 80, 90, 100},
	 {50, 60, 70, 80, 90, 100, 110, 120},
	 {70, 80, 90, 100, 110, 120, (byte)130, (byte)140},
	 {90, 100, 110, 120, (byte)130, (byte)140, (byte)150, (byte)160},
	 {110, 120, (byte)130, (byte)140, (byte)150, (byte)160, (byte)180, (byte)190},
	 {(byte)130, (byte)140, (byte)150, (byte)160, (byte)180, (byte)190, (byte)200, (byte)210},}; */


	static {
		int start = 255 - 64 * 1;
		for (int row = 0; row < patternValues[0].length; row++) {
			for (int col = 0; col < patternValues.length; col++) {
				patternValues[col][row] = (byte)start--;
			}
		}
	}

	private static final GreyscaleLampPattern pattern = new SimpleGreyscaleLampPattern(patternValues);

	@Override
	public void setup() {
		greyLampMatrix.setPattern(pattern);
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

}
