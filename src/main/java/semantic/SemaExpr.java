package semantic;

import ast.nodes.ASTNode;
import ast.nodes.Identifier;
import ast.nodes.expression.*;
import semantic.scope.Scope;
import java.util.List;

public class SemaExpr extends SemaBase {
    public boolean inError;
    public SemaExpr(Sema sema) {
        super(sema);
    }

    public IntegerLiteral semaNumberConstant(String textValue) {
        // Parser && Lexer did a great job to ensure textValue is in a numerical representation, so we can parse it easily to Integer
        // We'll use JVM help for now to check value fits in signed 32bit
        // FIXME - Do the algorithm and don't throw
        try {
            var ignored = Integer.parseInt(textValue);  // evaluates fits or throw an error
        } catch (Exception e) {
            System.err.println("Compile-error: number is too large for int type...");  // FIXME - pass the location of the integer node with the function
            inError = true;
            textValue = "1";
        }
        // We're good here.
        var intNode = new IntegerLiteral(textValue);
        intNode.type = sema.astContext.int32Type;
        return intNode;
    }

    public BoolLiteral semaBoolLiteral(String textValue) {
        var boolValue = new BoolLiteral(textValue);
        boolValue.type = sema.astContext.bool8Type;
        return boolValue;
    }


    public BlockExpr semaBlockExpr(/*Position loc*/ List<ASTNode> elements) {
        // Do any type semantic for the block return type
        return new BlockExpr(elements);
    }
}
