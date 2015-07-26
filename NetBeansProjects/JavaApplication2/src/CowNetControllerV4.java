
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.simple.tests.SimpleGreyscaleLampMatrixTest;
import us.cownet.lamps.tests.DebugPinballOutputController;
import us.cownet.testing.Test;
import us.cownet.timers.Timer;

public class CowNetControllerV4 {
	public static void main(String[] args) {
		PinballOutputController controller = new DebugPinballOutputController();

//		PinballOutputControllerTest test = new PinballOutputControllerTest(controller);
//		SimpleLampMatrixTest test = new SimpleLampMatrixTest(controller, 50);
		SimpleGreyscaleLampMatrixTest test = new SimpleGreyscaleLampMatrixTest(controller, 50L);
		//SimpleLampMatrixTest test(controller, 2000L * 1000L);
		Test currentTest = test;

		long count = 0;
		System.out.println("setup()");
		currentTest.setup();

//		TimerUtil.INSTANCE.enableHackTicks(false);
		Timer ticks = new Timer(1000L * 1000L);
		long t = System.currentTimeMillis();
		while (true) {
			currentTest.loop();
			count++;
			if (ticks.isTime()) {
				System.out.println("Frequency: " + count);
				t = System.currentTimeMillis();
				count = 0;
			}
			try {
//				Thread.sleep(50);
			} catch (Exception e) {

			}
		}
	}
}
