
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.lamps.tests.DutyCycleDebugPinballOutputController;
import us.cownet.lamps.tests.FadingLampPatternTest;
import us.cownet.lamps.tests.GreyscaleLampPatternTest;
import us.cownet.lamps.tests.LampSequenceTest;
import us.cownet.lamps.tests.PinballOutputControllerTest;
import us.cownet.lamps.tests.SimpleLampMatrixTest;
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
			DutyCycleDebugPinballOutputController dc = new DutyCycleDebugPinballOutputController(8, 8);
			dc.init();
			controller = dc;
		}

		PinballOutputControllerTest pinballOutputControllerTest
				= new PinballOutputControllerTest(controller);
		SimpleLampMatrixTest simpleLampMatrixTest = new SimpleLampMatrixTest(controller, 0);

		//------ matrix based tests
		SimpleLampMatrix lampMatrix = new SimpleLampMatrix(controller, 0);

		GreyscaleLampPatternTest simpleGreyscaleLampMatrixTest
				= new GreyscaleLampPatternTest(lampMatrix);

		FadingLampPatternTest fadingPatternTest = new FadingLampPatternTest(lampMatrix);

		LampSequenceTest sequenceTest = new LampSequenceTest(lampMatrix);

		//------ main test loop
		Test currentTest = sequenceTest;

		long count = 0;
		System.out.println("setup()");
		currentTest.setup();

//		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		while (!currentTest.isDone()) {
			currentTest.loop();
			count++;
			if (ticks.isTime()) {
				System.out.println("Frequency: " + count);
				count = 0;
			}
			try {
				for (long i = 0; i < 10000; i++) {
					long x = count;
					count = 0;
					count = x;
				}
//				Thread.sleep(0, 1);
			} catch (Exception e) {

			}
		}
	}
}
