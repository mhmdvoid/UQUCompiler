package Lexer;

import lang_tokinezer.LangToken;

public enum TokenType implements LangToken {


    // COMMENT INCLUDED IN AST ? OR JUST SKIP ?  LOOK LATER;
    IDENTIFIER,
    NUMBER_LITERAL,
    ASSIGN_OP,
    SEMICOLON,
    L_PAREN,
    R_PAREN ,
    L_BRACE,
    R_BRACE;

}
