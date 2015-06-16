package us.cownet.timers;

public class TickerCallback implements Callback {
	public TickerCallback(targetTicks) {
		callbacks = new Vector<Callback>();
		this.targetTicks = targetTicks;
	}
	
	public void call() {
		if (ticks >= targetTicks) {}
 			for (Callback c : callbacks) {
				c.call();
			}
			ticks = 0;
		} else {
			ticks++;
		}
	}
	
	public void addCallback(Callback c) {
		callbacks.append(c);
	}
	
	public void removeCallback(Callback c) {
		callbacks.remove(c);
	}
	
	private long targetTicks;
	private long ticks;
	private Vector<Callback> callbacks;
}
