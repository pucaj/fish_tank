package com.company.draw_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawWithColor extends State {
    private final State inner;
    private final Color color;

    public DrawWithColor(State inner, Color color) {
        this.inner = inner;
        this.color = color;
    }

    @Override
    public void draw(Statek context, Graphics2D g) {
        g.setColor(color);
        inner.draw(context, g);
    }
}
