package parser;

import Lexer.LexerManager;
import Lexer.Token;
import Lexer.TokenType;

public class Parser {

    private final LexerManager lexer;
    private Token currentToken;
    private int idx;

    public Parser(String srcPath) {
        lexer = new LexerManager(srcPath);
        idx = 0;
    }


    /// Helpers

    void skipTill(TokenType tokenType) {

        while (currentToken.isNot(tokenType) && currentToken.isNot(TokenType.EOF))
            // Todo: Switch on tokens;
            consume();

    }


    boolean consume() {
        if (idx < lexer.getTokens().size()) {
            currentToken = lexer.getTokens().get(idx++);
            return true;
        }
        return false;
    }


    /**
     * Should consume otherwise it's optional token found
     * @param tokenType TokenType
     * @return bool if match
     */
    boolean have(TokenType tokenType) {
        if (see(tokenType)) {
            consume();
            return true;
        }
        return false;
    }

    /**
     * check currentToken equal the one being parsed;
     * @param tokenType TokenType
     * @return true if match
     */
    boolean see(TokenType tokenType) {
        return tokenType == currentToken.getType();
    }


    /**
     * This should consume otherwise throw syntax error;
     * @param tokenType TokenType
     * @return true if parse error occurs, consume and return false otherwise
     */
    boolean parseEat(TokenType tokenType, String parseErrorMsg) {
        if (see(tokenType)) {
            consume();
            return false;
        } else {
            System.err.println(parseErrorMsg);
            return true;
        }
    }

    public Token token() {
        return currentToken;
    }
}
