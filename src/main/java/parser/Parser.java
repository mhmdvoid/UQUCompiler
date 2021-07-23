package parser;

import ast.type.BuiltinType;
import ast.type.Type;
import ast.type.TypeAliasKind;
import ast.type.TypeKind;
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
        var line = currentToken.getLine();
        var gMembers = new GlobalScope(line);   // Fixme: see line issue;
        while (see(TokenType.FUNC) || see(TokenType.VAR) || see(TokenType.TYPEALIAS)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case VAR -> gMembers.addStatement(parseVarDeclAssign());
                case FUNC -> gMembers.addStatement(parseFuncDecl());
                case TYPEALIAS -> { have(TokenType.TYPEALIAS);gMembers.addStatement(parseTypealias());}
                default -> System.out.println("Unknown syntax construction");
            }

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
        parseEat(TokenType.VAR, "var keyword missing;");
        var line = currentToken.getLine();
        parseEat(TokenType.IDENTIFIER, "variable name missing");
        var varName = skippedToken.getTokenValue();
        // we should have a diagnostic engine ? w/ type-checker;
        parseEat(TokenType.COLON, "colon for type");
        var varType = parseType();
        var initial = parseInitialization();
        parseEat(TokenType.SEMICOLON, "var decl statement missing `;` line" + line);
        return new VarDecl(line, varName, varType, initial);
    }

    Expression parseInitialization() {
        var line = currentToken.getLine();
        parseEat(TokenType.ASSIGN_OP, "variable initialization should start with `=`");
        return parseRhs();
    }

    Expression parseRhs() {
        return parseAdditive();
    }

    private Expression parseAdditive() {
        var line = currentToken.getLine();
        var lhs =  parseMul();
        while (true) {
            if (have(TokenType.ADD_OP))  {
                lhs = new AddOpExpression(line, lhs, parseMul());
            } else break;
        }
        return lhs;
    }

    private Expression parseMul() {
        var lhs = parsePrimary();
        while (true) {
            if (have(TokenType.MUL_OP))
                lhs = new MulOpExpression(currentToken.getLine(), lhs, parsePrimary());
            else break;
        }
        return lhs;
    }


    private Expression parsePrimary() {
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
        return new FuncDeclNode(line, parseType(), parseIdentifier(), parseParams(), parseBlock());
    }

    /*IdentifierObject*/ Type parseType() {
        switch (currentToken.getType()) {
            case INT_KWD -> { consume(); return new BuiltinType(BuiltinType.BuiltinContext.S_INT_32);}
            case BOOL -> { consume(); return new BuiltinType(BuiltinType.BuiltinContext.BOOL_8); }
            case VOID ->  { consume(); return new BuiltinType(BuiltinType.BuiltinContext.VOID_TYPE); }
            case IDENTIFIER ->  { consume(); return new TypeAliasKind(TypeKind.TYPEALIAS_KIND, skippedToken.getTokenValue());   // Fixme
            }
            default ->  {
                System.out.println("BUG");inError = true;return null; } // Todo:  if null we;ll throw an error later
        }

    }

    String parseIdentifier() {
        parseEat(TokenType.IDENTIFIER, "Missing Identifier");
        return skippedToken.getTokenValue();
    }

    ArrayList<ParameterNode> parseParams() {
        var params = new ArrayList<ParameterNode>();
        parseEat(TokenType.L_PAREN, "`(` missing");
        if (have(TokenType.R_PAREN))
            return params;
        do {
            params.add(parseParameter());
        } while (have(TokenType.COMMA));
        parseEat(TokenType.R_PAREN, "end params list with `)`, Check line: " + currentToken.getLine());
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
        var blockStartLine = currentToken.getLine();
        parseEat(TokenType.L_BRACE, "How do you want to implement func w/o block definition, Are you mad?. To fix your stupidity check line: "  + blockStartLine);
        var statements = new ArrayList<Statement>();
        while (!see(TokenType.R_BRACE) && !see(TokenType.EOF)) {
            statements.add(parseVarDeclAssign());
        }

        // TODO: 7/22/21 Parse rest of the block here, Statemetns of our language if-statement, for-loop, var-declaration and similar

        parseEat(TokenType.R_BRACE, "func end w close `}` line: " + currentToken.getLine());
        return new BlockNode(blockStartLine, statements);
    }

    TypeAliasDecl parseTypealias() {
        var li = currentToken.getLine();
        var aliasName = parseIdentifier();
        parseEat(TokenType.ASSIGN_OP, "typealias statement requires `=` followed by type");
        var type = parseType();
        parseEat(TokenType.SEMICOLON, "semi missing");
        return new TypeAliasDecl(li, aliasName, type);
    }
    public Token token() {
        return currentToken;
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
        var program = parser.translationUnit();
        program.semaAnalysis();
    }
}
