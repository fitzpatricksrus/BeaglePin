package us.cownet.timers;

public class TimerUtil {
	public boolean attachInterrupt(Callback callback, long microseconds) {
		this.callback = callback;
		ticker = new Ticker(microseconds);
		return true;
	}

	public void detachInterrupt(Callback callback) {
		callback = null;
		ticker = null;
	}

	public void hackTick() {
		if (hackMicros != REAL_TICKS) {
			hackMicros++;
		}
		if (callback != null && ticker.isTime()) {
			callback.call();
		}
	}

	public void hackTime(long timeMicros) {
		hackMicros = timeMicros;
	}

	public long currentTimeMillis() {
		return currentTimeMicros() * 1000;
	}

	public long currentTimeMicros() {
		if (hackMicros != REAL_TICKS) {
			return hackMicros;
		} else {
			return System.nanoTime() * 1000;
		}
	}

	public static TimerUtil INSTANCE = new TimerUtil();
	public static final long REAL_TICKS = -1;

	private Callback callback;
	private Ticker ticker;
	private long hackMicros = REAL_TICKS;
}
