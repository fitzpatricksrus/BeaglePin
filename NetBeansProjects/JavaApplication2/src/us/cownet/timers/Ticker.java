package us.cownet.timers;

public class Ticker {
	// hey jf - this should be called Timer
	
	public Ticker(long periodMicrosIn) {
		periodMicros = periodMicrosIn;
	}

	public void setPeriod(long periodMicrosIn) {
		periodMicros = periodMicrosIn;
	}

	public boolean isTime() {
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
