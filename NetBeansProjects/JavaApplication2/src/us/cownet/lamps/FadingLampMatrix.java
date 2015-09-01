package us.cownet.lamps;

/*
 A lamp matrix where transitions between off/on happen slowly.
 */
public class FadingLampMatrix extends SimpleLampMatrix {
	private int speed;
	private int pixels[][];
	private int pixelDirection[][];
	private int flipNdx;
	private int cycle;

	public FadingLampMatrix(PinballOutputController controller, long ticks) {
		super(controller, ticks);
		this.speed = speed;
		pixels = new int[8][8];
		pixelDirection = new int[8][8];
		cycle = 0;
	}

	protected byte internalGetColumn(LampPattern pattern, int columnNumber) {
		byte value = 0;

		for (int row = 0; row < 8; row++) {
			value <<= 1;
			boolean lamp = pattern.getLamp(columnNumber, row);

		}

		return value;
	}

	protected void internalSetPattern(LampPattern newPattern) {
		super.internalSetPattern(newPattern);
		if (newPattern != null) {
			for (int col = 0; col < newPattern.getColCount(); col++) {
				for (int row = 0; row < 8; row++) {
					if (newPattern.getLamp(col, row)) {
						// pixel is getting brighter
						pixelDirection[col][row] = 1;
					} else {
						// pixel starts getting dimmer
						pixelDirection[col][row] = -1;
					}
				}
			}
		} else {
			for (int col = 0; col < 8; col++) {
				for (int row = 0; row < 8; row++) {
					pixelDirection[col][row] = -1;
				}
			}
		}
	}

	protected void internalEndOfMatrixSync(LampPattern pattern) {
		super.internalEndOfMatrixSync(pattern);
		cycle = Math.min(cycle + 1, 255);
	}
}
