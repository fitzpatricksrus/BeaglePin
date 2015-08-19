package us.cownet.lamps;

import us.cownet.timers.Callback;

public interface LampMatrix {

	public LampPattern getPattern();

	/*
	 detached() is called on the current pattern is there is one.  Then call
	 attached on the new lamp pattern.  The entire life cycle looks like this:

	 attached()
	 --getColumn()
	 --getColCount()
	 --endOfMatrixSync() // callback is called here
	 detached()

	 */
	public void setPattern(LampPattern lamps);

	/*
	 The callback is called after all columns are refreshed.
	 */
	public void setSyncCallback(Callback callback);
}
