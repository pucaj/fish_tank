package com.company;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Library containing useful functions used in the program.
 */
public class UtilityFunctions {

    static double courseAngle = 6;
    public static double DLUGOSCMAPY;
    public static double SZEROKOSCMAPY;
    static double speed = 1; // prędkość symulacji
    static boolean antiAliasing;
    static boolean isRunning;
    static boolean realVariables; // Pokazywanie rzeczywistych wartości
    static boolean showId;
    static boolean showActive;
    static boolean showCourse;
    static boolean showActiveBox;
    static boolean showWarnings;
    static boolean circulation;
    static boolean advancedOptions;
    static boolean editMode = false;
    static boolean doShift = false;
    static int FPS = 120; // ilość FPSów logiki + grafiki
    static int activeShip; // Wybrany statek
    static int precision = 2; // Precyzja liczb rzeczywistych

    static Vec2d shift = new Vec2d(0,0);
    static double angleShift = 0;

    /* Losowanie znaku {-1,1} */
    /**
     * @return randomly generated sing value 1 or -1.
     */
    static double rand_sgn(){
        if(Math.floor(rand(2)) == 0 ) return -1.0;
        else return 1.0;
    }

    /* Losowanie liczby z odchylenia VARIATION liczby VALUE */
    /**
     * @param value Value of the number.
     * @param variation Variation precising the range.
     * @return randomly generated number from the range of values precised by given parameters: variation and value of the number.
     * @throws IllegalArgumentException
     */
    static double rand_avg(double value, double variation) throws IllegalArgumentException{
        if(variation < 0) throw new IllegalArgumentException("Variation cannot be negative");
        return ThreadLocalRandom.current().nextDouble(value - variation*value, value + variation*value);
    }

    /* Losowanie liczby z przedziału ((1-VARIATION)*VALUE,VALUE) */
    /**
     * @param value Value of the number.
     * @param variation Variation precising the range.
     * @return randomly generated number from the given range of values: ((1-VARIATION)*VALUE,VALUE).
     * @throws IllegalArgumentException
     */
    public static double rand_avg_lower(double value, double variation) throws IllegalArgumentException{
        if(variation < 0) throw new IllegalArgumentException("Variation cannot be negative");
        double origin = value - variation*value;
        if(value < 0) origin = value + variation*value;
        return ThreadLocalRandom.current().nextDouble(origin, value);
    }

    /* Losowanie liczby z przedziału (VALUE,(1+VARIATION)*VALUE) */
    /**
     * @param value Value of the number.
     * @param variation Variation precising the range.
     * @return randomly generated number from the given range of values: (VALUE,(1+VARIATION)*VALUE).
     * @throws IllegalArgumentException
     */
    public static double rand_avg_upper(double value, double variation) throws IllegalArgumentException{
        if(variation < 0) throw new IllegalArgumentException("Variation cannot be negative");
        return ThreadLocalRandom.current().nextDouble(value, value + variation*value);
    }

    /* Losowanie liczby z przedzialu (min, max) */
    /**
     * @param min Minimal value.
     * @param max Maximum value.
     * @return randomly generated number from the given range of values: (min, max).
     * @throws IllegalArgumentException
     */
    public static double rand(double min, double max) throws IllegalArgumentException{
        if(max < min) throw new IllegalArgumentException("Minimum value cannot be greater than maximum!");
        //if(max < min) return ThreadLocalRandom.current().nextDouble(max,min);
        return ThreadLocalRandom.current().nextDouble(min,max);
    }

    /* Losowanie liczby z przedzialu (max, 0) lub (0, max) */
    /**
     * @param max Maximum value.
     * @return randomly generated number from the (max, 0) range of values or from the (0, max) range.
     */
    public static double rand(double max){
        if(max<0){return -ThreadLocalRandom.current().nextDouble(max);}
        else if(max>0){return ThreadLocalRandom.current().nextDouble(max);}
        else return 0;
    }

    /* Losowanie liczby z przedzialu (0,1) */
    /**
     * @return randomly generated number from the (0, 1) range of values.
     */
    public static double rand(){return ThreadLocalRandom.current().nextDouble();}

