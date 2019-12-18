package com.company.draw_decorators.image_decorators;

import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

public class DrawImageWithId extends State {
    private State inner;

    public DrawImageWithId(State inner) {
        this.inner = inner;
    }

    @Override
    public void drawImg(Statek context, Graphics2D g) {
        context.drawId(g);
        inner.drawImg(context, g);
    }
}
