package us.cownet.timers;

public class Ticker implements PeriodicEvent {
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
		if (periodTicks == 0) {
			return true;
		}
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
