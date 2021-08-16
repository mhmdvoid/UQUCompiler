package compile.utils;

public enum IndentationKind {
    WhiteSpace(' '),
    DottedDashes('-'),
    LongContinuousDash('—');

    private final char shapeChar;

    IndentationKind(char shapeChar) {
        this.shapeChar = shapeChar;
    }

    public char getShapeChar() {
        return shapeChar;
    }
}
