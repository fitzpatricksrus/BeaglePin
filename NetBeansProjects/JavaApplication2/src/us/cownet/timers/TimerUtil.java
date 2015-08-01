package us.cownet.timers;

import java.util.HashMap;

public class TimerUtil {
	public void attachTickerCallback(Callback c, long ticks) {
		attachCallback(c, new Ticker(ticks));
	}

	public void attachTimerCallback(Callback c, long micros) {
		attachCallback(c, new Timer(micros));
	}

	private void attachCallback(Callback c, PeriodicEvent p) {
		callbackList.put(c, new CallbackHandler(c, p));
	}

	public void detachCallback(Callback c) {
		callbackList.put(c, null);
	}

	public void tick() {
		ticks++;
		for (CallbackHandler handler : callbackList.values()) {
			handler.tick();
		}
	}

	public void enableHackTicks(boolean useHacks) {
		useHackTicks = useHacks;
	}

	public long currentTimeMillis() {
		return currentTimeMicros() / 1000;
	}

	public long currentTimeMicros() {
		if (useHackTicks) {
			return currentTicks();
		} else {
			return System.nanoTime() / 1000;
		}
	}

	public long currentTicks() {
		return ticks;
	}

	public static TimerUtil INSTANCE = new TimerUtil();
	public static final long REAL_TICKS = -1;

	private TimerUtil() {
		callbackList = new HashMap<>();
		ticks = 0;
		useHackTicks = false;
	}

	private class CallbackHandler {
		public CallbackHandler(Callback c, PeriodicEvent e) {
			this.c = c;
			this.e = e;
		}

		public void tick() {
			if (e.isTime()) {
				c.call();
			}
		}

		public Callback c;
		public PeriodicEvent e;
	}

	private HashMap<Callback, CallbackHandler> callbackList;
	private long ticks;
	private boolean useHackTicks;
}
