package us.cownet.testing;

import us.cownet.timers.Timer;
import us.cownet.timers.TimerUtil;

public interface Test {
	public default void setup() {
	}

	public default void loop() {
	}

	public default boolean isDone() {
		return false;
	}

	public default void execute() {
		execute(2048L / 256);
	}

	public default void execute(long ticksPerLoop) {
		long count = 0;
		long tickCount = 0;
		System.out.println("setup()");
		setup();

		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		Timer loopTick = new Timer(ticksPerLoop);
		while (!isDone()) {
			if (loopTick.isTime()) {
				loop();
				tickCount++;
			}
			count++;
			if (ticks.isTime()) {
//				System.out.println("Frequency: " + count + ",  ticks: " + tickCount);
				count = 0;
				tickCount = 0;
			}
		}
	}

	public static void assertTrue(boolean condition) {
		if (!condition) {
			System.out.println("Fail");
		}
	}

	public static void assertTrue(boolean condition, String message) {
		if (!condition) {
			System.out.println(message);
		}
	}

}
