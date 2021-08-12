package lexer;

public class Position {
    public int row = 1;
    int column;  // should be private ? write ?
    public int index;

    public Position(int row, int index) {
        this.row = row;
        this.index = index;
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
