package us.cownet.timers;

import java.util.Vector;

public class CallbackHandler {
    public CallbackHandler() {
        callbacks = new Vector<Callback>();
    }

    public void invokeCallbacks() {
        for (Callback c : callbacks) {
            c.call();
        }
    }

    public void addCallback(Callback c) {
        callbacks.add(c);
    }

    public void removeCallback(Callback c) {
        callbacks.remove(c);
    }

    private Vector<Callback> callbacks;
}
