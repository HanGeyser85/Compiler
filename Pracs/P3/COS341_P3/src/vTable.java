import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class vTable {
    Hashtable<Integer, Variable> table = new Hashtable<Integer, Variable>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<Integer>();

    public void addVariable(Variable var, Integer index) {
        table.put(index, var);
        sizInteger++;
    }

    public Variable getVariable(String name, _TokenType type) {
        for (int i = 0; i < sizInteger; i++) {
            if (table.get(i).name.equals(name) && table.get(i).type.equals(type)) {
                return table.get(i);
            }
        }

        return null;
    }

    public boolean hasVariable(String name, _TokenType type) {
        if (table.isEmpty()) {
            return false;
        }

        for (Integer i : ids) {
            if (this.table.get(i) ==  null) {
                continue;
            }
            if (this.table.get(i).name.equals(name) && this.table.get(i).type.equals(type)) {
                return true;
            }
        }

        return false;
    }

    public void removeVariable(String name, _TokenType type) {
        for (int i = 0; i < sizInteger; i++) {
            if (table.get(i).name.equals(name) && table.get(i).type.equals(type)) {
                table.remove(i);
                sizInteger--;
            }
        }
    }

    public void printTable() {
        for (int i = 0; i < sizInteger; i++) {
            System.out.println(table.get(i).toString());
        }
    }

    public String toXML() {
        String xml = "<vTable>";

        for (int i = 0; i < sizInteger; i++) {
            xml += table.get(i).toXML();
        }

        xml += "</vTable>";

        return xml;
    }

    public String toHTML() {
        String html = "<table border=\"1\">\n";

        html += "<h2>Variable Table</h2><br><tr>\n<th>Name</th>\n<th>Type</th>\n<th>ID</th>\n</tr>\n";

        for (Integer integer : ids) {
            html += "<tr>\n<td>" + table.get(integer).name + "</td>\n<td>" + table.get(integer).type + "</td>\n<td>" + table.get(integer).id + "</td>\n</tr>\n";
        }

        html += "</table><br>";

        return html;
    }

    public void populateTable(ASTNode node) {
        if (node == null) {
            return;
        }

        Variable var = new Variable();

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

            
            if (!hasVariable(nameString, _TokenType.NUMVAR)) {
                ids.add(node.number);
                addVariable(var, node.number);
            }
        } else if (node.type == _TokenType.STRINGV) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.STRINGV;
            var.id = node.number;

            
            if (!hasVariable(nameString, _TokenType.STRINGV)) {
                ids.add(node.number);
                addVariable(var, node.number);
            }
        } else if (node.type == _TokenType.BOOLVAR) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            var.name = nameString;
            var.type = _TokenType.BOOLVAR;
            var.id = node.number;

            
            if (!hasVariable(nameString, _TokenType.BOOLVAR)) {
                ids.add(node.number);
                addVariable(var, node.number);
            }
        }

        for (ASTNode child : node.children) {
            populateTable(child);
        }

        return;
    }
}