    /* Losowy punkt na okregu w punkcie ORIGIN*/
    /**
     * @param origin Central point.
     * @param radius Radius value.
     * @return randomly generated point on the circle with the center and radius precised by arguments.
     */
    public static Vec2d rand_point_on_ring(Vec2d origin, double radius){
        if(radius < 0) radius = normalize_radius(radius,-1);
        double x = origin.x + rand(-radius,radius);
        double sgn = rand_sgn();
        double y = sgn*Math.sqrt(Math.pow(radius,2)-Math.pow(x-origin.x,2)) + origin.y;
        return new Vec2d(x,y);
    }
    /* Losowy punkt na okregu w punkcie ORIGIN ze znakiem (gorna, czy dolna wartosc) SIGN */
    /**
     * @param origin Central point.
     * @param radius Radius value.
     * @param sign Positive or negative value.
     * @return ?
     */
    public static Vec2d rand_point_on_ring(Vec2d origin, double radius, double sign){
        double x = origin.x + rand(-radius,radius);
        double y = sign*Math.sqrt(Math.pow(radius,2)-Math.pow(x-origin.x,2)) + origin.y;
        return new Vec2d(x,y);
    }
    /* Losowy punkt na okregu wpunkcie (0,0) */
    /**
     * @param radius Radius value.
     * @return randomly generated point on the circle with the center point (0,0)
     * and the radius precised by the argumemt.
     */
    public static Vec2d rand_point_on_ring(double radius){
        double x = rand(-radius,radius);
        double sgn = rand_sgn();
        double y = sgn*Math.sqrt(Math.pow(radius,2)-Math.pow(x,2));
        return new Vec2d(x,y);
    }
    /* Losowy punkt na okregu w punkcie (0,0) ze znakiem SIGN */
    public static Vec2d rand_point_on_ring(double radius,double sign){
        double x = rand(-radius,radius);
        double y = sign*Math.sqrt(Math.pow(radius,2)-Math.pow(x,2));
        return new Vec2d(x,y);
    }

    /* Obrócenie punktu TARGET względem punktu (0,0) o kąt ANGLE */
    /*                  FUNKCJA PRZYJMUJE RADIANY!               */
    public static Vec2d rotate(Vec2d target, double angle){
        double x = target.x * Math.cos(angle) - target.y * Math.sin(angle);
        double y = target.x * Math.sin(angle) + target.y * Math.cos(angle);
        return new Vec2d(x,y);
    }

    /* Obrócenie punktu TARGET względem punktu ORIGIN o kąt ANGLE */
    /*                  FUNKCJA PRZYJMUJE RADIANY!                */
    static Vec2d rotate(Vec2d origin, Vec2d target, double angle){
        double realx = target.x - origin.x;
        double realy = target.y - origin.y;
        double x = realx * Math.cos(angle) - realy * Math.sin(angle);
        double y = realx * Math.sin(angle) + realy * Math.cos(angle);
        x += origin.x; y += origin.y;
        return new Vec2d(x,y);
    }

    /* Losowy punkt na prostokacie o kotwicy w punkcie (0,0) oraz wymiarach WIDTH x HEIGHT */
    /**
     * @param width Width dimension of the rectangle.
     * @param height Height dimension of the rectangle.
     * @return randomly generated point in the area of the rectangle with the center point (0,0).
     * @throws IllegalArgumentException
     */
    private static Vec2d rand_point_on_rect(double width, double height) throws IllegalArgumentException{
        if(width < 0) throw new IllegalArgumentException("Width cannot be negative");
        if(height < 0) throw new IllegalArgumentException("Height cannot be negative");
        double x,y;
        if(rand_sgn() > 0){
            if(rand_sgn() > 0){
                x = 0;
                y = rand(height);
            } else {
                x = width;
                y = rand(height);
            }
        } else {
            if(rand_sgn() > 0){
                x = rand(width);
                y = 0;
            } else {
                x = rand(width);
                y = height;
            }
        }
        return new Vec2d(x,y);
    }

    /**
     *
     * @param bounds Dimensions of the rectangle.
     * @return randomly generated point in the area of the rectangle. ??
     * @throws IllegalArgumentException
     */
    public static Vec2d rand_point_on_rect(Vec2d bounds) throws IllegalArgumentException{
        if(bounds.x <= 0 || bounds.y <= 0) throw new IllegalArgumentException("Bounds cannot be negative or equal zero");
        double x,y;
        if(rand_sgn() > 0){
            if(rand_sgn() > 0){
                x = 0;
                y = rand(bounds.y);
            } else {
                x = bounds.x;
                y = rand(bounds.y);
            }
        } else {
            if(rand_sgn() > 0){
                x = rand(bounds.x);
                y = 0;
            } else {
                x = rand(bounds.x);
                y = bounds.y;
            }
        }
        return new Vec2d(x,y);
    }

    /* Odleglosc miedzy dwoma punktami, s-poczatek, e-koniec */
    /**
     * @param s First point.
     * @param e Second point.
     * @return the length of vector between two given points.
     */
    public static double vector_length (Vec2d s, Vec2d e) {
        return Math.sqrt(Math.pow((e.x - s.x), 2) + Math.pow((e.y - s.y), 2));
    }

    /* Tworzenie wektora przesuniecia AB */
    public static Vec2d create_shift_vector(Vec2d A, Vec2d B){
        return new Vec2d(B.x - A.x,B.y - A.y);
    }

