package com.company.states;

import com.company.ships.Statek;
import com.company.strategies.Strategy;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import static com.company.UtilityFunctions.pulsingColor;

public class InDangerState extends State {
    private Strategy strategy;

    @Override
    public void draw(Statek context, Graphics2D g) {
        g.setColor(context.getColor());
        context.drawShape(g);
    }

    public void avoid() {
        //TODO napisać implementację metody avoid() oraz strategie
    }
}
