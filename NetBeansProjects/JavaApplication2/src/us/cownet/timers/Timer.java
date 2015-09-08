package us.cownet.timers;

public class Timer implements PeriodicEvent {
	public Timer(long periodMicrosIn) {
		periodMicros = periodMicrosIn;
	}

	public void setPeriod(long periodMicrosIn) {
		periodMicros = periodMicrosIn;
	}

	public long getPeriod() {
		return periodMicros;
	}

	public boolean isTime() {
		if (periodMicros == 0) {
			return true;
		}
		long now = TimerUtil.INSTANCE.currentTimeMicros();
		if (now - lastTick > periodMicros) {
			lastTick = now;
			return true;
		} else {
			return false;
		}
	}

	private long periodMicros;
	private long lastTick;
}
