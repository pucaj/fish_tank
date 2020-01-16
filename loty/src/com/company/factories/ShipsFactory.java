package com.company.factories;

import com.company.ships.Statek;

public interface ShipsFactory {
    Statek createBalon();
    Statek createHelikopter();
    Statek createSamolot();
    Statek createSterowiec();
    Statek createSzybowiec();
}
