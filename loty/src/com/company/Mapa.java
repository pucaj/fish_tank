package com.company;

import com.company.buildings.Budynek;
import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * Radar shows objects flying over
 * certain field of terrain.
 * <p>
 * Mapa contains information about
 * the size of that field, the buildings
 * that are placed there, their location on the
 * map, their height and dimensions.
 */
public class Mapa {
    /**
     * Dimensions of the map, specifies width and height values.
     */
    private Dimension dimension;
    /**
     * Name of the file with the map to load.
     */
    private String src;
    /**
     * ArrayList containing all the building placed on the map.
     */
    public ArrayList<Budynek> budynki;


    /**
     * @return dimensions of the map.
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * @return map's width value.
     */
    public int getWidth() {
        return dimension.width;
    }

    /**
     * @return map's height value.
     */
    public int getHeight() {
        return dimension.height;
    }


    /**
     * Map constructor.
     *
     * @param s Name of the file containing map to load.
     * @throws FileNotFoundException
     */
    public Mapa(String s) throws FileNotFoundException {
        this.src = s;
        initMap(s);
    }

    /**
     * Default contsructor.
     *
     * @throws FileNotFoundException
     */
    public Mapa() throws FileNotFoundException {
        this.src = "map_1280x720.txt";
        initMap(src);
    }


    /**
     * This method is responsible for map initialization.
     * It reads the information from the given file, sets parameters like
     * dimensions of the map and creates list of the buildings on the map.
     *
     * @param src Name of the file to read from.
     * @throws FileNotFoundException
     */
    private void initMap(String src) throws FileNotFoundException {
        Scanner plik = new Scanner(new File("loty\\".concat(src)));

        while (plik.hasNextDouble()) {

            double s = plik.nextDouble(); //szerokosc mapy
            double d = plik.nextDouble(); //dlugosc mapy

            this.dimension = new Dimension((int) d, (int) s);

            budynki = new ArrayList<>();

            while (plik.hasNextDouble()) {

                double x = plik.nextDouble();
                double y = plik.nextDouble();
                double dl = plik.nextDouble();
                double sz = plik.nextDouble();
                double w = plik.nextDouble();

                budynki.add(new Budynek(new Vec2d(x, y), dl, sz, w));

            }
        }
        plik.close();
        UtilityFunctions.DLUGOSCMAPY = dimension.width;
        UtilityFunctions.SZEROKOSCMAPY = dimension.height;
    }
}


