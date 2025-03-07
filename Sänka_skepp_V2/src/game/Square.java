/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.*;

/**
 * @author einar.salamon
 */
//varje ruta är en separat klass som ritar ut sig själv
public class Square {

    public int xPos, yPos; //rutans koordinater
    int width, height; //dimensionerna på rutan

    //index på rutan;
    int index;

    //rutans färger
    int alpha = 127;
    Color squareColor = new Color(230, 240, 190);
    Color markedColor = new Color(255, 0, 0, alpha);
    Color boatColor = new Color(30, 30, 30);

    int[] xFill1, xFill2, yFill1, yFill2;

    public boolean missed;
    public boolean hit;
    public boolean marked;
    public boolean boat;
    public boolean paintBoat;

    public Square(int index, int width, int height, int xPos, int yPos) {
        this.index = index;
        this.boat = false;
        this.marked = false;
        this.hit = false;
        this.paintBoat = false;
        this.missed = false;

        this.width = width;
        this.height = height;
        this.xPos = xPos;
        this.yPos = yPos;

        //listorna för korsets ena linje
        xFill1 = new int[]{xPos, xPos, xPos + width - 4, xPos + width, xPos + width, xPos + 4};
        yFill1 = new int[]{yPos, yPos + 4, yPos + height, yPos + height, yPos + height - 4, yPos};

        //listorna för korsets andra linje
        xFill2 = new int[]{xPos + width - 4, xPos + width, xPos + width, xPos + 4, xPos, xPos};
        yFill2 = new int[]{yPos, yPos, yPos + 4, yPos + height, yPos + height, yPos + height - 4};
    }

    public void draw(Graphics g) {

        //ritar ut rutan
        paintSquare(g, squareColor);

        //markerar rutan
        if (marked) {
            paintSquare(g, markedColor);
        }
        //ritar båten
        if (boat && paintBoat) {
            g.setColor(boatColor);
            g.fillOval(xPos, yPos, width, height);
        }
        //blått kors om en båt blev träffad
        if (hit) {
            g.setColor(Color.blue);
            drawCross(g);
        }
        //rött kors om man inte träffade båten
        if (missed) {
            g.setColor(Color.red);
            drawCross(g);
        }

        //gör det till en separat metod så att det är lätt att stäna av
        writeIndex(g);
    }

    //bryter ut upprepad kod för att rita rutan
    private void paintSquare(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(xPos + 2, yPos + 2, width - 4, height - 4);
    }

    //ritar korsen
    private void drawCross(Graphics g) {
        g.fillPolygon(xFill1, yFill1, xFill1.length);
        g.fillPolygon(xFill2, yFill2, xFill2.length);
    }

    //skriver ut rutans index så att man slipper räkna
    private void writeIndex(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Sans-Serif", Font.BOLD, (width + height) / 4));
        g.drawString("" + index, xPos + width/4, yPos + (2*height)/3);
    }

}
