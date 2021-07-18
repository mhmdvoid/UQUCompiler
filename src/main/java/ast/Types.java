package ast;

public class Types {

    public static int typenameToInt(String typename) {
        var hashValue = 0;
        for (var i = 0; i < typename.length(); i++)
            hashValue += typename.charAt(i);
        return hashValue;
    }
}