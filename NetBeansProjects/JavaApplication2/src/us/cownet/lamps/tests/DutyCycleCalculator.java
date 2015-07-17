package us.cownet.lamps.tests;

public class DutyCycleCalculator {
	public DutyCycleCalculator(int sampleSize, int maxValue) {
		samples = new boolean[sampleSize];
		sampleIndex = 0;
		sampleTotal = 0.0;
	}

	public double addSample(boolean isOn) {
		sampleTotal = sampleTotal - (samples[sampleIndex] ? 0 : 1);
		samples[sampleIndex] = isOn;
		sampleTotal = sampleTotal + (samples[sampleIndex] ? 0 : 1);
		sampleIndex = (sampleIndex + 1) % samples.length;
		return getDutyCycle();
	}

	public double getDutyCycle() {
		return sampleTotal / samples.length;
	}

	private boolean samples[];
	private int sampleIndex;
	private double sampleTotal;
}
