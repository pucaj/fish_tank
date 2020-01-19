package com.company.strategies;

import com.company.ships.Statek;

import java.io.Serializable;

public class CollisionAvoidanceByAngleChangeStrategy implements CollisionAvoidanceStrategy, Serializable {
    private double angle;

    public CollisionAvoidanceByAngleChangeStrategy(double angle){
        this.angle = angle;
    }

    @Override
    public void avoid(Statek statek) {
        statek.zmienKierunek(angle);
    }
}
