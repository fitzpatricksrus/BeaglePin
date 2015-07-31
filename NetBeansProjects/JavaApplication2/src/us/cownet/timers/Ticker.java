package us.cownet.timers;

public class Ticker {
	public Ticker(long periodTicksIn) {
		periodTicks = periodTicksIn;
	}

	public void setPeriod(long periodTicksIn) {
		periodTicks = periodTicksIn;
	}

	public long getPeriod() {
		return periodTicks;
	}

	public boolean isTime() {
		long now = TimerUtil.INSTANCE.currentTicks();
		if (now - lastTick > periodTicks) {
			lastTick = now;
			return true;
		} else {
			return false;
		}
	}

	private long periodTicks;
	private long lastTick;
}
