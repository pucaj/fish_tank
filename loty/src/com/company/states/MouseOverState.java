package com.company.states;

import com.company.ships.Statek;

import java.awt.*;

import static com.company.UtilityFunctions.activeColor;

public class MouseOverState extends State {
    @Override
    public void draw(Statek context, Graphics2D g) {
        g.setColor(activeColor());
        context.drawShape(g);
    }
}
