import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Scope {
    String name;
    Scope parent;
    Hashtable<Integer, Symbol> siblings = new Hashtable<Integer, Symbol>();
    Hashtable<Integer, Symbol> table = new Hashtable<Integer, Symbol>();
    Hashtable<Integer, Symbol> calls = new Hashtable<Integer, Symbol>();
    List<Integer> ids = new ArrayList<Integer>();
    List<Integer> idsSiblings = new ArrayList<Integer>();
    List<Integer> idsCalls = new ArrayList<Integer>();
    Integer sizInteger = 0;

    public Scope(String name, Scope parent, List<Scope> children, List<Symbol> symbols) {
        this.name = name;
        this.parent = parent;
    }

    public Scope (Scope scope) {
        this.name = scope.name;
        this.parent = scope.parent;
        this.siblings = scope.siblings;
        this.table = scope.table;
        this.ids = scope.ids;
        this.sizInteger = scope.sizInteger;
    }

    public Scope() {
    }

    public String toString() {
        return "Scope: " + name + " Parent: " + parent.name;
    }

    public void populateTable(ASTNode node) {
        if (node == null) {
            return;
        }

        Symbol var = new Symbol();

        if (node.type == _TokenType.PROC) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.PROC;
            var.id = node.number;

            ids.add(node.number);
            System.out.println(this.name);

            if (!hasProc(nameString)) {
                addSymbol(var, node.number);
            } else {
                throw new RuntimeException("Procedure " + nameString + " not allowed in the scope of "+ this.name +" as it already exists.");
            }
             
            return;
        } 
        
        if (node.type == _TokenType.CALL) { 
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.CALL;
            var.id = node.number;

            idsCalls.add(node.number);
            System.out.println(this.name);

            this.calls.put(node.number, var);
            
            return;
        }
        
        for (ASTNode  child : node.children) {
            populateTable(child);
        }       
    }

    private void addSymbol(Symbol var, Integer number) {
        table.put(number, var);
        sizInteger++;
    }

    public void addSibling(Symbol var, Integer id) {
        siblings.put(id, var);
    }


    public String toHTML() {
        String html = "";

        for (Integer id : ids) {
            html += "<tr>\n<td>" + table.get(id).name + "</td><td>" + table.get(id).type + "</td><td>" + table.get(id).id + "</td>\n</tr>";
        }

        for (Integer id : idsSiblings) {
            html += "<tr>\n<td>" + siblings.get(id).name + "</td><td>" + siblings.get(id).type + "</td><td>" + siblings.get(id).id + "</td>\n</tr>";
        }

        return html;
    }

    public boolean hasProc(String s) {
        if (!table.isEmpty()) {
        for (Integer i : ids) {
            if (this.table.get(i) ==  null) {
                continue;
            }
            if (this.table.get(i).name.equals(s)) {
                return true;
            }
        }}

        if (!siblings.isEmpty()) {

            for (Integer i : idsSiblings) {
                if (this.siblings.get(i) ==  null) {
                    continue;
                }
                if (this.siblings.get(i).name.equals(s)) {
                    return true;
                }
            }
        }


        return false;
    }

    public boolean hasCall(String name){
        if (calls.isEmpty()) {
            return false;
        }

        for (Integer i : idsCalls) {
            if (this.calls.get(i) ==  null) {
                continue;
            }
            if (this.calls.get(i).name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void addSiblingProcs() {
        if (this.parent == null) {
            return;
        }

        System.out.println(this.parent.name);
        for (Integer s: this.parent.ids) {
            if (this.hasProc(this.parent.table.get(s).name)) {
                throw new RuntimeException("Procedure " + this.parent.table.get(s).name + " not allowed in the scope of "+ this.name +" as it already exists.");
            } else {
                System.out.println("Adding procedure " + this.parent.table.get(s).name + " to scope " + this.name);
                this.idsSiblings.add(s);
                addSibling(this.parent.table.get(s), s);
            }
        }
    }

    public String checkForCalls() {
        String html = "";

        for (Integer id : ids) {
            if (this.table.get(id) == null) {
                continue;
            }
            if (this.hasCall((String)table.get(id).name)) {
                continue;
            } else {                
                html += "<br>WARNING : Proc " + this.table.get(id).name + " is never called in scope: " + name+".";
            }
        }

        for (Integer sid : idsSiblings) {
            if (this.siblings.get(sid) == null) {
                continue;
            }
            if (this.hasCall((String)siblings.get(sid).name)) {
                continue;
            } else {
                html += "<br>WARNING : Proc " + this.siblings.get(sid).name + " is never called in scope: " + name+".";
            }
        }

        return html;
    }

    public String checkIfCalledProcExists() {
        String html = "";

        for (Integer id : idsCalls) {
            if (this.calls.get(id) == null) {
                continue;
            }
            if (this.hasProc((String)calls.get(id).name)) {
                continue;
            } else {
                html += "<br>ERROR : Proc " + this.calls.get(id).name + " is called in Proc " + name + " but does not exist within the scope of Proc "+name+".";
            }
        }

        return html;
    }

    public boolean containsCallAndProc(String callName, String procName) {
        if (this.hasProc(procName) && this.hasCall(callName)) {
            return true;
        } else {
            return false;
        }
    }
}
