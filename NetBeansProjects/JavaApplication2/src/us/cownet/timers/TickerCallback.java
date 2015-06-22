package us.cownet.timers;

public class TickerCallback implements Callback extends CallbackHandler {
	public TickerCallback(targetTicks) {
		ticker = new Ticker(targetTicks);
	}
	
	public void call() {
		if (ticker.isTime()) {
			invokeCallbacks();
		}
	}
		
	private Ticker ticker;
}
