package com.company.gui;

import com.company.buildings.Budynek;
import com.company.Radar;
import com.company.states.State;
import com.company.Waypoint;
import com.company.ships.Statek;
import com.company.states.ActiveState;
import com.company.states.InDangerState;
import com.company.states.MouseOverState;
import com.company.states.NormalState;
import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.company.UtilityFunctions.*;

/**
 * MapGui is responsible for handling the design of the upper-left
 * window of graphic user interface and actions connected with proper
 * simulation of the flights.
 */
public class MapJPanel extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * List of the ships on the map.
     *
     * @see Statek
     */
    private final State[] states = {new NormalState(), new MouseOverState(), new ActiveState(), new InDangerState()};
    private ArrayList<Statek> ships;
    /**
     * List containing the buildings placed on the map.
     *
     * @see Budynek
     */
    private ArrayList<Budynek> buildings;
    /**
     * List of ships id which are in danger
     *
     * @see Radar
     */
    private LinkedList<Integer> warnings;
    /**
     * Ship id which is pointed by mouse
     */
    private int hover = -1;
    /**
     * Tells if the mouse is in the map bounds or not
     */
    private boolean trackable = false;
    /**
     * Tells that the ship can be hover again or not
     */
    private boolean hover_again = true;


    /**
     * Constructor of the the upper-left
     * window of graphic user interface
     *
     * @param dim Size of the frame.
     */
    public MapJPanel(Dimension dim) {
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
        setBackground(Color.WHITE);
    }

    /**
     * Constructor of the the upper-left
     * window of graphic user interface.
     *
     * @param dim       Size of the frame.
     * @param ships     List of the objects currently positioned on the map.
     * @param buildings List of the buildings on the map.
     * @author Daniel Skórczyński
     */
    public MapJPanel(Dimension dim, ArrayList<Statek> ships, ArrayList<Budynek> buildings) {
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.ships = new ArrayList<>(ships);
        this.buildings = new ArrayList<>(buildings);
    }

    /**
     * Repaints the upper-left
     * window of graphic user interface.
     *
     * @param ships     List of the objects currently positioned on the map.
     * @param buildings List of the buildings on the map.
     * @param warnings  List of the ships in danger
     * @author Daniel Skórczyński
     */
    public void draw(final ArrayList<Statek> ships, ArrayList<Budynek> buildings, LinkedList<Integer> warnings) {
        /* Przyjmuje liste budynkow i statkow */
        this.ships = ships;
        this.buildings = buildings;
        this.warnings = warnings;
        repaint();  //przerysowuje okno
    }

    /**
     * Draws the upper-left
     * window of graphic user interface.
     *
     * @param g_origin the <code>Graphics</code> object to protect
     * @author Daniel Skórczyński
     * @author Magdalena Sawicka
     */
    public void paintComponent(Graphics g_origin) {
        Graphics2D g = (Graphics2D) g_origin;
        super.paintComponent(g);
        g.setFont(new Font("monospaced", Font.PLAIN, 12));
        /* AntiAliasing */
        if (antiAliasing) {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }


        /* Rysuje budynki */
        g.setColor(Color.GRAY);
        buildings.forEach(b -> b.draw(g));

        hover_again = false;

        for (int i = 0; i < ships.size(); i++) {
            Statek statek = ships.get(i);
            statek.setState(states[State.NORMAL_STATE]);
            if (!hover_again) {
                hover_again = hover(statek);
            }
            drawCourse(statek, g);
            if (showWarnings) {
                warnings.forEach(j -> {
                    if (j == statek.id) {
                        statek.setState(states[State.IN_DANGER_STATE]);
                    }
                });
            }
            if (ships.indexOf(statek) == hover)
                statek.setState(states[State.MOUSE_OVER_STATE]);
            if (statek.id == activeShip && showActive)
                //TODO dodać dekoratory, ustawiający kolor lub rysujący ramkę
                if (showActiveBox)
                    statek.setState(states[State.ACTIVE_STATE]);
                else
                    statek.setState(states[State.ACTIVE_STATE]);
            statek.draw(g);
            if (showId)
                statek.drawId(g);
            if (editMode && (statek.id == activeShip)) {
                drawEditedCourse(statek, g);
            }
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        trackable = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        trackable = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Statek s : ships) {
            if (Vec2d.distance(s.getPozycja().x, s.getPozycja().y, e.getX(), e.getY()) < 25) {
                activeShip = s.id;
                break;
            } else {
                activeShip = -1;
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * The hover is used to select elements when you mouse over them.
     * Checking if cursor pointing ship <code>s</code>
     *
     * @param s ship to check
     * @return boolean, true if cursor pointing ship, false if cursor are not pointing the ship
     * @author Daniel Skórczyński
     */
    private boolean hover(Statek s) {
        if (!trackable) {
            hover = -1;
            return false;
        } else {
            double mX, mY;
            try {
                mX = getMousePosition().getX();
            } catch (Exception NullPointerException) {
                mX = -200;
            }
            try {
                mY = getMousePosition().getY();
            } catch (Exception NullPointerException) {
                mY = -200;
            }
            if (Vec2d.distance(s.getPozycja().x, s.getPozycja().y, mX, mY) < 25) {
                hover = ships.indexOf(s);
                return true;
            } else {
                hover = -1;
                return false;
            }
        }
    }

    /**
     * Joining all shapes to one area.
     *
     * @param a List of the all shapes
     * @return Area, which is collection of all combined shapes
     * @author Daniel Skórczyński
     * @see Area
     * @see Shape
     */
    private Area addAllShapes(ArrayList<Shape> a) {
        if (a.size() == 0) return null;
        Area all = new Area(a.get(0));
        for (int i = 1; i < a.size(); i++)
            all.add(new Area(a.get(i)));
        return all;
    }

    /**
     * @param s active ship which edited course will be displayed
     * @param g graphic element
     * @author Daniel Skórczyński
     */
    private void drawCourse(Statek s, Graphics2D g) {
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
    private void drawEditedCourse(Statek s, Graphics2D g) {
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
