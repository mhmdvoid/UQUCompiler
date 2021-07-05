package lang_tokinezer;


import java.util.HashMap;
import java.util.Map;

/**
 * This class is mother of all supported languages by the lexer;
 */


public abstract class Language {



    Map<String, LangToken> keywords;
    // Do some math operation on them? difference/intersection/union?
    public enum OldLangToken implements LangToken{
        VOID_KWD,
        INT_KWD,
        SWITCH,
        IF,
//        SEMICOLON,
//        L_PAREN,
//        R_PAREN,
//        L_BRACE,
//        R_BRACE,
        ELSE,
        ENUM,
//        ASSIGN_OP,
//        IDENTIFIER,
        DO,
        CONTINUE,
        BREAK,
        CASE,
        FLOAT_KWD,
        DOUBLE_KWD,
        RETURN,
        WHILE,
        LONG_KWD,
        STATIC,
        DEFAULT
    }


    public Language() {
        keywords = new HashMap<>();
        keywords.put("void", OldLangToken.VOID_KWD);
        keywords.put("int", OldLangToken.INT_KWD);
    }



    protected String tokenValue;
    protected LangToken token;


    public boolean mapToken(String tokenValue) {
        if (keywords.containsKey(tokenValue)) {
            if (keywords.get(tokenValue) != null) {
                this.tokenValue = tokenValue;
                token = keywords.get(tokenValue);
                return true;

            }
        }
        return false;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public LangToken getToken() {
        return token;
    }
}

