package us.cownet.lamps.wpc;

import java.util.HashSet;
import java.util.Set;
import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Edge;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.gpio.DigitalOutput;
import org.bulldog.core.gpio.event.InterruptListener;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;

public class WpcInput {

//    private final byte[] cachedData;
	private final Set<InterruptListener>[] listeners;
	private final DigitalInput[] inputStrobe;
	private final DigitalOutput[] dataInputSourceSelect;
	private final DigitalInput[] dataInput;

	public WpcInput(int firstPin) {
		this.inputStrobe = new DigitalInput[SIGNAL_COUNT];
		this.dataInputSourceSelect = new DigitalOutput[SIGNAL_COUNT];
		this.dataInput = new DigitalInput[8];
		this.listeners = new HashSet[SIGNAL_COUNT];
		for (int i = 0; i < SIGNAL_COUNT; i++) {
			this.listeners[i] = new HashSet<>();
		}
//        this.cachedData = new byte[SIGNAL_COUNT];
//        for (int i = 0; i < SIGNAL_COUNT; i++) {
//            this.cachedData[i] = 0;
//        }

		Board board = Platform.createBoard();

		int pin = firstPin;
		for (WpcSignal i : WpcSignal.values()) {
			inputStrobe[i.ordinal()] = board.getPin(pinName(pin++)).as(DigitalInput.class);
		}

		for (WpcSignal i : WpcSignal.values()) {
			dataInputSourceSelect[i.ordinal()] = board.getPin(pinName(pin++)).as(DigitalOutput.class);
			dataInputSourceSelect[i.ordinal()].write(Signal.High);
		}

		for (int i = 0; i < 8; i++) {
			dataInput[i] = board.getPin(pinName(pin++)).as(DigitalInput.class);
		}
	}

	private String pinName(int ndx) {
		return BBBNames.P8 + "_" + ndx;
	}

	public enum WpcSignal {
		LAMP_ROW,
		LAMP_COL,
		//		SOL1,
		//		SOL2,
		//		SOL3,
		//		SOL4,
		//		TRIAC,
		//		SWITCH_ROW,
		//		SWITCH_COL,
		//		SWITCH_DEDICATED,
		SIGNAL_COUNT
	}

	public static final int SIGNAL_COUNT = WpcSignal.SIGNAL_COUNT.ordinal();

	/* add a listener for the specified input strobe signal */
	public void attachListener(WpcSignal signal, InterruptListener listener) {
		final int ndx = signal.ordinal();
		if (!listeners[ndx].contains(listener)) {
			listeners[ndx].add(listener);
			inputStrobe[ndx].setInterruptTrigger(Edge.Rising);
			inputStrobe[ndx].addInterruptListener(listener);
			if (listeners[ndx].size() == 1) {
				inputStrobe[ndx].enableInterrupts();
			}
		}
	}

	/* remove a listener from the specified input strobe signal */
	public void detachListener(WpcSignal signal, InterruptListener listener) {
		final int ndx = signal.ordinal();
		inputStrobe[ndx].removeInterruptListener(listener);
		listeners[ndx].remove(listener);
		if (listeners[ndx].isEmpty()) {
			inputStrobe[ndx].disableInterrupts();
		}
	}

	private static final int MASK[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};

	/* Read from the specified data input source */
	public byte read(WpcSignal signal) {
		// read has to first select the 74x374 flip flop (active low)
		// to put data on the data bus.  Read the bits (active high) and
		// then release the data bus again.
		dataInputSourceSelect[signal.ordinal()].write(Signal.Low);
		int value = 0;
		for (int i = 0; i < 8; i++) {
			// signals are inverted on WPC data lines, so we convert
			// low to ON
			if (dataInput[i].read() == Signal.Low) {
				value |= MASK[i];
			}
		}
		dataInputSourceSelect[signal.ordinal()].write(Signal.High);
//        cachedData[signal.ordinal()] = (byte)value;
		return (byte)value;
	}
}
