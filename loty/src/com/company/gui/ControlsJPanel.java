package com.company.gui;

import com.company.ships.*;
import com.sun.org.apache.regexp.internal.RE;
//import javafx.scene.control.SelectionMode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.company.UtilityFunctions.*;

/**
 * ControlsGui creates the design of the bottom
 * window of graphic user interface and is responsible for handling the actions
 * connected with the options available for the user regarding simulation and
 * information about the objects.
 */
public class ControlsJPanel extends JPanel implements ActionListener, ChangeListener {

    /* Przyciski do kontroli symulacji */
    private JButton startSim = new JButton("start");
    private JButton stopSim = new JButton("stop");
    private JButton slowT2 = new JButton("x0.5");
    private JButton slowT1 = new JButton("x0.75");
    private JButton realT = new JButton("x1");
    private JButton accT1 = new JButton("x1.25");
    private JButton accT2 = new JButton("x1.5");

    /* Przyciski shipPanel */
    private JButton course = new JButton("Zmień kurs");
    private JButton ship = new JButton("Zmień statek");

    /* Labele z info o samolotach */
    private JLabel id = new JLabel("-1");
    private JLabel type = new JLabel("brak");
    private JLabel location = new JLabel("0, 0");
    private JLabel height = new JLabel("0 m.n.p.m.");
    private JLabel vel = new JLabel("0.0 m/s");
    private JLabel waypoint = new JLabel("0, 0");

    /* optionPanel */
    // Grafika
    private JCheckBox AA = new JCheckBox("Antyaliasing", true);

    // Samoloty
    private JCheckBox ID = new JCheckBox("Id statków", true);
    private JCheckBox ACTIVE = new JCheckBox("Pokoloruj aktywny statek", true);
    private JCheckBox COURSE = new JCheckBox("Pokazuj trase aktywnego statku", true);
    private JCheckBox ACTIVEBOX = new JCheckBox("Pokazuj obramowanie aktywnego statku", true);
    private JCheckBox CIRCULATE = new JCheckBox("Zawsze pokazuj aktywny statek", true);
    private JCheckBox WARNINGS = new JCheckBox("Pokazuj zagrożenia", true);
    private JCheckBox IMAGE_REPRESENTATION = new JCheckBox("Rysuj jako obrazki", false);

    // Wartości
    private JCheckBox PRECISION = new JCheckBox("Zaokrąglanie wyników", true);
    private JSpinner PRECISION_LEVEL = new JSpinner(new SpinnerNumberModel(2, 0, 10, 1));

    /* Zaawansowane opcje */
    // Symulacja
    private JCheckBox ADV_ENABLED = new JCheckBox("Włącz ustawienia zaawansowane", false);
    private JLabel CUSTOM_SPEED_l = new JLabel("Prędość symulacji");
    private JTextField CUSTOM_SPEED = new JTextField(Double.toString(speed));
    private JLabel CUSTOM_FPS_l = new JLabel("FPS");
    private JTextField CUSTOM_FPS = new JTextField(Integer.toString(FPS));
    private JLabel CUSTOM_ANGLE_l = new JLabel("Kąt odchylenia tras");
    private JTextField CUSTOM_ANGLE = new JTextField(Double.toString(courseAngle));

    // Memento
    private JButton SAVE_STATE = new JButton("Zapisz stan");
    private JButton READ_STATE = new JButton("Odczytaj stan");

    //lista statków dla ActionListenera
    /**
     * List containing all the objects in the simulation.
     */
    private ArrayList<Statek> shipList;

    /* Konstruktor */

    /**
     * Creates window using given size parameters.
     *
     * @param dim Size of the window.
     * @author Daniel Skórczyński
     */
    public ControlsJPanel(Dimension dim) {
        setPreferredSize(dim);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new FlowLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(getMainContener(dim));

        optionsUpdate();
    }

