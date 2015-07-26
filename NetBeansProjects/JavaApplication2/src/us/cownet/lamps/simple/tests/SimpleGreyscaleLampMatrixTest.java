package us.cownet.lamps.simple.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.simple.SimpleGreyscaleLampMatrix;
import us.cownet.lamps.simple.SimpleLampMatrix;
import us.cownet.lamps.tests.GreyscaleLampMatrixTest;
import us.cownet.testing.Test;

public class SimpleGreyscaleLampMatrixTest implements Test {
	public SimpleGreyscaleLampMatrixTest(PinballOutputController controller, long micros) {
		matrix = new SimpleLampMatrix(controller, micros);
		greyMatrix = new SimpleGreyscaleLampMatrix(matrix);
		test = new GreyscaleLampMatrixTest(greyMatrix);
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
	private SimpleGreyscaleLampMatrix greyMatrix;
	private GreyscaleLampMatrixTest test;
}
