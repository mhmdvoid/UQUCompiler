package Lexer.language;

import Lexer.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Java {



    private static Java shared;

    private TokenType tokenType;
    private Map<String, TokenType> keywords;
    private Java() {

        keywords = new HashMap<>();
        keywords.put("public", TokenType.PUBLIC);
        keywords.put("private", TokenType.PRIVATE);
        keywords.put("import", TokenType.IMPORT);
        keywords.put("int", TokenType.INT_KWD); // and much more;
        keywords.put("FOR", TokenType.FOR); // and much more;
        keywords.put("new", TokenType.NEW); // and much more;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public static Java instance() {
        if (shared == null) {
            shared = new Java();
        }
        return shared;
    }
    public boolean foundKeyword(String tokenValue) {
        if (keywords.containsKey(tokenValue)) {
            tokenType = keywords.get(tokenValue);
            return tokenType  != null;
        }
        return false;

    }


}

