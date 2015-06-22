package us.cownet.timers;

public class TickerCallback extends CallbackHandler implements Callback {
	public TickerCallback(long targetTicks) {
		ticker = new Ticker(targetTicks);
	}

	public void call() {
		if (ticker.isTime()) {
			invokeCallbacks();
		}
	}

	private Ticker ticker;
}
