package parser;

import ast.ASTInfo;
import ast.Identifier;
import ast.decl_def.MultiCommentDecl;
import ast.decl_def.TranslationUnit;
import ast.decl_def.TypeAliasDecl;
import ast.decl_def.VarDecl;
import ast.expr_def.BoolLiteral;
import ast.expr_def.Expression;
import ast.type.Type;
import lexer.LexerManager;
import lexer.Token;
import lexer.TokenType;
import semantic.Scope;
import semantic.Sema;
import semantic.TranslationUnitScope;


public class Parser {
    private final LexerManager lexer;
    private Token skippedToken, currentToken;
    private int idx;
    private boolean inError;

    private final Sema sema;
    public boolean isInError() {
        return inError;
    }

    public Parser(String srcPath, ASTInfo astInfo) {
        lexer = new LexerManager(srcPath);
        idx = 0;
        sema = new Sema(astInfo);
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

    public TranslationUnit parseTranslateUnit() {
        consume();  // Pump the lexer;
        var line = currentToken.getLine();
        var fileScope = new TranslationUnitScope();
        var tu = new ast.decl_def.TranslationUnit(sema.astContext);
//        var gMembers = new GlobalScope(line);   // Fixme: see line issue;
        while (see(TokenType.VAR) || see(TokenType.TYPEALIAS) || see(TokenType.OPEN_MULTICOM)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case VAR -> { tu.push(parseVarDeclAssign(fileScope));}
                case TYPEALIAS -> {
                    consume();
                    tu.push(parseTypeAlias(fileScope));
                }
                case OPEN_MULTICOM -> parseMultiComment();
                default -> System.err.println("Error syntax construct");
            }
        }

//        System.out.println("ValueDecl " + fileScope.table);
//        System.out.println("TypeScope " + fileScope.getTypeContext().typeScope);
        sema.decl.handleEndOfTranslationUnit();  // Replace: NameBinder.nameBinding(tu, this.sema.astContext);
        return tu;
    }


    VarDecl parseVarDeclAssign(Scope ctx) {
        parseEat(TokenType.VAR, "var missing ");
        var id = parseIdentifier();
        if (id.name.equals("x99998")) {
            System.out.println("Found");
        }
        parseEat(TokenType.COLON, "missing colon ");
        var t = parseType(ctx);
        var expression = parseExpression(ctx);  // Sema.expre !;
        parseEat(TokenType.SEMICOLON, "missing sim");
        return sema.decl.varDeclSema(id, t, expression, ctx);
    }

    Expression parseExpression(Scope scope) {
        var line = currentToken.getLine();
        parseEat(TokenType.ASSIGN_OP, "variable initialization should start with `=`");
        return parsePrimary(scope);
    }



    private Expression parsePrimary(Scope scope) {
        switch (currentToken.getType()) {
            case NUMBER_LITERAL: return parseValue();
            case IDENTIFIER: {
                return parseIdentifierRef(scope);
            }
        }
        return  null;
    }

    Expression parseValue() {
        var line = currentToken.getLine();
        if (have(TokenType.NUMBER_LITERAL)) {
            return sema.expr.semaNumberConstant(skippedToken.getTokenValue());// Sema.exp.actOnConstant();
        } else if (have(TokenType.TRUE) || have(TokenType.FALSE)) {
            return new BoolLiteral(skippedToken.getTokenValue());
        }

        return null;  // should never reach
    }
    // WE should have a method called parse statementList(Scope ctx); gets called by the translation unit as well as class decl, struct decl, method decl;
    public TypeAliasDecl parseTypeAlias(Scope ctx) {   // For every decl parsing and type parsing . decl means is this already defined /not defined at all ? type does this type even exist? this why we need scope
        // for both type and decl
        var line = currentToken.getLine();
        var identifier = parseIdentifier();
        parseEat(TokenType.ASSIGN_OP, "`=` should appear after variable name");
        var typ = parseType(ctx);
        parseEat(TokenType.SEMICOLON, "typealias should end with `;` ");  // Diagnostic;
        return sema.decl.typeAliasSema(identifier, typ, ctx.getTypeContext());
    }

    public Identifier parseIdentifier() {
        parseEat(TokenType.IDENTIFIER, "Missing identifier");
        return sema.astContext.getIdentifier(skippedToken.getTokenValue());  // This will store if for me; right?
    }

    public Type parseType(Scope ctx) {
        switch (currentToken.getType()) {
            case IDENTIFIER -> { var Id = parseIdentifier();
                return sema.type.resolveTypename(Id, ctx.getTypeContext()); }
            case INT_KWD -> { consume(); return sema.type.resolveIntType();
            }
            case BOOL -> { consume(); return sema.type.resolveBoolType(); }
            default -> {
                System.err.println("Must have type.." + currentToken.getLine());

            }
        }
        return null; //  FIXME: 8/1/21 UnresolvedType
    }

    Expression parseIdentifierRef(Scope scope) {
        var id = parseIdentifier();
        return sema.expr.semaIdentifierRef(id, scope);
    }
    MultiCommentDecl parseMultiComment() {
        while (!see(TokenType.CLOSE_MULTICOM)) {
            consume();
        }
        consume();
        // FIXME sema.decl.process;
        return new MultiCommentDecl();
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang", new ASTInfo());
        var tu = parser.parseTranslateUnit();
    }
}
