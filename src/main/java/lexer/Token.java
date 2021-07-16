package lexer;


public class Token {

    private final TokenType type;


    private final String tokenValue;

    private final int line;
    public Token(int line, TokenType type, String tokenValue) {
        this.line = line;
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

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", tokenValue='" + tokenValue + '\'' +
                ", line=" + line +
                '}';
    }

    public boolean isNot(TokenType tokenType) {
        return tokenType == this.type;
    }
}
