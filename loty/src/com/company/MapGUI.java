package com.company;

import com.sun.javafx.geom.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.LinkedList;

import static com.company.UtilityFunctions.*;

/**
 * MapGui is responsible for handling the design of the upper-left
 * window of graphic user interface and actions connected with proper
 * simulation of the flights.
 */
public class MapGUI extends JPanel implements MouseListener,MouseMotionListener {
    /**
     * List of the ships on the map.
     * @see Statek
     */
    private ArrayList<Statek> ships;
    /**
     * List containing the buildings placed on the map.
     * @see Budynek
     */
    private ArrayList<Budynek> buildings;
    /**
     * List of ships id which are in danger
     * @see Radar
     */
    private LinkedList<Integer> warnings;
    /**
     * List of ships danger zones which are shapes
     */
    private ArrayList<Shape> warning_boxes;
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
     * @param dim Size of the frame.
     */
    public MapGUI(Dimension dim){
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
        setBackground(Color.WHITE);
    }

    /**
     * Constructor of the the upper-left
     * window of graphic user interface.
     * @param dim Size of the frame.
     * @param ships List of the objects currently positioned on the map.
     * @param buildings List of the buildings on the map.
     * @author Daniel Skórczyński
     */
    public MapGUI(Dimension dim, ArrayList<Statek> ships, ArrayList<Budynek> buildings){
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(dim);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.ships = new ArrayList<>(ships);
        this.buildings = new ArrayList<>(buildings);
        this.warning_boxes = new ArrayList<>();
    }

    /**
     * Repaints the upper-left
     * window of graphic user interface.
     * @param ships List of the objects currently positioned on the map.
     * @param buildings  List of the buildings on the map.
     * @param warnings List of the ships in danger
     * @author Daniel Skórczyński
     */
    public void draw(final ArrayList<Statek> ships, ArrayList<Budynek> buildings, LinkedList<Integer> warnings ){
        /* Przyjmuje liste budynkow i statkow */
        this.ships = ships;
        this.buildings = buildings;
        this.warnings = warnings;
        repaint();  //przerysowuje okno
    }

    /**
     * Draws the upper-left
     * window of graphic user interface.
     * @param g_origin the <code>Graphics</code> object to protect
     * @author Daniel Skórczyński
     * @author Magdalena Sawicka
     */
    public void paintComponent(Graphics g_origin){
        Graphics2D g = (Graphics2D)g_origin;
        super.paintComponent(g);
        /* AntiAliasing */
        if(antiAliasing) {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        else {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }


        /* Rysuje budynki */
        g.setColor(Color.GRAY);
        for(Budynek b: buildings){
            g.fillRect((int)(b.getSrodek().x - b.getDlugosc()/2),(int)(b.getSrodek().y - b.getSzerokosc()/2),
                    (int)b.getDlugosc(),(int)b.getSzerokosc());
        }

        hover_again = false;

        // Definicja obszaru zagrożeń
        if(showWarnings) {
            Area shape = new Area(new Rectangle(getWidth(),getHeight()));
        }

        /* Rysuje statki */
        // for
        for (int i = 0; i < ships.size(); i++) {
            Statek s = ships.get(i);
            //if (!in_bounds(getWidth(), getHeight(), s.getPozycja())) continue;

            /* Rysuje zagrożenia */
            if(showWarnings && warnings != null) {
                for (Integer j: warnings){
                    if(j == s.id)
                        warning_boxes.add(new Ellipse2D.Double(s.getPozycja().x-25,s.getPozycja().y-25,50,50));
                }
            }

            /* Rysuje box aktywnego statku */
            if (s.id == activeShip && showActiveBox) {
                g.setColor(new Color(179, 117, 255, 255));
                final float[] dash1 = {3.0f};
                final BasicStroke dashed =
                        new BasicStroke(3.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                2.0f, dash1, 0.0f);
                g.setStroke(dashed);
                g.drawRect((int) s.getPozycja().x - 20, (int) s.getPozycja().y - 20, 40, 40);
            }

            /* Rysuje aktywną trasę */
            drawCourse(s,g);

            if(!hover_again){
                hover_again = hover(s);
            }

            if (s instanceof Balon) {
                if (s.id == activeShip && showActive) {
                    g.setColor(activeColor());
                }
                else {
                    g.setColor(Color.cyan);
                }

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }

                g.fillOval((int) s.getPozycja().x - 5, (int) s.getPozycja().y - 5, 10, 10);

                if(showId){
                    Font baseFont = new Font("monospaced", Font.PLAIN, 12);
                    g.setFont(baseFont);
                    g.drawString(Integer.toString(s.id), (int) s.getPozycja().x - 10, (int) s.getPozycja().y + 15);
                }
            } else if (s instanceof samolot) {
                if (s.id == activeShip && showActive) {
                    g.setColor(activeColor());
                } else
                    g.setColor(Color.RED);
                int x = (int) s.getPozycja().x - 6;
                int y = (int) s.getPozycja().y - 6;

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }

                g.fillPolygon(new int[]{x, x + 6, x + 12}, new int[]{y, y + 12, y}, 3);

                if(showId){
                    Font baseFont = new Font("monospaced", Font.PLAIN, 12);
                    g.setFont(baseFont);
                    g.drawString(Integer.toString(s.id), (int) s.getPozycja().x - 12, (int) s.getPozycja().y + 16);
                }
            } else if (s instanceof Helikopter) {
                if (s.id == activeShip && showActive) {
                    g.setColor(activeColor());
                } else
                    g.setColor(new Color(67, 255, 0, 255));

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }

                g.fillRect((int) s.getPozycja().x - 10, (int) s.getPozycja().y - 1, 20, 2);
                g.fillRect((int) s.getPozycja().x - 1, (int) s.getPozycja().y - 10, 2, 20);

                if (s.id == activeShip && showActive)
                    g.setColor(activeColor());
                else
                    g.setColor(new Color(54, 204, 0, 255));

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }

