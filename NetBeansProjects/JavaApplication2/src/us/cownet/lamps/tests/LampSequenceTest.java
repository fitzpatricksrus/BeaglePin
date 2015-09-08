package us.cownet.lamps.tests;

import us.cownet.lamps.*;
import us.cownet.testing.Test;
import us.cownet.timers.TimerUtil;

public class LampSequenceTest implements Test {

	private LampMatrix matrix;

	public LampSequenceTest(LampMatrix matrix) {
		this.matrix = matrix;
	}

	private LampPattern createOnOffPattern(int patternSource[]) {
		final int speed = 15;
		SimpleLampPattern sourcePattern = new SimpleLampPattern(patternSource);
		FadingLampPattern fadeOnPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_ON, speed);
		FadingLampPattern fadeOffPattern = new FadingLampPattern(sourcePattern,
				FadingLampPattern.FadeDirection.FADE_OFF, speed);
		LampSequence mainSequence = new LampSequence(LampSequence.CycleType.ONETIME_RESETABLE);
		mainSequence.add(fadeOnPattern);
		mainSequence.add(fadeOffPattern);
		return mainSequence;
	}

	private void setup1() {
		LampSequence mainSequence = new LampSequence(LampSequence.CycleType.REPEATING);
		mainSequence.add(createOnOffPattern(new int[]{
			0b10000001,
			0b01000010,
			0b00100100,
			0b00011000,
			0b00001000,
			0b00010100,
			0b00100010,
			0b01000001
		}));
		mainSequence.add(createOnOffPattern(new int[]{
			0b01111110,
			0b10111101,
			0b11011011,
			0b11100111,
			0b11110111,
			0b11101011,
			0b11011101,
			0b10111110,}));

		matrix.setPattern(mainSequence);
	}

	private void setup2() {
		LampPattern p1 = createOnOffPattern(new int[]{
			0b11111111,
			0b10000001,
			0b10000001,
			0b10000001,
			0b10000001,
			0b10000001,
			0b10000001,
			0b11111111,});

		LampPattern p2 = createOnOffPattern(new int[]{
			0b00000000,
			0b01111110,
			0b01000010,
			0b01000010,
			0b01000010,
			0b01000010,
			0b01111110,
			0b00000000,});

		LampPattern p3 = createOnOffPattern(new int[]{
			0b00000000,
			0b00000000,
			0b00111100,
			0b00100100,
			0b00100100,
			0b00111100,
			0b00000000,
			0b00000000,});

		LampPattern p4 = createOnOffPattern(new int[]{
			0b00000000,
			0b00000000,
			0b00000000,
			0b00011000,
			0b00011000,
			0b00000000,
			0b00000000,
			0b00000000,});

		LampPattern p5 = createOnOffPattern(new int[]{
			0b00000000,
			0b00000000,
			0b00000000,
			0b00010000,
			0b00001000,
			0b00000000,
			0b00000000,
			0b00000000,});

		LampPattern p6 = createOnOffPattern(new int[]{
			0b00000000,
			0b00000000,
			0b00000000,
			0b00001000,
			0b00010000,
			0b00000000,
			0b00000000,
			0b00000000,});

		LampSequence mainSequence = new LampSequence(LampSequence.CycleType.CYLON);
		mainSequence.add(p1);
		mainSequence.add(p2);
		mainSequence.add(p3);
		mainSequence.add(p4);
		mainSequence.add(p5);
		mainSequence.add(p6);

		matrix.setPattern(mainSequence);
	}

	@Override
	public void setup() {
		setup2();
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
	}

	public static void main(String args[]) {
		new LampSequenceTest(LampMatrixTest.createTestMatrix()).execute(256);
	}
}
