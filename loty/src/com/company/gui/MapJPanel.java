package com.company.gui;

import com.company.buildings.Budynek;
import com.company.Radar;
import com.company.map_drawing_templates.DrawMapUsingImages;
import com.company.map_drawing_templates.DrawMapUsingShapes;
import com.company.states.State;
import com.company.ships.Statek;
import com.company.states.InDangerState;
import com.company.states.NormalState;
import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.company.UtilityFunctions.*;

/**
 * MapGui is responsible for handling the design of the upper-left
 * window of graphic user interface and actions connected with proper
 * simulation of the flights.
 */
public class MapJPanel extends JPanel implements MouseListener, MouseMotionListener {
    public void setHover(int hover) {
        this.hover = hover;
    }

    public void setTrackable(boolean trackable) {
        this.trackable = trackable;
    }

    public void setHover_again(boolean hover_again) {
        this.hover_again = hover_again;
    }

    /**
     * List of the ships on the map.
     *
     * @see Statek
     */
    private final State[] states = {new NormalState(), new InDangerState()};

    public ArrayList<Statek> getShips() {
        return ships;
    }

    public ArrayList<Budynek> getBuildings() {
        return buildings;
    }

    public LinkedList<Integer> getWarnings() {
        return warnings;
    }

    public boolean isTrackable() {
        return trackable;
    }

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

    public int getHover() {
        return hover;
    }

    /**
     * Ship id which is pointed by mouse
     */
    private int hover = -1;
    /**
     * Tells if the mouse is in the map bounds or not
     */
    private boolean trackable = false;

    public boolean isNotHover_again() {
        return !hover_again;
    }

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
        if (imageRepresentation) {
            new DrawMapUsingImages(this, g).draw();
        } else {
            new DrawMapUsingShapes(this, g).draw();
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
    public boolean hover(Statek s) {
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

}
