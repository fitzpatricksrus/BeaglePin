package us.cownet.lamps.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.SimpleLampMatrix;
import us.cownet.testing.Test;

public class SimpleGreyscaleLampPatternTest implements Test {
	public SimpleGreyscaleLampPatternTest(PinballOutputController controller, long micros) {
		matrix = new SimpleLampMatrix(controller, micros);
		test = new GreyscaleLampPatternTest(matrix);
	}

	@Override
	public void setup() {
		test.setup();
	}

	@Override
	public void loop() {
		test.loop();
	}

	private final SimpleLampMatrix matrix;
	private final GreyscaleLampPatternTest test;
}
