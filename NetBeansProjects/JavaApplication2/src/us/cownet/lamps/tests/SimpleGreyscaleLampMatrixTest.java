package us.cownet.lamps.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.lamps.tests.GreyscaleLampMatrixTest;
import us.cownet.testing.Test;

public class SimpleGreyscaleLampMatrixTest implements Test {
	public SimpleGreyscaleLampMatrixTest(PinballOutputController controller, long micros) {
		matrix = new SimpleLampMatrix(controller, micros);
		test = new GreyscaleLampMatrixTest(matrix);
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
	private GreyscaleLampMatrixTest test;
}
