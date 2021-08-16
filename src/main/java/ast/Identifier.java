package ast;

import compile.utils.IndentationKind;
import compile.utils.Indenter;
import lexer.Position;

import java.util.Objects;

public class Identifier {
    public String name;
    public Position location;
    public Identifier(String name) {
        this.name = name;
    }

    // if #debug:
    @Override
    public String toString() {
        return "Identifier{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void dump(int indent) {
        Indenter.indentWithShape(indent, IndentationKind.WhiteSpace);
        System.out.println(name);
    }
}
