package com.company.gui;

import com.company.Vec2d;
import com.company.ships.Statek;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.company.UtilityFunctions.*;

class ChangeCourseJFrame extends JFrame implements ActionListener {
    private JFrame window = new JFrame("Zmień kurs");
    private JPanel right = new JPanel();
    private JPanel left = new JPanel();
    private JPanel lower_r = new JPanel();
    private JPanel lower_l = new JPanel();

    private JRadioButton radio_l = new JRadioButton("Przesuń o wektor", true);
    private JRadioButton radio_r = new JRadioButton("Przesuń o kąt", false);

    JLabel spinX = new JLabel("x");
    JLabel spinY = new JLabel("y");
    JLabel spinAngle = new JLabel("kąt");

    private JSpinner spin_l_x = new JSpinner(new SpinnerNumberModel(0, -5000, 5000, 1));
    private JSpinner spin_l_y = new JSpinner(new SpinnerNumberModel(0, -5000, 5000, 1));
    private JSpinner spin_r_angle = new JSpinner(new SpinnerNumberModel(0, -360, 360, 0.1));

    private JButton OK = new JButton("Zatwierdź");
    private JButton CANCEL = new JButton("Odrzuć");

    public ChangeCourseJFrame(Statek s) {

        //initializing components
        ButtonGroup group = new ButtonGroup();
        group.add(radio_l);
        group.add(radio_r);
        JPanel buttonL = new JPanel();
        JPanel buttonR = new JPanel();


        //setting layouts
        window.setLayout(new GridLayout(0, 2));
        right.setLayout(new FlowLayout());
        right.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        left.setLayout(new FlowLayout());
        left.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        buttonL.setLayout(new FlowLayout());
        buttonR.setLayout(new FlowLayout());

        //adding components to main Frame
        buttonL.add(OK);
        buttonR.add(CANCEL);
        lower_l.add(spinX);
        lower_l.add(spin_l_x);
        lower_l.add(spinY);
        lower_l.add(spin_l_y);
        lower_r.add(spinAngle);
        lower_r.add(spin_r_angle);
        right.add(radio_r);
        right.add(lower_r);
        left.add(radio_l);
        left.add(lower_l);
        window.add(left);
        window.add(right);
        window.add(buttonL);
        window.add(buttonR);

        //setting panels
        spin_r_angle.setEnabled(false);
        spinAngle.setEnabled(false);

        //setting size
        window.setPreferredSize(new Dimension(450, 180));
        window.setSize(new Dimension(450, 180));
        window.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 225,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 90);
        window.setResizable(false);

        //setting margins/padding
        right.setBorder(new EmptyBorder(0, 20, 10, 20));
        left.setBorder(new EmptyBorder(0, 20, 10, 20));
        OK.setMargin(new Insets(10, 20, 10, 20));
        CANCEL.setMargin(new Insets(10, 20, 10, 20));


        //setting globals
        editMode = true;
        isRunning = false;


        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //adding listeners
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shift = new Vec2d(0, 0);
                angleShift = 0;
                editMode = false;
                isRunning = true;
            }
        });
        radio_l.addChangeListener(e -> {
            if (radio_l.isSelected()) {
                shift = new Vec2d((Integer) spin_l_x.getValue(), (Integer) spin_l_y.getValue());
                angleShift = 0;
                spin_r_angle.setEnabled(false);
                spinAngle.setEnabled(false);
                spinX.setEnabled(true);
                spinY.setEnabled(true);
                spin_l_x.setEnabled(true);
                spin_l_y.setEnabled(true);
            }
        });
        radio_r.addChangeListener(e -> {
            if (radio_r.isSelected()) {
                shift = new Vec2d(0, 0);
                angleShift = (Double) spin_r_angle.getValue();
                spin_r_angle.setEnabled(true);
                spinAngle.setEnabled(true);
                spinX.setEnabled(false);
                spinY.setEnabled(false);
                spin_l_x.setEnabled(false);
                spin_l_y.setEnabled(false);
            }
        });

        spin_l_x.addChangeListener(e -> {
            shift = new Vec2d((Integer) spin_l_x.getValue(), (Integer) spin_l_y.getValue());
            angleShift = 0;
        });
        spin_l_y.addChangeListener(e -> {
            shift = new Vec2d((Integer) spin_l_x.getValue(), (Integer) spin_l_y.getValue());
            angleShift = 0;
        });
        spin_r_angle.addChangeListener(e -> {
            shift = new Vec2d(0, 0);
            angleShift = (Double) spin_r_angle.getValue();
        });
        OK.addActionListener(this);
        CANCEL.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s.equals(OK)) {
            if (radio_l.isSelected()) {
                doShift = true;
                shift = new Vec2d((Integer) spin_l_x.getValue(), (Integer) spin_l_y.getValue());
            } else if (radio_r.isSelected()) {
                doShift = true;
                angleShift = (double) spin_r_angle.getValue();
            }
            editMode = false;
            isRunning = true;
            window.dispose();
            remove(window);
            this.dispose();
            remove(this);
        } else if (s.equals(CANCEL)) {
            shift = new Vec2d(0, 0);
            angleShift = 0;
            editMode = false;
            isRunning = true;
            window.dispose();
            remove(window);
            this.dispose();
            remove(this);
        }
    }

}
