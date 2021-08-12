package lexer;

import lex_erro_log.LexLog;
import lex_erro_log.ErrorLogger;

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




    public boolean advance() {
        // var x = 10
        //          ^
        if (charPosition.column < source.length()) {
            wrappedChar = source.charAt(charPosition.column ++);
            charPosition.index ++;
            if (wrappedChar == '\n') {
                charPosition.index = 0;
                charPosition.row ++;
            }
            return true;
        }

        wrappedChar = '\0';
        return false;

    }
    public boolean peek(int by) {
        var ableToPeek = true;
        for (var i = 0; i < by && ableToPeek; i++)
            ableToPeek = advance();
        return ableToPeek;
    }

    // Todo: return a logger instance and check for null test;
    public boolean log(int actualIdx, int newLineIdx) { // can throw? right?
        return ErrorLogger.log(source, actualIdx, newLineIdx);
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