    /**
     * Event Handler
     *
     * @author Paweł Raglis
     * @author Daniel Skórczyński
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == PRECISION_LEVEL) {
            optionsUpdate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(startSim)) {
            isRunning = true;
        } else if (source.equals(stopSim)) {
            isRunning = false;
        } else if (source.equals(realT)) {
            isRunning = true;
            speed = 1;
        } else if (source.equals(slowT1)) {
            isRunning = true;
            speed = 0.75;
        } else if (source.equals(slowT2)) {
            isRunning = true;
            speed = 0.5;
        } else if (source.equals(accT1)) {
            isRunning = true;
            speed = 1.25;
        } else if (source.equals(accT2)) {
            isRunning = true;
            speed = 1.5;
        } else if (source.equals(ID) || source.equals(ACTIVE) || source.equals(ACTIVEBOX)
                || source.equals(COURSE) || source.equals(CIRCULATE) || source.equals(WARNINGS)
                || source.equals(AA) || source.equals(PRECISION) || source.equals(PRECISION_LEVEL)
                || source.equals(ADV_ENABLED) || source.equals(CUSTOM_ANGLE) || source.equals(CUSTOM_ANGLE_l)
                || source.equals(CUSTOM_FPS) || source.equals(CUSTOM_FPS_l) || source.equals(CUSTOM_SPEED)
                || source.equals(CUSTOM_SPEED_l) || source.equals(IMAGE_REPRESENTATION)) {
            optionsUpdate();
        } else if (source.equals(ship)) {
            isRunning = false;
            JOptionPane window = new JOptionPane();
            String s = (String) JOptionPane.showInputDialog(this, null, "Wybierz statek",
                    JOptionPane.PLAIN_MESSAGE, null, shListToStringArray(), shListToStringArray()[0]);
            add(window);

            if (s != null) {
                String ideStr = s.substring(22);
                int ide = Integer.parseInt(ideStr);
                for (Statek aShipList : shipList)
                    if (aShipList.id == ide) {
                        activeShip = ide;
                        break;
                    }
            }
            window.setVisible(false);
            isRunning = true;
        } else if (source.equals(course)) {
            ChangeCourseJFrame cc = new ChangeCourseJFrame(shipList.get(realID(activeShip, shipList)));
        }
    }

    /**
     * This method converts list containing ships to string array
     *
     * @return list containing ships converted to string array
     * @author Paweł Raglis
     */
    private String[] shListToStringArray() {
        String[] StrShList = new String[shipList.size()];
        String type;
        for (int i = 0; i < shipList.size(); i++) {
            if (shipList.get(i) instanceof Balon) type = "Balon     ";
            else if (shipList.get(i) instanceof Samolot) type = "Samolot   ";
            else if (shipList.get(i) instanceof Sterowiec) type = "Sterowiec ";
            else if (shipList.get(i) instanceof Helikopter) type = "Helikopter";
            else if (shipList.get(i) instanceof Szybowiec) type = "Szybowiec ";
            else type = "???";
            StrShList[i] = "typ: " + type + "   id: " + shipList.get(i).id;
        }
        return StrShList;
    }

    /**
     * This method uptades displayed information about active ship
     *
     * @param s      active ship
     * @param shList list containing all ships
     * @author Daniel Skórczyński
     */
    public void update(Statek s, ArrayList<Statek> shList) {
        if (s != null) {
            activeShip(true);
            id.setText(" " + s.id);
            type.setText(" " + s.getClass().getCanonicalName().replace("com.company.", ""));
            if (realVariables) {
                location.setText(" " + round(s.getPozycja().x, precision) + ", " +
                        round(s.getPozycja().y, precision));
                vel.setText(" " + round(s.getPredkosc(), precision));
                height.setText(" " + round(s.getWysokosc(), 2));
                waypoint.setText(" " + round(s.trasa.get(s.getN_kurs()).coord.x, precision) + ", " +
                        round(s.trasa.get(s.getN_kurs()).coord.y, precision));
            } else {
                location.setText(" " + (int) s.getPozycja().x + ", " + (int) s.getPozycja().y);
                vel.setText(" " + (int) s.getPredkosc());
                height.setText(" " + (int) s.getWysokosc());
                waypoint.setText(" " + (int) s.trasa.get(s.getN_kurs()).coord.x + ", " + (int) s.trasa.get(s.getN_kurs()).coord.y);
            }
        } else {
            activeShip(false);
        }
        this.shipList = shList;
    }

