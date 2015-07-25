package us.cownet.lamps.tests;

import us.cownet.timers.TimerUtil;

public class DutyCycleCalculator {
	public DutyCycleCalculator(int sampleSize) {
		samples = new int[sampleSize];
		sampleIndex = 0;
		sampleTotal = 0.0;
		lastTick = 0;
	}

	public double addSample(boolean isOn) {
		long newTick = TimerUtil.INSTANCE.currentTicks();
		if (lastTick != newTick) {
			sampleIndex = (sampleIndex + 1) % samples.length;
			lastTick = newTick;
		}
		sampleTotal = sampleTotal - samples[sampleIndex];
		samples[sampleIndex] = isOn ? 1 : 0;
		sampleTotal = sampleTotal + samples[sampleIndex];
		return getDutyCycle();
	}

	public double getDutyCycle() {
		return sampleTotal / samples.length;
	}

	public String toString() {
		return "" + getDutyCycle();
	}

	private int samples[];
	private int sampleIndex;
	private double sampleTotal;
	private long lastTick;

	public static void main(String[] args) {
		DutyCycleCalculator dcc = new DutyCycleCalculator(10);
		for (int i = 0; i < 10; i++) {
			System.out.println(dcc);
			dcc.addSample(true);
		}
		for (int i = 0; i < 20; i++) {
			System.out.println(dcc);
			if (i % 3 != 0) {
				dcc.addSample(false);
			} else {
				dcc.addSample(true);
			}
		}
		System.out.println(dcc);
	}
}
