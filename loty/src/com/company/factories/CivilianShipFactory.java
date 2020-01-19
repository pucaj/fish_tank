package com.company.factories;

import com.company.ships.*;

import java.io.Serializable;

public class CivilianShipFactory implements ShipsFactory, Serializable {
    @Override
    public Statek createBalon() {
        return new Balon();
    }

    @Override
    public Statek createHelikopter() {
        return new Helikopter();
    }

    @Override
    public Statek createSamolot() {
        return new Samolot();
    }

    @Override
    public Statek createSterowiec() {
        return new Sterowiec();
    }

    @Override
    public Statek createSzybowiec() {
        return new Szybowiec();
    }
}
