package parser;

import ast.nodes.ASTInfo;
import ast.nodes.Identifier;
import ast.nodes.declaration.*;
import ast.nodes.expression.BinExpr;
import ast.nodes.expression.Expr;
import ast.nodes.expression.BlockExpr;
import ast.nodes.ASTNode;
import ast.nodes.statement.ReturnState;
import ast.type.Type;
import lex_erro_log.ErrorLogger;
import lexer.*;
import semantic.*;
import semantic.redesign.ScopeService;
import semantic.scope.FuncScope;
import semantic.scope.LocalScope;
import semantic.scope.Scope;
import semantic.scope.TranslationUnitScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// parseArgType, parseArgName();  Why? Separation is always bette.
// 2- parseArgType(globalScopeType); parseArgName(localContex);
public class Parser {
    private final Lexer lexer;
    private Token skippedToken, currentToken;
    private int idx;
    private boolean inError;

    private final Sema sema;

    public boolean isInError() {
        return inError;
    }

    public Parser(String srcPath, ASTInfo astInfo) {
        lexer = new Lexer(srcPath);
        idx = 0;
        sema = new Sema(astInfo);
    }

    /// Helpers

    void skipTill(TokenType tokenType) {  // For simple error recovery instead of having chain of errors
        if (tokenType == null) return;

        while (currentToken.isNot(tokenType) && currentToken.isNot(TokenType.EOF))
            // Todo: Switch on tokens;
            consume();
    }

     void skipTill() {
        skipTill(null);
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
    boolean parseEat(TokenType tokenType, Position loc) {
        if (see(tokenType)) {
            consume();
            return false;
        } else {
            inError = true;
            System.out.println("Expected " + tokenType + " at " + loc);
            ErrorLogger.log(SManagerSingleton.shared().srcCode(), loc.column, loc.newColumn());
            return true;
        }
    }

    // used for symbolic token `:, ), {, }, (, "," ,`
    boolean parseToken(TokenType tokenType, TokenType skipToToken) {
        if (currentToken.is(tokenType)) {
            consume();
            return false;
        }
        // logError;

        // recover
        skipTill(skipToToken);
        if (tokenType == skipToToken && currentToken.is(skipToToken))
            consume();
        return true;
    }

    boolean parseToken(TokenType tokenType) {
        return parseToken(tokenType, null);
    }
    private boolean isExpr(TokenType tokenType) {
        return tokenType == TokenType.NUMBER_LITERAL || tokenType == TokenType.TRUE
               || tokenType == TokenType.FALSE || tokenType == TokenType.IDENTIFIER;
    }

    /*  ----------------------------------------------------------------------
         Parse Decls
        ----------------------------------------------------------------------   */
    public TranslationUnit parseTranslateUnitDecl() {
        // Todo: We should have parseTopLevelDecl() && parseStatement(withScope: Scope);
        consume();  // Pump the lexer;
        var fileScope = new TranslationUnitScope();
        var tu = new ast.nodes.declaration.TranslationUnit(sema.astContext);
        while (see(TokenType.IMPORT) || see(TokenType.VAR) || see(TokenType.FUNC) || see(TokenType.TYPEALIAS) || see(TokenType.OPEN_MULTICOM)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case IMPORT -> tu.push(parseImportDecl());
                case VAR -> tu.push(parseVarDecl(fileScope));
                case FUNC -> { consume(); tu.push(parseFuncDecl(fileScope)); }
                case TYPEALIAS -> tu.push(parseTypeAliasDecl(fileScope));
                case OPEN_MULTICOM -> parseMultiComment();
                default -> System.err.println("Error syntax construct");
            }
        }
        parseEat(TokenType.EOF, currentToken.loc());
        tu.tuScope = fileScope;
        System.out.println("Global ValueDecl " + fileScope.table);
        System.out.println("Global TypeScope " + fileScope.getTypeContext().typeScope);
        sema.decl.handleEndOfTranslationUnit(tu);  // Replace: NameBinder.nameBinding(tu, this.sema.astContext);
        return tu;
    }

