package us.cownet.timers;

import java.util.HashSet;

public class Notifier {
	public Notifier() {
		listeners = new HashSet<>();
	}

	public synchronized void addListener(Callback listener) {
		listeners.add(listener);
	}

	public synchronized void removeListener(Callback listener) {
		listeners.remove(listener);
	}

	public synchronized void notifyListeners() {
		for (Callback n : listeners) {
			n.call();
		}
	}

	private class NotifierThread implements Runnable {
		@Override
		public void run() {
//			synchronize()
			while (listeners.size() > 0) {

			}
		}
	}

	private final HashSet<Callback> listeners;
}
