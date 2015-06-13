
import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.simple.tests.SimpleLampMatrixTest;
import us.cownet.lamps.tests.DebugPinballOutputController;
import us.cownet.testing.Test;
import us.cownet.timers.Ticker;
import us.cownet.timers.TimerUtil;

public class CowNetControllerV4 {
    public static void main(String[] args) {
        String[] dataOutputPins = {"B8_1"};
        String[] signalOutputPins = {"B8_1"};

        PinballOutputController controller = new DebugPinballOutputController();

//        PinballOutputControllerTest test = new PinballOutputControllerTest(controller);
        SimpleLampMatrixTest test = new SimpleLampMatrixTest(controller, 1);

        //SimpleLampMatrixTest test(controller, 2000L * 1000L);
        Test currentTest = test;

        long count = 0;
        System.out.println("setup()");
        currentTest.setup();

        TimerUtil.INSTANCE.hackTime(0);
        Ticker ticks = new Ticker(1000L * 1000L);
        while (true) {
            currentTest.loop();
            count++;
            if (ticks.isTime()) {
                System.out.println("Frequency: " + count);
                count = 0;
            }
            try {
                Thread.sleep(15);
            } catch (Exception e) {

            }
        }
    }
}
