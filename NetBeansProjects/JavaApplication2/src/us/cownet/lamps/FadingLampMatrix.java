package us.cownet.lamps;

/*
 A lamp matrix where transitions between off/on happen slowly.
 As an optimization, lamps can't be turned on/off in mid-transition.
 They need to be completely on or completely off before the pattern
 is changed.  This allows the entire matrix to be treated as a unit
 and state doesn't need to be kept on a bulb per bulb basis.
 */
public class FadingLampMatrix extends CyclicLampMatrix {
	private int pixels[][];
	private int pixelDirection[][];
	private int flipNdx;
	private int mask[];

	public FadingLampMatrix(PinballOutputController controller, long ticks, int speed) {
		super(controller, ticks, speed);
		pixels = new int[8][8];
		pixelDirection = new int[8][8];
		mask = new int[speed];
		for (int i = 0; i < 256; i++) {
			mask[i] = (int)Math.pow(2, Math.floor(Math.log(i) / Math.log(2)));
		}
	}

	@Override
	protected byte internalGetColumn(LampPattern pattern, int columnNumber) {
		byte value = 0;

		for (int row = 0; row < 8; row++) {
			value <<= 1;
			if (pattern.getLamp(columnNumber, row)) {
			}
//			if (((lamp ? 1 : 0) & mask[getCycle()];
		}

		return value;
	}

	public static void main(String args[]) {
		for (int i = 0; i < 256; i++) {
			System.out.println("" + i + " " + Math.pow(2, Math.floor(Math.log(i) / Math.log(2))));
		}
	}
}
