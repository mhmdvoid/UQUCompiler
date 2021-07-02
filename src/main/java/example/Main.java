package example;
import Lexer.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        var srcBuffer = "foo bar";
        var lexer = new LexerManager(srcBuffer);

        System.out.println(Arrays.toString(lexer.getTokens().toArray()));
    }
}
