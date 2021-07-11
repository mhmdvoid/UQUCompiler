package lexer;


public class Token {

    private final TokenType type;


    private final String tokenValue;

    public Token(TokenType type, String tokenValue) {
        this.type = type;
        this.tokenValue = tokenValue;
    }



    // TODO: some tokenizer helper methods;  such as: isKeyword? etc...


    public TokenType getType() {
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

    public boolean isNot(TokenType tokenType) {
        return tokenType == this.type;
    }
}
