package com.company;

import com.company.factories.CivilianShipFactory;
import com.company.gui.ControlsJPanel;
import com.company.gui.LogsJPanel;
import com.company.gui.MapJPanel;
import com.company.strategies.CollisionAvoidanceByAngleChangeStrategy;
import com.company.strategies.CollisionAvoidanceByCourseChangeStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;

import static com.company.UtilityFunctions.*;

/**
 * Main class, handling the program as a whole.
 */
public class Main extends JFrame {
    /**
     * Map with the flight simulation,
     * upper-left window of the graphic user interface.
     */
    private static MapJPanel map;
    /**
     * Collisions register, warnings about the potential danger of collision.
     * Upper-right window of the graphic user interface.
     */
    private static LogsJPanel logs;
    /**
     * Option buttons, detailed information about the given object - bottom part of the frame.
     */
    private static ControlsJPanel controls;

    /**
     * Logic update, refreshes the program.
     *
     * @param R              Radar object.
     * @param simulationTime Delta time.
     */
    private static void logicUpdate(Radar R, double simulationTime) {
        if (rand(100) > 96) {
            R.addStatek();
        }

        if (R.kolizje.size() != 0) logs.updateK(R.kolizje, LocalTime.now());
        if (R.zagrozenia.size() != 0) logs.updateZ(R.zagrozenia);

        R.lec(simulationTime);
        R.zniszcz();
        if (!activeExcist(activeShip, R.statki))
            if (!circulation)
                activeShip = -1;
        if (doShift && angleShift == 0.0) {
            R.setAvoidanceStrategy(new CollisionAvoidanceByCourseChangeStrategy(shift));
            R.zmianaKursu(realID(activeShip, R.statki));
            shift = new Vec2d(0, 0);
            doShift = false;
        } else if (doShift && angleShift != 0) {
            R.setAvoidanceStrategy(new CollisionAvoidanceByAngleChangeStrategy(angleShift));
            R.zmianaKursu(realID(activeShip, R.statki));
            angleShift = 0;
            doShift = false;
        }
    }

    private static void MementoSave(Radar R){
        if(memento){
            memento = false;
            String filename = mementoFilename;
            try {
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(file);

                out.writeObject(R);

                out.close();
                file.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Radar MementoRead(String fileName) throws IOException, ClassNotFoundException, Exception {
        if(loadMemento){
            loadMemento = false;

            FileInputStream file = new FileInputStream(new File(fileName));
            ObjectInputStream in = new ObjectInputStream(file);

            Radar radar = (Radar)in.readObject();

            in.close();
            file.close();
            return radar;
        }

        throw new Exception("Memento didn't loaded");
    }

    /**
     * Draws the simulation.
     *
     * @param R Radar object.
     */
    private static void draw(Radar R) {
        try {
            controls.update(R.statki.get(realID(activeShip, R.statki)), R.statki);
        } catch (Exception ArrayIndexOutOfBoundsException) {
            controls.update(null, R.statki);
        }
        map.draw(R.statki, R.map.budynki, R.zagrozenia);
    }

    /**
     * Main frame constructor.
     *
     * @param dim Dimensions of the window.
     */
    private Main(Dimension dim, Dimension screenSize, boolean fullScreen) {

        super("Kontroler lotów");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Pobieram wymiary aktualnego monitora */
        //Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(dim);

        /* Ustawiam aplikacje w centrum monitora */
        setLocation(screenSize.width / 2 - dim.width / 2, screenSize.height / 2 - dim.height / 2);

        /* DOBIERANIE ROZDZELCZOSCI */
        if (fullScreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        setResizable(false);
        add(map, BorderLayout.LINE_START);
        add(logs, BorderLayout.LINE_END);
        add(controls, BorderLayout.PAGE_END);

        setVisible(true);

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
            this.createBufferStrategy(3);

        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Radar R = new Radar(new CivilianShipFactory());

        Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        boolean fullScreen = false;
        if (screenSize.height < 900 && screenSize.width < 1600) {
            if (screenSize.height == 720 && screenSize.width == 1280) {
                fullScreen = true;
            }
            R.initMap("map_1280x720.txt");
        } else if (screenSize.height < 1080 && screenSize.width < 1920) {
            if (screenSize.height == 900 && screenSize.width == 1600) {
                fullScreen = true;
            }
            R.initMap("map_1600x900.txt");
        } else if (screenSize.height >= 1080 && screenSize.width >= 1920) {
            if (screenSize.height == 1080 && screenSize.width == 1920) {
                fullScreen = true;
            }
            R.initMap("map_1920x1080.txt");
        }

        /* Inicjalizacja komponentów graficznych */
        //R.initMap("map_1280x720.txt");
        map = new MapJPanel(R.map.getDimension(), R.statki, R.map.budynki);
        controls = new ControlsJPanel(new Dimension(R.map.getWidth() * 2, R.map.getHeight()));
        logs = new LogsJPanel(new Dimension(R.map.getWidth(), R.map.getHeight()));
        new Main(new Dimension(R.map.getWidth() * 2, R.map.getHeight() * 2), screenSize, false);

        /* Temporary config - debug mode */
        isRunning = true;
        antiAliasing = true;
        realVariables = true;
        activeShip = -1;

        long lastTime = System.nanoTime();
        double amountOfTicks = FPS;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        /* Główna pętla */
        while (true) {
            Thread.sleep(5);
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (isRunning) {
                    logicUpdate(R, speed * delta);
                }
                updates++;
                delta--;
            }
            draw(R);
            frames++;

            MementoSave(R);
            try {
                R = MementoRead(mementoFilename);
            } catch(Exception ex) {

            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}