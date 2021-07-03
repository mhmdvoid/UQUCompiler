package Lexer;

public class CharManager {

    // we store the source ? and advance?
    private char wrappedChar;  // currentChar;

    private final String source;

    private final Position charPosition;

    public CharManager(String source) {
        this.charPosition = new Position();
        this.source = source;
        advance();
    }

    boolean advance() {
        if (charPosition.column < source.length()) {
            wrappedChar = source.charAt(charPosition.column ++);
            if (wrappedChar == '\n') {

                charPosition.row ++;
            }
            return true;
        }

        wrappedChar = '\0';
        return false;

    }

    public char getWrappedChar() {
        return wrappedChar;
    }

    public Position getCharPosition() {
        return charPosition;
    }




    public char currentChar() {
        return wrappedChar;
    }
}

class Position {
    int row = 1;
    int column;  // should be private ? write ?
}
