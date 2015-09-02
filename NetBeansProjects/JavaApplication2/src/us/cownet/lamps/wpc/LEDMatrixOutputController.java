package us.cownet.lamps.wpc;

import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import us.cownet.lamps.PinballOutputController;

public class LEDMatrixOutputController implements PinballOutputController {
	//	private final byte[] cachedData;
	private final DigitalOutput[] colPins;
	private final DigitalOutput[] rowPins;

	public LEDMatrixOutputController(String[] colPinsIn, String[] rowPinsIn) {
		this.colPins = new DigitalOutput[colPinsIn.length];
		this.rowPins = new DigitalOutput[rowPinsIn.length];
		//        this.cachedData = new byte[SIGNAL_COUNT];
		//        for (int i = 0; i < SIGNAL_COUNT; i++) {
		//            this.cachedData[i] = 0;
		//        }

		Board board = Platform.createBoard();
		for (int i = 0; i < dataPins.length; i++) {
			dataPins[i] = board.getPin(dataPinsIn[i]).as(DigitalOutput.class);
			dataPins[i].write(Signal.High);
		}

		for (Register i : Register.values()) {
			signalPins[i.ordinal()] = board.getPin(signalPinsIn[i.ordinal()]).as(DigitalOutput.class);
			// Note that the write() method will convert this to the
			// proper inverted value to turn things off/low
			write(i, (byte)0);
		}
	}

//    private String pinName(int ndx) {
//        return BBBNames.P8 + "_" + ndx;
//    }
	private static final int MASK[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};

	/* Write to the specified data input source */
	public void write(Register signal, byte value) {
//        if (value == cachedData[signal.ordinal()]) {
//            return;
//        }
//        cachedData[signal.ordinal()] = value;
		for (int i = 0; i < 8; i++) {
			// signals on the data lines are inverted on WPC so we convert
			// ON to low
			if ((value & MASK[i]) == 0) {
				dataPins[i].write(Signal.High);
			} else {
				dataPins[i].write(Signal.Low);
			}
		}
		signalPins[signal.ordinal()].write(Signal.Low);
		signalPins[signal.ordinal()].write(Signal.High);
	}

}
