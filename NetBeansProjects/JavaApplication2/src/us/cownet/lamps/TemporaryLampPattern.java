package us.cownet.lamps;

import us.cownet.testing.Test;

/*
 A one shot deal.  Start either on or off, stay that way for some duration, and
 then change state.  Done.  Only the lamps that are in in the source are affected.
 It's just delay and invert.
 */
public class TemporaryLampPattern extends ContainerLampPattern {
	public TemporaryLampPattern(LampPattern sourcePattern, int duration, boolean startOn) {
		super(sourcePattern);
		this.duration = duration;
		this.startOn = startOn;
	}

	public void reset() {
		position = 0;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean getInvert() {
		return startOn;
	}

	public void setInvert(boolean invert) {
		this.startOn = invert;
	}

	@Override
	public byte getColumn(int x) {
		if (position < duration) {
			return startOn ? sourcePattern.getColumn(x) : 0;
		} else {
			// we're done with the cycle
			return startOn ? 0 : sourcePattern.getColumn(x);
		}
	}

	@Override
	public int getColCount() {
		return sourcePattern.getColCount();
	}

	public void attached() {
		position = 0;
	}

	public void endOfMatrixSync() {
		super.endOfMatrixSync();
		position = Math.min(duration, position + 1);
	}

	public boolean isDone() {
		return position >= duration;
	}

	private int duration;
	private int position;
	private boolean startOn;

	// ------------- testing
	private static void testIteration(TemporaryLampPattern tlp, boolean onInitially, String testName) {
		int expectedInitialValue = (onInitially) ? -1 : 0;
		int expectedFinalValue = (onInitially) ? 0 : -1;

		Test.assertTrue(tlp.getColumn(0) == expectedInitialValue, testName + ">01");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">02");
		Test.assertTrue(tlp.isDone() == false, testName + ">03");

		tlp.endOfMatrixSync();
		Test.assertTrue(tlp.getColumn(0) == expectedInitialValue, testName + ">04");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">05");
		Test.assertTrue(tlp.isDone() == false, testName + ">06");

		tlp.endOfMatrixSync();
		Test.assertTrue(tlp.getColumn(0) == expectedFinalValue, testName + ">07");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">08");
		Test.assertTrue(tlp.isDone() == true, testName + ">09");

		tlp.endOfMatrixSync();
		Test.assertTrue(tlp.getColumn(0) == expectedFinalValue, testName + ">10");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">11");
		Test.assertTrue(tlp.isDone() == true, testName + ">12");

		tlp.endOfMatrixSync();
		Test.assertTrue(tlp.getColumn(0) == expectedFinalValue, testName + ">13");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">14");
		Test.assertTrue(tlp.isDone() == true, testName + ">15");

		tlp.endOfMatrixSync();
		Test.assertTrue(tlp.getColumn(0) == expectedFinalValue, testName + ">16");
		Test.assertTrue(tlp.getColumn(1) == 0, testName + ">17");
		Test.assertTrue(tlp.isDone() == true, testName + ">18");
	}

	public static void main(String args[]) {
		int lampData[] = {-1, 0};
		SimpleLampPattern pattern = new SimpleLampPattern(lampData);

		int testDuration = 2;

		TemporaryLampPattern tlp = new TemporaryLampPattern(pattern, testDuration, false);
		testIteration(tlp, false, "initially off");
		tlp = new TemporaryLampPattern(pattern, testDuration, true);
		testIteration(tlp, true, "initially on");

	}
}
