package com.company.draw_decorators.shape_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawShapeWithId extends State {
    private State inner;

    public DrawShapeWithId(State inner) {
        this.inner = inner;
    }

    @Override
    public void drawShape(Statek context, Graphics2D g) {
        context.drawId(g);
        inner.drawShape(context, g);
    }
}
