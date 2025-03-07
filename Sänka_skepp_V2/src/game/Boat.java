package game;

import java.awt.*;
import java.util.Stack;

public class Boat {
    int cornerRow, cornerColumn;
    int width, height;
    int rowSpan, colSpan;
    Square[] coveredSquares;
    Square[] squares;
    Panel panel;
    final Color boatColor = Color.black;

    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Boat(int cornerRow, int column, int width, int height, Panel panel) {
        this.panel = panel;
        this.cornerRow = cornerRow;
        this.cornerColumn = column;
        this.width = width;
        this.height = height;
        //coveredSquares = new Square[rowSpan * colSpan];
        squares = panel.squares;
        // calculateCoveredSquares();
    }

    protected void initialiseCoveredSquares(int rowSpan, int colSpan){
        coveredSquares = new Square[rowSpan * colSpan];
    }

    public void calculateCoveredSquares(Stack<Square> stack) {
        int index = 0;
        for (int row = cornerRow; row < cornerRow + rowSpan; row++) {
            for (int column = cornerColumn; column < cornerColumn + colSpan; column++) {
                Square square;
                try {
                    int i = panel.getIndex(row, column, panel.columns);
                    square = squares[panel.getIndex(column, row, panel.columns)];
                }
                catch(ArrayIndexOutOfBoundsException e){
                    continue;
                }
                coveredSquares[index] = square;
                panel.boatSquares.add(square);
                removeSquareFromStack(square, stack);
                index++;
            }
        }
        setBoat();
    }

    private void removeSquareFromStack(Square square, Stack<Square> stack) {
        stack.remove(square);
    }

    /*
    private int findStackIndex(Square square) {}
     */

    private Point calculateCords(int row, int column) {
        int x = column * panel.squareWidth;
        int y = row * panel.squareHeight;
        return new Point(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(boatColor);
        for (Square s : coveredSquares) {
            s.draw(g);
        }
    }

    protected void setBoat() {
        for (Square s : coveredSquares) {
            s.boat = true;
            s.paintBoat = true;
        }
    }

}
