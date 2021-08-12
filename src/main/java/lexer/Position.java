package lexer;

public class Position {
    public int row = 1;
    public int column;  // should be private ? write ?
    public int index;

    public Position(int row, int index, int column) {
        this.row = row;
        this.index = index;
        this.column = column;
    }

    public Position() {
    }
    public int newColumn() {
        return index;
    }

    @Override
    public String toString() {
        return " line: " + row + ", index: " + index;
    }
}
