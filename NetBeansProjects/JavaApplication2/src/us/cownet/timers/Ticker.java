package us.cownet.timers;

public class Ticker {
	public Ticker(long periodTicksIn) {
		periodMicros = periodTicksIn;
	}

	public void setPeriod(long periodTicksIn) {
		periodMicros = periodMicrosIn;
	}
	
	public long getPeriod() {
		return perdioMicros;
	}

	public boolean isTime() {
		long now = TimerUtil.INSTANCE.currentTimeTicks();
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
