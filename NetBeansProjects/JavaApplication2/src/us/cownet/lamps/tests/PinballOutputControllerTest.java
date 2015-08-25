package us.cownet.lamps.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.lamps.wpc.WpcOutputController;
import us.cownet.testing.Test;

public class PinballOutputControllerTest implements Test {
	private PinballOutputController hardware;
	private int row;
	private int col;

	public PinballOutputControllerTest(PinballOutputController testController) {
		hardware = testController;
		row = 0;
		col = 0;
	}

	@Override
	public void setup() {
	}

	@Override
	public void loop() {
		hardware.write(PinballOutputController.Register.LAMP_COL, (byte)0);
		// set the row lamps
		hardware.write(PinballOutputController.Register.LAMP_ROW, (byte)(1 << row));
		// turn the proper column back on
		hardware.write(PinballOutputController.Register.LAMP_COL, (byte)(1 << col));

		row = row + 1;
		if (row >= 8) {
			row = 0;
			col = col + 1;
			if (col >= 8) {
				col = 0;
			}
		}
	}

	public static PinballOutputController createTestController() {
		PinballOutputController controller;
		if (false) {
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
		return controller;
	}

	public static void main(String args[]) {
		PinballOutputController controller = PinballOutputControllerTest.createTestController();

		PinballOutputControllerTest test = new PinballOutputControllerTest(controller);
		test.execute();
	}
}
