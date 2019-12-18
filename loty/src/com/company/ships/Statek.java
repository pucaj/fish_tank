package com.company.ships;

import com.company.buildings.Budynek;
import com.company.states.State;
import com.company.Waypoint;
import com.company.states.NormalState;
import com.sun.javafx.geom.Vec2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.UtilityFunctions.*;


/**
 * Statek objects represent real
 * objects flying over the map.
 *
 * @author Daniel Skórczyński
 * @author Magdalena Sawicka
 * @author Paweł Raglis
 */
public abstract class Statek {
    public BufferedImage getImg() {
        return img;
    }

    /**
     * Identification number.
     */
    protected BufferedImage img;

    public int id;

    public Color getColor() {
        return color;
    }

    /**
     * Object's trajectory.
     */
    protected Color color;
    public LinkedList<Waypoint> trasa;
    /**
     * Current position.
     */
    protected Vec2d pozycja;
    /**
     * Current altitude measured in meters.
     */
    private double wysokosc;
    /**
     * Current velocity measured in kilometers per hour.
     */
    private double predkosc;
    /**
     * Waypoint that object is currently heading for.
     */
    private int n_kurs;
    protected State state;

    /**
     * Creates new object with the given parameters.
     *
     * @param i   Identification number.
     * @param v   Average  velocity measured in kilometers per hour.
     * @param h   Average altitude measured in meters.
     * @param dl  Map's height value.
     * @param sz  Map's width value.
     * @param bud List of the buildings placed on the map.
     * @throws IllegalArgumentException if given parameters are negative or equal zero
     * @author Magdalena Sawic  ka
     */
    public Statek(int i, double v, double h, double dl, double sz, ArrayList<Budynek> bud) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dl <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (sz <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        id = i;
        trasa = new LinkedList<>();
        losujTrase(v, h, dl, sz, bud);
        pozycja = trasa.getFirst().coord;
        wysokosc = trasa.getFirst().wysokosc;
        predkosc = trasa.getFirst().predkosc;
        n_kurs = 1;
        state = new NormalState();
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i         Identification number.
     * @param v         Average  velocity measured in kilometers per hour.
     * @param h         Average altitude measured in meters.
     * @param dl        Map's height value.
     * @param sz        Map's width value.
     * @param bud       List of the buildings placed on the map.
     * @param variation The deviation from the mean.
     * @throws IllegalArgumentException if given parameters are negative or equal zero
     * @author Magdalena Sawicka
     */
    public Statek(int i, double v, double h, double dl, double sz, ArrayList<Budynek> bud, double variation) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dl <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (sz <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        id = i;
        trasa = new LinkedList<>();
        losujTrase(v, h, dl, sz, bud, variation);
        pozycja = trasa.getFirst().coord;
        wysokosc = trasa.getFirst().wysokosc;
        predkosc = trasa.getFirst().predkosc;
        n_kurs = 1;
        state = new NormalState();
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i          Identification number.
     * @param v          Average  velocity measured in kilometers per hour.
     * @param h          Average altitude measured in meters.
     * @param dl         Map's height value.
     * @param sz         Map's width value.
     * @param bud        List of the buildings placed on the map.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     * @throws IllegalArgumentException if given parameters are negative or equal zero
     * @author Magdalena Sawicka
     */
    public Statek(int i, double v, double h, double dl, double sz, ArrayList<Budynek> bud, boolean przylatuje) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dl <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (sz <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        id = i;
        trasa = new LinkedList<>();
        losujTrase(v, h, dl, sz, bud, przylatuje);
        pozycja = trasa.getFirst().coord;
        wysokosc = trasa.getFirst().wysokosc;
        predkosc = trasa.getFirst().predkosc;
        n_kurs = 1;
        state = new NormalState();
    }

    /**
     * Creates new object with the given parameters.
     *
     * @param i          Identification number.
     * @param v          Average  velocity measured in kilometers per hour.
     * @param h          Average altitude measured in meters.
     * @param dl         Map's height value.
     * @param sz         Map's width value.
     * @param bud        List of the buildings placed on the map.
     * @param variation  The deviation from the mean.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     * @throws IllegalArgumentException if given parameters are negative or equal zero
     * @author Magdalena Sawicka
     */
    public Statek(int i, double v, double h, double dl, double sz, ArrayList<Budynek> bud, double variation, boolean przylatuje) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dl <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (sz <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        id = i;
        trasa = new LinkedList<>();
        losujTrase(v, h, dl, sz, bud, variation, przylatuje);
        pozycja = trasa.getFirst().coord;
        wysokosc = trasa.getFirst().wysokosc;
        predkosc = trasa.getFirst().predkosc;
        n_kurs = 1;
        state = new NormalState();
    }

    public Statek(Statek s) {
        this.id = s.id;
        this.trasa = s.trasa;
        this.pozycja = s.pozycja;
        this.wysokosc = s.wysokosc;
        this.predkosc = s.predkosc;
        this.n_kurs = s.n_kurs;
        state = new NormalState();
        this.color = s.color;
        this.img = s.img;
    }

    public abstract void drawShape(Graphics2D g);

    public abstract void drawId(Graphics2D g);

    public void drawImg(Graphics2D g) {
        state.drawImg(this, g);
    }

    public void draw(Graphics2D g) {
        state.drawShape(this, g);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void incrementN_kurs() {
        this.n_kurs += 1;
    }

    /**
     * This method simulates flight of the object.
     * It changes its parameters and position accordingly.
     *
     * @param time Delta time.
     * @throws IllegalArgumentException if time is negative or equal zero
     * @author Magdalena Sawicka
     */
    public void lec(double time) throws IllegalArgumentException {
        state.lec(this, time);
    }

    /**
     * This method generates randomly arc-shaped trajectory
     * for the object.
     *
     * @param v         Object's average velocity measured in kilometers per hour.
     * @param h         Object's average altitude measured in meters.
     * @param dlugosc   Map's height value.
     * @param szerokosc Map's width value.
     * @param budynki   List of the buildings placed on the map.
     * @throws IllegalArgumentException if constants values are negative or equal zero
     * @author Daniel Skórczyński
     */
    private void losujTrase(double v, double h, double dlugosc, double szerokosc, ArrayList<Budynek> budynki) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dlugosc <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (szerokosc <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        double randWys = rand_avg(h, 0.1);
        double randPred = rand_avg(v, 0.1);
        int odcinek = (int) Math.floor(dlugosc / 8), i = 1;

        trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki), randWys, randPred));
        double sgn = rand_sgn();

        trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, 0.1), rand_avg(v, 0.1)));

        while (in_bounds(dlugosc, szerokosc, trasa.get(i).coord)) {
            Vec2d dxdy = new Vec2d(create_shift_vector(trasa.get(i - 1).coord, trasa.get(i).coord));
            Vec2d nowa_pozycja = shift_vector(trasa.get(i).coord, dxdy, Math.toRadians(normalize_radius(sgn)));
            trasa.add(new Waypoint(nowa_pozycja, rand_avg(h, 0.1), rand_avg(v, 0.1)));
            ++i;
        }
    }

    /**
     * This method generates randomly arc-shaped trajectory
     * for the object.
     *
     * @param v         Object's average velocity measured in kilometers per hour.
     * @param h         Object's average altitude measured in meters.
     * @param dlugosc   Map's height value.
     * @param szerokosc Map's width value.
     * @param budynki   List of the buildings placed on the map.
     * @param variation The deviation from the mean.
     * @throws IllegalArgumentException if constants values are negative or equal zero
     * @author Daniel Skórczyński
     */
    private void losujTrase(double v, double h, double dlugosc, double szerokosc, ArrayList<Budynek> budynki, double variation) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dlugosc <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (szerokosc <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        double randWys = rand_avg(h, variation);
        double randPred = rand_avg(v, variation);
        int odcinek = (int) Math.floor(dlugosc / 8), i = 1;

        trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki), randWys, randPred));
        double sgn = rand_sgn();
        trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, variation), rand_avg(v, variation)));

        while (in_bounds(dlugosc, szerokosc, trasa.get(i).coord)) {
            Vec2d dxdy = new Vec2d(create_shift_vector(trasa.get(i - 1).coord, trasa.get(i).coord));
            Vec2d nowa_pozycja = shift_vector(trasa.get(i).coord, dxdy, Math.toRadians(normalize_radius(sgn)));
            trasa.add(new Waypoint(nowa_pozycja, rand_avg(h, variation), rand_avg(v, variation)));
            ++i;
        }
    }

    /* Sprawdzanie kolizji  */

    /**
     * This method generates randomly arc-shaped trajectory
     * for the object.
     *
     * @param v          Object's average velocity measured in kilometers per hour.
     * @param h          Object's average altitude measured in meters.
     * @param dlugosc    Map's height value.
     * @param szerokosc  Map's width value.
     * @param budynki    List of the buildings placed on the map.
     * @param variation  The deviation from the mean.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     * @throws IllegalArgumentException if constants values are negative or equal zero
     * @author Daniel Skórczyński
     */
    private void losujTrase(double v, double h, double dlugosc, double szerokosc, ArrayList<Budynek> budynki, double variation, boolean przylatuje) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dlugosc <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (szerokosc <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        double randWys = rand_avg(h, variation);
        double randPred = rand_avg(v, variation);
        int odcinek = (int) Math.floor(dlugosc / 8), i = 1;
        double sgn;

        if (przylatuje) {
            trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki, true), randWys, randPred));
            sgn = rand_sgn();
            trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, variation), rand_avg(v, variation)));
            while (not_in_bounds(new Vec2d(dlugosc, szerokosc), trasa.getLast().coord)) {
                sgn = rand_sgn();
                trasa.set(trasa.indexOf(trasa.getLast()),
                        new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, variation), rand_avg(v, variation)));
            }
        } else {
            trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki), rand_avg(h, variation), rand_avg(v, variation)));
            sgn = rand_sgn();
            trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, variation), rand_avg(v, variation)));
        }

        while (in_bounds(dlugosc, szerokosc, trasa.get(i).coord)) {
            Vec2d dxdy = new Vec2d(create_shift_vector(trasa.get(i - 1).coord, trasa.get(i).coord));
            Vec2d nowa_pozycja = shift_vector(trasa.get(i).coord, dxdy, Math.toRadians(normalize_radius(sgn)));
            trasa.add(new Waypoint(nowa_pozycja, rand_avg(h, variation), rand_avg(v, variation)));
            ++i;
        }
    }

    /* Sprawdzanie zagrozen */

    /**
     * This method generates randomly arc-shaped trajectory
     * for the object.
     *
     * @param v          Object's average velocity measured in kilometers per hour.
     * @param h          Object's average altitude measured in meters.
     * @param dlugosc    Map's height value.
     * @param szerokosc  Map's width value.
     * @param budynki    List of the buildings placed on the map.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     * @throws IllegalArgumentException if constants values are negative or equal zero
     * @author Daniel Skórczyński
     */
    private void losujTrase(double v, double h, double dlugosc, double szerokosc, ArrayList<Budynek> budynki, boolean przylatuje) throws IllegalArgumentException {
        if (v <= 0) throw new IllegalArgumentException("Velocity cannot be equal zero or less");
        if (h <= 0) throw new IllegalArgumentException("Height cannot be equal zero or less");
        if (dlugosc <= 0) throw new IllegalArgumentException("Long cannot be equal zero or less");
        if (szerokosc <= 0) throw new IllegalArgumentException("Width cannot be equal zero or less");
        double randWys = rand_avg(h, 0.1);
        double randPred = rand_avg(v, 0.1);
        int odcinek = (int) Math.floor(dlugosc / 8), i = 1;
        double sgn;

        if (przylatuje) {
            trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki, true), randWys, randPred));
            sgn = rand_sgn();
            trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, 0.1), rand_avg(v, 0.1)));
            while (not_in_bounds(new Vec2d(dlugosc, szerokosc), trasa.getLast().coord)) {
                sgn = rand_sgn();
                trasa.set(trasa.indexOf(trasa.getLast()),
                        new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, 0.1), rand_avg(v, 0.1)));
            }
        } else {
            trasa.add(new Waypoint(safe_place(dlugosc, szerokosc, randWys, budynki), rand_avg(h, 0.1), rand_avg(v, 0.1)));
            sgn = rand_sgn();
            trasa.add(new Waypoint((rand_point_on_ring(trasa.getFirst().coord, odcinek, sgn)), rand_avg(h, 0.1), rand_avg(v, 0.1)));
        }

        while (in_bounds(dlugosc, szerokosc, trasa.get(i).coord)) {
            Vec2d dxdy = new Vec2d(create_shift_vector(trasa.get(i - 1).coord, trasa.get(i).coord));
            Vec2d nowa_pozycja = shift_vector(trasa.get(i).coord, dxdy, Math.toRadians(normalize_radius(sgn)));
            trasa.add(new Waypoint(nowa_pozycja, rand_avg(h, 0.1), rand_avg(v, 0.1)));
            ++i;
        }
    }

    /* Zmiana położenia następnego waypointu */

    /**
     * This method checks if the given object collides
     * with any building or with any other object on the map.
     *
     * @param P Object's current position.
     * @param h Object's current altitude measured in meters.
     * @return True if there is a collision.
     * @throws IllegalArgumentException if height is negative
     * @author Daniel Skórczyński
     */
    public boolean kolizja(Vec2d P, double h) throws IllegalArgumentException {
        if (h < 0) throw new IllegalArgumentException("Height cannot be negative");
        return P.distance(this.pozycja) <= 10 && Math.abs(h - this.wysokosc) <= 10;
    }

    /* Zmiana całej trasy zaczynając od następnego punktu o dany KĄT */

    /**
     * This method checks if the given object is in the danger
     * of collision with any building or with any other object on the map.
     *
     * @param P Object's current position.
     * @param h Object's current altitude measured in meters.
     * @return True if there is danger of collision.
     * @throws IllegalArgumentException if height is negative
     * @author Daniel Skórczyński
     */
    public boolean zagrozenie(Vec2d P, double h) throws IllegalArgumentException {
        if (h < 0) throw new IllegalArgumentException("Height cannot be negatvie");
        return P.distance(this.pozycja) > 10 && P.distance(this.pozycja) <= 30 &&
                Math.abs(h - this.wysokosc) > 10 && Math.abs(h - this.wysokosc) <= 30;
    }

    /* Funkcje GET */

    /**
     * Changes location of the waypoint that given object is
     * heading for.
     *
     * @param przesuniecie Shift's x and y values.
     * @author Daniel Skórczyński
     */
    public void zmienKierunek(Vec2d przesuniecie) {
        int i = n_kurs;
        double x = trasa.get(i).coord.x;
        double y = trasa.get(i).coord.y;
        double vel = trasa.get(i).predkosc;
        double h = trasa.get(i).wysokosc;
        x = x + przesuniecie.x;
        y = y + przesuniecie.y;
        trasa.set(i, new Waypoint(x, y, h, vel));
    }

    /**
     * Changes location of the waypoint that given object is
     * heading for.
     *
     * @param kat Shift's angle.
     * @author Daniel Skórczyński
     */
    public void zmienKierunek(double kat) {
        int i = n_kurs;
        int last = trasa.indexOf(trasa.getLast());
        for (int j = i; j < last; j++) {
            trasa.set(j, new Waypoint(new Vec2d(rotate(trasa.get(j - 1).coord, trasa.get(j).coord, Math.toRadians(kat))),
                    trasa.get(j).wysokosc, trasa.get(j).predkosc));
            if (j == i) {
                //kurs = trasa.get(j);
                n_kurs = trasa.indexOf(trasa.get(j));
            }
        }
    }

    /**
     * @return Object's current position.
     */
    public Vec2d getPozycja() {
        return pozycja;
    }

    public void setPozycja(Vec2d pozycja) {
        this.pozycja = pozycja;
    }


    /* Konstruktory */

    /**
     * @return Object's current altitude measured in meters.
     */
    public double getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(double wysokosc) {
        this.wysokosc = wysokosc;
    }

    /**
     * @return Object's current velocity measured in kilometers per hour.
     */
    public double getPredkosc() {
        return predkosc;
    }

    public void setPredkosc(double predkosc) {
        this.predkosc = predkosc;
    }

    /**
     * @return Waypoint that given object is
     * heading for.
     */
    public int getN_kurs() {
        return n_kurs;
    }

    protected void loadImage(String filename) throws FileNotFoundException {
        File plik = new File("loty\\img\\".concat(filename));
        try {
            img = ImageIO.read(plik);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawImage(Graphics2D g) {
        g.drawImage(img, (int) pozycja.x - img.getWidth() / 2, (int) pozycja.y - img.getHeight() / 2, null);
    }
}


