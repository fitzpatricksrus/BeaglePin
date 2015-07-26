package us.cownet.lamps.tests;

public class DutyCycleCalculator {
	public DutyCycleCalculator(int sampleSize, int scale) {
		samples = new int[sampleSize];
		sampleIndex = 0;
		sampleTotal = 0.0;
		lastTick = 0;
		this.scale = scale;
	}

	public DutyCycleCalculator(int sampleSize) {
		samples = new int[sampleSize];
		sampleIndex = 0;
		sampleTotal = 0.0;
		lastTick = 0;
	}

	public void addSample(boolean isOn) {
		nextSample();
		setSample(isOn);
	}

	public void nextSample() {
		sampleIndex = (sampleIndex + 1) % samples.length;
	}

	public void setSample(boolean isOn) {
		sampleTotal = sampleTotal - samples[sampleIndex];
		samples[sampleIndex] = isOn ? 1 : 0;
		sampleTotal = sampleTotal + samples[sampleIndex];
	}

	public double getDutyCycle() {
		double result = sampleTotal / samples.length * scale;
		result *= 1000;
		result = Math.floor(result);
		return result / 10;
	}

	public String toString() {
		String r = "" + getDutyCycle();
		return r.substring(0, Math.min(5, r.length()));
	}

	private int samples[];
	private int sampleIndex;
	private double sampleTotal;
	private long lastTick;
	private int scale;

	public static void main(String[] args) {
		DutyCycleCalculator dcc = new DutyCycleCalculator(10);
		for (int i = 0; i < 10; i++) {
			dcc.addSample(true);
		}
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);
		System.out.println(dcc);
		dcc.addSample(false);

		dcc = new DutyCycleCalculator(10);
		for (int i = 0; i < 10; i++) {
			dcc.addSample(false);
		}
		System.out.println(dcc);

		dcc = new DutyCycleCalculator(10);
		for (int i = 0; i < 5; i++) {
			dcc.addSample(true);
		}
		System.out.println(dcc);
	}
}
