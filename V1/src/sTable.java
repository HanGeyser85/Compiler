
import java.util.ArrayList;
import java.util.List;

public class sTable {

    List<Scope> scopes = new ArrayList<>();
    Integer sizInteger = 0;
    vTable variableTable = new vTable();
    fTable functionTable = new fTable();
    boolean hasError = false;
    String synAl = "";
    AST tree;

    public void addScope(Scope scope, Integer index) {
        scopes.add(scope);
        sizInteger++;
    }

    public Scope getScope(Integer index) {
        return scopes.get(index);
    }

    public void printTable() {
        for (int i = 0; i < sizInteger; i++) {
            System.out.println(scopes.get(i).toString());
        }
    }

    public String toHTML() {
        String html = "";

        for (int i = 0; i < sizInteger; i++) {
            html += "<br><h2>Proc " + scopes.get(i).name + " Scope</h2><table border=\"1\"> <br><th> Name </th> <th> Type </th> <th> ID </th>";
            html += scopes.get(i).toHTML();
            html += "</table><br>";
        }

        return html;
    }

    public void addVisibleVariables(Scope scope, Scope parent) {
        for (Symbol s : parent.table.values()) {
            if (null != s.type && s.isVisibleToChild) {
                switch (s.type) {
                    case NUMVAR -> {
                        if (scope.containsVarByName(scope, s.name, s.type)) {
                            continue;
                        }

                        Symbol newSymbol = new Symbol(s.id, s.name, s.type);
                        newSymbol.isFromParent = true;
                        newSymbol.isVisibleToChild = true;
                        newSymbol.isUsed = false;
                        newSymbol.isInitialized = false;
                        scope.table.put(s.id, newSymbol);
                        scope.ids.add(s.id);
                        scope.sizInteger++;
                    }
                    case STRINGV -> {
                        if (scope.containsVarByName(scope, s.name, s.type)) {
                            continue;
                        }

                        Symbol newSymbol = new Symbol(s.id, s.name, s.type);
                        newSymbol.isFromParent = true;
                        newSymbol.isVisibleToChild = true;
                        newSymbol.isUsed = false;
                        newSymbol.isInitialized = false;
                        scope.table.put(s.id, newSymbol);
                        scope.ids.add(s.id);
                        scope.sizInteger++;
                    }
                    case BOOLVAR -> {
                        if (scope.containsVarByName(scope, s.name, s.type)) {
                            continue;
                        }

                        Symbol newSymbol = new Symbol(s.id, s.name, s.type);
                        newSymbol.isFromParent = true;
                        newSymbol.isVisibleToChild = true;
                        newSymbol.isUsed = false;
                        newSymbol.isInitialized = false;
                        scope.table.put(s.id, newSymbol);
                        scope.ids.add(s.id);
                        scope.sizInteger++;
                    }
                    default -> {
                    }
                }
            }
        }
    }

    public void defineScopes(ASTNode root, Scope parent) {
        if (root == null) {
            return;
        }

        if (root.type == _TokenType.PROC) {
            Scope scope = new Scope(variableTable);

            String nameString = "";
            for (ASTNode astNode : root.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }
            scope.name = nameString;
            scope.parent = parent;

            for (ASTNode astNode : root.children) {
                scope.populateTable(astNode);
            }

            addScope(scope, root.number);

            addVisibleVariables(scope, parent);

            for (ASTNode child : root.children) {
                defineScopes(child, scope);
            }
        } else {
            for (ASTNode child : root.children) {
                defineScopes(child, parent);
            }
        }
    }

    public void addSiblings() {
        for (Scope integer : scopes) {
            if (integer.name.equals("main")) {
                continue;
            }
            while (integer.parent.ids.isEmpty()) {
                integer.parent = integer.parent.parent;
            }
        }

        for (Scope s : scopes) {
            s.addSiblingProcs();
        }
    }

    public String checkForCalls() {
        String ret = "";

        for (Scope s : scopes) {
            ret += s.checkForCalls();
        }
        return ret;
    }

    public String checkIfCalledProcExists() {
        String ret = "";

        for (Scope s : scopes) {
            ret += s.checkIfCalledProcExists();
        }
        return ret;
    }

    public void defineMainScope(ASTNode tree) {
        Scope scope = new Scope(variableTable);
        scope.name = "main";
        scope.populateTable(tree);
        addScope(scope, 0);
        for (ASTNode child : tree.children) {

            defineScopes(child, scope);
        }
    }

    public void semanticAnalysis(ASTNode node) {
        if (node == null) {
            return;
        }

        if (null != node.type) {
            switch (node.type) {
                case ASSIGN -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Expression after assignment operator has no value!<br>";
                    }
                }
                case INPUT_GET ->
                    variableTable.hasValue(node);
                case NUMEXPR_ADDITION -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in addition expression has no value!<br>";
                    }
                }
                case NUMEXPR_MULTIPLICATION -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in multiplication expression has no value!<br>";
                    }
                }
                case NUMEXPR_DIVISION -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in division expression has no value!<br>";
                    }
                }
                case NUMVAR -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Numvar with id " + node.number + " has no value!<br>";
                    }
                }
                case STRINGV -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Stringvar with id " + node.number + " has no value!<br>";
                    }
                }
                case BOOLVAR -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Boolvar with id " + node.number + " has no value!<br>";
                    }
                }
                case NOT -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Expression has no value!<br>";
                    }
                }
                case OR -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in OR expression has no value!<br>";
                    }
                }
                case CMPR_GREATERTHAN -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in greater than expression has no value!<br>";
                    }
                }
                case CMPR_EQUAL -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in equal expression has no value!<br>";
                    }
                }
                case CMPR_LESSTHAN -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: One or more parameters in less than expression has no value!<br>";
                    }
                }
                case BRANCH -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Boolexpr in IF statement has no value!<br>";
                        hasError = true;
                        return;
                    }
                }
                case LOOP -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Boolexpr in WHILE loop has no value!<br>";
                        hasError = true;
                        return;
                    }
                }
                case OUTPUT -> {
                    if (!variableTable.hasValue(node)) {
                        synAl += "SEMANTIC ERROR: Output value has no value!<br>";
                    }
                }
                default -> {
                }
            }
        }

        for (ASTNode child : node.children) {

            if (child.type == _TokenType.HALT) {
                return;
            }
            if (hasError) {
                hasError = false;
                return;
            }
            semanticAnalysis(child);
        }
    }

    public void jumpToProc(ASTNode node) {
        if (node == null) {
            return;
        }

        if (node.type == _TokenType.CALL) {
            ASTNode varNode = node.children.get(0);

            String nameString = "";

            for (ASTNode astNode : varNode.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            ASTNode jump = findProcId(varNode, nameString);

            if (jump == null) {
                synAl += "SEMANTIC ERROR: Procedure " + nameString + " does not exist!<br>";
            } else {
                if (checkIfProcIsInCallScope(nameString)) {
                    jumpToProc(jump);
                }
            }
        }
    }

    public ASTNode findProcId(ASTNode current, String name) {
        if (current.type == _TokenType.CALL) {
            String nameString = "";

            for (ASTNode astNode : current.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            if (nameString.equals(name)) {
                return current;
            }
        }
        return null;
    }

    public boolean checkIfProcIsInCallScope(String node) {
        if (node == null) {
            return false;
        }

        for (Scope s : scopes) {
            if (s.containsCallAndProc(node, node)) {
                return true;
            }
        }

        return false;
    }
}
