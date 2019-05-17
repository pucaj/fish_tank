import com.sun.javafx.geom.Vec2d;

public class budynek {
    private Vec2d srodek;
    private double dlugosc; //X axis
    private double szerokosc; //Y axis
    private double wysokosc;

    public double getDlugosc() {
        return this.dlugosc;
    }
    public double getSzerokosc() {
        return this.szerokosc;
    }
    public double getWysokosc() {
        return this.wysokosc;
    }
    public Vec2d getSrodek() { return  this.srodek; }

    public budynek(Vec2d poz, double dlug, double szer, double wys){
        srodek = new Vec2d(poz);
        dlugosc = dlug;
        szerokosc = szer;
        wysokosc = wys;
    }
    public boolean zagrozenie(Vec2d P,Vec2d dim) {
        if(P.x >= srodek.x-dlugosc/2-dim.x && P.x <= srodek.x+dlugosc/2+dim.x && P.y <= srodek.y+szerokosc+dim.y &&
                P.y >= srodek.y-szerokosc-dim.y) return true;
        else return false;
    }
}



