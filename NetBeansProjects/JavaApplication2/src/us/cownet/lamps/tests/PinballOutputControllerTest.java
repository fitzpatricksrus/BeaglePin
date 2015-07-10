package us.cownet.lamps.tests;

import us.cownet.lamps.PinballOutputController;
import us.cownet.testing.Test;
import us.cownet.timers.Timer;
import us.cownet.timers.TimerUtil;

public class PinballOutputControllerTest implements Test {
	public PinballOutputControllerTest(PinballOutputController testController) {
		hardware = testController;
		row = 0;
		col = 0;
		ticks = new Timer(delayPeriod);
	}

	@Override
	public void setup() {
	}

	@Override
	public void loop() {
		TimerUtil.INSTANCE.tick();
		if (ticks.isTime()) {
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
	}

	private static final long delayPeriod = 200L * 1000L;
	private PinballOutputController hardware;
	private int row;
	private int col;
	private Timer ticks;
}
