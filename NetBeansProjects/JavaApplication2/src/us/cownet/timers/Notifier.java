package us.cownet.timers;

import java.util.HashSet;

public class Notifier {
	public Notifier() {
		this(false);
	}

	public Notifier(boolean compressNotifications) {
		this.listeners = new HashSet<>();
		this.compressNotifications = compressNotifications;
		this.pendingNotifications = 0;
	}

	public synchronized void addListener(Callback listener) {
		listeners.add(listener);
	}

	public synchronized void removeListener(Callback listener) {
		listeners.remove(listener);
	}

	public synchronized void notifyListeners() {
		pendingNotifications++;
		this.notifyAll();
	}

	private synchronized void waitForPendingNotifications() {
		while (pendingNotifications == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (compressNotifications) {
			pendingNotifications = 0;
		} else {
			pendingNotifications--;
		}
	}

	private class NotifierThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				waitForPendingNotifications();
				for (Callback n : listeners) {
					n.call();
				}
			}
		}
	}

	private final HashSet<Callback> listeners;
	private final boolean compressNotifications;
	private int pendingNotifications;
}
