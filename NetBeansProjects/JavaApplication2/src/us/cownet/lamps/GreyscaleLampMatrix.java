package us.cownet.lamps;

import us.cownet.timers.Callback;

public interface GreyscaleLampMatrix {

    public GreyscaleLampPattern getPattern();

    public void setPattern(GreyscaleLampPattern lamps);

    public void setSyncCallback(Callback callback);

}
