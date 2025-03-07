package Score;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;

//detta är i princip en frame i en min huvudframe med en ny grid
public class ScoreKeeper extends JInternalFrame {
    public int width, height;
    final static Color backgroundComponentColor = new Color(120, 120, 120);
    final static Border border = BorderFactory.createLineBorder(backgroundComponentColor, 2);

    //komponenter
    public Leaderboard txa;
    public AddNameButton btn;
    userNameField txf;
    JScrollPane sp;

    //konstruktorn
    public ScoreKeeper(int mainWidth, int mainHeight) {
        this.width = mainWidth;
        this.height = mainHeight;
        txf = new userNameField(width);
        txa = new Leaderboard(width, height, txf);
        sp = new JScrollPane(txa);
        btn = new AddNameButton(txa);
        grid();
    }

    //bryter ut hela komponentens grid
    private void grid() {
        final Color backgroundColor = new Color(25, 25, 25);
        Dimension size = new Dimension(width, height);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setPreferredSize(size);
        this.pack();
        setResizable(false);
        setLayout(new GridBagLayout());
        //skapar fyra olika constraints för att lättare kunna skilja dem
        GridBagConstraints txfC = new GridBagConstraints();
        GridBagConstraints txaC = new GridBagConstraints();
        GridBagConstraints btnC = new GridBagConstraints();
        GridBagConstraints lblC = new GridBagConstraints();

        txfC.gridx = 1;
        txfC.gridy = 0;
        txfC.weightx = 0.5;
        txfC.weighty = 0.0;
        txfC.fill = GridBagConstraints.HORIZONTAL;
        txfC.insets = new Insets(15, 15, 15, 15);
        add(txf, txfC);

        txaC.gridx = 0;
        txaC.gridy = 1;
        txaC.weighty = 1;
        txaC.weightx = 0;
        txaC.insets = new Insets(0, 5, 15, 15);
        txaC.fill = GridBagConstraints.BOTH;
        txaC.gridwidth = 3;
        txaC.gridheight = 1;
        getContentPane().add(sp, txaC);

        btnC.gridx = 2;
        btnC.gridy = 0;
        btnC.insets = new Insets(0, 0, 0, 15);
        add(btn, btnC);

        //lägger JLabel i en Box för att den ska kunna få en bakgrund
        Box box = Box.createVerticalBox();
        JLabel lbl = new JLabel();
        lbl.setText("användarnamn:");
        lbl.setBackground(backgroundComponentColor);
        lbl.setOpaque(true);
        lbl.setForeground(Color.black);
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblC.gridx = 0;
        lblC.gridy = 0;
        lblC.weightx = 0.0;
        lblC.fill = GridBagConstraints.BOTH;
        lblC.insets = new Insets(15, 5, 15, 0);
        box.add(lbl, lblC);
        add(box, lblC);

        setBackground(backgroundColor);
        setResizable(false);
        setBorder(BorderFactory.createEmptyBorder());
        pack();

        setVisible(true);
    }

    @Override
    public void setUI(InternalFrameUI ui) {
        super.setUI(ui);
        BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
        if (frameUI != null) {
            frameUI.setNorthPane(null);
        }
    }
}
