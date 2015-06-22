package us.cownet.timers;

public class TimerCallback implements Callback extends CallbackHandler {
	public TimerCallback(long micros) {
		timer = new Timer(micros);
	}
	
	public void call() {
		if (timer.isTime()) {
			invokeCallbacks();
		}
	}
	
	private Timer timer;
}
