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

	protected LampPattern currentPattern;
}
