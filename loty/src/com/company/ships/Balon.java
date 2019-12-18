package com.company.ships;

import com.company.buildings.Budynek;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Balon is one of the types of <i>statek</i>.
 *
 * @author Magdalena Sawicka
 */
public class Balon extends Statek {


    /**
     * Creates new object with the given parameters.
     *
     * @param i    Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud  List of the buildings placed on the map.
     */
    public Balon(int i, double dlug, double szer, ArrayList<Budynek> bud) {
        super(i, 20.0, 600.0, dlug, szer, bud);
        color = Color.cyan;
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
    public Balon(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation) {
        super(i, 20.0, 600.0, dlug, szer, bud, variation);
        color = Color.cyan;
        try {
            loadImage("balloon.png");
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
    public Balon(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje) {
        super(i, 20.0, 600.0, dlug, szer, bud, przylatuje);
        color = Color.cyan;
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
    public Balon(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje) {
        super(i, 20.0, 600.0, dlug, szer, bud, variation, przylatuje);
        color = Color.cyan;
        try {
            loadImage("balloon.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Balon(Balon b) {
        super(b);
        color = Color.cyan;
        try {
            loadImage("balloon.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void drawShape(Graphics2D g) {
        g.fillOval((int) getPozycja().x - 5, (int) getPozycja().y - 5, 10, 10);
    }

    @Override
    public void drawId(Graphics2D g2d) {
        g2d.drawString(String.valueOf(id), (int) pozycja.x - 20, (int) pozycja.y + 25);

    }

    public Color getColor() {
        return color;
    }
}
