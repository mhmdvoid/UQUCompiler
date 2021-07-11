package lexer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LexerManager {


    private final CharManager charManager;

    private final SourceManager bufferManager;
    private boolean inError = false;

    String bufferSource;

    private Map<String, TokenType> keywords;
    private final ArrayList<Token> tokens = new ArrayList<>();


    public LexerManager(SourceManager bufferManager) {
        this.bufferManager = bufferManager;
        bufferSource = bufferManager.getBufferContent().toString();
        charManager = new CharManager(bufferSource);
        keywords = new HashMap<>();
        fillKwds();


        this.lex();
        tokens.add(new Token(TokenType.EOF, "\0"));
    }

    private void fillKwds() {
        keywords.put("func", TokenType.FUNC);
        keywords.put("var", TokenType.VAR);
//        keywords.put("mutable", TokenType.MUTABLE);
//        keywords.put("immutable", TokenType.IMMUTABLE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("int", TokenType.INT_KWD);
        keywords.put("string", TokenType.STRING_KWD);
    }

    public LexerManager(String srcPath) {
        this(new SourceManager(srcPath));
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
            } else if (charManager.currentChar() == '}') {
                tokens.add(new Token(TokenType.R_BRACE, "}"));
                nextChar();
            } else if (charManager.currentChar() == ',') {
                tokens.add(new Token(TokenType.COMMA, ","));
                nextChar();
            } else if (charManager.currentChar() == '+') {
                tokens.add(new Token(TokenType.ADD_OP, "+"));
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

        do {
            buffer.append(charManager.currentChar());
            nextChar();
        } while (Character.isJavaIdentifierPart(charManager.currentChar()) && charManager.currentChar() != '\0') ;
        tokens.add(new Token(keywords.getOrDefault(buffer.toString(), TokenType.IDENTIFIER), buffer.toString()));

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
        var lexer = new LexerManager("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
        System.out.println(lexer.getTokens());
    }

}