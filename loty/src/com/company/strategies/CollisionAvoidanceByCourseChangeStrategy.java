package com.company.strategies;

import com.company.Vec2d;
import com.company.ships.Statek;

import java.io.Serializable;

public class CollisionAvoidanceByCourseChangeStrategy implements CollisionAvoidanceStrategy, Serializable {
    private Vec2d przesuniecie;

    public CollisionAvoidanceByCourseChangeStrategy(Vec2d przesuniecie){
        this.przesuniecie = przesuniecie;
    }

    @Override
    public void avoid(Statek statek) {
        statek.zmienKierunek(przesuniecie);
    }
}
