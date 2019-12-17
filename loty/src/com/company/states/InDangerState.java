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
        g.setColor(pulsingColor(new Color(255, 19, 0, 255), 0.6));
        final float[] dash1 = {2.5f};
        final BasicStroke dashed =
                new BasicStroke(2.5f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND,
                        1.0f, dash1, 0.0f);
        g.setStroke(dashed);
        g.draw(new Area(new Ellipse2D.Double(context.getPozycja().x - 25, context.getPozycja().y - 25, 50, 50)));
        g.setColor(context.getColor());
        g.setColor(context.getColor());
        context.drawShape(g);
    }

    public void avoid(){
        //TODO napisać implementację metody avoid() oraz strategie
    }
}
