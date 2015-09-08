package us.cownet.timers;

import java.util.ArrayList;

public class AsyncCallback implements Callback {
	public AsyncCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	public synchronized void call() {
		callbacks.add(callback);
		if (callbackThread == null) {
			callbackThread = new Thread() {
				@Override
				@SuppressWarnings("empty-statement")
				public void run() {
					while (ping());
					callbackThread = null;
				}
			};
			callbackThread.setPriority(Thread.MIN_PRIORITY);
			callbackThread.setDaemon(true);
			callbackThread.start();
		}
	}

	public synchronized void done() {
		callbacks.remove(this);
	}

	private synchronized boolean ping() {
		boolean result = callbacks.size() > 0;
		for (Callback c : callbacks) {
			c.call();
		}
		callbacks.clear();
		return result;
	}

	private Callback callback;

	private static ArrayList<Callback> callbacks = new ArrayList<>();
	private static Thread callbackThread;
}
