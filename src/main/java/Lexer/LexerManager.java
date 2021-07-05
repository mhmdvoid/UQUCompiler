package Lexer;


import lang_tokinezer.Cpp;
import lang_tokinezer.Java;
import lang_tokinezer.Language;

import java.util.ArrayList;

public class LexerManager {

//    private int position; // currentCursor; ???????
//    private char charManager.currentChar();

    
    private CharManager charManager;
    private final Language language;

    private final Reader bufferManager;
    private boolean inError = false;

    String bufferSource;
    private final ArrayList<Token> tokens = new ArrayList<>();


    public LexerManager(Reader reader, Language language) {
        this.bufferManager = reader;
        this.language = language;
        if (reader instanceof SourceManager) {
            bufferSource = ((SourceManager) reader).getBufferContent().toString();
        }

        charManager = new CharManager(bufferSource);
        this.lex();
    }


    public LexerManager(String srcPath) {
        this(new SourceManager(srcPath), new Java());
    }

    public LexerManager(String srcPath, Language language) {
        this(new SourceManager(srcPath), language);
    }
    // Low-level system level?
    // Void method has side effect and i hate that explicitly;
    public boolean nextChar() {
        return charManager.advance();
//        if (position < bufferSource.length()) {
//            charManager.currentChar() = bufferSource.charAt(position++);
//            return true;
//        }
//
//        charManager.currentChar() = '\0';
//        return false;  // unable to advance;
    }


    void lex() {
        // Recursion function here;

        while (charManager.currentChar() != '\0') {
            if (Character.isWhitespace(charManager.currentChar())) {
                // TODO: skip();
                nextChar();
            } else if (Character.isJavaIdentifierStart(charManager.currentChar())) {
//                 nextChar();//
                lexIdentifier();
//                nextChar();
            } else if (Character.isDigit(charManager.currentChar())) {
                lexNumber();
            } else if (charManager.currentChar() == '=') {
                tokens.add(new Token(TokenType.ASSIGN_OP, "="));
                nextChar();
            } else if (charManager.currentChar() == ';') {
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                nextChar();
            } else if (charManager.currentChar() == '{') {
                tokens.add(new Token(TokenType.L_BRACE, "{"));
                nextChar();
            }
            else if (charManager.currentChar() == '}') {
                tokens.add(new Token(TokenType.R_BRACE, "}"));
                nextChar();
            } else if (charManager.currentChar() == '(') {
                tokens.add(new Token(TokenType.L_PAREN, "("));
                nextChar();
            } else if (charManager.currentChar() == ')') {
                tokens.add(new Token(TokenType.R_PAREN, ")"));
                nextChar();
            } else {
                inError = true;
                System.out.println("Unsupported symbol yet `" + charManager.currentChar() + "`, Found index: " + charManager.getCharPosition().column + " line: " + charManager.getCharPosition().row);
                break; // maybe?
            }
        }
    }

    private void lexIdentifier() {
        var buffer = new StringBuilder();

        while (Character.isJavaIdentifierPart(charManager.currentChar()) && charManager.currentChar() != '\0') {
            buffer.append(charManager.currentChar());
            nextChar();
        }

        if (language.mapToken(buffer.toString())) {
            tokens.add(new Token(language.getToken(), language.getTokenValue()));
        } else {
            tokens.add(new Token(TokenType.IDENTIFIER, buffer.toString()));
        }
    }
    // Think of the api that's much better;
    // Don;t think too much about it? cuz really it's just another MVC problem we need extensibility reader, writer logic modifier, and other

    private void lexNumber() {
        var buffer = new StringBuilder();
        while (Character.isDigit(charManager.currentChar()) && charManager.currentChar() != '\0') {
            buffer.append(charManager.currentChar());
            nextChar();
        }
        tokens.add(new Token(TokenType.NUMBER_LITERAL, buffer.toString()));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public boolean isInError() {
        return inError;
    }

    public static void main(String[] args) {
        var lexer = new LexerManager("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/Fail1", new Cpp());
        System.out.println(lexer.getTokens());
        // By default is java?

    }
}
