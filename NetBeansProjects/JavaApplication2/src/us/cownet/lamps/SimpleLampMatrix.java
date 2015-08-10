package us.cownet.lamps;

import us.cownet.lamps.LampMatrix;
import us.cownet.lamps.LampPattern;
import us.cownet.lamps.PinballOutputController;
import us.cownet.timers.Callback;
import us.cownet.timers.TimerUtil;

public class SimpleLampMatrix implements LampMatrix {

	private final PinballOutputController controller;
	private final long ticks;
	private int currentColumn;
	private LampPattern currentPattern;
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
		nextPattern = lamps;
		if (currentPattern == null && nextPattern != null) {
			// nothing was running and this is the first item
			currentPattern = lamps;
			currentPattern.attached();
			TimerUtil.INSTANCE.attachTimerCallback(thisCallback, ticks);
		} else if (currentPattern != null && nextPattern == null) {
			// something is running, but we're turning it all off
			TimerUtil.INSTANCE.detachCallback(thisCallback);
			currentPattern.detached();
			currentPattern = null;
		} else {
			// transitioning from one pattern to another is done in tock()
			// do the transition is done cleanly
		}
	}

	private void tock() {
		controller.write(PinballOutputController.Register.LAMP_COL, (byte)0);
		controller.write(PinballOutputController.Register.LAMP_ROW, currentPattern.getColumn(currentColumn));
		controller.write(PinballOutputController.Register.LAMP_COL, (byte)(1 << currentColumn));
		currentColumn = (currentColumn + 1) % currentPattern.getColCount();
		if (currentColumn == 0) {
			if (callback != null) {
				callback.call();
				currentPattern.endOfMatrixSync();
			}
			if (currentPattern != nextPattern) {
				// transition from one pattern to next on sync
				currentPattern.detached();
				currentPattern = nextPattern;
				currentPattern.attached();
			}
		}
	}

	@Override
	public void setSyncCallback(Callback callback) {
		this.callback = callback;
	}
}
