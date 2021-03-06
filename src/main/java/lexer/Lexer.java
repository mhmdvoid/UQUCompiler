package lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lexer {


    private final CharManager charManager;

    private final SourceManager bufferManager;
    private boolean inError = false;

    String bufferSource;
    Position currentTokenPos;
    private final Map<String, TokenType> keywords;
    private final ArrayList<Token> tokens = new ArrayList<>();


    public Lexer(SourceManager bufferManager) {
        this.bufferManager = bufferManager;
        bufferSource = bufferManager.getBufferContent().toString();
        charManager = new CharManager(bufferSource);
        keywords = new HashMap<>();
        fillKwds();


        this.lex();

        tokens.add(new Token(charManager.getCharPosition().index,
                charManager.getCharPosition().row, charManager.getCharPosition().column, TokenType.EOF, "\0"));
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

    public Lexer(String srcPath) {
        this(new SourceManager(srcPath));
        var s = SManagerSingleton.shared(srcPath);
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
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row, charManager.getCharPosition().column, TokenType.ASSIGN_OP, "="));
                nextChar();
            } else if (charManager.currentChar() == '*') {
                var loc = new Position(charManager.getCharPosition().row, charManager.getCharPosition().index, charManager.getCharPosition().column);
                nextChar();
                if (charManager.currentChar() == '/') {
                    tokens.add(new Token(loc, TokenType.CLOSE_MULTICOM, "*/"));
                    nextChar();
                }
                else {
                    tokens.add(new Token(loc, TokenType.MUL_OP, "*"));
                }
            } else if (charManager.currentChar() == ';') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.SEMICOLON, ";"));
                nextChar();
            } else if (charManager.currentChar() == '{') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.L_BRACE, "{"));
                nextChar();
            } else if (charManager.currentChar() == '}') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.R_BRACE, "}"));
                nextChar();
            } else if (charManager.currentChar() == ',') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.COMMA, ","));
                nextChar();
            } else if (charManager.currentChar() == '+') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.ADD_OP, "+"));
                nextChar();
            } else if (charManager.currentChar() == '(') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.L_PAREN, "("));
                nextChar();
            } else if (charManager.currentChar() == ')') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.R_PAREN, ")"));
                nextChar();
            } else if (charManager.currentChar() == ':') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.COLON, ":"));
                nextChar();
            } else if (charManager.currentChar() == '[') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row,  charManager.getCharPosition().column,TokenType.L_BRACKET, "["));
                nextChar();
            } else if (charManager.currentChar() == ']') {
                tokens.add(new Token(charManager.getCharPosition().index,
                        charManager.getCharPosition().row, charManager.getCharPosition().column, TokenType.R_BRACKET, "]"));
                nextChar();
            } else if (charManager.currentChar() == '/') {
                nextChar();
                if (charManager.currentChar() == '/') {
                    while (charManager.currentChar() != '\n' && charManager.currentChar() != '\0')
                        nextChar();
                } else if (charManager.currentChar() == '*') {
                    tokens.add(new Token(charManager.getCharPosition().index,
                            charManager.getCharPosition().row, charManager.getCharPosition().column, TokenType.OPEN_MULTICOM, "/*"));
                    nextChar();
                } else{
                    System.out.println("Division operation not supported yet .. ):");
                    inError = true;
                }
            } else {
                inError = true;
                charManager.log(charManager.getCharPosition().column, charManager.getCharPosition().newColumn());
                System.out.println("Unsupported symbol yet `" + charManager.currentChar() + "`, Found index: " + charManager.getCharPosition().column + " line: " + charManager.getCharPosition().row);
                break; // maybe?
            }
        }
    }

    private void lexIdentifier() {
        var buffer = new StringBuilder();
        var identifierLoc = new Position(charManager.getCharPosition().row, charManager.getCharPosition().newColumn(), charManager.getCharPosition().column);

        do {
            buffer.append(charManager.currentChar());
            nextChar();
        } while (Character.isJavaIdentifierPart(charManager.currentChar()) && charManager.currentChar() != '\0') ;
        if (keywords.containsKey(buffer.toString())) {
//            tokens.add(new Token(identifierLoc));
            tokens.add(new Token(identifierLoc, keywords.get(buffer.toString()), buffer.toString()));
        }
        else
//            tokens.add(new Token(identifierLoc));

            tokens.add(new Token(identifierLoc, TokenType.IDENTIFIER, buffer.toString()));
    }

    private void lexNumber() {
        var buffer = new StringBuilder();
        var numberLoc = new Position(charManager.getCharPosition().row, charManager.getCharPosition().newColumn(), charManager.getCharPosition().column);
        while (Character.isDigit(charManager.currentChar()) && charManager.currentChar() != '\0') {
            buffer.append(charManager.currentChar());
            nextChar();
        }
        tokens.add(new Token(numberLoc, TokenType.NUMBER_LITERAL, buffer.toString()));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public boolean isInError() {
        return inError;
    }

    public static void main(String[] args) {
        var lexer = new Lexer("/Users/engmoht/IdeaProjects/UQULexer/main.uqulang");
        System.out.println(lexer.getTokens());
    }

}