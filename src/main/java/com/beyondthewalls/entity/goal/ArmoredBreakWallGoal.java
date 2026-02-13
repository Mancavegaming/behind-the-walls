package com.beyondthewalls.entity.goal;

import com.beyondthewalls.Config;
import com.beyondthewalls.entity.TitanEntity;

public class ArmoredBreakWallGoal extends TitanBreakWallGoal {

    public ArmoredBreakWallGoal(TitanEntity titan) {
        super(titan);
    }

    @Override
    public void start() {
        super.start();
        // Apply armored wall break speed multiplier (halves break time by default)
        this.breakTime = (int) (this.breakTime * Config.ARMORED_WALL_BREAK_SPEED.getAsDouble());
        if (this.breakTime < 1) this.breakTime = 1;
    }
}
