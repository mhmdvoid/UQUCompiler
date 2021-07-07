package parser;

import Lexer.LexerManager;
import Lexer.Token;

public class Parser {

    private final LexerManager lexer;
    private Token currentToken;
    private int idx;

    public Parser(String srcPath) {
        lexer = new LexerManager(srcPath);
        idx = 0;
    }

    boolean consume() {
        if (idx < lexer.getTokens().size()) {
            currentToken = lexer.getTokens().get(idx++);
            return true;
        }
        return false;
    }

    public Token token() {
        return currentToken;
    }
}
