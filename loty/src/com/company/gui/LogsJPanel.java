package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * LogsGui creates the design of the top-right
 * window of graphic user interface and is responsible for
 * handling the display of warnings about the potential danger of collisions
 * and information about collisions that actually happened.
 * @author Magdalena Sawicka
 */
public class LogsJPanel extends JPanel
{
    private JLabel zagrozenia = new JLabel("ZAGROŻENIA:");
    private JLabel kolizje = new JLabel("KOLIZJE:");
    private JTextArea zagr = new JTextArea();
    private JTextArea kol = new JTextArea();
    private JScrollPane scrollZ, scrollK;
    private LinkedList<Integer> logs;
    private LinkedList<LocalTime> logstime;
    private int Xsize, Ysize ;

    public LogsJPanel(Dimension dim){

        Xsize = (int)(dim.width/2*0.95);
        Ysize = (int)(dim.height*0.13) ;

        logs = new LinkedList<>();
        logstime = new LinkedList<>();

        setPreferredSize(dim);
        setBackground(Color.pink);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        BorderLayout layout = new BorderLayout();
        layout.setVgap(Ysize);

        settingJLabel(zagrozenia);
        settingJLabel(kolizje);

        add(zagrozenia, BorderLayout.LINE_START);
        add(kolizje, BorderLayout.LINE_END);

        scrollZ = new JScrollPane();
        settingTextArea(zagr, scrollZ);
        add(scrollZ,  BorderLayout.LINE_START);


        scrollK = new JScrollPane();
        settingTextArea(kol, scrollK);
        add(scrollK,  BorderLayout.LINE_END);



    }

    public void settingTextArea(JTextArea a, JScrollPane s)
    {
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        a.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        a.setOpaque(true);
        a.setBackground(Color.white);
        a.setFocusable(false);
        a.setEditable(false);

        s.setPreferredSize(new Dimension(Xsize, (int)(Ysize*5.92)));
        s.getViewport().setBackground(Color.white);
        s.getViewport().add(a);

    }


    public void settingJLabel(JLabel a)
    {
        a.setPreferredSize(new Dimension(Xsize,Ysize));
        a.setHorizontalTextPosition(SwingConstants.CENTER);
        a.setHorizontalAlignment(SwingConstants.CENTER);
        a.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        a.setOpaque(true);
        a.setBackground(Color.white);
    }

    public void updateK(LinkedList<Integer> kolizje,LocalTime time) {
        if (kolizje.size() != 0) {
            for (Integer integer : kolizje) {
                logs.add(integer);
                logstime.add(time);
            }
        }

        kol.setText("");
        for (int i = logs.size() - 1; i >= 0; i--) {
            kol.append(" " + logstime.get(i).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            kol.append("    Zderzenie, statek nr: " + logs.get(i) + ".\n");

        }
    }


    public void updateZ(LinkedList<Integer> zagrozenia){
        zagr.setText("");
        for (Integer integer : zagrozenia) {
            zagr.append(" Statkowi nr: " + integer + " grozi zderzenie!\n");
        }
    }

}