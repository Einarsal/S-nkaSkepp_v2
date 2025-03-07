package game;

import Score.ScoreKeeper;

import javax.swing.*;

//klassen har en popup som låter spelaren börja om eller avsluta programmet
public class StartOver {
    static PlayerBoatsPanel pl;
    static CompBoatsPanel cp;
    static ScoreKeeper sk;

    //konstruktorn
    public StartOver(PlayerBoatsPanel pl, CompBoatsPanel cp, ScoreKeeper sk) {
        StartOver.pl = pl;
        StartOver.cp = cp;
        StartOver.sk = sk;
    }

    //metoden som skapar en popup och sedan startar om eller avslutar
    //den är static så att alla andra klasser kan nå den
    static void startOver(String gameOverMSG) {
        JOptionPane optionPane = new JOptionPane();
        int startOver = optionPane.showConfirmDialog(pl, gameOverMSG + "\n vill du köra igen?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (startOver == JOptionPane.NO_OPTION) {
            System.exit(1); //om man trycker NO avslutas programmet
        }

        Panel.boatsCreated = false;

        pl.reset();
        cp.reset();
        
        pl.repaint();
        cp.repaint();


    }
}
