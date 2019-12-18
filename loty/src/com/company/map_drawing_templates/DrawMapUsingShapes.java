package com.company.map_drawing_templates;

import com.company.draw_decorators.shape_decorators.DrawShapeWithCircleBorder;
import com.company.draw_decorators.shape_decorators.DrawShapeWithColor;
import com.company.draw_decorators.shape_decorators.DrawShapeWithId;
import com.company.draw_decorators.shape_decorators.DrawShapeWithSquareBorder;
import com.company.gui.MapJPanel;
import com.company.ships.Statek;
import com.company.states.State;

import java.awt.*;

import static com.company.UtilityFunctions.*;

public class DrawMapUsingShapes extends DrawMap {
    public DrawMapUsingShapes(MapJPanel panel, Graphics2D g) {
        super(panel, g);
    }


    @Override
    protected void drawWarnings(Statek statek) {
        if (showWarnings && panel.getWarnings() != null) {
            panel.getWarnings().forEach(j -> {
                if (j == statek.id) {
                    statek.setState(
                            new DrawShapeWithColor(
                                    new DrawShapeWithCircleBorder(states[State.IN_DANGER_STATE]),
                                    pulsingColor(new Color(255, 19, 0, 255), 0.6)));
                }
            });
        }
    }

    @Override
    protected void drawStatki() {
        for (int i = 0; i < panel.getShips().size(); i++) {
            Statek statek = panel.getShips().get(i);
            statek.setState(new DrawShapeWithColor(states[State.NORMAL_STATE], statek.getColor()));
            if (panel.isNotHover_again()) {
                panel.setHover_again(panel.hover(statek));
            }
            drawCourse(statek, g);
            drawWarnings(statek);
            if (panel.getShips().indexOf(statek) == panel.getHover())
                statek.setState(new DrawShapeWithColor(states[State.NORMAL_STATE], activeColor()));
            if (statek.id == activeShip && showActive)
                if (showActiveBox)
                    statek.setState(
                            new DrawShapeWithColor(
                                    new DrawShapeWithSquareBorder(new DrawShapeWithColor(states[State.NORMAL_STATE], activeColor())),
                                    statek.getColor())
                    );
                else
                    statek.setState(new DrawShapeWithColor(states[State.NORMAL_STATE], activeColor()));
            if (showId)
                statek.setState(new DrawShapeWithColor(new DrawShapeWithId(statek.getState()), statek.getColor()));


            statek.draw(g);
            drawEditedCourse(statek, g);
        }
    }
}
