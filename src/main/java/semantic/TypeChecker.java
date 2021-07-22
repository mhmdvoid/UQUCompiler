package semantic;

import ast.BinaryExpression;
import ast.Expression;
import ast.FuncDeclNode;
import ast.VarDecl;
import ast.type.BuiltinType;
import ast.type.Type;
public class TypeChecker {

    // we should have statement?
    public VarDecl typeDeclState(VarDecl varDeclNode) {
        var declInitType = varDeclNode.getInitialExpression().type;  // we type check it !;


        if (!varDeclNode.type.equalType(declInitType)) {
            System.err.println("Variable type: ``" + varDeclNode.type + "  mismatch initial value type " + declInitType);
            // fixme: give a var unresolved/ambiguous type;
        }
        return varDeclNode;
    }

    // for constant builtin such as int, char, bool, it's trivial we just plug the type to
    public Expression typeConstantValue(Expression valueNode, BuiltinType.BuiltinContext type) {
        valueNode.type = new BuiltinType(type);
        return valueNode;
    }
    public Expression typeBinExpression(BinaryExpression expressionNode) {

        var lhsInt = expressionNode.getLhs().type;
        var rhsInt = expressionNode.getRhs().type;
        if (lhsInt.binAccept() && rhsInt.binAccept()) { // var x: int = false + false;
            expressionNode.type = lhsInt;
            return expressionNode;
        } else {
            System.err.println("Error typing!");
            expressionNode.type = new BuiltinType(null);
            return expressionNode;
        }

    }

    public Expression typeExpression(Expression expression) {
        return null;
    }

    public FuncDeclNode checkFuncType(FuncDeclNode funcDeclNode) {
        // we look it up? this is a sema analysis ?
        return null;
    }
    // Todo: for binding variables; we need to lookup the variable to get its type if it


}
