package com.company;

import com.company.buildings.Budynek;
import com.company.buildings.BudynekCloneFactory;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.Serializable;
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
public class Mapa implements Serializable {
    private static Mapa instance;

    public static Mapa getInstance() throws FileNotFoundException {
        if(instance == null)
            instance = new Mapa();
        return instance;
    }

    public static Mapa getInstance(String src) throws FileNotFoundException {
        if(instance == null)
            instance = new Mapa(src);
        return instance;
    }

    private static final long serialVersionUID = 1L;
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
    private Mapa(String s) throws FileNotFoundException {
        this.src = s;
        initMap(s);
    }

    /**
     * Default contsructor.
     *
     * @throws FileNotFoundException
     */
    private Mapa() throws FileNotFoundException {
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
        BudynekCloneFactory.loadCache();

        while (plik.hasNextDouble()) {

            double s = plik.nextDouble(); //szerokosc mapy
            double d = plik.nextDouble(); //dlugosc mapy

            this.dimension = new Dimension((int) d, (int) s);

            budynki = new ArrayList<>();

            while (plik.hasNextDouble()) {
                Budynek budynek;
                double x = plik.nextDouble();
                double y = plik.nextDouble();
                double dl = plik.nextDouble();
                double sz = plik.nextDouble();
                double w = plik.nextDouble();

                if (w < 15)
                    budynek = BudynekCloneFactory.getBudynek("DOM");
                else if(w < 100)
                    budynek = BudynekCloneFactory.getBudynek("BLOK");
                else
                    budynek = BudynekCloneFactory.getBudynek("WYSOKIBUDYNEK");

                budynek.setSrodek(new Vec2d(x,y));
                budynek.setDlugosc(dl);
                budynek.setSzerokosc(sz);
                budynek.setWysokosc(w);
                budynki.add(budynek);
            }
        }
        plik.close();
        UtilityFunctions.DLUGOSCMAPY = dimension.width;
        UtilityFunctions.SZEROKOSCMAPY = dimension.height;
    }
}


