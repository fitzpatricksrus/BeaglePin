package us.cownet.lamps;

public interface PinballOutputController {

	public enum Register {
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

	public static final int SIGNAL_COUNT = Register.SIGNAL_COUNT.ordinal();

	/* Write to the specified data input source */
	public void write(Register signal, byte value);

}
