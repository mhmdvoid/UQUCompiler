package lang_tokinezer;

import java.util.Arrays;

public class Java extends TypedLanguage {

    enum JavaToken implements LangToken {
        BOOLEAN_KWD,
        PACKAGE;
    }

    public Java() {
        keywords.put("boolean", JavaToken.BOOLEAN_KWD);

    }
}