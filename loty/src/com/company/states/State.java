package com.company.states;

import com.company.Vec2d;
import com.company.Waypoint;
import com.company.ships.Statek;

import java.awt.*;
import java.io.Serializable;

import static com.company.UtilityFunctions.*;
import static com.company.UtilityFunctions.shift_vector;

public abstract class State implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int NORMAL_STATE = 0;
    public static final int IN_DANGER_STATE = 1;

    public void lec(Statek context, double time) throws IllegalArgumentException {
        if (time <= 0) throw new IllegalArgumentException("Time cannot be negative");

        if (in_bounds(DLUGOSCMAPY, SZEROKOSCMAPY, context.getPozycja()) || context.trasa.size() - context.getN_kurs() != 1) {

            Waypoint s = new Waypoint(context.trasa.get(context.getN_kurs() - 1).coord, context.trasa.get(context.getN_kurs() - 1).wysokosc,
                    context.trasa.get(context.getN_kurs() - 1).predkosc);
            s.coord = new Vec2d(context.getPozycja());

            Waypoint e = context.trasa.get(context.getN_kurs());

            context.setPredkosc((s.predkosc + e.predkosc) / 2);
            context.setWysokosc((s.wysokosc + e.wysokosc) / 2);


            double droga = context.getPredkosc() * time * 10 / 36; // odleglosc pokonana przez statek w czasie 'time' wyrazona w metrach
            double piksele = droga / 500; // droga wyrazona w pikselach, 1 piksel = 500m
            double skala = piksele / vector_length(s.coord, e.coord);

            if (vector_length(context.getPozycja(), e.coord) < piksele) {
                double l = vector_length(context.getPozycja(), e.coord);

                s = context.trasa.get(context.getN_kurs());
                context.setPozycja(s.coord);
                if (context.trasa.size() - context.trasa.indexOf(s) >= 2) {
                    context.incrementN_kurs();
                    e = context.trasa.get(context.getN_kurs());
                    skala = (piksele - l) / vector_length(s.coord, e.coord);
                    context.setPozycja(shift_vector(context.getPozycja(), new Vec2d(skala * (e.coord.x - s.coord.x), skala * (e.coord.y - s.coord.y))));
                }
            } else
                context.setPozycja(shift_vector(context.getPozycja(), new Vec2d(skala * (e.coord.x - s.coord.x), skala * (e.coord.y - s.coord.y))));
        }
    }

    public void drawShape(Statek context, Graphics2D g) {
    }

    public void drawImg(Statek context, Graphics2D g) {
        context.drawImage(g);
    }
}
