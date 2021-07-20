package compile;

import parser.Parser;

public class CompilerIO {

    // Todo: bash script to run custom command for console
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error, at least you need to provide src file which to be compiled...");
            return;
        }
        var parser  = new Parser(args[0]);
        var program = parser.translationUnit();
        if (args.length > 1) {
            if (args[1].equals("-da")) {
                program.dump();
            }
        }
    }
}
