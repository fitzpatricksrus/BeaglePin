package us.cownet.lamps.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.lamps.tests.LampMatrixTest;
import us.cownet.testing.Test;

public class SimpleLampMatrixTest implements Test {
    public SimpleLampMatrixTest(PinballOutputController controller, long micros) {
        matrix = new SimpleLampMatrix(controller, micros);
        test = new LampMatrixTest(matrix);
    }

    @Override
    public void setup() {
        test.setup();
    }

    @Override
    public void loop() {
        test.loop();
    }

    private SimpleLampMatrix matrix;
    private LampMatrixTest test;
}
