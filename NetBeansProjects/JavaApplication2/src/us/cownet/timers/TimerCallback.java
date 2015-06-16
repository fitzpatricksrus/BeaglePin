package us.cownet.timers;

public class TimerCallback implements Callback {
	public TimerCallback(long micros) {
		ticker = new Ticker(micros);
		callbacks = new Vector<Callback>();
	}
	
	public void call() {
		if (ticker.isTime()) {
			for (Callback c : callbacks) {
				c.call();
			}
		}
	}
	
	private Ticker ticker;
	private Vector<Callback> callbacks;
	
}
