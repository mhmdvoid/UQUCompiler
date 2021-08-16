package parser;

import ast.ASTInfo;
import ast.Identifier;
import ast.decl_def.*;
import ast.expr_def.BoolLiteral;
import ast.expr_def.Expression;
import ast.expr_def.FuncBlockExpr;
import ast.redesign.ASTNode;
import ast.type.Type;
import lex_erro_log.ErrorLogger;
import lexer.*;
import semantic.*;
import semantic.scope.FuncScope;
import semantic.scope.LocalScope;
import semantic.scope.Scope;
import semantic.scope.TranslationUnitScope;

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

    public TranslationUnit parseTranslateUnit() {
        // Todo: We should have parseTopLevelDecl() && parseStatement(withScope: Scope);
        consume();  // Pump the lexer;
        var fileScope = new TranslationUnitScope();
        var tu = new ast.decl_def.TranslationUnit(sema.astContext);
        while (see(TokenType.IMPORT) || see(TokenType.VAR) || see(TokenType.FUNC) || see(TokenType.TYPEALIAS) || see(TokenType.OPEN_MULTICOM)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case IMPORT -> tu.push(parseImportDecl());
                case VAR -> tu.push(parseVarDecl(fileScope));
                case FUNC -> { consume(); tu.push(parseFuncDecl(fileScope)); }
                case TYPEALIAS -> tu.push(parseTypeAlias(fileScope));
                case OPEN_MULTICOM -> parseMultiComment();
                default -> System.err.println("Error syntax construct");
            }
        }
//        if (parseEat(TokenType.EOF, "Expected decl...")) {
//            ErrorLogger.log(SManagerSingleton.shared().srcCode(), currentToken.getPosition().column, currentToken.getPosition().newColumn());
//        }
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

    public Identifier parseIdentifier() {
        var loc = currentToken.loc();
        parseEat(TokenType.IDENTIFIER,  loc);
        var identifier = sema.astContext.getIdentifier(skippedToken.getTokenValue());  // This will store if for me; right?
        identifier.location = loc;
        return identifier;
    }

    VarDecl parseVarDecl(Scope ctx) {
        var loc = currentToken.loc();
        parseEat(TokenType.VAR, loc);  // Should be consumed by client. not implementor as it's top-level decl kind of nodes.
        var id = parseIdentifier();
        parseEat(TokenType.COLON, currentToken.loc());
//            ErrorLogger.log(SManagerSingleton.shared().srcCode(), currentToken.getPosition().column, currentToken.getPosition().newColumn());
        var t = parseType(ctx);
        var expression = parseExpression(ctx);  // Sema.expre !;
        parseEat(TokenType.SEMICOLON, currentToken.loc());  // we need to even advance loc
        return sema.decl.varDeclSema(id, t, expression, ctx);
    }

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

    Expression parseExpression(Scope scope) {
        var loc = currentToken.loc();
        parseEat(TokenType.ASSIGN_OP,  loc);

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

    ASTNode parseFuncState(LocalScope scope) {
        switch (currentToken.getType()) {
            case VAR:
                return parseVarDecl(scope);
            default:
                System.err.println("Unexpected construct token ");
                return null;
        }
    }

    FuncDecl parseFuncDecl(Scope tuScope) {
        var type = parseType(tuScope);  // resolving funcType to be in TUScope
        var id = parseIdentifier();
        var funcArgsScope = new FuncScope(tuScope);  // This should do it for now ;
        var blockScope = new LocalScope(funcArgsScope);
//        blockScope.surroundingScope ;// this is the funcArgsScope
//        blockScope.translationUnitScope
        var params = parseParamDecl(funcArgsScope);
        var block = parseBlockExpr(blockScope);
        System.out.println("Local ValueDecl " + funcArgsScope.table);
        System.out.println("LocalTypeScope: " + funcArgsScope.getTypeContext().typeScope);
        return sema.decl.funcDeclSema(type, id, tuScope);

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
    public TypeAliasDecl parseTypeAlias(Scope ctx) {   // For every decl parsing and type parsing . decl means is this already defined /not defined at all ? type does this type even exist? this why we need scope
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
        var bLoc = currentToken.loc();
        parseEat(TokenType.L_BRACE, bLoc);
        var list = new ArrayList<ASTNode>();
        while (!see(TokenType.R_BRACE) && !see(TokenType.EOF)) {
            list.add(parseFuncState(nestedScope));   // NOTE: function statements are handled here. lookup, and all simple semantic resolution process is already done through sending nestedScope as param
        }
        parseEat(TokenType.R_BRACE, currentToken.loc());
        return new FuncBlockExpr(list); // FIXME to do differently on blockExpr Why?, as semantic analysis is done for the function body through parseFuncState i.e they got into a local scope and everything;
        // now this block should be injected into a different scope and give it a type. as of now it doesn't hold a type!
    }

    public static void main(String[] args) {
        var parser = new Parser("/Users/engmoht/IdeaProjects/UQULexer/main.uqulang", new ASTInfo());

        var tu = parser.parseTranslateUnit();
        tu.dump();
        NameBinder.nameBinding(tu, tu.astInfo);
    }

}
