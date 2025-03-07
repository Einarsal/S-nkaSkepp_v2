package game;

public class Destroyer extends Boat{

    public Destroyer(int row, int column, int width, int height, Panel panel) {
        super(row, column, width, height, panel);
        rowSpan = 4;
        colSpan = 1;
        initialiseCoveredSquares(rowSpan, colSpan);

    }
}
