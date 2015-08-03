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

	private static final byte patternValues[][] = {
		{1, 3, 7, 15, 31, 63, 127, (byte)255},
		{0, 5, 10, 15, 20, 25, 30, 35},
		{40, 45, 50, 55, 60, 65, 70, 75},
		{80, 85, 90, 95, 100, 105, 110, 115},
		{120, 125, (byte)130, (byte)135, (byte)140, (byte)145, (byte)150, (byte)155},
		{(byte)160, (byte)165, (byte)170, (byte)175, (byte)180, (byte)185, (byte)190, (byte)195},
		{(byte)200, (byte)205, (byte)210, (byte)215, (byte)220, (byte)225, (byte)230, (byte)235},
		{(byte)240, (byte)245, (byte)250, (byte)251, (byte)252, (byte)253, (byte)254, (byte)255},};

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
