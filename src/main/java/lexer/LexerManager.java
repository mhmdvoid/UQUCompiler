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
        tokens.add(new Token(Integer.MAX_VALUE,TokenType.EOF, "\0"));
    }

    private void fillKwds() {
        keywords.put("func", TokenType.FUNC);
        keywords.put("var", TokenType.VAR);
        keywords.put("bool", TokenType.BOOL);
        keywords.put("return", TokenType.RETURN);
        keywords.put("int", TokenType.INT_KWD);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("string", TokenType.STRING_KWD);
        keywords.put("void", TokenType.VOID);
        keywords.put("typealias", TokenType.TYPEALIAS);
        keywords.put("import", TokenType.IMPORT);
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
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.ASSIGN_OP, "="));
                nextChar();
            } else if (charManager.currentChar() == '*') {
                nextChar();
                if (charManager.currentChar() == '/') {
                    tokens.add(new Token(charManager.getCharPosition().row, TokenType.CLOSE_MULTICOM, "*/"));
                    nextChar();
                }
                else {
                    tokens.add(new Token(charManager.getCharPosition().row, TokenType.MUL_OP, "*"));
                }
            } else if (charManager.currentChar() == ';') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.SEMICOLON, ";"));
                nextChar();
            } else if (charManager.currentChar() == '{') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.L_BRACE, "{"));
                nextChar();
            } else if (charManager.currentChar() == '}') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.R_BRACE, "}"));
                nextChar();
            } else if (charManager.currentChar() == ',') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.COMMA, ","));
                nextChar();
            } else if (charManager.currentChar() == '+') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.ADD_OP, "+"));
                nextChar();
            } else if (charManager.currentChar() == '(') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.L_PAREN, "("));
                nextChar();
            } else if (charManager.currentChar() == ')') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.R_PAREN, ")"));
                nextChar();
            } else if (charManager.currentChar() == ':') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.COLON, ":"));
                nextChar();
            } else if (charManager.currentChar() == '[') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.L_BRACKET, "["));
                nextChar();
            } else if (charManager.currentChar() == ']') {
                tokens.add(new Token(charManager.getCharPosition().row, TokenType.R_BRACKET, "]"));
                nextChar();
            } else if (charManager.currentChar() == '/') {
                nextChar();
                if (charManager.currentChar() == '/') {
                    while (charManager.currentChar() != '\n' && charManager.currentChar() != '\0')
                        nextChar();
                } else if (charManager.currentChar() == '*') {
                    tokens.add(new Token(charManager.getCharPosition().row, TokenType.OPEN_MULTICOM, "/*"));
                    nextChar();
                } else{
                    System.out.println("Division operation not supported yet .. ):");
                    inError = true;
                }
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
        tokens.add(new Token(charManager.getCharPosition().row, keywords.getOrDefault(buffer.toString(), TokenType.IDENTIFIER), buffer.toString()));

    }

    private void lexNumber() {
        var buffer = new StringBuilder();
        while (Character.isDigit(charManager.currentChar()) && charManager.currentChar() != '\0') {
            buffer.append(charManager.currentChar());
            nextChar();
        }
        tokens.add(new Token(charManager.getCharPosition().row, TokenType.NUMBER_LITERAL, buffer.toString()));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public boolean isInError() {
        return inError;
    }

    public static void main(String[] args) {
        var lexer = new LexerManager("/Users/engmoht/IdeaProjects/UQULexer/src/main/java/example/main.uqulang");
    }

}