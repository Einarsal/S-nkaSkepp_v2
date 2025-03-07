/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * @author einar.salamon
 */
public class Panel extends JPanel {
    //alla globala variabler
    public Square[] squares; //lista med alla rutor
    public static ArrayList<Square> boatSquares; //lista med alla båtar
    Stack<Square> boatStack;
    Font buttonFont; //fonten på knapparna
    static int PANEL_WIDTH;
    int PANEL_HEIGHT;
    int columns = 10;
    int rows = 10;
    int squareWidth;
    int squareHeight;
    final static Color backgroundColor = new Color(25, 25, 25);
    final static Color buttonColor = new Color(120, 120, 120);
    static boolean boatsCreated = false; //har spelaren skapat sina båtar än
    final int boatsNumber = 6;
    Boat[] boats = new Boat[boatsNumber];

    //konstruktorn
    public Panel(int width, int height) {
        //initialiserar alla variabler
        buttonFont = new Font("Times New Roman", Font.BOLD, 20);
        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;
        Dimension size = new Dimension(PANEL_WIDTH + (PANEL_WIDTH / 3), PANEL_HEIGHT);
        boatSquares = new ArrayList<>();
        createSquares();
        placeBoats();

        //bestämmer utseende
        setBackground(backgroundColor);
        setFocusable(true);
        setLayout(null);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);

        repaint();
    }

    protected void createSquares() {
        //bredd och höjd på rutorna
        squareWidth = PANEL_WIDTH / columns;
        squareHeight = PANEL_HEIGHT / rows;
        squares = new Square[rows * columns];

        //loopar över varje rad och skapar en ruta för varje collumn
        for (int row = 0; row < rows; row++) {
            // bryt ut
            int yPos = squareHeight * row;

            for (int column = 0; column < columns; column++) {
                int xPos = squareWidth * column;
                int index = getIndex(column, row, columns);
                squares[index] = new Square(index, squareWidth, squareHeight, xPos, yPos);
            }
        }
    }

    //returnerar index på rutan genom att kolla vilken ruta som har de givna koordinaterna
    public int findSquare(int x, int y) {
        int column = x / squareWidth;
        int row = y / squareHeight;
        if (column >= 10 || row >= 10) {
            return -1;
        }
        return getIndex(column, row, columns);
    }

    //räknar ut vad index för den rutan är
    public int getIndex(int column, int row, int columns) {
        int i = row * columns + column;
        //return y * columns + column;
        return i;
    }

    //uppdaterar alla rutor och ritar ut dem
    protected void updateSquares(Graphics g) {
        for (Square s : squares) {
            s.draw(g);
        }
    }

    protected void placeBoats() {
        boatStack = createStack();
        for (int i = 0; i < boatsNumber; i++) {
            Square boatCorner = boatStack.pop();
            int cornerCol = boatCorner.xPos / squareWidth;
            int cornerRow = boatCorner.yPos / squareHeight;
            boats[i] = selectBoat(i, cornerRow, cornerCol);
        }
        repaint();
    }

    private Boat selectBoat(int i, int cornerRow, int cornerCol) {
        switch (i) {
            case 0:
                Carrier carrier = new Carrier(cornerRow, cornerCol, squareWidth, squareHeight, this);
                removeSquaresFromStack(carrier);
                return carrier;
            case 1:
                Destroyer destroyer = new Destroyer(cornerRow, cornerCol, squareWidth, squareHeight, this);
                removeSquaresFromStack(destroyer);
                return destroyer;
            case 2, 3:
                Cruiser cruiser = new Cruiser(cornerRow, cornerCol, squareWidth, squareHeight, this);
                removeSquaresFromStack(cruiser);
                return cruiser;
            case 4, 5:
                Speedboat speedboat = new Speedboat(cornerRow, cornerCol, squareWidth, squareHeight, this);
                removeSquaresFromStack(speedboat);
                return speedboat;
            default:
                return null;
        }
    }

    private void removeSquaresFromStack(Boat boat){
        boat.calculateCoveredSquares(boatStack);
    }

    private Stack<Square> createStack() {
        Stack<Square> stack = new Stack<>();
        for (int i = 0; i < 50; i++) {
            Square s = squares[i];
            stack.push(s);
        }
        /*

        for (Square s : squares) {
            stack.push(s);
        }
         */
        return shuffleStack(stack); //blandar stacken
    }

    //blandar och returnerar en stack
    protected Stack shuffleStack(Stack stack) {
        Random r = new Random();
        for (int i = stack.size() - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);

            Object temp = stack.get(i);
            stack.set(i, stack.get(j));
            stack.set(j, temp);
        }
        return stack;
    }

}
