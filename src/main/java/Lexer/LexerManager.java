package Lexer;


import java.util.ArrayList;

public class LexerManager {

    private int position;

    private char currentChar;


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
                // nextChar();//
                lexIdentifier();
                nextChar();
            }
        }
    }

    void lexIdentifier() {
        var buffer = new StringBuilder();

        while (Character.isJavaIdentifierPart(currentChar) && currentChar != '\0') {
            buffer.append(currentChar);
            nextChar();
        }

        tokens.add(new Token(TokenType.IDENTIFIER, buffer.toString()));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
