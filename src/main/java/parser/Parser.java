package parser;

import ast.ASTInfo;
import ast.Identifier;
import ast.decl_def.*;
import ast.expr_def.BoolLiteral;
import ast.expr_def.Expression;
import ast.expr_def.FuncBlockExpr;
import ast.redesign.ASTNode;
import ast.type.NameAliasType;
import ast.type.Type;
import ast.type.TypeKind;
import lex_erro_log.ErrorLogger;
import lexer.LexerManager;
import lexer.SManagerSingleton;
import lexer.Token;
import lexer.TokenType;
import semantic.*;

import java.util.ArrayList;
import java.util.List;

// parseArgType, parseArgName();  Why? Separation is always bette.
// 2- parseArgType(globalScopeType); parseArgName(localContex);
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
        // Todo: We should have parseTopLevelDecl() && parseStatement(withScope: Scope);
        consume();  // Pump the lexer;
        var fileScope = new TranslationUnitScope();
        var tu = new ast.decl_def.TranslationUnit(sema.astContext);
//        var gMembers = new GlobalScope(line);   // Fixme: see line issue;
        while (see(TokenType.IMPORT) || see(TokenType.VAR) || see(TokenType.FUNC) || see(TokenType.TYPEALIAS) || see(TokenType.OPEN_MULTICOM)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case FUNC -> {
                    consume();
                    tu.push(parseFuncDecl(fileScope));
                }
                case TYPEALIAS -> {
                    System.out.println("YOURE" + currentToken.getPosition());
                    tu.push(parseTypeAlias(fileScope));
                }
                case VAR -> tu.push(parseVarDeclAssign(fileScope));
                case OPEN_MULTICOM -> parseMultiComment();
                case IMPORT ->  {
                    tu.push(parseImportDecl());
                }
                default -> System.err.println("Error syntax construct");
            }
        }
        if (parseEat(TokenType.EOF, "Expected decl...")) {
            ErrorLogger.log(SManagerSingleton.shared().srcCode(), currentToken.getPosition().column, currentToken.getPosition().newColumn());
        }
        tu.tuScope = fileScope;
        System.out.println("Global ValueDecl " + fileScope.table);
        System.out.println("Global TypeScope " + fileScope.getTypeContext().typeScope);
        sema.decl.handleEndOfTranslationUnit(tu);  // Replace: NameBinder.nameBinding(tu, this.sema.astContext);
        return tu;
    }

    ImportDecl parseImportDecl() {
        // var loc
        parseEat(TokenType.IMPORT, "import keyword missing");
        var identifier = parseIdentifier();
        return sema.decl.importDeclSema(identifier);
    }
    ASTNode parseFuncState(LocalScope scope) {
        switch (currentToken.getType()) {
            case VAR:
                return parseVarDeclAssign(scope);
            default:
                System.err.println("Unexpected construct token ");
                return null;
        }
    }

    FuncDecl parseFuncDecl(Scope gb) {
        var type = parseType(gb);
        var id = parseIdentifier();
        var localContext = new LocalScope(gb);  // This should do it for now ;
        var params = parseParamDecl(localContext, gb);
        var block = parseBlockExpr(localContext);
        System.out.println("Local ValueDecl " + localContext.table);
        System.out.println("LocalTypeScope: " + localContext.getTypeContext().typeScope);
        return sema.decl.funcDeclSema(type, id, gb);

    }

    // We need to figure a better way to represent scopeLogic!; and should be notified respectfully!;  see A container !; where the default is the global !
    List<ParamDecl> parseParamDecl(Scope localScope, Scope gb) {
        // should push them to a local scope;
        var list = new ArrayList<ParamDecl>();
        parseEat(TokenType.L_PAREN, "param should start with `(`");
        if (have(TokenType.R_PAREN)) {/*empty param*/
            return list;
        }
        do {
            list.add(parseParam(localScope, gb));
        } while (have(TokenType.COMMA));
        parseEat(TokenType.R_PAREN, "How to forget closing param");
        return list;
    }

    ParamDecl parseParam(Scope localScope, Scope gb) {
        var identifier = parseIdentifier();
        parseEat(TokenType.COLON, "Where is the colon after param name !");
        var paramType = parseType(gb);
        return sema.decl.paramDeclSema(identifier, paramType, localScope);  // Fixme there's a bug sending localScope to type will do no good maybe later!;
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
        parseEat(TokenType.ASSIGN_OP, "variable initialization should start with `=`");
        return parsePrimary(scope);
    }


    private Expression parsePrimary(Scope scope) {
        switch (currentToken.getType()) {
            case NUMBER_LITERAL:
                return parseValue();
            case IDENTIFIER: {
                return parseIdentifierRef(scope);
            } /*case L_BRACE: {consume(); return parseBlockExpr(scope);}*/
        }
        return null;
    }

    Expression parseValue() {
        if (have(TokenType.NUMBER_LITERAL)) {
            return sema.expr.semaNumberConstant(skippedToken.getTokenValue());// Sema.exp.actOnConstant();
        } else if (have(TokenType.TRUE) || have(TokenType.FALSE)) {
            return new BoolLiteral(skippedToken.getTokenValue());
        }

        return null;  // should never reach
    }

    // WE should have a method called parse statementList(Scope ctx); gets called by the translation unit as well as class decl, struct decl, method decl;
    public TypeAliasDecl parseTypeAlias(Scope ctx) {   // For every decl parsing and type parsing . decl means is this already defined /not defined at all ? type does this type even exist? this why we need scope
        var loc = currentToken.getPosition();

        parseEat(TokenType.TYPEALIAS, "...");
        var identifier = parseIdentifier();
        parseEat(TokenType.ASSIGN_OP, "`=` should appear after variable name");
        var typ = parseType(ctx);
        parseEat(TokenType.SEMICOLON, "typealias should end with `;` ");  // Diagnostic;
        var node = sema.decl.typeAliasSema(/*loc,*/ identifier, typ, ctx.getTypeContext());
        node.location = loc;
        return node;
    }

    public Identifier parseIdentifier() {
        var loc = currentToken.getPosition();
        parseEat(TokenType.IDENTIFIER, "Missing identifier");
        var identifier = sema.astContext.getIdentifier(skippedToken.getTokenValue());  // This will store if for me; right?
        identifier.location = loc;
        return identifier;
    }

    public Type parseType(Scope ctx) {
        switch (currentToken.getType()) {
            case IDENTIFIER -> {
                var Id = parseIdentifier();
                return sema.type.resolveTypename(Id, ctx.getTypeContext());
            }
            case INT_KWD -> {
                consume();
                return sema.type.resolveIntType();
            }
            case BOOL -> {
                consume();
                return sema.type.resolveBoolType();
            }
            default -> {
                System.err.println("Must have type.." + currentToken.getPosition());

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

    FuncBlockExpr parseBlockExpr(LocalScope nestedScope) {
        parseEat(TokenType.L_BRACE, "block should start with `{`");
        var list = new ArrayList<ASTNode>();
        while (!see(TokenType.R_BRACE) && !see(TokenType.EOF)) {
            list.add(parseFuncState(nestedScope));   // NOTE: function statements are handled here. lookup, and all simple semantic resolution process is already done through sending nestedScope as param
        }
        parseEat(TokenType.R_BRACE, "Forgot to close the function? `}`");
        return new FuncBlockExpr(list); // FIXME to do differently on blockExpr Why?, as semantic analysis is done for the function body through parseFuncState i.e they got into a local scope and everything;
        // now this block should be injected into a different scope and give it a type. as of now it doesn't hold a type!
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/main.uqulang", new ASTInfo());
        var tu = parser.parseTranslateUnit();
        NameBinder.nameBinding(tu, tu.astInfo);
    }

}
