package Lexer;


public enum TokenType {


    // COMMENT INCLUDED IN AST ? OR JUST SKIP ?  LOOK LATER;
    IDENTIFIER,
    NUMBER_LITERAL,
    ASSIGN_OP,
    SEMICOLON,
    L_PAREN,
    R_PAREN ,
    L_BRACE,
    R_BRACE,
    COLON,
    SUB,
    PUBLIC,
    PACKAGE,
    PRIVATE,
    IMPORT,
    VOID,
    INT_KWD,
    BOOLEAN,
    FOR,
    L_BRACKET,
    R_BRACKET,
    NEW,

    RIGHT_ARROW;

}
