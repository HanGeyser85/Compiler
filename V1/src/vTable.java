
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class vTable {

    ConcurrentHashMap<Integer, Variable> table = new ConcurrentHashMap<>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<>();

    public Integer getIndexOnParent(ASTNode node) {
        if (node == null) {
            return -1;
        }

        return node.parent.children.indexOf(node);
    }

    public void addVariable(Variable var, Integer index) {
        table.put(index, var);
        sizInteger++;
    }

    public Variable getVariable(String name, _TokenType type) {
        for (Integer i : ids) {
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
            if (this.table.get(i) == null) {
                continue;
            }
            if (this.table.get(i).name.equals(name) && this.table.get(i).type.equals(type)) {
                return true;
            }
        }

        return false;
    }

    public void removeVariable(String name, _TokenType type) {
        Iterator<Integer> it = ids.iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            Variable var = table.get(key);
            if (var != null && var.name.equals(name) && var.type.equals(type)) {
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

        html += "<h2>Variable Table</h2><br><tr>\n<th>Name</th>\n<th>Type</th>\n<th>ID</th>\n<th>Has Value</th>\n<th>Not Used</th>\n</tr>\n";

        for (Integer integer : ids) {
            html += "<tr>\n<td>" + table.get(integer).name + "</td>\n<td>" + table.get(integer).type + "</td>\n<td>"
                    + table.get(integer).id + "</td>\n<td>" + table.get(integer).hasValue + "</td>\n<td>" + table.get(integer).notUsed + "</td>\n</tr>\n";
        }

        html += "</table><br>";

        return html;
    }

    public void populateTable(ASTNode node) {
        if (node == null) {
            return;
        }

        Variable var = new Variable();

        if (null != node.type) {
            switch (node.type) {
                case NUMVAR -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }
                    if (!hasVariable(nameString, _TokenType.NUMVAR) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        var.name = nameString;
                        var.type = _TokenType.NUMVAR;
                        var.id = node.number;
                        var.hasValue = hasValue(node.parent);
                        var.notUsed = true;
                        var.initialized = true;

                        ids.add(node.number);
                        addVariable(var, node.number);
                    } else if (hasVariable(nameString, _TokenType.NUMVAR) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        Variable v = getVariable(nameString, _TokenType.NUMVAR);
                        Integer index = ids.indexOf(v.id);

                        if (ids.get(index) < 0) {
                            v.id = node.number;
                            v.hasValue = hasValue(node.parent);
                            v.notUsed = false;
                            v.initialized = true;

                            ids.set(index, node.number);
                            addVariable(v, node.number);
                        } else {
                            v.notUsed = false;
                            v.usedAtIds.add(node.number);
                        }
                    } else if ((!hasVariable(nameString, _TokenType.NUMVAR)
                            && !getIndexOnParent(node).equals(0))
                            || (!hasVariable(nameString, _TokenType.NUMVAR)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type == _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.NUMVAR)
                            && getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.NUMVAR)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)) {

                        var.name = nameString;
                        var.type = _TokenType.NUMVAR;
                        var.id = -node.number;
                        var.hasValue = false;
                        var.notUsed = false;
                        var.initialized = false;
                        var.usedAtIds.add(node.number);

                        ids.add(-node.number);
                        addVariable(var, -node.number);
                    } else if (hasVariable(nameString, _TokenType.NUMVAR)) {
                        Variable v = getVariable(nameString, _TokenType.NUMVAR);

                        v.notUsed = false;
                        v.usedAtIds.add(node.number);
                    }
                }
                case STRINGV -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }
                    if (!hasVariable(nameString, _TokenType.STRINGV) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        var.name = nameString;
                        var.type = _TokenType.STRINGV;
                        var.id = node.number;
                        var.hasValue = hasValue(node.parent);
                        var.notUsed = true;
                        var.initialized = true;

                        ids.add(node.number);
                        addVariable(var, node.number);
                    } else if (hasVariable(nameString, _TokenType.STRINGV) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        Variable v = getVariable(nameString, _TokenType.STRINGV);
                        Integer index = ids.indexOf(v.id);

                        if (ids.get(index) < 0) {
                            v.id = node.number;
                            v.hasValue = hasValue(node.parent);
                            v.notUsed = false;
                            v.initialized = true;

                            ids.set(index, node.number);
                            addVariable(v, node.number);
                        } else {
                            v.notUsed = false;
                            v.usedAtIds.add(node.number);
                        }
                    } else if ((!hasVariable(nameString, _TokenType.STRINGV)
                            && !getIndexOnParent(node).equals(0))
                            || (!hasVariable(nameString, _TokenType.STRINGV)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type == _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.STRINGV)
                            && getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.STRINGV)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)) {

                        var.name = nameString;
                        var.type = _TokenType.STRINGV;
                        var.id = -node.number;
                        var.hasValue = false;
                        var.notUsed = false;
                        var.initialized = false;
                        var.usedAtIds.add(node.number);

                        ids.add(-node.number);
                        addVariable(var, -node.number);
                    } else if (hasVariable(nameString, _TokenType.STRINGV)) {
                        Variable v = getVariable(nameString, _TokenType.STRINGV);

                        v.notUsed = false;
                        v.usedAtIds.add(node.number);
                    }
                }
                case BOOLVAR -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }

                    if (!hasVariable(nameString, _TokenType.BOOLVAR) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        var.name = nameString;
                        var.type = _TokenType.BOOLVAR;
                        var.id = node.number;
                        var.hasValue = hasValue(node.parent);
                        var.notUsed = true;
                        var.initialized = true;

                        ids.add(node.number);
                        addVariable(var, node.number);
                    } else if (hasVariable(nameString, _TokenType.BOOLVAR) && getIndexOnParent(node).equals(0) && node.parent.type == _TokenType.ASSIGN) {
                        Variable v = getVariable(nameString, _TokenType.BOOLVAR);
                        Integer index = ids.indexOf(v.id);

                        if (ids.get(index) < 0) {
                            v.id = node.number;
                            v.hasValue = hasValue(node.parent);
                            v.notUsed = false;
                            v.initialized = true;

                            ids.set(index, node.number);
                            addVariable(v, node.number);
                        } else {
                            v.notUsed = false;
                            v.usedAtIds.add(node.number);
                        }
                    } else if ((!hasVariable(nameString, _TokenType.BOOLVAR)
                            && !getIndexOnParent(node).equals(0))
                            || (!hasVariable(nameString, _TokenType.BOOLVAR)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type == _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.BOOLVAR)
                            && getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)
                            || (!hasVariable(nameString, _TokenType.BOOLVAR)
                            && !getIndexOnParent(node).equals(0)
                            && node.parent.type != _TokenType.ASSIGN)) {

                        var.name = nameString;
                        var.type = _TokenType.BOOLVAR;
                        var.id = -node.number;
                        var.hasValue = false;
                        var.notUsed = false;
                        var.initialized = false;
                        var.usedAtIds.add(node.number);

                        ids.add(-node.number);
                        addVariable(var, -node.number);
                    } else if (hasVariable(nameString, _TokenType.BOOLVAR)) {
                        Variable v = getVariable(nameString, _TokenType.BOOLVAR);

                        v.notUsed = false;
                        v.usedAtIds.add(node.number);
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
            populateTable(child);
        }
    }

    public boolean hasValue(ASTNode node) {
        if (node == null) {
            return false;
        }

        if (null != node.type) {
            switch (node.type) {
                case ASSIGN -> {
                    ASTNode varNode = node.children.get(0);
                    ASTNode valueNode = node.children.get(1);
                    if (null != varNode.type) {
                        switch (varNode.type) {
                            case NUMVAR -> {
                                String nameString = "";
                                boolean ret = hasValue(valueNode);

                                for (ASTNode astNode : varNode.children) {
                                    if (astNode.type == _TokenType.D) {
                                        nameString += astNode.content;
                                    }
                                }

                                for (Integer integer : ids) {
                                    if (table.get(integer).name.equals(nameString)
                                            && table.get(integer).type.equals(_TokenType.NUMVAR)) {
                                        table.get(integer).hasValue = ret;
                                    }
                                }

                                return ret;
                            }
                            case STRINGV -> {
                                String nameString = "";
                                boolean ret = hasValue(valueNode);

                                for (ASTNode astNode : varNode.children) {
                                    if (astNode.type == _TokenType.D) {
                                        nameString += astNode.content;
                                    }
                                }

                                for (Integer integer : ids) {
                                    if (table.get(integer).name.equals(nameString)
                                            && table.get(integer).type.equals(_TokenType.STRINGV)) {
                                        table.get(integer).hasValue = ret;
                                    }
                                }

                                return ret;
                            }
                            case BOOLVAR -> {
                                String nameString = "";
                                boolean ret = hasValue(valueNode);

                                for (ASTNode astNode : varNode.children) {
                                    if (astNode.type == _TokenType.D) {
                                        nameString += astNode.content;
                                    }
                                }

                                for (Integer integer : ids) {
                                    if (table.get(integer).name.equals(nameString)
                                            && table.get(integer).type.equals(_TokenType.BOOLVAR)) {
                                        table.get(integer).hasValue = ret;
                                    }
                                }

                                return ret;
                            }
                            default -> {
                            }
                        }

                    }
                }
                case DECNUM -> {
                    return true;
                }
                case STRI -> {
                    return true;
                }
                case INPUT_GET -> {
                    ASTNode varNode = node.children.get(0);
                    String nameString = extractDigits(varNode);
                    for (Integer key : ids) {
                        Variable v = table.get(key);
                        if (v.name.equals(nameString) && v.type == _TokenType.NUMVAR) {
                            v.hasValue = true;
                        }
                    }
                }
                case NUMEXPR_ADDITION -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);

                }
                case NUMEXPR_MULTIPLICATION -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case NUMEXPR_DIVISION -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case NUMVAR -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }
                    for (Integer integer : ids) {
                        if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.NUMVAR)) {

                            return table.get(integer).hasValue;
                        }
                    }
                }
                case STRINGV -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }
                    for (Integer integer : ids) {
                        if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.STRINGV)) {

                            return table.get(integer).hasValue;
                        }
                    }
                }
                case BOOLVAR -> {
                    String nameString = "";
                    for (ASTNode astNode : node.children) {
                        if (astNode.type == _TokenType.D) {
                            nameString += astNode.content;
                        }
                    }
                    for (Integer integer : ids) {
                        if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.BOOLVAR)) {

                            return table.get(integer).hasValue;
                        }
                    }
                }
                case LOGIC_NOT -> {
                    ASTNode varNode1 = node.children.get(0);

                    return hasValue(varNode1);
                }
                case LOGIC_AND -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case LOGIC_OR -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case CMPR_GREATERTHAN -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case CMPR_EQUAL -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case CMPR_LESSTHAN -> {
                    ASTNode varNode1 = node.children.get(0);
                    ASTNode varNode2 = node.children.get(1);

                    return hasValue(varNode1) && hasValue(varNode2);
                }
                case LOGIC_FALSE -> {
                    return true;
                }
                case LOGIC_TRUE -> {
                    return true;
                }
                case HALT -> {
                    return false;
                }
                case BRANCH -> {
                    ASTNode varNode1 = node.children.get(0);

                    return hasValue(varNode1);
                }
                case LOOP -> {
                    ASTNode varNode1 = node.children.get(0);

                    return hasValue(varNode1);
                }
                case OUTPUT -> {
                    ASTNode varNode1 = node.children.get(0);

                    return hasValue(varNode1);
                }
                default -> {
                }
            }
        }

        return false;
    }

    private String extractDigits(ASTNode node) {
        StringBuilder sb = new StringBuilder();
        for (ASTNode child : node.children) {
            if (child.type == _TokenType.D) {
                sb.append(child.content);
            }
        }
        return sb.toString();
    }

}
