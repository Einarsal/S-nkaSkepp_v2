package Score;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

//knappen för att välja/skapa spelare
public class AddNameButton extends JButton {
    public static boolean buttonPressed = false;
    final static Color backgroundColor = new Color(120, 120, 120);
    final static Border border = BorderFactory.createLineBorder(backgroundColor, 2);

    //konstruktorn gör inget utom att ta in Leaderboard så att den kan nås senare
    public AddNameButton(Leaderboard sk) {
        createButton(sk);
    }

    //skapar och ritar ut knappen
    private void createButton(Leaderboard txa){
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 20);
        setText("Välj");
        setFont(buttonFont);
        setForeground(Color.black);
        setBackground(backgroundColor);
        setBorder(border);
        addActionListener((ActionEvent e) -> {
            buttonPressed(txa);
        });
    }

    //känner av ett knapptryck
    private void buttonPressed(Leaderboard txa) {
        txa.existingPlayer();
        txa.writeToFile();
        buttonPressed = true;
    }
}
