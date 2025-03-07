package Score;

import javax.swing.*;

//en JTextField där spelaren skriver in sitt namn
public class userNameField extends JTextField {
    int width, height;
    //JTextField txf = new JTextField();

    //konstruktor
    public userNameField(int width) {
        this.width = width;
        height = 100;
        setBorder(ScoreKeeper.border);
    }

    //anropas av AddNameButton och returnerar en Player med namnet som spelaren har skrivit in
    public String getPlayer() {
        return this.getText() + ": ";
    }

}
