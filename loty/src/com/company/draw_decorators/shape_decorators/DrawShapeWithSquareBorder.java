package com.company.draw_decorators.shape_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawShapeWithSquareBorder extends State {
    private State inner;

    public DrawShapeWithSquareBorder(State state) {
        inner = state;
    }

    @Override
    public void drawShape(Statek context, Graphics2D g) {
        final float[] dash1 = {3.0f};
        final BasicStroke dashed =
                new BasicStroke(3.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        2.0f, dash1, 0.0f);
        g.setStroke(dashed);
        g.drawRect((int) context.getPozycja().x - 20, (int) context.getPozycja().y - 20, 40, 40);
        inner.drawShape(context, g);
    }
}
