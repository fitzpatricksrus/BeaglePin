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
	}

	public static final int SIGNAL_COUNT = Register.LAMP_COL.ordinal() + 1;

	/* Write to the specified data input source */
	public void write(Register signal, byte value);

	public default void writeRow(byte value) {
		write(Register.LAMP_ROW, value);
	}

	public default void writeCol(byte value) {
		write(Register.LAMP_COL, value);
	}
}
