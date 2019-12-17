package com.company.ships;

import com.company.buildings.Budynek;

import java.awt.*;
import java.util.ArrayList;

/**
 * Sterowiec is one of the types of <i>statek</i>.
 *
 * @author Magdalena Sawicka
 */
public class Sterowiec extends Statek {

    /**
     * Creates new object with the given parameters.
     *
     * @param i    Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud  List of the buildings placed on the map.
     */
    public Sterowiec(int i, double dlug, double szer, ArrayList<Budynek> bud) {
        super(i, 125.0, 2600.0, dlug, szer, bud);
        color = Color.ORANGE;
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
    public Sterowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation) {
        super(i, 125.0, 2600.0, dlug, szer, bud, variation);
        color = Color.ORANGE;
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
    public Sterowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje) {
        super(i, 125.0, 2600.0, dlug, szer, bud, przylatuje);
        color = Color.ORANGE;
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
    public Sterowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje) {
        super(i, 125.0, 2600.0, dlug, szer, bud, variation, przylatuje);
        color = Color.ORANGE;
    }

    public Sterowiec(Sterowiec s) {
        super(s);
        color = Color.ORANGE;
    }


    @Override
    public void draw(Graphics2D g) {
        state.draw(this, g);
    }


    @Override
    public void drawShape(Graphics g) {
        g.fillRect((int) pozycja.x - 5, (int) pozycja.y - 5, 10, 10);
    }

    @Override
    public void drawId(Graphics2D g) {
        g.drawString(Integer.toString(id), (int) pozycja.x - 10, (int) pozycja.y + 16);

    }

    public Color getColor() {
        return color;
    }
}
