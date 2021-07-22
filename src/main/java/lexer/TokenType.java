package lexer;

public enum TokenType  {


    // COMMENT INCLUDED IN AST ? OR JUST SKIP ?  LOOK LATER;
    IDENTIFIER,
    ADD_OP,
    MUL_OP,
    NUMBER_LITERAL,
    COMMA,
    ASSIGN_OP,
    SEMICOLON,
    L_PAREN,
    R_PAREN ,
    L_BRACE,
    R_BRACE,
    COLON,
    SUB,
    INT_KWD,
    VOID,
    L_BRACKET,
    R_BRACKET,
    FUNC,
    STRING_KWD,
    VAR,
    TRUE, FALSE,
    BOOL,
    RETURN,
    EOF;


}
