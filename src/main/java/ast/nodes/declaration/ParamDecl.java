package ast.nodes.declaration;

import ast.nodes.Identifier;
import ast.type.Type;

// FIXME: Either this should be renamed to FuncVarDecl or, create subclass of it called localVarDecl
public class ParamDecl extends ValueDecl {
    int offset;  // for runtime stack frame. For example if OO is supported then allocate 1 for `self` type. Again keep the lang simple for now.
    public ParamDecl(Identifier identifier, Type type, int offset) {
        super(DeclKind.FormalDecl, identifier, type, null);  // TODO: 8/4/21 param initial value shouldn't be null, Need support for default values.
        this.offset = offset;
    }
    // #if debug
    @Override
    public String toString() {
        return "ParamDecl{" +
                "offset=" + offset +
                ", identifier=" + identifier +
                ", type=" + type +
                '}';
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        identifier.dump(indent + 2);
    }
}
