package com.company;

import com.sun.javafx.geom.Vec2d;

/**
 * Map contains sets of objects from this class,
 * each has different location, height and size,
 * Buildings pose potential danger of collision
 * for hot-air baloons.
 * @author Paweł Raglis
 */
public class budynek {
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
    public Vec2d getSrodek() { return  this.srodek; }

    /**
     * Creates new building using the given parameters.
     * @param poz Location of the center point of the building.
     * @param dlug Dimension of the building, x axis.
     * @param szer Dimension of the building, y axis.
     * @param wys Height of the building, measured in meters.
     */
    budynek(Vec2d poz, double dlug, double szer, double wys){
        srodek = new Vec2d(poz);
        dlugosc = dlug;
        szerokosc = szer;
        wysokosc = wys;
    }

    /**
     * Checks if the object at position P
     * and height h collides with the given building.
     * @param P current position of the object
     * @param h height of the object given in meters above the ground
     */
    public boolean kolizja(Vec2d P, double h) {
        return !(h > wysokosc) && P.x >= srodek.x - dlugosc / 2 && P.x <= srodek.x + dlugosc / 2 && P.y <= srodek.y + szerokosc / 2 && P.y >= srodek.y - szerokosc / 2;
    }

    /**
     * Checks if the object
     * collides with the given building.
     * @param st flying object
     */
    public boolean kolizja(statek st){
        if( st.getWysokosc() > wysokosc ) return false;
        return st.getPozycja().x >= srodek.x - dlugosc / 2 && st.getPozycja().x <= srodek.x + dlugosc / 2 &&
                st.getPozycja().y <= srodek.y + szerokosc / 2 && st.getPozycja().y >= srodek.y - szerokosc / 2;
    }

    /**
     * Checks if the object at position P
     * and height h is in danger of collision with the given building.
     * @param P current position of the object
     * @param h height of the object given in meters above the ground
     */
    public boolean zagrozenie(Vec2d P, double h) {
        if( h > wysokosc+51.2 ) return false;
        return P.x >= srodek.x - dlugosc / 2 - 51.2 && P.x <= srodek.x + dlugosc / 2 + 51.2 && P.y <= srodek.y + szerokosc + 51.2 &&
                P.y >= srodek.y - szerokosc - 51.2;
    }

    /**
     * Checks if the object is in danger
     * of collision with the given building.
     * @param st flying object
     */
    public boolean zagrozenie(statek st) {
        if( st.getWysokosc() > wysokosc+51.2 ) return false;
        return st.getPozycja().x >= srodek.x - dlugosc / 2 - 51.2 && st.getPozycja().x <= srodek.x + dlugosc / 2 + 51.2 &&
                st.getPozycja().y <= srodek.y + szerokosc + 51.2 && st.getPozycja().y >= srodek.y - szerokosc - 51.2;
    }
}



