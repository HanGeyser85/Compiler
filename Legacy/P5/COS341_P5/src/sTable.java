import java.util.ArrayList;
import java.util.List;

public class sTable {
    List<Scope> scopes = new ArrayList<Scope>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<Integer>();
    vTable variableTable = new vTable();
boolean hasHalt = false;
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
            html += "<br><h2>Proc "+scopes.get(i).name+" Scope</h2><table border=\"1\"> <br><th> Name </th> <th> Type </th> <th> ID </th>";
            html += scopes.get(i).toHTML();
            html += "</table><br>";
        }


        return html;
    }
    
    public void defineScopes(ASTNode root, Scope parent) {
        if (root == null) {
            return;
        }

        if (sizInteger == 0 && parent == null) {
            defineMainScope(root);
        }

        
        Scope scope = new Scope();
        
        if (root.type == _TokenType.PROC) {
            String nameString = "";
            
            for (ASTNode astNode : root.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }
            scope.name = nameString;
            for (ASTNode integer : root.children) {
                scope.populateTable(integer);  
            }
            addScope(scope, root.number);
            
            
        }

        if (scope.parent == null) {
            scope.parent = parent;
        }

        for (ASTNode child : root.children) {
            
            defineScopes(child, scope);
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
        Scope scope = new Scope();
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

        if (node.type == _TokenType.ASSIGN) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Expression after assignment operator has no value!<br>";
            }
        } else if (node.type == _TokenType.INPUT_GET) {
            variableTable.hasValue(node);
        } else if (node.type == _TokenType.NUMEXPR_ADDITION) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in addition expression has no value!<br>";
            }
        } else if (node.type == _TokenType.NUMEXPR_MULTIPLICATION) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in multiplication expression has no value!<br>";
            }
        } else if (node.type == _TokenType.NUMEXPR_DIVISION) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in division expression has no value!<br>";
            }
        } else if (node.type == _TokenType.NUMVAR) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Numvar with id " + node.number + " has no value!<br>";
            }
        } else if (node.type == _TokenType.STRINGV) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Stringvar with id " + node.number + " has no value!<br>";
            }
        } else if (node.type == _TokenType.BOOLVAR) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Boolvar with id " + node.number + " has no value!<br>";
            }
        } else if (node.type == _TokenType.NOT) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Expression has no value!<br>";
            }
        } else if (node.type == _TokenType.OR) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in OR expression has no value!<br>";
            }
        } else if (node.type == _TokenType.CMPR_GREATERTHAN) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in greater than expression has no value!<br>";
            }
        } else if (node.type == _TokenType.CMPR_EQUAL) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in equal expression has no value!<br>";
            }
        } else if (node.type == _TokenType.CMPR_LESSTHAN) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: One or more parameters in less than expression has no value!<br>";
            }
        } else if (node.type == _TokenType.BRANCH) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Boolexpr in IF statement has no value!<br>";
                hasError = true;
                return;
            }
        } else if (node.type == _TokenType.LOOP) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Boolexpr in WHILE loop has no value!<br>";
                hasError = true;
                return;
            }
        } else if (node.type == _TokenType.OUTPUT) {
            if (!variableTable.hasValue(node)) {
                synAl += "SEMANTIC ERROR: Output value has no value!<br>";
            }
        }
        
        for (ASTNode child : node.children) {
            
            if (child.type == _TokenType.HALT) {
                hasHalt = true;
                return;
            }
            if (hasHalt) {
                hasHalt = false;
                return;
            }
            if (hasError) {
                hasError = false;
                return;
            }
            semanticAnalysis(child);
        }

        return;
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

        return;
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