    private JComponent getMainContener(Dimension dim) {
        JPanel inner = new JPanel();
        JPanel upper = new JPanel();
        JPanel left = new JPanel();
        JPanel right = new JPanel();


        inner.setPreferredSize(new Dimension(dim.width - 8, dim.height * 7 / 8));
        left.setPreferredSize(new Dimension(dim.width / 2, dim.height * 7 / 8));
        right.setPreferredSize(new Dimension(dim.width / 2, dim.height * 7 / 8));
        upper.setPreferredSize(new Dimension(dim.width / 2, dim.height / 8));

        inner.setLayout(new GridLayout(0, 2));
        upper.setLayout(new GridLayout());

        upper.add(getSimulationButtons(upper.getPreferredSize()));

        left.add(upper);
        left.add(getShipPanel(left.getPreferredSize()));
        right.add(getOptions(right.getPreferredSize()));
        inner.add(left);
        inner.add(right);

        left.setVisible(true);
        right.setVisible(true);
        inner.setVisible(true);
        return inner;
    }

    /* Buttons */
    private JComponent getSimulationButtons(Dimension dim) {
        JPanel inner = new JPanel();
        inner.setLayout(new FlowLayout());
        inner.setVisible(true);
        inner.add(startSim);
        inner.add(stopSim);
        inner.add(slowT2);
        inner.add(slowT1);
        inner.add(realT);
        inner.add(accT1);
        inner.add(accT2);
        startSim.addActionListener(this);
        stopSim.addActionListener(this);
        slowT1.addActionListener(this);
        slowT2.addActionListener(this);
        accT1.addActionListener(this);
        accT2.addActionListener(this);
        realT.addActionListener(this);
        ship.addActionListener(this);
        course.addActionListener(this);
        startSim.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        stopSim.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        slowT1.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        slowT2.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        realT.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        accT1.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        accT2.setPreferredSize(new Dimension(dim.width / 8, dim.height * 3 / 4));
        inner.setPreferredSize(new Dimension(dim.width, dim.height));
        return inner;
    }

    /* Labels */
    private JComponent getShipPanel(Dimension dim) {
        int fontSize = 20;
        JPanel inner = new JPanel();
        inner.setVisible(true);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        /* Tworze 2 kolumny oraz panel o tym layoucie */
        GridLayout grid = new GridLayout(0, 2);
        JPanel innerGrid = new JPanel(grid);

        /* Ustawiam czcionke wraz z jej rozmiarem 'fontSize' */
        id.setFont(new Font("", Font.PLAIN, fontSize));
        type.setFont(new Font("", Font.PLAIN, fontSize));
        location.setFont(new Font("", Font.PLAIN, fontSize));
        height.setFont(new Font("", Font.PLAIN, fontSize));
        vel.setFont(new Font("", Font.PLAIN, fontSize));
        waypoint.setFont(new Font("", Font.PLAIN, fontSize));

        /* Ustawiam obramowanie */
        id.setBorder(BorderFactory.createTitledBorder("Id"));
        type.setBorder(BorderFactory.createTitledBorder("Typ"));
        location.setBorder(BorderFactory.createTitledBorder("Pozycja"));
        height.setBorder(BorderFactory.createTitledBorder("Wysokosc"));
        vel.setBorder(BorderFactory.createTitledBorder("Prędkość"));
        waypoint.setBorder(BorderFactory.createTitledBorder("Aktulany kurs"));

        /* Dodaje buttony */
        JPanel _course = new JPanel();
        JPanel _ship = new JPanel();
        course.setMargin(new Insets(5, 40, 5, 40));
        ship.setMargin(new Insets(5, 40, 5, 40));
        _course.add(course);
        _ship.add(ship);

        /* Dodaje labele do JPanelu */
        innerGrid.add(id);
        innerGrid.add(height);
        innerGrid.add(type);
        innerGrid.add(vel);
        innerGrid.add(location);
        innerGrid.add(waypoint);

        /* Dodaje buttony do JPanelu */
        innerGrid.add(_course);
        innerGrid.add(_ship);

        /* Dodaje tabele do głównego kontenera */
        inner.add(innerGrid);

        inner.setPreferredSize(new Dimension(dim.width - (int) (dim.width * 0.05), dim.height - (int) (dim.height * 0.15)));

        return inner;
    }

