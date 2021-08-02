package semantic.redesign;

import ast.redesign.expr_def.IntegerLiteral;

public class SemaExpr extends SemaBase {
    public SemaExpr(Sema sema) {
        super(sema);
    }

    public IntegerLiteral semaNumberConstant(String textValue) {

        // this is the sema part. assigning type, lookup, create node, redefinition error and similar;
        /* 1- We check integer fits in 32-bits for mips otherwise compile-error value too large;
           2- assign to it a type 'built-in type'
           3- create a new node
         */
        return new IntegerLiteral(textValue);
    }
}