                if(showId){
                    Font baseFont = new Font("monospaced", Font.PLAIN, 12);
                    g.setFont(baseFont);
                    g.drawString(Integer.toString(s.id), (int) s.getPozycja().x - 20, (int) s.getPozycja().y + 15);
                }
            } else if (s instanceof Szybowiec) {
                if (s.id == activeShip && showActive) {
                    g.setColor(activeColor());
                } else
                    g.setColor(Color.BLACK);

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }

                int x = (int) s.getPozycja().x;
                int y = (int) s.getPozycja().y;
                g.setStroke(new BasicStroke(2));

                Vec2d sec = new Vec2d(x, y - 8);
                g.drawLine(x, y, (int) sec.x, (int) sec.y);
                sec = rotate(new Vec2d(x, y), sec, Math.toRadians(72));
                g.drawLine(x, y, (int) sec.x, (int) sec.y);
                sec = rotate(new Vec2d(x, y), sec, Math.toRadians(72));
                g.drawLine(x, y, (int) sec.x, (int) sec.y);
                sec = rotate(new Vec2d(x, y), sec, Math.toRadians(72));
                g.drawLine(x, y, (int) sec.x, (int) sec.y);
                sec = rotate(new Vec2d(x, y), sec, Math.toRadians(72));
                g.drawLine(x, y, (int) sec.x, (int) sec.y);

                if(showId){
                    Font baseFont = new Font("monospaced", Font.PLAIN, 12);
                    g.setFont(baseFont);
                    g.drawString(Integer.toString(s.id), (int) s.getPozycja().x - 15, (int) s.getPozycja().y + 16);
                }
            } else if (s instanceof Sterowiec) {
                if (s.id == activeShip && showActive) {
                    g.setColor(activeColor());
                } else
                    g.setColor(Color.ORANGE);

                if (ships.indexOf(s) == hover) {
                    g.setColor(activeColor());
                }
                g.fillRect((int) s.getPozycja().x - 5, (int) s.getPozycja().y - 5, 10, 10);

                if(showId){
                    Font baseFont = new Font("monospaced", Font.PLAIN, 12);
                    g.setFont(baseFont);
                    g.drawString(Integer.toString(s.id), (int) s.getPozycja().x - 10, (int) s.getPozycja().y + 16);
                }

            }

            if(editMode && (s.id == activeShip)) {
                drawEditedCourse(s,g);
            }

        } //for

        if(warning_boxes.size() != 0 && showWarnings){
            Shape all_warnings = addAllShapes(warning_boxes);
            g.setColor(pulsingColor(new Color(255, 19, 0, 255),0.6));
            final float[] dash1 = {2.5f};
            final BasicStroke dashed =
                    new BasicStroke(2.5f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_ROUND,
                            1.0f, dash1, 0.0f);
            g.setStroke(dashed);
            g.draw(all_warnings);
        }
        warning_boxes.clear();
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
        for( Statek s: ships ){
            if(Vec2d.distance(s.getPozycja().x,s.getPozycja().y, e.getX(), e.getY()) < 25){
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
     * @param s ship to check
     * @return boolean, true if cursor pointing ship, false if cursor are not pointing the ship
     *
     * @author Daniel Skórczyński
     */
    private boolean hover(Statek s){
        if(!trackable) {
            hover = -1;
            return false;
        } else {
            double mX,mY;
            try{
                mX = getMousePosition().getX();
            } catch (Exception NullPointerException){
                mX = -200;
            }
            try{
                mY = getMousePosition().getY();
            } catch (Exception NullPointerException){
                mY = -200;
            }
            if(Vec2d.distance(s.getPozycja().x,s.getPozycja().y, mX,mY) < 25){
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
     * @param a List of the all shapes
     * @return Area, which is collection of all combined shapes
     *
     * @author Daniel Skórczyński
     * @see Area
     * @see Shape
     */
    private Area addAllShapes(ArrayList<Shape> a){
        if(a.size() == 0) return null;
        Area all = new Area(a.get(0));
        for (int i = 1; i < a.size(); i++)
            all.add(new Area(a.get(i)));
        return all;
    }

    /**
     *
     * @author Daniel Skórczyński
     *
     * @param s active ship which edited course will be displayed
     * @param g graphic element
     */
    private void drawCourse(Statek s, Graphics2D g){
        if(s.id == activeShip && (showCourse || editMode)){
            if(!editMode) {
                g.setStroke(new BasicStroke(1));
            } else {
                g.setStroke(new BasicStroke(2));
            }
            if(!editMode) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(new Color(99, 107, 118,255));
            }

            for(Waypoint w : s.trasa){
                if(s.trasa.indexOf(w) == 0) continue;

                g.drawLine( (int)s.trasa.get(s.trasa.indexOf(w)-1).coord.x,
                        (int)s.trasa.get(s.trasa.indexOf(w)-1).coord.y,
                        (int)w.coord.x, (int)w.coord.y);

                g.fillOval( (int)s.trasa.get(s.trasa.indexOf(w)-1).coord.x-2,
                        (int)s.trasa.get(s.trasa.indexOf(w)-1).coord.y-2,
                        4,4);
            }
        }
    }
    /**
     * @param s flying object
     * @param g graphic element
     * @author Daniel Skórczyński
     */
    private void drawEditedCourse(Statek s, Graphics2D g){
        g.setStroke(new BasicStroke(2));

        LinkedList<Waypoint> temp = new LinkedList<>(s.trasa);
        int k = s.getN_kurs()+1;
        temp.add(s.getN_kurs(),new Waypoint(s.getPozycja(),s.getWysokosc(),s.getPredkosc()));

        if(angleShift == 0) {
            temp.set(k,new Waypoint(shift_vector(temp.get(k).coord,shift),
                    temp.get(k).wysokosc,temp.get(k).predkosc));
        } else {
            int last = temp.indexOf(temp.getLast());
            for(int j = k; j < last; j++){
                temp.set(j,new Waypoint(new Vec2d(rotate(temp.get(j-1).coord,temp.get(j).coord,Math.toRadians(angleShift))),
                        temp.get(j).wysokosc,temp.get(j).predkosc));
            }
        }
        g.setColor(pulsingColor(new Color(255,0,0,255),0.4));
        for(Waypoint w : temp){
            if(temp.indexOf(w) == 0) continue;

            g.drawLine( (int)temp.get(temp.indexOf(w)-1).coord.x,
                    (int)temp.get(temp.indexOf(w)-1).coord.y,
                    (int)w.coord.x, (int)w.coord.y);

            g.fillOval( (int)temp.get(temp.indexOf(w)-1).coord.x-2,
                    (int)temp.get(temp.indexOf(w)-1).coord.y-2,
                    4,4);
        }
    }
}
