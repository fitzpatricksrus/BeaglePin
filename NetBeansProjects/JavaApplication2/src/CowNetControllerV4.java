
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.PrefetchSimpleLampMatrix;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.lamps.tests.CompisiteOrLampPatternTest;
import us.cownet.lamps.tests.FadingLampPatternTest;
import us.cownet.lamps.tests.GreyscaleLampPatternTest;
import us.cownet.lamps.tests.LampMatrixTest;
import us.cownet.lamps.tests.LampSequenceTest;
import us.cownet.lamps.tests.PinballOutputControllerTest;
import us.cownet.testing.Test;
import us.cownet.timers.Timer;

public class CowNetControllerV4 {

	private static final boolean RUN_ON_WPC = false;

	public static void main(String[] args) {

		//------ controller based tests
		PinballOutputController controller = PinballOutputControllerTest.createTestController();

		PinballOutputControllerTest pinballOutputControllerTest
				= new PinballOutputControllerTest(controller);
		//------ matrix based tests
		SimpleLampMatrix lampMatrix = new SimpleLampMatrix(controller, 1);
		PrefetchSimpleLampMatrix prefetchLampMatrix = new PrefetchSimpleLampMatrix(controller, 1);

		LampMatrixTest simpleLampMatrixTest = new LampMatrixTest(lampMatrix);

		GreyscaleLampPatternTest simpleGreyscaleLampMatrixTest
				= new GreyscaleLampPatternTest(prefetchLampMatrix);

		FadingLampPatternTest fadingPatternTest = new FadingLampPatternTest(prefetchLampMatrix);

		LampSequenceTest sequenceTest = new LampSequenceTest(prefetchLampMatrix);

		CompisiteOrLampPatternTest orTest = new CompisiteOrLampPatternTest(prefetchLampMatrix);

		//------ main test loop
		Test currentTest = simpleGreyscaleLampMatrixTest;

		long count = 0;
		long tickCount = 0;
		System.out.println("setup()");
		currentTest.setup();

//		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		Timer loopTick = new Timer(70);
		while (!currentTest.isDone()) {
//			if (loopTick.isTime()) {
			currentTest.loop();
			tickCount++;
//			}
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
}
