package ast;

import java.util.ArrayList;

public class ParameterNode extends ASTNode {

    ArrayList<ParamNode> params;

    public ParameterNode(ArrayList<ParamNode> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ParameterNode{" +
                "params=" + params +
                '}';
    }
}
