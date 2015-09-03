package us.cownet.lamps.wpc;

import us.cownet.lamps.PinballOutputController;

public class LEDMatrixOutputController implements PinballOutputController {

	public LEDMatrixOutputController(String[] colPinsIn, String[] rowPinsIn) {
	}

//    private String pinName(int ndx) {
//        return BBBNames.P8 + "_" + ndx;
//    }
	private static final int MASK[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};

	/* Write to the specified data input source */
	public void write(Register signal, byte value) {
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

}
