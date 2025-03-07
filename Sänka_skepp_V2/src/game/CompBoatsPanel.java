/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import Score.ScoreKeeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.JButton;

/**
 * @author einar.salamon
 */

//Panelen som spelaren gissar i och datorn har sina båtar i.
public class CompBoatsPanel extends Panel implements MouseListener {
    PlayerBoatsPanel p;
    boolean hasWon;
    static boolean madeGuess = false; //gör så att inte spelaren kan gissa flera gånger före datorn
    ArrayList<Square> guessedSquares;
    ScoreKeeper sk;

    //konstruktorn
    public CompBoatsPanel(int width, int height, PlayerBoatsPanel p, ScoreKeeper sk) {
        super(width, height);
        this.sk = sk;
        this.p = p;
        guessedSquares = new ArrayList<>();
        //placeBoats();
        makeGuessButton();
        this.addMouseListener(this);
        hasWon = false;
    }

    //skapar knappen som spelaren låser sin gissning med
    protected void makeGuessButton() {
        JButton guessButton = new JButton("gissa");
        add(guessButton);
        guessButton.setFont(buttonFont);
        guessButton.setForeground(new Color(0, 25, 200, 150));
        guessButton.setBounds(PANEL_WIDTH, 0, PANEL_WIDTH / 3, PANEL_HEIGHT);
        guessButton.setBackground(buttonColor);
        guessButton.setBorderPainted(false);
        guessButton.setFocusable(true);
        guessButton.addActionListener(this::makeGuessButtonPressed);
    }

    //känner av när spelaren trycker på knappen
    private void makeGuessButtonPressed(ActionEvent e) {
        //avsluta om spelarens båtar inte har skapats än
        if (!boatsCreated) {
            return;
        }
        playerGuess();
        //låter datorn gissa i panelen med spelarens båtar
        if (!hasWon && madeGuess) {
            p.makeRandomGuess();
        }
        repaint();
    }

/*
    //skapar en stack av alla rutor och blandar den
    private Stack<Square> createStack() {
        Stack<Square> stack = new Stack<>();
        for (Square s : squares) {
            stack.push(s);
        }
        return shuffleStack(stack); //blandar stacken
    }
    //placerar båtar i de översta rutorna i en slumpad stack
    public void placeBoats() {
        Stack<Square> stack = createStack();
        for (int i = 0; i < boatsNumber; i++) {
            Square s = stack.pop();
            s.boat = true;
            boatSquares.add(s);
            System.out.println(s.index);
        }
    }
 */

    //kollar om spelaren har vunnit
    public boolean hasWon() {
        for (Square s : boatSquares) {
            if (!s.hit) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSquares(g);

        if (hasWon()) { //om spelaren har vunnit ska spelet avslutas
            winSequence(g);
        }
    }

    //ökar spelarens poäng och uppdaterar textfilen innan den startar om
    private void winSequence(Graphics g) {
        sk.txa.currentPlayer.score++;
        sk.txa.updateList();
        StartOver.startOver("Du vann");
    }

    //blir kallad från klassen StartOver och gör en soft-reset av den här komponenten
    public void reset(){
        hasWon = false;
        createSquares();
        boatSquares.clear();
        guessedSquares.clear();
        placeBoats();
        repaint();
        requestFocus();
    }

    //kollar om rutan spelaren gissade på; har en båt på sig
    private void playerGuess() {
        for (Square s : squares) {
            if (s.marked && !guessedSquares.contains(s)) {
                madeGuess = true;
                if (s.boat) {
                    s.hit = true;
                } else {
                    s.missed = true;
                }
                s.marked = false;
                guessedSquares.add(s);
            }
        }
        repaint();
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
        placeGuess(x, y);
    }

    //metoden kollar vilken ruta spelaren gissade på och markerar sedan denna ruta
    private void placeGuess(int x, int y) {
        if (boatsCreated && x < PANEL_WIDTH && y < PANEL_HEIGHT) {
            int i = findSquare(x, y);
            if (i != -1) { //kollar om spelaren klickade utanför rutnätet
                for (Square temp : squares) {//avmarkerar alla tidigare rutor så att bara en ruta är markerad
                    temp.marked = false;
                }
                squares[i].marked = true;//markerar den nya rutan
            }
        }
        repaint();
    }


}
