package com.company;

import java.util.ArrayList;

/**
 * Samolot is one of the types of <i>statek</i>.
 * @author Magdalena Sawicka
 */
class samolot extends Statek {

    /**
     * Creates new object with the given parameters.
     * @param i Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud List of the buildings placed on the map.
     */
    public samolot(int i, double dlug, double szer, ArrayList<Budynek> bud){
        super(i,900.0, 11000.0,dlug,szer,bud);
    }
    /**
     * Creates new object with the given parameters.
     * @param i Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud List of the buildings placed on the map.
     * @param variation The deviation from the mean.
     */
    public samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation){
        super(i,900.0, 11000.0,dlug,szer,bud,variation);
    }
    /**
     * Creates new object with the given parameters.
     * @param i Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud List of the buildings placed on the map.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     */
    public samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, boolean przylatuje){
        super(i,900.0, 11000.0,dlug,szer,bud,przylatuje);
    }
    /**
     * Creates new object with the given parameters.
     * @param i Identification number.
     * @param dlug Map's height value.
     * @param szer Map's width value.
     * @param bud List of the buildings placed on the map.
     * @param variation The deviation from the mean.
     * @param przylatuje Determines object's starting position. If the value is true, first waypoint
     *                   is placed on the map's x or y axis - else it can be placed randomly anywhere on
     *                   the map.
     */
    public samolot(int i, double dlug, double szer, ArrayList<Budynek> bud, double variation, boolean przylatuje){
        super(i,900.0, 11000.0,dlug,szer,bud,variation,przylatuje);
    }

    public samolot(samolot s){
        super(s);
    }
}
