package us.cownet.lamps;

/*
 A lamp matrix where transitions between off/on happen slowly.  Bulbs each
 have their own state and can rise and fall individually.

 todo:
 deal with patterns of size different from matrix size
 */
public class FadingLampMatrix extends CyclicLampMatrix {
	private final int pixels[][];
	private final int mask[];

	public FadingLampMatrix(PinballOutputController controller, long ticks, int fadeSpeed) {
		super(controller, ticks, fadeSpeed);
		pixels = new int[controller.getColumnCount()][8];
		mask = new int[fadeSpeed];
		for (int i = 0; i < fadeSpeed; i++) {
			mask[i] = (int)Math.pow(2, Math.floor(Math.log(Math.min(fadeSpeed - 1, i + 1)) / Math.log(2)));
		}
	}

	@Override
	protected byte internalGetColumn(LampPattern pattern, int columnNumber) {
		byte value = 0;
		int bitMask = mask[getCycle()];

		for (int row = 0; row < 8; row++) {
			value <<= 1;
			if ((pixels[columnNumber][row] & bitMask) != 0) {
				value |= 1;
			}
		}

		return value;
	}

	@Override
	protected void internalEndOfCycle() {
		LampPattern pattern = getDisplayedPattern();
		for (int col = 0; col < pixels[0].length; col++) {
			for (int row = 0; row < 8; row++) {
				if (pattern.getLamp(col, row)) {
					pixels[col][row] = Math.min(pixels[col][row] + 1, getCycleSize());
				} else {
					pixels[col][row] = Math.max(pixels[col][row] - 1, 0);
				}
			}
		}
	}

	public static void main(String args[]) {
		int speed = 32;
		for (int i = 0; i < speed; i++) {
			int num = (int)Math.pow(2, Math.floor(Math.log(Math.min(speed - 1, i + 1)) / Math.log(2)));
			System.out.println("" + i + " " + Integer.toBinaryString(num));
		}
	}
}
