package us.cownet.lamps.simple;

import us.cownet.lamps.GreyscaleLampPattern;

public class SimpleGreyscaleLampPattern implements GreyscaleLampPattern {
    private byte[][] pattern;

    public static final SimpleGreyscaleLampPattern EmptyPattern = new SimpleGreyscaleLampPattern();

    public SimpleGreyscaleLampPattern() {
        pattern = new byte[0][0];
    }

    public SimpleGreyscaleLampPattern(byte[][] pattern) {
        this.pattern = pattern;
    }

    @Override
    public byte[][] getPattern() {
        return pattern;
    }

    public void setPattern(byte[][] newPattern) {
        pattern = newPattern;
    }

    @Override
    public byte getLamp(int x, int y) {
        return pattern[x][y];
    }

    @Override
    public byte getColCount() {
        return (byte)pattern.length;
    }

}
