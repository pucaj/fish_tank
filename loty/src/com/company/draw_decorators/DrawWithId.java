package com.company.draw_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawWithId extends State {
    private State inner;

    public DrawWithId(State inner) {
        this.inner = inner;
    }

    @Override
    public void draw(Statek context, Graphics2D g) {
        context.drawId(g);
        inner.draw(context, g);
    }
}
