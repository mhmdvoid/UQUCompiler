package ast;
import java.util.ArrayList;
import java.util.List;

// global variables and function  each file/translation unit has a global scope that contains

// file.uqulang
/*  var x: int = 10 + 10;
    func int computeAverage() {return 0;}
    func void anotherFunc  () {         }

    - Highlight: x is accessible throughout the file from any function -todo: nested later;
                 anotherFunc can call computeAverage since computerAverage is also in global Scope;

 */

public class GlobalScope extends ScopeNode {
    private final List<Statement> statements;
    public GlobalScope(int line) {
        super(line);
        this.statements = new ArrayList<>();
    }
    /* GlobalContext gContext*/
    public void addStatement(Statement statement) { statements.add(statement); }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return statements.toString();
    }
}
