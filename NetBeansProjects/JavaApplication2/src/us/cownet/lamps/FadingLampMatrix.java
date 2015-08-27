package us.cownet.lamps;

import us.cownet.timers.Callback;

/*
 A lamp matrix where transitions between off/on happen slowly.
 */
public class FadingLampMatrix implements LampMatrix {
	private LampMatrix matrix;
	private LampPattern pattern;
	private FadingLampPattern fadingOn;
	private FadingLampPattern fadingOff;

	//light progression for a 256 fade would be:
	//1, 128, 192, 224, 240, 248, 252, 254, 255
	@Override
	public LampPattern getPattern() {
		return pattern;
	}

	@Override
	public void setPattern(LampPattern lamps) {
	}

	@Override
	public void setSyncCallback(Callback callback) {
	}

}
