package compile.utils;

final public class ShapeDump {

    public enum BasicShape {
        WhiteSpace(' '),
        DottedDashes('-'),
        LongContinuousDash('—');

        private final char shapeChar;

        BasicShape(char shapeChar) {
            this.shapeChar = shapeChar;
        }

        public char getShapeChar() {
            return shapeChar;
        }
    }
    private ShapeDump() {}


    public static void spaceTreeShape(BasicShape basicShape) {
        System.out.print(basicShape.shapeChar);
    }
}
