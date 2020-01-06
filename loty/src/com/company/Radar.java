package com.company;

import com.company.buildings.Budynek;
import com.company.ships.*;

import java.io.FileNotFoundException;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Radar contains the map, the list of the objects currently flying above the mapped
 * terrain and information about current collisions or the potential danger of them.
 * @author Daniel Skórczyński
 * @author Paweł Raglis
 */
public class Radar implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * List of the objects currently flying above the mapped terrain.
     */
    public ArrayList<Statek> statki;
    /**
     * List containing objects' numbers of identification.
     */
    private LinkedList<Integer> idList;
    /**
     * List containing information about the potential danger of collisions.
     */
    public LinkedList<Integer> zagrozenia = null;
    /**
     * List containing information about the current collisions.
     */
    public LinkedList<Integer> kolizje = null;
    /**
     * Map of the terrain.
     */
    public Mapa map;

    public Radar() {

    }

    /**
     * For each flying object on the map checks if there is a potential
     * danger of collision with any other object or building.
     * @return List of objects in danger of collision.
     * @author Daniel Skórczyński
     */
    private LinkedList<Integer> zagrozenia(){
        LinkedList<Integer> a = new LinkedList<>();
        for (int i=0; i<statki.size()-1; i++)
        {
            for (int j=i+1; j<statki.size(); j++)
            {
                if (statki.get(i).zagrozenie(statki.get(j).getPozycja(), statki.get(j).getWysokosc()))
                {
                    if (!a.contains(statki.get(i).id)) a.add(statki.get(i).id);
                    if (!a.contains(statki.get(j).id)) a.add(statki.get(j).id);
                }

            }

            for (Budynek b : map.budynki) {
                if (b.zagrozenie(statki.get(i)) && !a.contains(statki.get(i).id)) a.add(statki.get(i).id);
            }

        }
        return a;
    }
    /**
     * For each flying object on the map checks if there was a collision with any other object or building.
     * @return List of objects that had a collision and should be removed form the map.
     * @author Daniel Skórczyński
     */
    private LinkedList<Integer> kolizje() {
        LinkedList<Integer> a = new LinkedList<>();

        for (int i=0; i<statki.size()-1; i++)
        {
            for (int j=i+1; j<statki.size(); j++)
            {
                if (statki.get(i).kolizja(statki.get(j).getPozycja(), statki.get(j).getWysokosc()))
                {
                    if (!a.contains(statki.get(i).id)) a.add(statki.get(i).id);
                    if (!a.contains(statki.get(j).id)) a.add(statki.get(j).id);
                }

            }

            for (Budynek b : map.budynki) {
                if (b.kolizja(statki.get(i)) && !a.contains(statki.get(i).id)) a.add(statki.get(i).id);
            }

        }
        return a;
    }


    /**
     * Changes object's trajectory.
     * @param id Object's identification number.
     * @param przesuniecie Shift vector.
     * @throws IndexOutOfBoundsException if ship's id is greater or equal than size of the list containing all the ships
     * @author Daniel Skórczyński
     */
    public void zmianaKursu(int id, Vec2d przesuniecie) throws IndexOutOfBoundsException{
        if(id >= statki.size()) throw new IndexOutOfBoundsException("Index out of bounds");
        statki.get(id).zmienKierunek(przesuniecie);
    }
    /**
     * Changes object's trajectory.
     * @param id Object's identification number.
     * @param kat Angle of shift.
     * @throws IndexOutOfBoundsException if ship's id is greater or equal than size of the list containing all the ships
     * @author Daniel Skórczyński
     */
    public void zmianaKursu(int id, double kat) throws IndexOutOfBoundsException{
        if(id >= statki.size()) throw new IndexOutOfBoundsException("Index out of bounds");
        statki.get(id).zmienKierunek(kat);
    }
    /**
     * Changes object's trajectory.
     * @param cel Flying object.
     * @param przesuniecie Shift vector.
     * @author Daniel Skórczyński
     */
    public void zmianaKursu(Statek cel, Vec2d przesuniecie) { statki.get(statki.indexOf(cel)).zmienKierunek(przesuniecie); }
    /**
     * Changes object's trajectory.
     * @param cel Flying object.
     * @param kat Angle of shift.
     * @author Daniel Skórczyński
     */
    public void zmianaKursu(Statek cel, double kat) {
        statki.get(statki.indexOf(cel)).zmienKierunek(kat);
    }
    /**
     * Adds new object.
     * @author Daniel Skórczyński
     */
    public void addStatek() {
        int iden;
        if(idList.size() == 0) iden = statki.size();
        else iden = idList.removeFirst();

        int i = ThreadLocalRandom.current().nextInt(100);
        if (i >= 0 && i < 2) {
            statki.add(new Balon(iden, map.getWidth(), map.getHeight(), map.budynki, 0.8, true));
        } else if (i >= 2 && i < 15) {
            statki.add(new Helikopter(iden, map.getWidth(), map.getHeight(), map.budynki, true));

        } else if (i >= 15 && i < 75) {
            statki.add(new Samolot(iden, map.getWidth(), map.getHeight(), map.budynki, true));

        } else if (i >= 75 && i < 85) {
            statki.add(new Sterowiec(iden, map.getWidth(), map.getHeight(), map.budynki, true));

        } else if (i >= 85 && i <= 100) {
            statki.add(new Szybowiec(iden, map.getWidth(), map.getHeight(), map.budynki, true));

        }
    }

    /**
     * Destroys the objects from the list that are out of radar's reach or collided with something.
     * @author Paweł Raglis
     */
    public void zniszcz(){
        LinkedList<Integer> a = new LinkedList<>(); //Lista statkow do usuniecia
        if( statki.size() != 0 ) {

            /* Zbieranie informacji o statkach poza zasiegiem */
            for (Statek s : statki) {
                if (!UtilityFunctions.in_bounds(map.getWidth(), map.getHeight(), s.getPozycja())) {
                    a.add(statki.indexOf(s)); idList.add(s.id);
                }
            }

            /* Usuwanie statkow poza zasiegiem */
            int size = a.size();
            for(int i = 0; i < size; i++) {
                statki.remove(statki.get(a.removeLast()));
            }

            /* Zbieranie informacji o kolizji */
            LinkedList<Integer> b = new LinkedList<>(kolizje);
            for (Statek s : statki) {
                for(Integer i: b){
                    if(i == s.id){
                        a.add(statki.indexOf(s)); idList.add(s.id);
                    }
                }
            }

            /* Usuwanie statkow po kolizji */
            size = a.size();
            for(int i = 0; i < size; i++) {
                statki.remove(statki.get(a.removeLast()));
            }

        }
    }

    /**
     * Creates radar.
     * @param map Map to initialize.
     * @author Magdalena Sawicka
     */
    public Radar(Mapa map){
        this.map = map;

        if(map != null)
            initStatki();
    }
    /**
     * Chosen map initialization. Reads information from the given file,
     * sets parameters like dimensions of the map and creates the list of buildings.
     *
     * @param src Name of the file to read map from
     * @author Daniel Skórczyński
     */
    public void initMap(String src){
        try {
            map = new Mapa(src);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            map = null;
            e.printStackTrace();
        }
        if(map != null)
            initStatki();
        zagrozenia = new LinkedList<>();
        kolizje = new LinkedList<>();
    }
    /**
     * Default map initialization.
     * @author Daniel Skórczyński
     */
    public void initMap(){
        try {
            map = new Mapa();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            map = null;
            e.printStackTrace();
        }
        if(map != null)
            initStatki();
        zagrozenia = new LinkedList<>();
        kolizje = new LinkedList<>();
    }
    /**
     * Creates randomly starting set of the objects on the map, right after map initialization.
     * @author Paweł Raglis
     */
    private void initStatki(){
        int max = (int) Math.floor(UtilityFunctions.rand(5, 10));
        statki = new ArrayList<>();
        idList = new LinkedList<>();
        for (int i = 0; i < max; i++) {
            int i1 = ThreadLocalRandom.current().nextInt(100);
            if (i1 >= 0 && i1 < 10) {
                statki.add(new Balon(i, map.getWidth(), map.getHeight(), map.budynki, 0.8));
            } else if (i1 >= 10 && i1 < 20) {
                statki.add(new Helikopter(i, map.getWidth(), map.getHeight(), map.budynki));

            } else if (i1 >= 20 && i1 < 75) {
                statki.add(new Samolot(i, map.getWidth(), map.getHeight(), map.budynki));

            } else if (i1 >= 75 && i1 < 85) {
                statki.add(new Sterowiec(i, map.getWidth(), map.getHeight(), map.budynki));

            } else if (i1 >= 85 && i1 <= 100) {
                statki.add(new Szybowiec(i, map.getWidth(), map.getHeight(), map.budynki));

            }
        }
    }
    /**
     * Simulates flight of the objects, initiates list of collisions and list of potential danger of collisions.
     * @param time Delta time.
     * @author Paweł Raglis
     */
    public void lec(double time){
        if(statki.size() != 0) {
            for (Statek s : statki) {
                try{
                    s.lec(time);
                } catch (Exception IllegalArgumentException){
                    System.out.println("Czas musi być dodatni");
                    break;
                }
            }
        }
        kolizje = kolizje();
        zagrozenia = zagrozenia();
    }
}
