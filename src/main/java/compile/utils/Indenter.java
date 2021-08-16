package compile.utils;

final public class Indenter {


    private Indenter() {}

    // one place to change
    public static void indentWithShape(int indent, IndentationKind indentationKind) {
        for (int i = 0; i < indent; i++)
            System.out.print(indentationKind.getShapeChar());
    }
}
