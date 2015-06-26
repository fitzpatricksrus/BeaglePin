package us.cownet.timers;

import java.util.HashMap;
import java.util.Vector;

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
			// hey jf - it might be nice to do garbage collection of empty lists here.
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
			// hey jf - it might be nice to do garbage collection of empty lists here.
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
		tickers = new HashMap<Long, Ticker>();
		timers = new HashMap<Long, Timer>();
		tickerCallbackList = new HashMap<Ticker, CallbackHandler>();
		timerCallbackList = new HashMap<Timer, CallbackHandler>();
		ticks = 0;
		useHackTicks = false;
	}

	private class CallbackHandler {
		public CallbackHandler() {
			callbacks = new Vector<Callback>();
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

		private Vector<Callback> callbacks;
	}

	private HashMap<Ticker, CallbackHandler> tickerCallbackList;
	private HashMap<Long, Ticker> tickers;
	private HashMap<Timer, CallbackHandler> timerCallbackList;
	private HashMap<Long, Timer> timers;
	private long ticks;
	private boolean useHackTicks;
}