    ImportDecl parseImportDecl() {
        var loc = currentToken.loc();
        parseEat(TokenType.IMPORT, loc);
        var identifier = parseIdentifier();
        return sema.decl.importDeclSema(identifier);
    }

    VarDecl parseVarDecl(Scope ctx) {
        var loc = currentToken.loc();
        parseEat(TokenType.VAR, loc);  // Should be consumed by client. not implementor as it's top-level decl kind of nodes.
        var id = parseIdentifier();
        parseEat(TokenType.COLON, currentToken.loc());
//            ErrorLogger.log(SManagerSingleton.shared().srcCode(), currentToken.getPosition().column, currentToken.getPosition().newColumn());
        var t = parseType(ctx);
        var expression = parseInitializer(ctx);  // Sema.expre !;
        parseEat(TokenType.SEMICOLON, currentToken.loc());  // we need to even advance loc
        return ScopeService.init().varDeclScope(id, t, expression);
    }


    public Identifier parseIdentifier() {
        var loc = currentToken.loc();
        parseEat(TokenType.IDENTIFIER,  loc);
        var identifier = sema.astContext.getIdentifier(skippedToken.getTokenValue());  // This will store if for me; right?
        identifier.location = loc;
        return identifier;
    }


    FuncDecl parseFuncDecl(Scope tuScope) {
        var type = parseType(tuScope);  // resolving funcType to be in TUScope
        var id = parseIdentifier();
        var funcArgsScope = new FuncScope(tuScope);  // This should do it for now ;
        var blockScope = new LocalScope(funcArgsScope);
        var params = parseParamDecl(funcArgsScope);
        var missingBlock = sema.decl.funcDeclSema(type, id, params, tuScope);
        var block = parseBlockExpr(blockScope);
        if (block == null) {
            System.err.println("For now function should have body " + currentToken.loc());
            skipTill(TokenType.EOF);
        }
        return sema.decl.funcBodySema(missingBlock, block);

    }

    // We need to figure a better way to represent scopeLogic!; and should be notified respectfully!;  see A container !; where the default is the global !
    List<ParamDecl> parseParamDecl(FuncScope funcScope) {
        // should push them to a local scope;
        var list = new ArrayList<ParamDecl>();
        parseEat(TokenType.L_PAREN, currentToken.loc());
        if (have(TokenType.R_PAREN)) {/*empty param*/
            return list;
        }
        do {
            list.add(parseParam(funcScope));
        } while (have(TokenType.COMMA));
        parseEat(TokenType.R_PAREN, currentToken.loc());
        return list;
    }

    ParamDecl parseParam(FuncScope funcScope) {
        var identifier = parseIdentifier();
        parseEat(TokenType.COLON, currentToken.loc());
        var paramType = parseType(funcScope.translationUnitScope);
        return sema.decl.paramDeclSema(identifier, paramType, funcScope);  // Fixme there's a bug sending funcScope to type will do no good maybe later!;
    }

    // WE should have a method called parse statementList(Scope ctx); gets called by the translation unit as well as class decl, struct decl, method decl;
    public TypeAliasDecl parseTypeAliasDecl(Scope ctx) {   // For every decl parsing and type parsing . decl means is this already defined /not defined at all ? type does this type even exist? this why we need scope
        var loc = currentToken.loc();

        parseEat(TokenType.TYPEALIAS, loc);
        var identifier = parseIdentifier();
        parseEat(TokenType.ASSIGN_OP, currentToken.loc());
        var typ = parseType(ctx);
        parseEat(TokenType.SEMICOLON, currentToken.loc());  // Diagnostic;
        var node = sema.decl.typeAliasSema(/*loc,*/ identifier, typ, ctx.getTypeContext());
        node.location = loc;
        return node;
    }

