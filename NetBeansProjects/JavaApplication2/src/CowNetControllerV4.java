
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.PrefetchSimpleLampMatrix;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.lamps.tests.*;
import us.cownet.lamps.wpc.WpcOutputController;
import us.cownet.testing.Test;
import us.cownet.timers.Timer;

public class CowNetControllerV4 {

	private static final boolean RUN_ON_WPC = false;

	public static void main(String[] args) {

		//------ controller based tests
		PinballOutputController controller;
		if (RUN_ON_WPC) {
			controller = new WpcOutputController(
					new String[]{"P8_37", "P8_38", "P8_39", "P8_40", "P8_41", "P8_42", "P8_43", "P8_44"},
					new String[]{"P8_45", "P8_46"}
			);
		} else {
//			controller = new DebugPinballOutputController();
			DutyCycleDebugPinballOutputController dc = new DutyCycleDebugPinballOutputController(8, 8, 512);
			dc.init();
			controller = dc;
		}

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
		Timer loopTick = new Timer(7);
		while (!currentTest.isDone()) {
			if (loopTick.isTime()) {
				currentTest.loop();
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
}
