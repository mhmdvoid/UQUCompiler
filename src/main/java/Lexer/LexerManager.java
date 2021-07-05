package Lexer;

import Lexer.language.Java;
import Lexer.language.Language;

import java.util.ArrayList;

public class LexerManager {

    
    private final CharManager charManager;

    private final Java java;

    private final Reader bufferManager;
    private boolean inError = false;

    String bufferSource;
    private final ArrayList<Token> tokens = new ArrayList<>();



    public LexerManager(Reader reader, Java java) {
        this.bufferManager = reader;
        this.java = java;
        if (reader instanceof SourceManager) {
            bufferSource = ((SourceManager) reader).getBufferContent().toString();
        }

        charManager = new CharManager(bufferSource);
        this.lex();
    }

    public LexerManager(String srcPath) {
        this(new SourceManager(srcPath), Java.instance());
    }

    // Low-level system level?
    // Void method has side effect and i hate that explicitly;
    public boolean nextChar() {
        return charManager.advance();
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
            } else if (charManager.currentChar() == ':') {
                tokens.add(new Token(TokenType.COLON, ":"));
                nextChar();
            } else if (charManager.currentChar() == '[') {
                tokens.add(new Token(TokenType.L_BRACKET, "["));
                nextChar();
            } else if (charManager.currentChar() == ']') {
                tokens.add(new Token(TokenType.R_BRACKET, "]"));
                nextChar();
            } else if (charManager.currentChar() == '-') {
                nextChar();
                if (charManager.currentChar() == '>') {
                    tokens.add(new Token(TokenType.RIGHT_ARROW, "->"));
                    nextChar();
                }
                // todo:  implement dec -= later;
                else
                    tokens.add(new Token(TokenType.SUB, "-"));
            } else {
                inError = true;
                charManager.log(charManager.getCharPosition().column, charManager.m_idx);
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

        if (java.foundKeyword(buffer.toString())) {
            tokens.add(new Token(java.getTokenType(), buffer.toString()));
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
        var lexer = new LexerManager("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/java_example_pass/Main.java");
        System.out.println(lexer.getTokens());

    }

}
