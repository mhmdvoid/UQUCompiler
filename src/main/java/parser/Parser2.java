package parser;

import ast.GlobalScope;
import ast.NameAliasDecl;
import ast.TranslationUnit;
import ast.redesign.ASTInfo;
import ast.redesign.Identifier;
import ast.redesign.NameAliasDeclNode;
import ast.type.Type;
import ast.type.redesign.NameAliasType;
import lexer.LexerManager;
import lexer.Token;
import lexer.TokenType;
import semantic.redesign.Scope;
import semantic.redesign.Sema;
import semantic.redesign.TranslationUnitScope;

import java.util.concurrent.TransferQueue;

public class Parser2 {
    private final LexerManager lexer;
    private Token skippedToken, currentToken;
    private int idx;
    private boolean inError;

    private final Sema sema;
    public boolean isInError() {
        return inError;
    }

    public Parser2(String srcPath, ASTInfo astInfo) {
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
        var damn = new TranslationUnitScope();
        var gMembers = new GlobalScope(line);   // Fixme: see line issue;
        while (see(TokenType.TYPEALIAS)) { // Fixme: Should have *parseStatement(); and then branch
            switch (currentToken.getType()) {
                case TYPEALIAS -> {
                    consume();
                    gMembers.addStatement(parseTypeAlias(damn));
                }
                default -> System.err.println("Error syntax construct");
            }
        }


        return new TranslationUnit(line, /*filename: should be from lexer*/ "main.uqulang", gMembers);
    }

    // WE should have a method called parse statementList(Scope ctx); gets called by the translation unit as well as class decl, struct decl, method decl;
    public NameAliasDeclNode parseTypeAlias(Scope ctx) {   // For every decl parsing and type parsing . decl means is this already defined /not defined at all ? type does this type even exist? this why we need scope
        // for both type and decl
        var line = currentToken.getLine();
        var identifier = parseIdentifier();
        parseEat(TokenType.ASSIGN_OP, "`=` should appear after variable name");
        var typ = parseType(ctx);
        parseEat(TokenType.SEMICOLON, "typealias should end with `;` ");  // Diagnostic;
        return sema.decl.tpAliasSema(identifier.name, typ, ctx);
    }

    public Identifier parseIdentifier() {
        parseEat(TokenType.IDENTIFIER, "Missing identifier");
        return sema.astContext.getIdentifier(skippedToken.getTokenValue());  // This will store if for me; right?
    }

    public Type parseType(Scope ctx) {
        if (have(TokenType.INT_KWD))

            return sema.type.resolveIntType();
        // Identifier?
        else if (see(TokenType.IDENTIFIER))
        {
            var Id = parseIdentifier();
            return sema.type.resolveTypename(Id, ctx);
        }
        return null; // not reached yet. Change to void default type;
    }

    public static void main(String[] args) {
        var parser = new Parser2("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang", new ASTInfo());
        var tu = parser.parseTranslateUnit();
        System.out.println(tu);
    }
}
