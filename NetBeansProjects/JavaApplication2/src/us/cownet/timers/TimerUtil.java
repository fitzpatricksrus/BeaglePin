package us.cownet.timers;

import java.util.vector;

public class TimerUtil extends CallbackHandler {
	public void hackTick() {
		if (hackMicros != REAL_TICKS) {
			hackMicros++;
		}
		ticks++;
		invokeCallbacks();
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
	
	public long currentTimeTicks() {
		return ticks;
	}

	public static TimerUtil INSTANCE = new TimerUtil();
	public static final long REAL_TICKS = -1;

	private long ticks;
	private long hackMicros = REAL_TICKS;
}
