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

	public void addListener(Callback listener) {
		synchronized (listeners) {
			if (listeners.isEmpty()) {
				// assertTrue(thread == null);
				thread = new Thread(new NotifierRunnable());
				thread.start();
			}
			listeners.add(listener);
		}
	}

	public void removeListener(Callback listener) {
		synchronized (listeners) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				pendingNotifications = -1;
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
				thread = null;
			}
		}
	}

	public synchronized void notifyListeners() {
		pendingNotifications++;
		this.notifyAll();
	}

	private synchronized boolean waitForPendingNotifications() {
		while (pendingNotifications == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (pendingNotifications < 0) {
			return false;		// we're done.  terminate the thread.
		} else {
			if (compressNotifications) {
				pendingNotifications = 0;
			} else {
				pendingNotifications--;
			}
			return true;
		}
	}

	private class NotifierRunnable implements Runnable {
		@Override
		public void run() {
			while (waitForPendingNotifications()) {
				synchronized (listeners) {
					for (Callback n : listeners) {
						n.call();
					}
				}
			}
		}
	}

	private final HashSet<Callback> listeners;
	private final boolean compressNotifications;
	private int pendingNotifications;
	private Thread thread;
}
