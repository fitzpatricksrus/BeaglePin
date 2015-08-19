package us.cownet.lamps.wpc;

import us.cownet.lamps.PinballOutputController;

public class LEDMatrixOutputController implements PinballOutputController {

	@Override
	public void write(Register signal, byte value) {
		switch (signal) {
			case LAMP_ROW:
				break;
			case LAMP_COL:
				break;
			default:
				break;
		}
	}

}