    /* Przesuniecie punktu A o wektor SHIFT */
    public static Vec2d shift_vector(Vec2d A, Vec2d shift){
        return new Vec2d(A.x + shift.x, A.y + shift.y);
    }

    /* Przesuniecie punktu A o wektor SHIFT z odchyleniem ANGLE */
    /*                FUNKCJA PRZYJMUJE RADIANY!                */
    public static Vec2d shift_vector(Vec2d A, Vec2d shift, double angle){
        return new Vec2d( rotate( A, shift_vector(A, shift), angle));
    }

    /* Sprawdza czy POINT jest w granicach prostokata o wymiarach WIDTH x HEIGHT */
    /**
     * @param width Width dimension of the rectangle.
     * @param height Height dimension of the rectangle.
     * @param point Point.
     * @return true if the point's location is in the area of the given rectangle.
     * @throws IllegalArgumentException
     */
    public static boolean in_bounds(double width, double height, Vec2d point) throws IllegalArgumentException{
        if(width < 0) throw new IllegalArgumentException("Width cannot be negative");
        if(height < 0) throw new IllegalArgumentException("Height cannot be negative");
        return !(point.x <= 0) && !(point.x >= width) && !(point.y <= 0) && !(point.y >= height);
    }

    /* Sprawdza czy POINT jest w granicach prostokata o wymiarach BOUNDS */
    /**
     * @param bounds Dimensions of the rectangle.
     * @param point Point.
     * @return true if the point's location is in the area of the given rectangle.
     * @throws IllegalArgumentException
     */
    public static boolean not_in_bounds(Vec2d bounds, Vec2d point) throws IllegalArgumentException{
        if(bounds.x <= 0 || bounds.y <= 0) throw new IllegalArgumentException("Bounds cannot be negative or equal zero");
        return point.x <= 0 || point.x >= bounds.x || point.y <= 0 || point.y >= bounds.y;
    }

    /* Zamienia SIGN = {-1;1} * RADIUS na tozsamosciowy dodatni */
    public static double normalize_radius(double radius, double sign){
        if (sign < 0) return (360 - radius);
        else return radius;
    }

    /* Zamienia SIGN = {-1;1} * courseAngle na tozsamosciowy dodatni */
    public static double normalize_radius(double sign){
        if (sign < 0) return (360 - courseAngle);
        else return courseAngle;
    }

    /* Losuje koordynaty nie pokrywajace sie z zadnymi innymi obiektami w danym prostokacie */
    public static Vec2d safe_place(double _long, double width, double height, ArrayList<Budynek> budynki) throws IllegalArgumentException{
        if(_long <= 0) throw new IllegalArgumentException("Long cannot be negative or equal zero");
        if(width <= 0) throw new IllegalArgumentException("Width cannot be negative or equal zero");
        if(height <= 0) throw new IllegalArgumentException("Height cannot be negative or equal zero");
        boolean can_place = false;
        double x = rand(_long),y = rand(width);
        if(budynki.size() == 0) return new Vec2d(x,y);
        while(!can_place) {
            x = rand(_long);
            y = rand(width);
            for (Budynek b : budynki) {
                if (b.zagrozenie(new Vec2d(x,y),height)) { can_place = false; break;}
                else { can_place = true; }
            }
        }
        return new Vec2d(x,y);
    }

    /* Losuje koordynaty nie pokrywajace sie z zadnymi innymi obiektami w danym prostokacie */
    public static Vec2d safe_place(Vec2d bounds, double height, ArrayList<Budynek> budynki) throws IllegalArgumentException{
        if(bounds.x <= 0 || bounds.y <= 0) throw new IllegalArgumentException("Bounds cannot be negative or equal zero");
        if(height <= 0) throw new IllegalArgumentException("Height cannot be negative or equal zero");
        boolean can_place = false;
        double x = rand(bounds.x),y = rand(bounds.y);
        if( budynki.size() == 0 ) return new Vec2d(x,y);
        while(!can_place) {
            x = rand(bounds.x);
            y = rand(bounds.y);
            for (Budynek b : budynki) {
                if (b.zagrozenie(new Vec2d(x,y),height)) { can_place = false; break;}
                else { can_place = true; }
            }
        }
        return new Vec2d(x,y);
    }

    /* Losuje koordynaty nie pokrywajace sie z zadnymi innymi obiektami w danej linii prostokata */
    public static Vec2d safe_place(Vec2d bounds, double height, ArrayList<Budynek> budynki, boolean arrival) throws IllegalArgumentException{
        if(bounds.x <= 0 || bounds.y <= 0) throw new IllegalArgumentException("Bounds cannot be negative or equal zero");
        if(height <= 0) throw new IllegalArgumentException("Height cannot be negative or equal zero");
        if(!arrival) return safe_place(bounds,height,budynki);

        boolean can_place = false;
        Vec2d point = new Vec2d(rand_point_on_rect(bounds));
        if(budynki.size() == 0) return point;
        while(!can_place) {
            point = new Vec2d(rand_point_on_rect(bounds));
            for (Budynek b : budynki) {
                if (b.zagrozenie(new Vec2d(point.x,point.y),height)) { can_place = false; break;}
                else { can_place = true; }
            }
        }
        return point;
    }

