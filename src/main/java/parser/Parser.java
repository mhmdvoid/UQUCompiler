package parser;

import lexer.LexerManager;
import lexer.Token;
import lexer.TokenType;
import ast.*;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

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
            skippedToken = currentToken;
            consume();
            return false;
        } else {
            inError = true;
            return true;
        }
    }

    public TranslationUnit translationUnit() {
        consume();  // Pump the lexer;
        parseMethodDecl();
        return null;
    }

    MethodDeclNode parseMethodDecl() {

        if (parseEat(TokenType.FUNC, "func keyword missing; signature should start with func ")) {
            // Todo: Handle error;
        }

        parseEat(TokenType.INT_KWD, "function should have return type");
        var returnType = skippedToken.getTokenValue();
        parseEat(TokenType.IDENTIFIER, "function name missing");
        var funcName = skippedToken.getTokenValue();

        // Fixme: we'll skip types for now and implement them later;

        return new MethodDeclNode(returnType, funcName, parseParams());
    }

    ParameterNode parseParams() {
        var params = new ArrayList<ParamNode>();
        parseEat(TokenType.L_PAREN, "`(` missing");
        if (currentToken.getType() == TokenType.R_PAREN)
            return new ParameterNode(params);
        do {
            parseEat(TokenType.IDENTIFIER, "param should have type and name");
            var pName = skippedToken.getTokenValue();
            parseEat(TokenType.COLON, "colon after param name");
            parseEat(TokenType.INT_KWD, "param type missing");
            var type = skippedToken.getTokenValue();

            params.add(new ParamNode(pName, type));
        } while (have(TokenType.COMMA));
        return new ParameterNode(params);
    }

    BlockNode parseBlock() {
        // TODO: 7/10/21 Unimplemented method
        return null;
    }

    public Token token() {
        return currentToken;
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
        parser.translationUnit();
    }
}
