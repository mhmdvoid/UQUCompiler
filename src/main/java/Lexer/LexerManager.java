package Lexer;


import java.util.ArrayList;

public class LexerManager {

//    private int position; // currentCursor; ???????
//    private char charManager.currentChar();

    
    private CharManager charManager;
    

    private final Reader bufferManager;
    private boolean inError = false;

    String bufferSource;
    private final ArrayList<Token> tokens = new ArrayList<>();


    public LexerManager(Reader reader) {
        this.bufferManager = reader;

        if (reader instanceof SourceManager) {
            bufferSource = ((SourceManager) reader).getBufferContent().toString();
        }

        charManager = new CharManager(bufferSource);
        this.lex();
    }


    public LexerManager(String srcPath) {
        this(new SourceManager(srcPath));
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

        tokens.add(new Token(TokenType.IDENTIFIER, buffer.toString()));
    }

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
        var lexer = new LexerManager("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/Fail1");


    }
}
