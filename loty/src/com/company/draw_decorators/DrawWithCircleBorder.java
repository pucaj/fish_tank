package com.company.draw_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class DrawWithCircleBorder extends State {
    private State inner;

    public DrawWithCircleBorder(State inner) {
        this.inner = inner;
    }

    @Override
    public void draw(Statek context, Graphics2D g) {
        final float[] dash1 = {2.5f};
        final BasicStroke dashed =
                new BasicStroke(2.5f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND,
                        1.0f, dash1, 0.0f);
        g.setStroke(dashed);
        g.draw(new Area(new Ellipse2D.Double(context.getPozycja().x - 25, context.getPozycja().y - 25, 50, 50)));
        inner.draw(context, g);
    }
}
