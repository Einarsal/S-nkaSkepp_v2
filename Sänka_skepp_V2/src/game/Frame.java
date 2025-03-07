/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import Score.ScoreKeeper;

import java.awt.*;
import javax.swing.*;

/**
 * @author einar.salamon
 */
public class Frame extends JFrame {

    //private String name;
    static boolean gameOver = false;
    static Frame frame;
    private PlayerBoatsPanel pl;
    private CompBoatsPanel cp;
    private ScoreKeeper sk;
    private StartOver so;

    public static void main(String[] args) {
        //skapar en frame i konstruktorn
        new Frame("Battleship");
    }

    //konstruktorn
    public Frame(String a) {
        super("Battleship");
        final int WIDTH = 300;
        final int HEIGHT = 300;
        sk = new ScoreKeeper(WIDTH, HEIGHT);
        pl = new PlayerBoatsPanel(WIDTH, HEIGHT, sk);
        cp = new CompBoatsPanel(WIDTH, HEIGHT, pl, sk);
        so = new StartOver(pl, cp, sk);
        setLayout(new GridBagLayout()); //I en gridbag layout kan man bestämma storleken för varje komponent
        GridBagConstraints c = new GridBagConstraints(); //c är komponentens egenskaper

        //c.weightx = 1;
        //c.weighty = 0.1;
        //c.gridy = 0;
        //c.fill = GridBagConstraints.BOTH;

        addPanelToGrid(0, cp, c);
        addPanelToGrid(1, pl, c);
        addPanelToGrid(2, sk, c);

        //sätter alla egenskaper för programmets frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        cp.requestFocus(true);
        pl.requestFocus(true);
        setVisible(true);
        gameOver = false;
    }

    //bryter ut upprepad kod
    public void addPanelToGrid(int x, Component component, GridBagConstraints c) {
        c.gridx = x;
        add(component, c);
    }

}