    /* Panel opcji */
    private JComponent getOptions(Dimension dim) {
        JPanel inner = new JPanel();
        JPanel graphics = new JPanel();
        JPanel ships = new JPanel();
        JPanel mementoPanel = new JPanel();
        JPanel values = new JPanel();
        JPanel adv_settings = new JPanel();

        /* Tworzenie GroupLayout */
        GroupLayout gL = new GroupLayout(inner);
        gL.setAutoCreateGaps(true);
        gL.setAutoCreateContainerGaps(true);

        /* Layouty paneli */
        inner.setLayout(gL);
        graphics.setLayout(new FlowLayout(FlowLayout.LEFT));
        ships.setLayout(new BoxLayout(ships, BoxLayout.Y_AXIS));
        values.setLayout(new FlowLayout(FlowLayout.LEFT));
        mementoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        adv_settings.setLayout(new GridLayout(0, 1));

        /* Rozmiary paneli */
        inner.setPreferredSize(new Dimension(dim.width - (int) (dim.width * 0.05), dim.height - (int) (dim.height * 0.05)));
        graphics.setPreferredSize(new Dimension(dim.width / 4 - dim.width / 50, dim.height / 8));
        ships.setPreferredSize(new Dimension(dim.width / 2 - (int) (dim.width * 0.1), dim.height / 3));
        mementoPanel.setPreferredSize(new Dimension(dim.width / 2 - (int) (dim.width * 0.05), dim.height / 8));
        values.setPreferredSize(new Dimension(dim.width / 4 + (int) (dim.width * 0.1), dim.height / 8));
        adv_settings.setPreferredSize(new Dimension(dim.width / 2 - (int) (dim.width * 0.05), dim.height / 2));

        /* Obramowanie */
        graphics.setBorder(BorderFactory.createTitledBorder("Grafika"));
        ships.setBorder(BorderFactory.createTitledBorder("Statki"));
        values.setBorder(BorderFactory.createTitledBorder("Wartości"));
        mementoPanel.setBorder(BorderFactory.createTitledBorder("Stan programu"));
        adv_settings.setBorder(BorderFactory.createTitledBorder("Zaawansowane"));

        /* Dodawanie składowych do paneli */
        graphics.add(AA);
        ships.add(ID);
        ships.add(ACTIVE);
        ships.add(COURSE);
        ships.add(ACTIVEBOX);
        ships.add(WARNINGS);
        ships.add(CIRCULATE);
        ships.add(IMAGE_REPRESENTATION);
        values.add(PRECISION);
        values.add(PRECISION_LEVEL);
        adv_settings.add(ADV_ENABLED);
        adv_settings.add(CUSTOM_SPEED_l);
        adv_settings.add(CUSTOM_SPEED);
        adv_settings.add(CUSTOM_FPS_l);
        adv_settings.add(CUSTOM_FPS);
        adv_settings.add(CUSTOM_ANGLE_l);
        adv_settings.add(CUSTOM_ANGLE);
        mementoPanel.add(SAVE_STATE);
        mementoPanel.add(READ_STATE);
        AA.addActionListener(this);
        ID.addActionListener(this);
        ACTIVE.addActionListener(this);
        COURSE.addActionListener(this);
        ACTIVEBOX.addActionListener(this);
        WARNINGS.addActionListener(this);
        CIRCULATE.addActionListener(this);
        PRECISION.addActionListener(this);
        PRECISION_LEVEL.addChangeListener(this);
        ADV_ENABLED.addActionListener(this);
        CUSTOM_FPS.addActionListener(this);
        CUSTOM_SPEED.addActionListener(this);
        CUSTOM_ANGLE.addActionListener(this);
        IMAGE_REPRESENTATION.addActionListener(this);
        SAVE_STATE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = null;
                try {
                    fc = new JFileChooser(new File(".").getCanonicalPath());

                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fc.setMultiSelectionEnabled(false);
                    fc.setAcceptAllFileFilterUsed(false);
                    fc.setFileFilter(new FileNameExtensionFilter("Pliki kontrolera lotów", "ser"));
                    fc.setSelectedFile(new File("snapshot_" + (new Date()).getTime() + "_" +  (new Random()).nextInt(1000) + ".ser"));

                    int returnVal = fc.showSaveDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        File file = fc.getSelectedFile();
                        mementoFilename = file.getAbsolutePath();
                        memento = true;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        READ_STATE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = null;
                try {
                    fc = new JFileChooser(new File(".").getCanonicalPath());

                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fc.setMultiSelectionEnabled(false);
                    fc.setAcceptAllFileFilterUsed(false);
                    fc.setFileFilter(new FileNameExtensionFilter("Pliki kontrolera lotów", "ser"));


                    int returnVal = fc.showOpenDialog(null);
                    if(returnVal == JFileChooser.APPROVE_OPTION){
                        File file = fc.getSelectedFile();
                        mementoFilename = file.getAbsolutePath();
                        loadMemento = true;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /* Dodawanie paneli do głownego kontenera */
        inner.add(graphics);
        inner.add(ships);
        inner.add(values);
        inner.add(adv_settings);
        inner.add(mementoPanel);

        /* Ustawianie grup */
        gL.setHorizontalGroup(gL.createSequentialGroup()
                .addGroup(gL.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(graphics)
                        .addComponent(ships))
                .addGroup(gL.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(values)
                        .addComponent(adv_settings)
                        .addComponent(mementoPanel)));
        gL.linkSize(SwingConstants.HORIZONTAL, ships, adv_settings);
        gL.setVerticalGroup(gL.createSequentialGroup()
                .addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(graphics).addComponent(values))
                .addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(ships).addComponent(adv_settings))
                .addGroup(gL.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(mementoPanel)));

        return inner;
    }

    private void activeShip(boolean enabled) {
        id.setEnabled(enabled);
        type.setEnabled(enabled);
        location.setEnabled(enabled);
        vel.setEnabled(enabled);
        height.setEnabled(enabled);
        waypoint.setEnabled(enabled);
        course.setEnabled(enabled);
    }

    private void activeSimulation(boolean enabled) {
        startSim.setEnabled(enabled);
        stopSim.setEnabled(enabled);
        accT1.setEnabled(enabled);
        accT2.setEnabled(enabled);
        realT.setEnabled(enabled);
        slowT1.setEnabled(enabled);
        slowT2.setEnabled(enabled);
    }

    private void optionsUpdate() {
        // Graphics
        antiAliasing = AA.isSelected();

        // Variables
        realVariables = PRECISION.isSelected();
        if (!realVariables) PRECISION_LEVEL.setEnabled(false);
        else PRECISION_LEVEL.setEnabled(true);
        precision = (int) PRECISION_LEVEL.getValue();

        // Ships
        showId = ID.isSelected();
        showActive = ACTIVE.isSelected();
        showCourse = COURSE.isSelected();
        showActiveBox = ACTIVEBOX.isSelected();
        showWarnings = WARNINGS.isSelected();
        circulation = CIRCULATE.isSelected();
        imageRepresentation = IMAGE_REPRESENTATION.isSelected();

        // Advanced
        advancedOptions = ADV_ENABLED.isSelected();
        if (!advancedOptions) {
            CUSTOM_ANGLE.setEnabled(false);
            CUSTOM_ANGLE_l.setEnabled(false);
            CUSTOM_SPEED.setEnabled(false);
            CUSTOM_SPEED_l.setEnabled(false);
            CUSTOM_FPS.setEnabled(false);
            FPS = 120;
            courseAngle = 6;
        } else {
            CUSTOM_ANGLE.setEnabled(true);
            CUSTOM_ANGLE_l.setEnabled(true);
            CUSTOM_SPEED.setEnabled(true);
            CUSTOM_SPEED_l.setEnabled(true);
            CUSTOM_FPS.setEnabled(true);
            CUSTOM_FPS_l.setEnabled(true);

            try {
                speed = Double.parseDouble(CUSTOM_SPEED.getText().trim().replace(',', '.'));
            } catch (NullPointerException | NumberFormatException ignored) {
            }

            try {
                FPS = Integer.parseInt(CUSTOM_FPS.getText().trim());
            } catch (NullPointerException | NumberFormatException ignored) {
            }

            try {
                courseAngle = Double.parseDouble(CUSTOM_ANGLE.getText().trim().replace(',', '.'));
            } catch (NullPointerException | NumberFormatException ignored) {
            }
        }
    }
}

