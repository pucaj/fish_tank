package com.company.draw_decorators.shape_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawShapeWithColor extends State {
    private final State inner;
    private final Color color;

    public DrawShapeWithColor(State inner, Color color) {
        this.inner = inner;
        this.color = color;
    }

    @Override
    public void drawShape(Statek context, Graphics2D g) {
        g.setColor(color);
        inner.drawShape(context, g);
    }
}
