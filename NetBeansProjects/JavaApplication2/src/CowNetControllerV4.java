
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.tests.DutyCycleDebugPinballOutputController;
import us.cownet.lamps.tests.PinballOutputControllerTest;
import us.cownet.lamps.tests.SimpleGreyscaleLampMatrixTest;
import us.cownet.lamps.tests.SimpleLampMatrixTest;
import us.cownet.lamps.wpc.WpcOutputController;
import us.cownet.testing.Test;
import us.cownet.timers.Timer;

public class CowNetControllerV4 {

	private static final boolean RUN_ON_WPC = false;

	public static void main(String[] args) {
		PinballOutputController controller;
		if (RUN_ON_WPC) {
			controller = new WpcOutputController(
					new String[]{"P8_37", "P8_38", "P8_39", "P8_40", "P8_41", "P8_42", "P8_43", "P8_44"},
					new String[]{"P8_45", "P8_46"}
			);
		} else {
//			controller = new DebugPinballOutputController();
			controller = new DutyCycleDebugPinballOutputController(2, 8);
		}

		PinballOutputControllerTest pinballOutputControllerTest
				= new PinballOutputControllerTest(controller);
		SimpleLampMatrixTest simpleLampMatrixTest = new SimpleLampMatrixTest(controller, 50);
		SimpleGreyscaleLampMatrixTest simpleGreyscaleLampMatrixTest
				= new SimpleGreyscaleLampMatrixTest(controller, 50L);
		//SimpleLampMatrixTest test(controller, 2000L * 1000L);

		Test currentTest = simpleLampMatrixTest;

		long count = 0;
		System.out.println("setup()");
		currentTest.setup();

//		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		while (true) {
			currentTest.loop();
			count++;
			if (ticks.isTime()) {
				System.out.println("Frequency: " + count);
				count = 0;
			}
			try {
//				Thread.sleep(50);
			} catch (Exception e) {

			}
		}
	}
}
