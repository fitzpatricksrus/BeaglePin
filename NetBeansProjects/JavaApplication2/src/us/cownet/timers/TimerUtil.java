package us.cownet.timers;

import java.util.ArrayList;
import java.util.HashMap;

public class TimerUtil {
	public void attachTickerCallback(Callback c, long ticks) {
		Ticker ticker = tickers.get(ticks);
		if (ticker == null) {
			ticker = new Ticker(ticks);
			tickers.put(ticks, ticker);
			tickerCallbackList.put(ticker, new CallbackHandler());
		}
		CallbackHandler handler = tickerCallbackList.get(ticker);
		handler.addCallback(c);
	}

	public void detachTickerCallback(Callback c) {
		for (CallbackHandler handler : tickerCallbackList.values()) {
			handler.removeCallback(c);
			if (handler.isEmpty()) {
				// hey jf - it might be nice to do garbage collection of empty lists here.
			}
		}

	}

	public void attachTimerCallback(Callback c, long micros) {
		Timer timer = timers.get(micros);
		if (timer == null) {
			timer = new Timer(micros);
			timers.put(micros, timer);
			timerCallbackList.put(timer, new CallbackHandler());
		}
		CallbackHandler handler = timerCallbackList.get(timer);
		handler.addCallback(c);
	}

	public void detachTimerCallback(Callback c) {
		for (CallbackHandler handler : timerCallbackList.values()) {
			handler.removeCallback(c);
			if (handler.isEmpty()) {
				// hey jf - it might be nice to do garbage collection of empty lists here.
			}
		}
	}

	public void tick() {
		ticks++;
		for (Ticker ticker : tickerCallbackList.keySet()) {
			if (ticker.isTime()) {
				tickerCallbackList.get(ticker).invokeCallbacks();
			}
		}
		for (Timer timer : timerCallbackList.keySet()) {
			if (timer.isTime()) {
				timerCallbackList.get(timer).invokeCallbacks();
			}
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
		tickers = new HashMap<>();
		timers = new HashMap<>();
		tickerCallbackList = new HashMap<>();
		timerCallbackList = new HashMap<>();
		ticks = 0;
		useHackTicks = false;
	}

	private class CallbackHandler {
		public CallbackHandler() {
			callbacks = new ArrayList<>();
		}

		public void invokeCallbacks() {
			for (Callback c : callbacks) {
				c.call();
			}
		}

		public void addCallback(Callback c) {
			callbacks.add(c);
		}

		public void removeCallback(Callback c) {
			callbacks.remove(c);
		}

		public boolean isEmpty() {
			return callbacks.isEmpty();
		}

		private final ArrayList<Callback> callbacks;
	}

	private final HashMap<Ticker, CallbackHandler> tickerCallbackList;
	private final HashMap<Long, Ticker> tickers;
	private final HashMap<Timer, CallbackHandler> timerCallbackList;
	private final HashMap<Long, Timer> timers;
	private long ticks;
	private boolean useHackTicks;
}
