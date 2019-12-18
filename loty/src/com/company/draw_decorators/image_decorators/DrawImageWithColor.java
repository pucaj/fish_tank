package com.company.draw_decorators.image_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawImageWithColor extends State {

    private final State inner;
    private final Color color;

    public DrawImageWithColor(State inner, Color color) {
        this.inner = inner;
        this.color = color;
    }

    @Override
    public void drawImg(Statek context, Graphics2D g) {
        g.setColor(color);
        inner.drawImg(context, g);
    }
}
