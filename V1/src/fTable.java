import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class fTable {
    HashMap<Integer, Function> table = new HashMap<>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<>();

    public void addFunction(Function func, Integer index) {
        this.table.put(index, func);
        sizInteger++;
    }

    public Function getFunction(String name) {
        for (Integer i : ids) {
            if (this.table.get(i).name.equals(name)) {
                return this.table.get(i);
            }
        }

        return null;
    }

    public boolean hasFunction(String name) {
        if (table.isEmpty()) {
            return false;
        }

        for (Integer i : ids) {
            if (this.table.get(i) == null) {
                continue;
            }
            if (this.table.get(i).name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void removeFunction(String name) {
        Iterator<Integer> it = ids.iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            Function func = table.get(key);
            if (func != null && func.name.equals(name)) {
                table.remove(key);
                it.remove();       
            }
        }
        sizInteger = ids.size(); 
    }
    

    public void printTable() {
        for (Integer i : ids) {
            System.out.println(table.get(i).toString());
        }
    }

    public String toXML() {
        String xml = "<vTable>";

        for (Integer i : ids) {
            xml += table.get(i).toXML();
        }

        xml += "</vTable>";

        return xml;
    }

    public String toHTML() {
        String html = "<table border=\"1\">\n";

        html += "<h2>Function Table</h2><br><tr>\n<th>Name</th>\n<th>ID</th>\n<th>Is Called</th>\n</tr>\n";

        for (Integer integer : ids) {
            html += "<tr>\n<td>" + table.get(integer).name + "</td>\n<td>"
                    + table.get(integer).id + "</td>\n<td>" + table.get(integer).isCalled + "</td>\n</tr>\n";
        }

        html += "</table><br>";

        return html;
    }

    public void populateTable(ASTNode node) {
        if (node == null) {
            return;
        }

        Function func = new Function();

        if (node.type == _TokenType.PROC) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            func.name = nameString;
            func.id = node.number;
            func.isCalled = false;

            if (!hasFunction(nameString)) {
                ids.add(node.number);
                addFunction(func, node.number);
            }
        }

        for (ASTNode child : node.children) {
            if (child.type == _TokenType.HALT) {
                return;
            }
            populateTable(child);
        }
    }
}