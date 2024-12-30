import java.util.ArrayList;
import java.util.List;

public class sTable {
    List<Scope> scopes = new ArrayList<Scope>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<Integer>();

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
            html += "<br><h2>Proc "+scopes.get(i).name+" Scope</h2><table border=\"1\"> \n<th> Name </th> <th> Type </th> <th> ID </th>";
            html += scopes.get(i).toHTML();
            html += "</table>\n";
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
}
