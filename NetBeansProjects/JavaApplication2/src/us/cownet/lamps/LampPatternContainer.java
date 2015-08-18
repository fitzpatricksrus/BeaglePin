package us.cownet.lamps;

public class LampPatternContainer {

	public void setPattern(LampPattern newPattern) {
		if (currentPattern != newPattern) {
			if (currentPattern != null) {
				currentPattern.detached();
			}
			this.currentPattern = newPattern;
			if (currentPattern != null) {
				currentPattern.attached();
			}
		}
	}

	public LampPattern getAttachedPattern() {
		return currentPattern;
	}

	private LampPattern currentPattern;
}
