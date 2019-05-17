import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class main {
    public static ArrayList<budynek> budynki = new ArrayList<budynek>();
    public static double pit(double x, double y){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    public static double rand(double max){
        if(max<0){return ThreadLocalRandom.current().nextDouble(max,0);}
        else if(max>0){return ThreadLocalRandom.current().nextDouble(max);}
        else return 0;
    }

    public static Vec2d safe_place(Vec2d bounds, ArrayList<budynek> budynki){
        boolean can_place = false;
        double x = rand(bounds.x),y = rand(bounds.y);
        while(!can_place) {
            x = rand(bounds.x);
            y = rand(bounds.y);
            for (budynek b : budynki) {
                if (b.zagrozenie(new Vec2d(x,y),new Vec2d(b.getDlugosc(),b.getSzerokosc()))) { can_place = false; break;}
                else { can_place = true; }
            }
        }
        return new Vec2d(x,y);
    }

    public static void losujBudynki(int ile, Dimension wymiary) throws FileNotFoundException {
        double x,y,maxWidth = 25, maxLong = 25,maxHeight = 300, Width, Long, Height;
        double minWidth = 10, minLong = 10, minHeight = 5;
        Vec2d place;
        if(wymiary.height == 900){
            maxWidth *= 1.5;
            maxLong *= 1.5;
        } else if(wymiary.height == 1080){
            maxWidth *= 2;
            maxLong *= 2;
        }
        String file_name = "map_"+wymiary.width+"x"+wymiary.height+".txt";
        File file = new File(file_name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Zostala podana zla sciezka!");
                e.printStackTrace();
            }
        }
        PrintWriter write = new PrintWriter(file);
        write.println(wymiary.height/2);
        write.println(wymiary.width/2);

        for(int i = 0; i < ile; i++){
            Long = ThreadLocalRandom.current().nextDouble(minLong,maxLong);
            Width = ThreadLocalRandom.current().nextDouble(minWidth,maxWidth);
            Height = ThreadLocalRandom.current().nextDouble(minHeight,maxHeight);
            x = ThreadLocalRandom.current().nextDouble(wymiary.width/2-Long);
            y = ThreadLocalRandom.current().nextDouble(wymiary.height/2-Width);
            if(budynki.size() >= 1){
                place = safe_place(new Vec2d(wymiary.width/2,wymiary.height/2),budynki);
                x = place.x;
                y = place.y;
            }

            budynki.add( new budynek(new Vec2d(x,y), Long, Width, Height) );

            write.println(Double.toString(x).replace('.',','));
            write.println(Double.toString(y).replace('.',','));
            write.println(Double.toString(Long).replace('.',','));
            write.println(Double.toString(Width).replace('.',','));
            write.println(Double.toString(Height).replace('.',','));


        }
        write.close();
    }
    
    public static void main(String[] args) throws FileNotFoundException{
        int ile;
        Scanner scan = new Scanner(System.in);


        while(true){
            System.out.println("Generator map v1.0\n1. Stworz mape\n2. Wyjdz");
            switch(scan.nextInt()){
                case 1:
                    System.out.println("\nPodaj wymiary:\n1. 1280x720\n2. 1600x900\n3. 1920x1080");
                    int x = scan.nextInt();
                    System.out.print("\nPodaj ile wylosowac budynkow (10-200): ");
                    ile = scan.nextInt();
                    switch (x){
                        case 1:
                            losujBudynki(ile,new Dimension(1280,720));
                            break;
                        case 2:
                            losujBudynki(ile,new Dimension(1600,900));
                            break;
                        case 3:
                            losujBudynki(ile,new Dimension(1920,1080));
                            break;
                        default:
                            continue;
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    continue;
            }
        }
    }
}
