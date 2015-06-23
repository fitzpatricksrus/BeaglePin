package us.cownet.timers;

import java.util.HashMap;

public class TimerUtil {
	public void attachToTimerIntterupt(Callback callback, long micros) {
		TimerCallback c = timerHandlers.get(micros);
		if (c == null) {
			c = new TimerCallback(micros);
			timerHandlers.put(micros, c);
		}
		attachToInterrupt(callback, c);
	}

	public void attachToTickerIntterupt(Callback callback, long ticks) {
		TickerCallback c = tickerHandlers.get(new Long(ticks));
		if (c == null) {
			c = new TickerCallback(new Long(ticks));
			tickerHandlers.put(ticks, c);
		}
		attachToInterrupt(callback, c);
	}

	private void attachToInterrupt(Callback callback, CallbackHandler handler) {
		callbacks.put(callback, handler);
		handler.addCallback(callback);
	}

	public void detachInterrupt(Callback callback) {
		CallbackHandler handler = callbacks.get(callback);
		handler.removeCallback(callback);
		callbacks.remove(callback);
		// hey jf - it would be nice if we could remove empty handlers from the maps also
	}

	public void hackTick() {
		ticks++;
		for (long l : tickerHandlers.keySet()) {
			tickerHandlers.get(l).invokeCallbacks();
		}
		for (long l : timerHandlers.keySet()) {
			timerHandlers.get(l).invokeCallbacks();
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
		timerHandlers = new HashMap<Long, TimerCallback>();
		tickerHandlers = new HashMap<Long, TickerCallback>();
		callbacks = new HashMap<Callback, CallbackHandler>();
		ticks = 0;
		useHackTicks = false;
	}

	private HashMap<Long, TimerCallback> timerHandlers;
	private HashMap<Long, TickerCallback> tickerHandlers;
	private HashMap<Callback, CallbackHandler> callbacks;
	private long ticks;
	private boolean useHackTicks;
}
