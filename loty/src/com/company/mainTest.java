package com.company;

import com.sun.javafx.geom.Vec2d;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileNotFoundException;


public class mainTest{

    @Test
    public void tryRadarMaps(){
        radar R = new radar();
        R.initMap("map_1280x720.txt");
        Dimension exp = new Dimension(1280/2,720/2);
        Assertions.assertEquals(exp,R.map.getDimension());
        Assertions.assertNotNull(R.map,"Mapa powinna istnieć!");

        R.statki.clear();
        R.map = null;
        R.initMap("map_1600x900.txt");
        exp = new Dimension(1600/2,900/2);
        Assertions.assertEquals(exp,R.map.getDimension());
        Assertions.assertNotNull(R.map,"Mapa powinna istnieć!");

        R.statki.clear();
        R.map = null;
        R.initMap("map_1920x1080.txt");
        exp = new Dimension(1920/2,1080/2);
        Assertions.assertEquals(exp,R.map.getDimension());
        Assertions.assertNotNull(R.map,"Mapa powinna istnieć!");

        R.statki.clear();
        R.map = null;
        R.initMap("map_1920-1080.jpg");
        Assertions.assertNull(R.map,"Plik który nie istnieje, nie powinien inicjalizować mapy");

        mapa map = null;
        try {
            map = new mapa("map_1280x720.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Mapa nie istnieje!");
            e.printStackTrace();
        }
        radar R1 = new radar(map);
        Assertions.assertNotNull(R1.map,"Mapa powinna się załadować z pliku który istnieje");

        map = null;
        try {
            map = new mapa("brakmapy.txtcc");
        } catch (FileNotFoundException e) {
            System.out.println("Brak mapy");
            e.printStackTrace();
        }
        radar R2 = new radar(map);
        Assertions.assertNull(R2.map,"Mapa nie powinna się zinicjalizować, ponieważ nie istnieje plik z mapą.");
    }

    @Test
    public void tryInitStatki(){

        // Without map
        radar R = new radar();
        Assertions.assertNull(R.statki,"Ships cannot excist without map!");

        // With specified map
        mapa specifiedMap = null;
        try {
            specifiedMap = new mapa("map_1280x720.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot load specified map");
        }
        radar R1 = new radar(specifiedMap);
        Assertions.assertNotNull(R1.statki,"Ships shouldn't be null with map!");

        // Checking elements of list
        for(statek s : R1.statki){
            Assertions.assertNotNull(s.trasa,"Course list should be initialized");
            Assertions.assertNotNull(s.id,"Id should be assigned to the object");
            Assertions.assertNotNull(s.getPozycja(),"Position should be assigned to the object");
            Assertions.assertNotNull(s.getWysokosc(),"Height should be assigned to the object");
            Assertions.assertNotNull(s.getPredkosc(),"Velocity should be assigned to the object");
        }

    }

    @Test
    public void tryAddStatek(){
        radar R = null;
        try {
            R = new radar(new mapa("map_1280x720.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int primarySize = R.statki.size();
        R.addStatek();
        int afterAddSize = R.statki.size();

        Assertions.assertEquals(primarySize + 1, afterAddSize,
                "Size of the list should be about one greater than primary size");

        statek s = R.statki.get(R.statki.size()-1);
        Assertions.assertNotNull(s.trasa,"Course list should be initialized");
        Assertions.assertNotNull(s.id,"Id should be assigned to the object");
        Assertions.assertNotNull(s.getPozycja(),"Position should be assigned to the object");
        Assertions.assertNotNull(s.getWysokosc(),"Height should be assigned to the object");
        Assertions.assertNotNull(s.getPredkosc(),"Velocity should be assigned to the object");
    }

    @Test
    public void tryZmianaKursu(){
        radar R = null;
        try {
            R = new radar(new mapa("map_1280x720.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Vec2d oldCoords = new Vec2d(R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).coord);
        double oldVelocity = R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).predkosc;
        double oldHeight = R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).wysokosc;

        R.zmianaKursu(0,new Vec2d(100,100));

        Vec2d newCoords = new Vec2d(R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).coord);
        double newVelocity = R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).predkosc;
        double newHeight = R.statki.get(0).trasa.get(R.statki.get(0).getN_kurs()).wysokosc;
        Assertions.assertEquals(oldCoords.x + 100, newCoords.x,
                "New position.x should be about 100 greater than old position.x");
        Assertions.assertEquals(oldCoords.y + 100, newCoords.y,
                "New position.y should be about 100 greater than old position.y");
        Assertions.assertEquals(oldHeight,newHeight,"New height should be equal old height");
        Assertions.assertEquals(oldVelocity,newVelocity,"New velocity should be equal old velocity");

        R.zmianaKursu(0,4);
        statek s = R.statki.get(1);
        R.zmianaKursu(s,new Vec2d(500,-600));
        R.zmianaKursu(R.statki.get(2),-12.4);
    }

    @Test
    public void Simulate() {
        radar R = null;
        try {
            R = new radar(new mapa("map_1280x720.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //simulating and trying not deleting ships
        int oldSize = R.statki.size();
        for (int i = 0; i < 100000; i++)
            R.lec(4);

        for (statek s : R.statki) {
            Assertions.assertEquals(false, fun.in_bounds(640, 360, s.getPozycja()),
                    "All ships should be out of bounds");
        }
        Assertions.assertEquals(oldSize, R.statki.size(), "Ships shouldn't be destroyed");

        //simulating and deleting ships
        radar R1 = null;
        try {
            R1 = new radar(new mapa("map_1280x720.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100000; i++) {
            R.lec(4);
            R.zniszcz();
        }
        Assertions.assertEquals(0, R.statki.size(), "All ships should be destroyed");

        //testing lec(speed)
        radar R2 = null;
        try {
            R2 = new radar(new mapa("map_1280x720.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10000; i++) {
            R2.lec(-12);
        }

        for (statek s : R2.statki) {
            Assertions.assertEquals(s.trasa.getFirst().coord, s.getPozycja(),
                    "Ships shouldn't move with negative time");
        }

        for (int i = 0; i < 10000; i++) {
            R2.lec(0);
        }

        for (statek s : R2.statki) {
            Assertions.assertEquals(s.trasa.getFirst().coord, s.getPozycja(),
                    "Ships shouldn't move with time equal zero");
        }
    }
}