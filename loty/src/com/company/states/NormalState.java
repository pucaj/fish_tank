package com.company.states;

import com.company.ships.Statek;

import java.awt.*;


public class NormalState extends State {

    @Override
    public void draw(Statek context, Graphics2D g) {
        context.drawShape(g);
    }
}
