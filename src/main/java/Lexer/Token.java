package Lexer;

import lang_tokinezer.LangToken;

public class Token {

    private final LangToken type;


    private final String tokenValue;

    public Token(LangToken type, String tokenValue) {
        this.type = type;
        this.tokenValue = tokenValue;
    }



    // TODO: some tokenizer helper methods;  such as: isKeyword? etc...


    public LangToken getType() {
        return type;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }
}