    /*  ----------------------------------------------------------------------
        Parse Type
        ----------------------------------------------------------------------   */
    public Type parseType(Scope ctx) {
        var loc = currentToken.loc();
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
                System.err.println("Must have type.." + loc);

            }
        }
        return null; //  FIXME: 8/1/21 UnresolvedType
    }


    /*  ----------------------------------------------------------------------
        Parse Expr
        ----------------------------------------------------------------------   */

    Expr parseInitializer(Scope ctx) {
        var eqLoc = currentToken.loc();
        parseEat(TokenType.ASSIGN_OP, eqLoc);
        return parseExpression(ctx);
    }

    Expr parseExpression(Scope scope) {
        Expr expr;
        do {
            expr = parseExprPrimary(scope);
        } while (isExpr(currentToken.getType()));

        return expr;
    }
    Expr parseExprPrimary(Scope scope) {
        var pmLoc = currentToken.loc();
        Expr expr;  // should be named lhs?
        switch (currentToken.getType()) {
            case NUMBER_LITERAL: {
                expr = sema.expr.semaNumberConstant(currentToken.getTokenValue());
                consume();
                break;
            }
            case TRUE:
            case FALSE: {
                expr = sema.expr.semaBoolLiteral(currentToken.getTokenValue());
                consume();
                break;
            }
            case IDENTIFIER:
                expr = parseIdentifierRef(scope);
                break;
            default:
                System.out.println("Expected Expr got: " + currentToken.getTokenValue() + " At: " + currentToken.loc());
                consume();
                return null;
        }
        // for sure we have here *
        while (have(TokenType.MUL_OP)) {
            expr = parseMultiplication(expr, scope);
        }
        while (have(TokenType.ADD_OP) || have(TokenType.SUB)) {
            expr = parseAddition(expr, scope);
        }
        return expr;

    }

    Expr parseMultiplication(Expr expr, Scope scope) {
        expr = new BinExpr(expr, parseExprPrimary(scope), BinExpr.BinExprKind.Multi); // 10 * 10 * x;
        return expr;
    }

    Expr parseAddition(Expr expr, Scope scope) {
        var kind = skippedToken.getType() == TokenType.ADD_OP ? BinExpr.BinExprKind.Add : BinExpr.BinExprKind.Sub;
        expr = new BinExpr(expr, parseExprPrimary(scope), kind);  // We may use the kind as we walk
        return expr;
    }

    Expr parseIdentifierRef(Scope scope) {
        var id = parseIdentifier();
        return sema.expr.semaIdentifierRef(id, scope);
    }

    BlockExpr parseBlockExpr(LocalScope nestedScope) {
        var bLoc = currentToken.loc();
        if (parseEat(TokenType.L_BRACE, bLoc)) {
            return null;
        }
        var blockElements = new ArrayList<ASTNode>();
        while (!see(TokenType.R_BRACE) && !see(TokenType.EOF)) {
            blockElements.add(parseBlockElem(nestedScope));   // NOTE: function statements are handled here. lookup, and all simple semantic resolution process is already done through sending nestedScope as param
        }
        parseEat(TokenType.R_BRACE, currentToken.loc());
        return sema.expr.semaBlockExpr(blockElements); // FIXME to do differently on blockExpr Why?, as semantic analysis is done for the function body through parseFuncState i.e they got into a local scope and everything;
        // now this block should be injected into a different scope and give it a type. as of now it doesn't hold a type!
    }


    /*
    ////////////////// FIXME: These two should be moved/removed/redesigned
     */
    ASTNode parseBlockElem(LocalScope scope) {
        switch (currentToken.getType()) {
            case VAR:
                return parseVarDecl(scope);
            case TYPEALIAS:
                return parseTypeAliasDecl(scope);
            case RETURN: // return x; return 10 + 10; return 1;
                consume();
                Expr expr = null;
                if (isExpr(currentToken.getType()))
                    expr = parseExpression(scope);
                parseEat(TokenType.SEMICOLON, currentToken.loc()); // if not? eat to r_brace of course don't just chain problem tokens
                return new ReturnState(expr);
            default:
                return null;
        }
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
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQUCompiler/main.uqulang", new ASTInfo());

        var tu = parser.parseTranslateUnitDecl();
//        tu.dump();
        NameBinder.nameBinding(tu, tu.astInfo);
    }

}
