package us.cownet.testing;

import us.cownet.timers.Timer;

public interface Test {
	public default void setup() {
	}

	public default void loop() {
	}

	public default boolean isDone() {
		return false;
	}

	public default void execute() {
		long count = 0;
		long tickCount = 0;
		System.out.println("setup()");
		setup();

//		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		Timer loopTick = new Timer(100);
		while (!isDone()) {
			if (loopTick.isTime()) {
				loop();
				tickCount++;
			}
			count++;
			if (ticks.isTime()) {
				System.out.println("Frequency: " + count + ",  ticks: " + tickCount);
				count = 0;
				tickCount = 0;
			}
			/*			try {
			 for (long i = 10000; i < 10000; i++) {
			 long x = count;
			 count = 0;
			 count = x;
			 }
			 //				Thread.sleep(0, 1);
			 } catch (Exception e) {

			 } */
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
