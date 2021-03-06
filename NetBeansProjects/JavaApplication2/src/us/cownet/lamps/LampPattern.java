package us.cownet.lamps;

/*
 LampPattern has two protocols.  One for the lamp matrix and one for patterns
 that embed other patterns.

 For the lamp matrix, the life cycle looks like this:
 attached()
 getColumn()+
 getColCount()+
 endOfMatrixSync()+
 detached()

 isDone() and reset() are ignored by the lamp matrix.  They are only used
 by pattern containers to tell when to transition from one pattern to the next.

 Subclasses MUST override either getColumn or getLamp(col,row) or they things will
 just infinately recurse.  Define the pattern contents in terms of entire columns
 or individual lamps.  Up to you.

 */
public interface LampPattern {
	public byte getColumn(int x);

	public int getColCount();

	public int getLampCount();

	public boolean getLamp(int x, int y);

	public boolean getLamp(int index);

	public default void attached() {
	}

	public default void endOfMatrixSync() {
	}

	/*
	 Used only by pattern containers.  isDone() should return true when the
	 pattern has displayed its entire state sequence once.  This has no affect
	 on the lamp matrix, which will continue to cycle through columns.  Containers
	 can decide how to proceed and may reset() the pattern, replace it, or
	 continue to refresh the matrix with it.
	 */
	public default boolean isDone() {
		return false;
	}

	/*
	 Start the refresh sequence from the beginning.
	 */
	public default void reset() {
	}

	public default void detached() {
	}
}
