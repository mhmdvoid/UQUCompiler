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
            System.out.println(parseErrorMsg);
            System.err.println("Expected " + tokenType + " Got " + currentToken);
            return true;
        }
    }

    public TranslationUnit translationUnit() {
        consume();  // Pump the lexer;
        var list = new ArrayList<>();
        while (have(TokenType.VAR)) {
            list.add(parseAssignmentStatement());
        }

//        TranslationUnit translationUnit = new TranslationUnit(list)));
        return null;
//        parseMethodDecl();  // Todo: Support more top-level declaration. e.g more funcs & vars
    }

    // var isGlobal : bool = true;
    // Syntax diagnostic engine;
    AssignmentOperationNode parseAssignmentStatement() {

        var lhs = parseVarDecl();
        var rhs = parseInitialization();
        parseEat(TokenType.SEMICOLON, "assignment statement end ; ");
        return new AssignmentOperationNode(lhs, rhs);
    }

    LhsVarNode parseVarDecl() {
            parseEat(TokenType.IDENTIFIER, "variable name missing");
            var varName = skippedToken.getTokenValue();
            // we should have a diagnostic engine ? w/ type-checker;
            parseEat(TokenType.COLON, "colon for type");
            var type = parseType();
            return new LhsVarNode(varName, type);
//            if (see(TokenType.SEMICOLON)) {
//                // todo: return new VarDeclaration(varName, varType);
//            } else {
//                // todo: parse VarInitializationNode;
//
//            }
        }

    Expression parseInitialization() {
        parseEat(TokenType.ASSIGN_OP, "variable initialization should start with `=`");
        return new IntegerLiteral(parseValue());
    }

    String parseValue() {
        if (have(TokenType.NUMBER_LITERAL)) {
            return skippedToken.getTokenValue();
        }
        return ""; // fixme;
    }

    FuncDeclNode parseMethodDecl() {
        if (parseEat(TokenType.FUNC, "func keyword missing; signature should start with func ")) {
            skipTill(TokenType.SEMICOLON);
        }
        return new FuncDeclNode(parseType(), parseIdentifier(), parseParams());
    }

    /*IdentifierObject*/ Type parseType() {

        switch (currentToken.getType()) {
            case INT_KWD -> {
                consume(); return new Type(Type.BasicType.Int); }
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

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
        parser.translationUnit();
    }
}
