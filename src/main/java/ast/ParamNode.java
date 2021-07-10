package ast;

public class ParamNode extends ASTNode {

    String paramName;

//    Type paramType; Todo : Implement simple type system

    public ParamNode(String paramName/*,*//* Type paramType*/) {
        this.paramName = paramName;
    }
}
