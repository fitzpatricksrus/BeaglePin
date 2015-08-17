package us.cownet.lamps;

import us.cownet.timers.Callback;
import us.cownet.timers.TimerUtil;

public class SimpleLampMatrix extends LampPatternContainer implements LampMatrix {

	private final PinballOutputController controller;
	private final long ticks;
	private int currentColumn;
	private LampPattern nextPattern;
	private Callback callback = null;
	private Callback thisCallback = null;

	public SimpleLampMatrix(PinballOutputController controller, long ticks) {
		this.controller = controller;
		this.ticks = ticks;
		this.currentColumn = 0;
		this.currentPattern = null;
		this.nextPattern = null;
		this.thisCallback = () -> tock();
	}

	public LampPattern getDisplayedPattern() {
		return currentPattern;
	}

	@Override
	public LampPattern getPattern() {
		return nextPattern;
	}

	@Override
	public void setPattern(LampPattern lamps) {
		if (currentPattern == null && lamps != null) {
			// kick start the first refresh.
			currentPattern = lamps;
			nextPattern = lamps;
			TimerUtil.INSTANCE.attachTickerCallback(thisCallback, ticks);
		} else {
			// transitioning from one pattern to another is done in tock()
			// tock() also terminates refresh if we're setting to null.
			nextPattern = lamps;
		}
	}

	private void tock() {
		controller.write(PinballOutputController.Register.LAMP_COL, (byte)0);
		controller.write(PinballOutputController.Register.LAMP_ROW, currentPattern.getColumn(currentColumn));
		controller.write(PinballOutputController.Register.LAMP_COL, (byte)(1 << currentColumn));
		currentPattern.endOfColumnSync();
		currentColumn = (currentColumn + 1) % currentPattern.getColCount();
		if (currentColumn == 0) {
			// we've finished refressing the matrix one complete cycle.
			if (callback != null) {
				callback.call();
			}
			currentPattern.endOfMatrixSync();
			// transition from one pattern to next on sync
			super.setPattern(nextPattern);
			if (currentPattern == null) {	//note currentPattern == nextPattern here
				TimerUtil.INSTANCE.detachCallback(thisCallback);
			}
		}
	}

	@Override
	public void setSyncCallback(Callback callback) {
		this.callback = callback;
	}
}
