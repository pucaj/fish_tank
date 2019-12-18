package com.company.ships;

import com.company.buildings.Budynek;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Samolot is one of the types of <i>statek</i>.
 *
 * @author Magdalena Sawicka
 */
public class Samolot extends Statek {

    /**
     * Creates new object with the given parameters.
     *
     * @param i    Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud  List of the buildings placed on the map.
     */
    public Samolot(int i, double dlug, double szer, ArrayList<Budynek> bud) {
        super(i, 900.0, 11000.0, dlug, szer, bud);
        color = Color.RED;
        try {
            loadImage("plane.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i         Identification number.
     * @param dlug      Map's height value.
     * @param szer      Map's width value.
     * @param bud       List of the buildings placed on the map.
     * @param variation The deviation from the mean.
     */
    public Samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation) {
        super(i, 900.0, 11000.0, dlug, szer, bud, variation);
        color = Color.RED;
        try {
            loadImage("plane.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i          Identification number.
     * @param dlug       Map's height value.
     * @param szer       Map's width value.
     * @param bud        List of the buildings placed on the map.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     */
    public Samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje) {
        super(i, 900.0, 11000.0, dlug, szer, bud, przylatuje);
        color = Color.RED;
        try {
            loadImage("plane.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i          Identification number.
     * @param dlug       Map's height value.
     * @param szer       Map's width value.
     * @param bud        List of the buildings placed on the map.
     * @param variation  The deviation from the mean.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     */
    public Samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje) {
        super(i, 900.0, 11000.0, dlug, szer, bud, variation, przylatuje);
        color = Color.RED;
        try {
            loadImage("plane.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Samolot(Samolot s) {
        super(s);
        color = Color.RED;
        try {
            loadImage("plane.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void drawShape(Graphics2D g) {
        int x = (int) pozycja.x - 6;
        int y = (int) pozycja.y - 6;
        g.fillPolygon(new int[]{x, x + 6, x + 12}, new int[]{y, y + 12, y}, 3);
    }

    @Override
    public void drawId(Graphics2D g) {
        g.drawString(Integer.toString(id), (int) pozycja.x - 22, (int) pozycja.y + 26);
    }

    public Color getColor() {
        return color;
    }
}
