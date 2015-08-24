package us.cownet.lamps;

import us.cownet.timers.Callback;
import us.cownet.timers.TimerUtil;

/*
 Simple lamp matrix that displays a cached column value and then fetches the new
 value from the pattern.  This in theory will allow it to display the next column
 quickly and hide the time to calculate the next column in the space after when
 the refresh has already taken place.
 */
public class PrefetchSimpleLampMatrix implements LampMatrix {

	private final PinballOutputController controller;
	private final long ticks;
	private int currentColumn;
	private LampPattern nextPattern;
	private Callback syncCallback = null;
	private Callback thisCallback = null;
	private LampPattern attachedPattern;
	private byte prefetchedColumnValue;

	public PrefetchSimpleLampMatrix(PinballOutputController controller, long ticks) {
		this.controller = controller;
		this.ticks = ticks;
		this.currentColumn = 0;
		this.nextPattern = null;
		this.attachedPattern = null;
		this.thisCallback = () -> tock();
	}

	public LampPattern getDisplayedPattern() {
		return attachedPattern;
	}

	@Override
	public LampPattern getPattern() {
		return nextPattern;
	}

	@Override
	public void setPattern(LampPattern lamps) {
		if (attachedPattern == null && lamps != null) {
			// kick start the first refresh.
			internalSetPattern(lamps);
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
		controller.write(PinballOutputController.Register.LAMP_ROW, prefetchedColumnValue);
		controller.write(PinballOutputController.Register.LAMP_COL, (byte)(1 << currentColumn));
		currentColumn = (currentColumn + 1) % attachedPattern.getColCount();
		if (currentColumn == 0) {
			// we've finished refressing the matrix one complete cycle.
			if (syncCallback != null) {
				syncCallback.call();
			}
			attachedPattern.endOfMatrixSync();
			// transition from one pattern to next on sync
			internalSetPattern(nextPattern);
			if (attachedPattern == null) {	//note currentPattern == nextPattern here
				TimerUtil.INSTANCE.detachCallback(thisCallback);
			}
		}
		prefetchedColumnValue = attachedPattern.getColumn(currentColumn);
	}

	@Override
	public void setSyncCallback(Callback callback) {
		this.syncCallback = callback;
	}

	private void internalSetPattern(LampPattern newPattern) {
		if (attachedPattern != newPattern) {
			if (attachedPattern != null) {
				attachedPattern.detached();
			}
			attachedPattern = newPattern;
			if (attachedPattern != null) {
				attachedPattern.attached();
				prefetchedColumnValue = attachedPattern.getColumn(currentColumn);
			}
		}
	}

}
