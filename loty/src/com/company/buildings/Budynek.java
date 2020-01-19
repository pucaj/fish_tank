package com.company.buildings;

import com.company.Vec2d;
import com.company.ships.Statek;

import java.awt.*;
import java.io.Serializable;

/**
 * Map contains sets of objects from this class,
 * each has different location, height and size,
 * Buildings pose potential danger of collision
 * for hot-air baloons.
 *
 * @author Paweł Raglis
 */
public abstract class Budynek implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * Location of the center point of the building.
     */
    private Vec2d srodek;
    /**
     * Dimension of the building, x axis.
     */
    private double dlugosc; //X axis
    /**
     * Dimension of the building, y axis.
     */
    private double szerokosc; //Y axis
    /**
     * Height of the building, measured in meters.
     */
    private double wysokosc;

    /**
     * @return dimension of the building, x axis.
     */
    public double getDlugosc() {
        return this.dlugosc;
    }

    /**
     * @return dimension of the building, y axis.
     */
    public double getSzerokosc() {
        return this.szerokosc;
    }

    /**
     * @return height of the building.
     */
    public double getWysokosc() {
        return this.wysokosc;
    }

    /**
     * @return location of the center point of the building.
     */
    public Vec2d getSrodek() {
        return this.srodek;
    }

    /**
     * Checks if the object at position P
     * and height h collides with the given building.
     *
     * @param P current position of the object
     * @param h height of the object given in meters above the ground
     */
    public boolean kolizja(Vec2d P, double h) {
        return !(h > wysokosc) && P.x >= srodek.x - dlugosc / 2 && P.x <= srodek.x + dlugosc / 2 && P.y <= srodek.y + szerokosc / 2 && P.y >= srodek.y - szerokosc / 2;
    }

    /**
     * Checks if the object
     * collides with the given building.
     *
     * @param st flying object
     */
    public boolean kolizja(Statek st) {
        if (st.getWysokosc() > wysokosc) return false;
        return st.getPozycja().x >= srodek.x - dlugosc / 2 && st.getPozycja().x <= srodek.x + dlugosc / 2 &&
                st.getPozycja().y <= srodek.y + szerokosc / 2 && st.getPozycja().y >= srodek.y - szerokosc / 2;
    }

    /**
     * Checks if the object at position P
     * and height h is in danger of collision with the given building.
     *
     * @param P current position of the object
     * @param h height of the object given in meters above the ground
     */
    public boolean zagrozenie(Vec2d P, double h) {
        if (h > wysokosc + 51.2) return false;
        return P.x >= srodek.x - dlugosc / 2 - 51.2 && P.x <= srodek.x + dlugosc / 2 + 51.2 && P.y <= srodek.y + szerokosc + 51.2 &&
                P.y >= srodek.y - szerokosc - 51.2;
    }

    /**
     * Checks if the object is in danger
     * of collision with the given building.
     *
     * @param st flying object
     */
    public boolean zagrozenie(Statek st) {
        if (st.getWysokosc() > wysokosc + 51.2) return false;
        return st.getPozycja().x >= srodek.x - dlugosc / 2 - 51.2 && st.getPozycja().x <= srodek.x + dlugosc / 2 + 51.2 &&
                st.getPozycja().y <= srodek.y + szerokosc + 51.2 && st.getPozycja().y >= srodek.y - szerokosc - 51.2;
    }


    public void draw(Graphics g) {
        g.fillRect((int) (srodek.x - dlugosc / 2), (int) (srodek.y - szerokosc / 2),
                (int) dlugosc, (int) szerokosc);
    }

    public void setSrodek(Vec2d srodek) {
        this.srodek = srodek;
    }

    public void setDlugosc(double dlugosc) {
        this.dlugosc = dlugosc;
    }

    public void setSzerokosc(double szerokosc) {
        this.szerokosc = szerokosc;
    }

    public void setWysokosc(double wysokosc) {
        this.wysokosc = wysokosc;
    }


    public Object clone() {
        Object clone = null;

        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }
}



