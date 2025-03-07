package game;

public class Speedboat extends Boat{

    public Speedboat(int row, int column, int width, int height, Panel panel) {
        super(row, column, width, height, panel);
        rowSpan = 1;
        colSpan = 1;
        initialiseCoveredSquares(rowSpan, colSpan);

    }

}
