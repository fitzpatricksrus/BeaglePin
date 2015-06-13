package us.cownet.lamps.simple;

import us.cownet.lamps.GreyscaleLampMatrix;
import us.cownet.lamps.GreyscaleLampPattern;
import us.cownet.lamps.LampMatrix;
import us.cownet.timers.Callback;

public class SimpleGreyscaleLampMatrix implements GreyscaleLampMatrix {
    private LampMatrix matrix;
    private GreyscaleLampPattern pattern;
    private int tickNumber;
    private Callback callback;
    private SimpleLampPattern patterns[] = new SimpleLampPattern[8];

    private static final int MASK[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
    private static final int INDEX[] = new int[256];

    static {
        int next = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < MASK[i]; j++) {
                INDEX[next++] = i;
            }
        }
    }

    public SimpleGreyscaleLampMatrix(LampMatrix matrix) {
        this.matrix = matrix;
        tickNumber = 0;
        matrix.setSyncCallback(() -> tick());
    }

    public GreyscaleLampPattern getPattern() {
        return pattern;
    }

    public void setPattern(GreyscaleLampPattern lamps) {
        this.pattern = lamps;
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = new SimpleLampPattern(new byte[lamps.getColCount()]);
            for (int col = 0; col < pattern.getColCount(); col++) {
                for (int row = 0; row < 8; row++) {
                    patterns[i].setLamp(col, row, (lamps.getLamp(col, row) & MASK[i]) != 0);
                }
            }
        }
        tickNumber = 0;
        matrix.setPattern(patterns[0]);
    }

    public void setSyncCallback(Callback callback) {
        this.callback = callback;
    }

    private void tick() {
        tickNumber = (tickNumber + 1) % 256;
        if (tickNumber == 0 && callback != null) {
            callback.call();
        }
        matrix.setPattern(patterns[INDEX[tickNumber]]);
    }
}
