package game;

public class Carrier extends Boat{

    public Carrier(int row, int column, int width, int height, Panel panel) {
        super(row, column, width, height, panel);
        rowSpan = 3;
        colSpan = 2;
        initialiseCoveredSquares(rowSpan, colSpan);
    }
}
