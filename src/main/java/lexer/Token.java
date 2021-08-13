package lexer;


public class Token {

    private  TokenType type;


    private  String tokenValue;

    private int line;
    private final Position position;

    public Position loc() {
        return position;
    }

    public Token(int index, int row, int column, TokenType type, String tokenValue) {
        this.position = new Position();
        position.row = row;
        position.index = index;  // actual
        position.column = column;
        this.type = type;
        this.tokenValue = tokenValue;
    }
    public Token(Position position, TokenType type, String tokenValue) {
        this.position = position;

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

//    public int getLine() {
//        return position.row;
//    }
//    public int getIndex() { return position.index; }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", tokenValue='" + tokenValue + '\'' +
                ", position" + position +
                '}';
    }

    public boolean isNot(TokenType tokenType) {
        return tokenType == this.type;
    }
}
