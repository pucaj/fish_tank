package com.company.states;

import com.company.ships.Statek;

import java.awt.*;

import static com.company.UtilityFunctions.activeColor;

public class ActiveState extends State {
    @Override
    public void draw(Statek context, Graphics2D g) {
//        g.setColor(new Color(179, 117, 255, 255));
        final float[] dash1 = {3.0f};
        final BasicStroke dashed =
                new BasicStroke(3.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        2.0f, dash1, 0.0f);
        g.setStroke(dashed);
        g.drawRect((int) context.getPozycja().x - 20, (int) context.getPozycja().y - 20, 40, 40);
        g.setColor(activeColor());
        context.drawShape(g);
    }
}
