
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scope {

    String name;
    Scope parent;
    HashMap<Integer, Symbol> siblings = new HashMap<>();
    HashMap<Integer, Symbol> table = new HashMap<>();
    HashMap<Integer, Symbol> calls = new HashMap<>();
    List<Integer> ids = new ArrayList<>();
    List<Integer> idsSiblings = new ArrayList<>();
    List<Integer> idsCalls = new ArrayList<>();
    Integer sizInteger = 0;

    public Scope(String name, Scope parent, List<Scope> children, List<Symbol> symbols) {
        this.name = name;
        this.parent = parent;
    }

    public Scope(Scope scope) {
        this.name = scope.name;
        this.parent = scope.parent;
        this.siblings = scope.siblings;
        this.table = scope.table;
        this.ids = scope.ids;
        this.sizInteger = scope.sizInteger;
    }

    public Scope(vTable variableTable) {
    }

    @Override
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
            var.isFromParent = parent != null ? parent.hasVar(nameString, var.type) : false;
            var.isVisibleToChild = !var.isFromParent;

            System.out.println(this.name);

            if (!hasProc(nameString) || !var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else {
                throw new RuntimeException("Procedure " + nameString + " not allowed in the scope of " + this.name + " as it already exists.");
            }

            return;
        }

        if (node.type == _TokenType.BOOLVAR) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.BOOLVAR;
            var.id = node.number;

            if (parent != null) {
                if (parent.hasVar(nameString, var.type)) {
                    if (parent.isVisibleToChild(nameString, var.type)) {
                        var.isFromParent = true;
                        var.isVisibleToChild = false;
                    } else {
                        throw new RuntimeException("BoolVar " + nameString + " not allowed in the scope of " + this.name + " as it already exists.");
                    }
                } else {
                    var.isFromParent = false;
                    var.isVisibleToChild = true;
                }
            } else {
                var.isFromParent = false;
                var.isVisibleToChild = true;
            }

            System.out.println(this.name);

            if (!hasVar(nameString, _TokenType.BOOLVAR) && !var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (!hasVar(nameString, _TokenType.BOOLVAR) && var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (hasVar(nameString, _TokenType.BOOLVAR) && var.isFromParent) {
                return;
            } else if (hasVar(nameString, _TokenType.BOOLVAR) && !var.isFromParent) {
                return;
            }

            return;
        }

        if (node.type == _TokenType.NUMVAR) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.NUMVAR;
            var.id = node.number;

            if (parent != null) {
                if (parent.hasVar(nameString, var.type)) {
                    if (parent.isVisibleToChild(nameString, var.type)) {
                        var.isFromParent = true;
                        var.isVisibleToChild = false;
                    } else {
                        throw new RuntimeException("Number " + nameString + " not allowed in the scope of " + this.name + " as it already exists.");
                    }
                } else {
                    var.isFromParent = false;
                    var.isVisibleToChild = true;
                }
            } else {
                var.isFromParent = false;
                var.isVisibleToChild = true;
            }

            System.out.println(this.name);

            if (!hasVar(nameString, _TokenType.NUMVAR) && !var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (!hasVar(nameString, _TokenType.NUMVAR) && var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (hasVar(nameString, _TokenType.NUMVAR) && var.isFromParent) {
                return;
            } else if (hasVar(nameString, _TokenType.NUMVAR) && !var.isFromParent) {
                return;
            }

            return;
        }

        if (node.type == _TokenType.STRINGV) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.STRINGV;

            if (parent != null) {
                if (parent.hasVar(nameString, var.type)) {
                    if (parent.isVisibleToChild(nameString, var.type)) {
                        var.isFromParent = false;
                        var.isVisibleToChild = true;
                    } else {
                        throw new RuntimeException("String " + nameString + " not allowed in the scope of " + this.name + " as it already exists.");
                    }
                } else {
                    var.isFromParent = false;
                    var.isVisibleToChild = true;
                }
            } else {
                var.isFromParent = false;
                var.isVisibleToChild = true;
            }

            System.out.println(this.name);

            if (!hasVar(nameString, _TokenType.STRINGV) && !var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (!hasVar(nameString, _TokenType.STRINGV) && var.isFromParent) {
                ids.add(node.number);
                addSymbol(var, node.number);
            } else if (hasVar(nameString, _TokenType.STRINGV) && var.isFromParent) {
                return;
            } else if (hasVar(nameString, _TokenType.STRINGV) && !var.isFromParent) {
                return;
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

        for (ASTNode child : node.children) {
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
            html += "<tr>\n<td>" + table.get(id).name + "</td><td>" + table.get(id).type + "</td><td>" + table.get(id).id + "</td><td>" + table.get(id).isFromParent + "</td>\n</tr>";
        }

        for (Integer id : idsSiblings) {
            html += "<tr>\n<td>" + siblings.get(id).name + "</td><td>" + siblings.get(id).type + "</td><td>" + siblings.get(id).id + "</td><td>" + siblings.get(id).isFromParent + "</td>\n</tr>";
        }

        return html;
    }

    public boolean hasProc(String s) {
        if (!table.isEmpty()) {
            for (Integer i : ids) {
                if (this.table.get(i) == null) {
                    continue;
                }
                if (this.table.get(i).name.equals(s) && this.table.get(i).type == _TokenType.PROC) {
                    return true;
                }
            }
        }

        if (!siblings.isEmpty()) {

            for (Integer i : idsSiblings) {
                if (this.siblings.get(i) == null) {
                    continue;
                }
                if (this.siblings.get(i).name.equals(s) && this.siblings.get(i).type == _TokenType.PROC) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasVar(String s, _TokenType type) {
        if (!table.isEmpty()) {
            for (Integer i : ids) {
                var symbol = this.table.get(i);

                if (symbol == null) {
                    continue;
                }
                if (symbol.name.equals(s) && symbol.type == type) {
                    return true;
                }
            }
        }

        if (!siblings.isEmpty()) {

            for (Integer i : idsSiblings) {
                var symbol = this.table.get(i);

                if (symbol == null) {
                    continue;
                }
                if (symbol.name.equals(s) && symbol.type == type) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isVisibleToChild(String s, _TokenType type) {
        if (!table.isEmpty()) {
            for (Integer i : ids) {
                var symbol = this.table.get(i);

                if (symbol == null) {
                    continue;
                }
                if (symbol.name.equals(s) && symbol.type == type) {
                    return symbol.isVisibleToChild;
                }
            }
        }

        if (!siblings.isEmpty()) {

            for (Integer i : idsSiblings) {
                var symbol = this.table.get(i);

                if (symbol == null) {
                    continue;
                }
                if (symbol.name.equals(s) && symbol.type == type) {
                    return symbol.isVisibleToChild;
                }
            }
        }

        return false;
    }

    public boolean hasCall(String name) {
        if (calls.isEmpty()) {
            return false;
        }

        for (Integer i : idsCalls) {
            if (this.calls.get(i) == null) {
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
        for (Integer s : this.parent.ids) {
            if (this.hasProc(this.parent.table.get(s).name)) {
                throw new RuntimeException("Procedure " + this.parent.table.get(s).name + " not allowed in the scope of " + this.name + " as it already exists.");
            } else if (this.parent.table.get(s).type == _TokenType.PROC) {
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
            if (this.hasCall((String) table.get(id).name)) {
            } else if (this.table.get(id).type == _TokenType.PROC) {
                html += "<br>WARNING : Proc " + this.table.get(id).name + " is never called in scope: " + name + ".";
            }
        }

        for (Integer sid : idsSiblings) {
            if (this.siblings.get(sid) == null) {
                continue;
            }
            if (this.hasCall((String) siblings.get(sid).name)) {
            } else if (this.siblings.get(sid).type == _TokenType.PROC) {
                html += "<br>WARNING : Proc " + this.siblings.get(sid).name + " is never called in scope: " + name + ".";
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
            if (this.hasProc((String) calls.get(id).name)) {
            } else {
                html += "<br>ERROR : Proc " + this.calls.get(id).name + " is called in Proc " + name + " but does not exist within the scope of Proc " + name + ".";
            }
        }

        return html;
    }

    public boolean containsCallAndProc(String callName, String procName) {
        return this.hasProc(procName) && this.hasCall(callName);
    }

    public boolean containsVarByName(Scope scope, String name, _TokenType type) {
        return this.hasVar(name, type);
    }
}
