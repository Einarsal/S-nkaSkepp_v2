package game;

public class Cruiser extends Boat{

    public Cruiser(int row, int column, int width, int height, Panel panel) {
        super(row, column, width, height, panel);
        rowSpan = 3;
        colSpan = 1;
        initialiseCoveredSquares(rowSpan, colSpan);
    }
}
