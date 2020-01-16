package com.company.ships;

import com.company.Vec2d;
import com.company.buildings.Budynek;
import com.company.UtilityFunctions;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Szybowiec is one of the types of <i>statek</i>.
 *
 * @author Magdalena Sawicka
 */
public class Szybowiec extends Statek {
    public Szybowiec() {
        predkosc = 300.0;
        wysokosc = 14000.0;

        try {
            loadImage("glider.png");
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
    public Szybowiec(int i, double dlug, double szer, ArrayList<Budynek> bud) {
        super(i, 300.0, 14000.0, dlug, szer, bud);
        try {
            loadImage("glider.png");
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
    public Szybowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation) {
        super(i, 300.0, 14000.0, dlug, szer, bud, variation);
        try {
            loadImage("glider.png");
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
    public Szybowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje) {
        super(i, 300.0, 14000.0, dlug, szer, bud, przylatuje);
        try {
            loadImage("glider.png");
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
    public Szybowiec(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje) {
        super(i, 300.0, 14000.0, dlug, szer, bud, variation, przylatuje);
        try {
            loadImage("glider.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Szybowiec(Szybowiec sz) {
        super(sz);
        try {
            loadImage("glider.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Color color = Color.BLACK;

    @Override
    public void drawShape(Graphics2D g) {
        int x = (int) pozycja.x;
        int y = (int) pozycja.y;
        g.setStroke(new BasicStroke(2));

        Vec2d sec = new Vec2d(x, y - 8);
        g.drawLine(x, y, (int) sec.x, (int) sec.y);
        sec = UtilityFunctions.rotate(new Vec2d(x, y), sec, Math.toRadians(72));
        g.drawLine(x, y, (int) sec.x, (int) sec.y);
        sec = UtilityFunctions.rotate(new Vec2d(x, y), sec, Math.toRadians(72));
        g.drawLine(x, y, (int) sec.x, (int) sec.y);
        sec = UtilityFunctions.rotate(new Vec2d(x, y), sec, Math.toRadians(72));
        g.drawLine(x, y, (int) sec.x, (int) sec.y);
        sec = UtilityFunctions.rotate(new Vec2d(x, y), sec, Math.toRadians(72));
        g.drawLine(x, y, (int) sec.x, (int) sec.y);
    }

    @Override
    public void drawId(Graphics2D g) {
        g.drawString(Integer.toString(id), (int) pozycja.x - 25, (int) pozycja.y + 26);
    }

    public Color getColor() {
        return color;
    }
}
