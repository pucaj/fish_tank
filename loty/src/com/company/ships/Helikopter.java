package com.company.ships;

import com.company.buildings.Budynek;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Helikopter is one of the types of <i>statek</i>.
 *
 * @author Magdalena Sawicka
 */
public class Helikopter extends Statek {
    public Helikopter() {
        predkosc = 300.0;
        wysokosc = 5100.0;

        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i    Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud  List of the buildings placed on the map.
     */
    public Helikopter(int i, double dlug, double szer, ArrayList<Budynek> bud) {
        super(i, 300.0, 5100.0, dlug, szer, bud);
        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
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
    public Helikopter(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation) {
        super(i, 300.0, 5100.0, dlug, szer, bud, variation);
        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
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
    public Helikopter(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje) {
        super(i, 300.0, 5100.0, dlug, szer, bud, przylatuje);
        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
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
    public Helikopter(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje) {
        super(i, 300.0, 5100.0, dlug, szer, bud, variation, przylatuje);
        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Helikopter(Helikopter h) {
        super(h);
        color = new Color(67, 255, 0, 255);
        try {
            loadImage("heli.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawId(Graphics2D g) {
        g.drawString(Integer.toString(id), (int) pozycja.x - 30, (int) pozycja.y + 25);
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void drawShape(Graphics2D g) {
        g.fillRect((int) pozycja.x - 10, (int) pozycja.y - 1, 20, 2);
        g.fillRect((int) pozycja.x - 1, (int) pozycja.y - 10, 2, 20);
    }
}
