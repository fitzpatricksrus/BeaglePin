package us.cownet.lamps.simple;

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
			currentPattern = lamps;
			TimerUtil.INSTANCE.attachTimerCallback(thisCallback, ticks);
		} else if (currentPattern != null && nextPattern == null) {
			TimerUtil.INSTANCE.detachCallback(thisCallback);
			currentPattern = null;
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
			}
			currentPattern = nextPattern;
		}
	}

	@Override
	public void setSyncCallback(Callback callback) {
		this.callback = callback;
	}
}
