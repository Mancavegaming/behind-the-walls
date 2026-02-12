package com.beyondthewalls.block;

public enum WallTier {
    MARIA(15.0F, 30.0F, 60),
    ROSE(30.0F, 60.0F, 120),
    SINA(50.0F, 1200.0F, 240);

    private final float hardness;
    private final float blastResistance;
    private final int titanBreakTicks;

    WallTier(float hardness, float blastResistance, int titanBreakTicks) {
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.titanBreakTicks = titanBreakTicks;
    }

    public float getHardness() { return hardness; }
    public float getBlastResistance() { return blastResistance; }
    public int getTitanBreakTicks() { return titanBreakTicks; }
}
