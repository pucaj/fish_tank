package com.company.map_drawing_templates;

import com.company.Vec2d;
import com.company.Waypoint;
import com.company.draw_decorators.shape_decorators.DrawShapeWithCircleBorder;
import com.company.draw_decorators.shape_decorators.DrawShapeWithColor;
import com.company.gui.MapJPanel;
import com.company.ships.Statek;
import com.company.states.InDangerState;
import com.company.states.NormalState;
import com.company.states.State;

import java.awt.*;
import java.util.LinkedList;

import static com.company.UtilityFunctions.*;
import static com.company.UtilityFunctions.editMode;


public abstract class DrawMap {
    protected MapJPanel panel;
    protected final Graphics2D g;
    protected final State[] states = {new NormalState(), new InDangerState()};

    public DrawMap(MapJPanel panel, Graphics2D g) {
        this.panel = panel;
        this.g = g;
    }

    public void draw() {
        setOptions();
        drawBudynki();
        panel.setHover_again(false);
        drawStatki();
    }

    protected abstract void drawStatki();

    private void setOptions() {
        g.setFont(new Font("monospaced", Font.PLAIN, 12));
        if (antiAliasing) {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }


    private void drawBudynki() {
        g.setColor(Color.GRAY);
        panel.getBuildings().forEach(b -> b.draw(g));
    }


    /**
     * @param s active ship which edited course will be displayed
     * @param g graphic element
     * @author Daniel Skórczyński
     */
    protected void drawCourse(Statek s, Graphics2D g) {
        if (s.id == activeShip && (showCourse || editMode)) {
            if (!editMode) {
                g.setStroke(new BasicStroke(1));
            } else {
                g.setStroke(new BasicStroke(2));
            }
            if (!editMode) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(new Color(99, 107, 118, 255));
            }

            for (Waypoint w : s.trasa) {
                if (s.trasa.indexOf(w) == 0) continue;

                g.drawLine((int) s.trasa.get(s.trasa.indexOf(w) - 1).coord.x,
                        (int) s.trasa.get(s.trasa.indexOf(w) - 1).coord.y,
                        (int) w.coord.x, (int) w.coord.y);

                g.fillOval((int) s.trasa.get(s.trasa.indexOf(w) - 1).coord.x - 2,
                        (int) s.trasa.get(s.trasa.indexOf(w) - 1).coord.y - 2,
                        4, 4);
            }
        }
    }

    /**
     * @param s flying object
     * @param g graphic element
     * @author Daniel Skórczyński
     */
    protected void drawEditedCourse(Statek s, Graphics2D g) {
        if (editMode && (s.id == activeShip)) {
            g.setStroke(new BasicStroke(2));

            LinkedList<Waypoint> temp = new LinkedList<>(s.trasa);
            int k = s.getN_kurs() + 1;
            temp.add(s.getN_kurs(), new Waypoint(s.getPozycja(), s.getWysokosc(), s.getPredkosc()));

            if (angleShift == 0) {
                temp.set(k, new Waypoint(shift_vector(temp.get(k).coord, shift),
                        temp.get(k).wysokosc, temp.get(k).predkosc));
            } else {
                int last = temp.indexOf(temp.getLast());
                for (int j = k; j < last; j++) {
                    temp.set(j, new Waypoint(new Vec2d(rotate(temp.get(j - 1).coord, temp.get(j).coord, Math.toRadians(angleShift))),
                            temp.get(j).wysokosc, temp.get(j).predkosc));
                }
            }
            g.setColor(pulsingColor(new Color(255, 0, 0, 255), 0.4));
            for (Waypoint w : temp) {
                if (temp.indexOf(w) == 0) continue;

                g.drawLine((int) temp.get(temp.indexOf(w) - 1).coord.x,
                        (int) temp.get(temp.indexOf(w) - 1).coord.y,
                        (int) w.coord.x, (int) w.coord.y);

                g.fillOval((int) temp.get(temp.indexOf(w) - 1).coord.x - 2,
                        (int) temp.get(temp.indexOf(w) - 1).coord.y - 2,
                        4, 4);
            }
        }
    }

    protected abstract void drawWarnings(Statek statek);


}
