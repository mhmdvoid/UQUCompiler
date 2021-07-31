package ast.redesign;

public class Identifier {
    public String name;

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
}
