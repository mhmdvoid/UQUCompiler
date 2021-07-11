package parser;

import lexer.LexerManager;
import lexer.Token;
import lexer.TokenType;
import ast.*;
import java.util.ArrayList;

public class Parser {

    private final LexerManager lexer;
    private Token skippedToken, currentToken;
    private int idx;
    private boolean inError;

    public boolean isInError() {
        return inError;
    }

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
            skippedToken = currentToken;
            currentToken = lexer.getTokens().get(idx++);
            return true;
        }
        return false;
    }


    /**
     * Should consume otherwise it's optional token found
     *
     * @param tokenType TokenType
     * @return bool if match
     */
    boolean have(TokenType tokenType) {   // This is like consumeIf(tokenType);
        if (see(tokenType)) {
            consume();
            return true;
        }
        return false;
    }

    /**
     * check currentToken equal the one being parsed;
     *
     * @param tokenType TokenType
     * @return true if match
     */
    boolean see(TokenType tokenType) {
        return tokenType == currentToken.getType();
    }

    /**
     * This should consume otherwise throw syntax error;
     *
     * @param tokenType TokenType
     * @return true if parse error occurs, consume and return false otherwise
     */
    boolean parseEat(TokenType tokenType, String parseErrorMsg) {
        if (see(tokenType)) {
            consume();
            return false;
        } else {
            inError = true;
            return true;
        }
    }

    public TranslationUnit translationUnit() {
        consume();  // Pump the lexer;
        parseMethodDecl();  // Todo: Support more top-level declaration. e.g more funcs & vars
        return null;
    }

    FuncDeclNode parseMethodDecl() {
        if (parseEat(TokenType.FUNC, "func keyword missing; signature should start with func ")) {
            skipTill(TokenType.SEMICOLON);
        }
        return new FuncDeclNode(parseType(), parseIdentifier(), parseParams());
    }

    /*IdentifierObject*/ String  parseType() {

        switch (currentToken.getType()) {
            case INT_KWD -> {
                consume(); return skippedToken.getTokenValue(); }
            default ->  { return null; } // Todo:  if null we;ll throw an error later
        }
    }

    String parseIdentifier() {
        parseEat(TokenType.IDENTIFIER, "Missing Identifier");
        return skippedToken.getTokenValue();
    }

    ArrayList<ParameterNode> parseParams() {
        var params = new ArrayList<ParameterNode>();
        parseEat(TokenType.L_PAREN, "`(` missing");
        if (currentToken.getType() == TokenType.R_PAREN)
            return params;
        do {
            params.add(parseParameter());
        } while (have(TokenType.COMMA));
        return params;
    }

    ParameterNode parseParameter() {
        var idName = parseIdentifier();
        parseEat(TokenType.COLON, "parameter should be followed by colon");
        var type = parseType();

        return new ParameterNode(idName, type);
    }

    BlockNode parseBlock() {
        // TODO: 7/10/21 Unimplemented method
        return null;
    }

    public Token token() {
        return currentToken;
    }

}
