package Lexer;


import java.util.ArrayList;

public class LexerManager {

    private int position;

    private char currentChar;


    private boolean inError = false;
    private final String bufferSource;

    private final ArrayList<Token> tokens;

    public LexerManager(String bufferSource) {
        this.bufferSource = bufferSource;
        tokens = new ArrayList<>();
        position = 0;
        nextChar();
        this.lex();


    }

    // Low-level system level?
    // Void method has side effect and i hate that explicitly;
    public boolean nextChar() {
        if (position < bufferSource.length()) {
            currentChar = bufferSource.charAt(position++);
            return true;
        }

        currentChar = '\0';
        return false;  // unable to advance;
    }


    void lex() {
        // Recursion function here;

        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                // TODO: skip();
                nextChar();
            } else if (Character.isJavaIdentifierStart(currentChar)) {
//                 nextChar();//
                lexIdentifier();
//                nextChar();
            } else if (Character.isDigit(currentChar)) {
                lexNumber();
            } else if (currentChar == '=') {
                tokens.add(new Token(TokenType.ASSIGN_OP, "="));
                nextChar();
            } else if (currentChar == ';') {
                tokens.add(new Token(TokenType.SEMICOLON, ";"));
                nextChar();
            } else {
                inError = true;
                break; // maybe?
            }
        }
    }

    private void lexIdentifier() {
        var buffer = new StringBuilder();

        while (Character.isJavaIdentifierPart(currentChar) && currentChar != '\0') {
            buffer.append(currentChar);
            nextChar();
        }

        tokens.add(new Token(TokenType.IDENTIFIER, buffer.toString()));
    }

    private void lexNumber() {
        var buffer = new StringBuilder();
        while (Character.isDigit(currentChar) && currentChar != '\0') {
            buffer.append(currentChar);
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
}
