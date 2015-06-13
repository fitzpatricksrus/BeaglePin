package us.cownet.lamps;

import us.cownet.timers.Callback;

public interface LampMatrix {
    public LampPattern getPattern();

    public void setPattern(LampPattern lamps);

    public void setSyncCallback(Callback callback);
}
