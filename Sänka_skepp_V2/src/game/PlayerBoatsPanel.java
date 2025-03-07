/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import Score.AddNameButton;
import Score.ScoreKeeper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;

/**
 * @author einar.salamon
 */
//panelen som innehåller spelarens båtar
public final class PlayerBoatsPanel extends Panel implements MouseListener {
    ArrayList<Square> boatSquares;
    boolean hasLost;
    public static Stack<Square> notGS; // GS betyder guessed squares
    ScoreKeeper sk;

    //konstruktorn
    public PlayerBoatsPanel(int width, int height, ScoreKeeper sk) {
        super(width, height);
        notGS = createNotGS(new Stack<>(), squares);
        this.sk = sk;
        boatSquares = new ArrayList<>();
        hasLost = false;
        createBoatsButton();
        this.addMouseListener(this);
    }

    //knappen som skapar båtarna
    protected void createBoatsButton() {
        JButton startButton = new JButton("klar");
        add(startButton);
        startButton.setFont(buttonFont);
        startButton.setForeground(new Color(40, 200, 40, 200));
        startButton.setBounds(PANEL_WIDTH, 0, PANEL_WIDTH / 3, PANEL_HEIGHT);
        startButton.setBackground(new Color(120, 120, 120));
        startButton.setBorderPainted(false);
        startButton.setFocusable(true);
        startButton.addActionListener((ActionEvent e) -> {
            boatButtonPressed();
        });
    }

    //känner av när spelaren trycker på knappen
    private void boatButtonPressed() {
        if (!shouldPaintBoats()) {
            return;
        }
        createBoats();
        repaint();
    }

    //kollar om alla krav för att låsa in sina båtar uppfylls
    private boolean shouldPaintBoats() {
        return AddNameButton.buttonPressed
                && !boatsCreated
                && boatSquares.size() == boatsNumber; //säkerställer att spelaren har placerat ut alla sina båtar

    }

    //låser in spelarens båtar och ritar ut dem
    protected void createBoats() {
        for (Square temp : squares) {
            if (temp.marked) {
                temp.boat = true;
                temp.marked = false;
                temp.paintBoat = true;
                boatSquares.add(temp);
            }
        }
        boatsCreated = true;
    }

    //skapar och slumpar en stack med alla rutor som datorn inte har gissat på
    public Stack createNotGS(Stack notGS, Square[] squares) {
        notGS.clear();
        for (Square square : squares) {
            notGS.push(square);
        }
        return shuffleStack(notGS);
    }

    //datorn gör en slumpmässig gissning på en av spelarens rutor
    public void makeRandomGuess() {
        try {
            Square s = notGS.pop();
            if (s.boat) {
                s.hit = true;
            } else {
                s.missed = true;
            }
            //System.out.println(s.index);
            CompBoatsPanel.madeGuess = false; //gör så att spelaren kan gissa på en av datorns rutor igen
        } catch (EmptyStackException e) {
            System.out.println("klar");
        }
        repaint();
    }

    //kollar om spelaren har förlorat mot datorn
    private boolean hasLost() {
        if (!boatsCreated || boatSquares.isEmpty()) {
            return false;
        }
        int i = 0;
        do {
            Square s = boatSquares.get(i);
            if (!s.hit) {
                return false;
            }
            i++;
        } while (i < boatSquares.size());
        return true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSquares(g);
        writeBoatsPlaced(g);

        if (hasLost()) { //om spelaren har förlorat ska spelet avslutas
            runLooseSequence(g);
        }
    }

    private void writeBoatsPlaced(Graphics g) {
        Font font = new Font("Sans-seriif", Font.ITALIC, 10);
        g.setFont(font);
        g.setColor(backgroundColor);
        String boats = boatSquares.size() + "/" + boatsNumber;
        g.drawString(boats, 5, 13);
    }

    public void reset() {
        createSquares();
        createNotGS(notGS, squares);
        boatSquares.clear();
        hasLost = false;
        repaint();
     //   requestFocus();
    }

    private void runLooseSequence(Graphics g) {
        int score = sk.txa.currentPlayer.score;
        if (score > 0) {//hindrar en spelares poäng från att bli negativa
            score--; //tar bort ett poäng om spelaren har förlorat
        }
        StartOver.startOver("Du förlorade");
    }


    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    //alla metoder över behöver finnas men används aldrig

    //känner av när spelaren klickar på en ruta
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        selectSquare(x, y);
    }

    //markerar den valda rutan som en båt
    private void selectSquare(int x, int y) {
        if (!boatsCreated) {
            placeBoats(x, y);
        }
        repaint();
    }

    //markerar de valda rutorna som spelarens båtar
    private void placeBoats(int x, int y) {
        limitBoats();
        int boatIndex = findSquare(x, y);
        if (boatIndex == -1) {
            return;
        }

        Square temp = squares[boatIndex];
        if (!boatSquares.contains(temp)) {
            boatSquares.add(temp);
        }
        markBoatSquares();
    }

    //ser till att spelaren inte skapar för många båtar
    private void limitBoats() {
        if (boatSquares.size() >= boatsNumber) {
            boatSquares.get(0).marked = false;
            boatSquares.get(0).boat = false;
            boatSquares.removeFirst();
        }
    }

    //markerar de valda rutorna
    private void markBoatSquares() {
        for (Square s : boatSquares) {
            s.marked = true;
            s.boat = true;
        }
    }
}
