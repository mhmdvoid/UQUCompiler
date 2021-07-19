package parser;

import lexer.LexerManager;
import lexer.Token;
import lexer.TokenType;
import ast.*;
import sema.AnalyzeExpression;
import sema.SemanticBase;

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
        var line = currentToken.getLine();
        var gMembers = new GlobalScope(line);   // Fixme: see line issue;
        while (see(TokenType.VAR) || see(TokenType.FUNC)) {
            if (have(TokenType.VAR))
                gMembers.addStatement(parseVarDeclAssign());
            else gMembers.addStatement(parseFuncDecl());
        }


        return new TranslationUnit(line, /*filename: should be from lexer*/ "main.uqulang", gMembers);
    }

    /* AssignmentOperationNode parseAssignmentStatement() {

        var line = currentToken.getLine();
        var lhs = parseVarDecl();
        var rhs = parseInitialization();
        parseEat(TokenType.SEMICOLON, "assignment statement end ; ");
        return new AssignmentOperationNode(line, lhs, rhs);
    }
    */

    VarDecl parseVarDeclAssign() {
        // in every node out of the way !
        var line = currentToken.getLine();
        parseEat(TokenType.IDENTIFIER, "variable name missing");
        var varName = skippedToken.getTokenValue();
        // we should have a diagnostic engine ? w/ type-checker;
        parseEat(TokenType.COLON, "colon for type");
        var type = parseType();
        var initial = parseInitialization();
        parseEat(TokenType.SEMICOLON, "var decl statement missing `;` line" + line);
        return new VarDecl(line, varName, type, initial);
    }

    Expression parseInitialization() {
        var line = currentToken.getLine();
        parseEat(TokenType.ASSIGN_OP, "variable initialization should start with `=`");
        return parseValue();
    }

    Expression parseValue() {
        var line = currentToken.getLine();
        if (have(TokenType.NUMBER_LITERAL)) {
            return new IntegerLiteral(line, skippedToken.getTokenValue());
        } else if (have(TokenType.TRUE) || have(TokenType.FALSE)) {
            return new BoolLiteral(line, skippedToken.getTokenValue());
        }

        return null;  // should never reach
    }

    FuncDeclNode parseFuncDecl() {
        var line = currentToken.getLine();
        if (parseEat(TokenType.FUNC, "func keyword missing; signature should start with func ")) {
            skipTill(TokenType.SEMICOLON);
        }
        return new FuncDeclNode(line, parseType(), parseIdentifier(), parseParams());
    }

    /*IdentifierObject*/ Type parseType() {

        switch (currentToken.getType()) {
            case INT_KWD -> { consume(); return new Type(Type.BasicType.Int);}
            case BOOL -> { consume(); return new Type(Type.BasicType.Bool); }
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
        var line = currentToken.getLine();
        var idName = parseIdentifier();
        parseEat(TokenType.COLON, "parameter should be followed by colon");
        var type = parseType();

        return new ParameterNode(line, idName, type);
    }

    BlockNode parseBlock() {
        // TODO: 7/10/21 Unimplemented method, should introduce a local scope
        return null;
    }

    public Token token() {
        return currentToken;
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
        var program = parser.translationUnit();
        System.out.println(program);
    }
}