    /* Losuje koordynaty nie pokrywajace sie z zadnymi innymi obiektami w danej linii prostokata */
    public static Vec2d safe_place(double _long, double width, double height, ArrayList<Budynek> budynki, boolean arrival) throws IllegalArgumentException{
        if(_long <= 0) throw new IllegalArgumentException("Long cannot be negative or equal zero");
        if(width <= 0) throw new IllegalArgumentException("Width cannot be negative or equal zero");
        if(height <= 0) throw new IllegalArgumentException("Height cannot be negative or equal zero");
        if(!arrival) return safe_place(_long,width,height,budynki);

        boolean can_place = false;
        Vec2d point = new Vec2d(rand_point_on_rect(_long,width));

        if(budynki.size() == 0) return point;
        while(!can_place) {
            point = new Vec2d(rand_point_on_rect(_long,width));
            for (Budynek b : budynki) {
                if (b.zagrozenie(new Vec2d(point.x,point.y),height)) { can_place = false; break;}
                else { can_place = true; }
            }
        }
        return point;
    }

    /* Funkcje czasowe */
    public static double min_to_sec(double min) throws IllegalArgumentException{
        if(min < 0) throw new IllegalArgumentException("Minutes cannot be negative"); return min*60; }
    public static int hour_to_sec(int hour) throws IllegalArgumentException{
        if(hour < 0) throw new IllegalArgumentException("Hours cannot be negative"); return hour*60*60; }
    public static double sec_to_min(int sec) throws IllegalArgumentException{
        if(sec < 0) throw new IllegalArgumentException("Seconds cannot be negative"); return sec/60; }
    public static double sec_to_hour(int sec) throws IllegalArgumentException{
        if(sec < 0) throw new IllegalArgumentException("Seconds cannot be negative"); return sec/3600; }
    public static int hour_to_min(int hour) throws IllegalArgumentException{
        if(hour < 0) throw new IllegalArgumentException("Hours cannot be negative"); return hour*60; }
    public static double min_to_hour(int min) throws IllegalArgumentException{
        if(min < 0) throw new IllegalArgumentException("Minutes cannot be negative"); return min/60; }

    /* Usuwanie z listy takich samych wartosci */
    public static LinkedList<Integer> remove_doubles(LinkedList<Integer> a){
        if(a.size() == 0) return a;
        LinkedList<Integer> b = new LinkedList<>();
        a.forEach((i) ->{ if(!b.contains(i)) b.add(i); });
        return b;
    }

    /* Tęczowy kolor! */
    public static Color activeColor(){
        return Color.getHSBColor((float)(Math.sin((double)System.currentTimeMillis()*0.002)),(float)1.0,(float)0.9);
    }

    public static Color pulsingColor(Color base, double pulse){
        float[] hsb = Color.RGBtoHSB(base.getRed(),base.getGreen(),base.getBlue(),null);
        return Color.getHSBColor(hsb[0],hsb[1],(float)(1-Math.abs(pulse*Math.sin((double)System.currentTimeMillis()*0.005))));
    }

    /* Zaokrąglanie po n miejsc po przecinku */
    public static double round(double value, int n) throws IllegalArgumentException{
        if(n < 0) throw new IllegalArgumentException();

        BigDecimal big = new BigDecimal(value);
        big = big.setScale(n, RoundingMode.HALF_UP);

        return big.doubleValue();
    }
    public static int checkingActive(int i, int size){
        if(i >= size)
            return 0;
        else
            return i;
    }

    public static int realID(int i, ArrayList<Statek> a){
        for(Statek s: a) if(i == s.id) {i = a.indexOf(s); break;}
        return i;
    }

    public static boolean activeExcist(int i, int size){
        return i > 0 && i < size;
    }
    public static boolean activeExcist(int i, ArrayList<Statek> ships){
        if(i <= 0 || i >= ships.size()) return false;

        for( Statek s: ships ){
            if(s.id == activeShip) return true;
        }
        return false;
    }
    public static int realWaypoint(Waypoint w, LinkedList<Waypoint> list){
        int id = -1;
        for(Waypoint wp : list){
            if(wp.coord == w.coord && wp.predkosc == w.predkosc && wp.wysokosc == w.wysokosc) {
                id = list.indexOf(wp);
                break;
            }
        }
        return id;
    }
}