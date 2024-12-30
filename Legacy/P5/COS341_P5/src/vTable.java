import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class vTable {
    Hashtable<Integer, Variable> table = new Hashtable<Integer, Variable>();
    Integer sizInteger = 0;
    List<Integer> ids = new ArrayList<Integer>();
    private boolean hasHalt = false;

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

        html += "<h2>Variable Table</h2><br><tr>\n<th>Name</th>\n<th>Type</th>\n<th>ID</th>\n<th>Has Value</th>\n<th>Not Reached</th>\n</tr>\n";

        for (Integer integer : ids) {
            html += "<tr>\n<td>" + table.get(integer).name + "</td>\n<td>" + table.get(integer).type + "</td>\n<td>"
                    + table.get(integer).id + "</td>\n<td>" + table.get(integer).hasValue + "</td>\n<td>" + table.get(integer).notReached + "</td>\n</tr>\n";
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
            var.hasValue = false;
            var.notReached = true;

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
            var.hasValue = false;
            var.notReached = true;

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
            var.hasValue = false;
            var.notReached = true;

            if (!hasVariable(nameString, _TokenType.BOOLVAR)) {
                ids.add(node.number);
                addVariable(var, node.number);
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
            populateTable(child);
        }

        return;
    }

    public boolean hasValue(ASTNode node) {
        if (node == null) {
            return false;
        }

        if (node.type == _TokenType.ASSIGN) {
            ASTNode varNode = node.children.get(0);
            ASTNode valueNode = node.children.get(1);

            if (varNode.type == _TokenType.NUMVAR) {
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
                        table.get(integer).notReached = false;
                    }
                }

                return ret;
            } else if (varNode.type == _TokenType.STRINGV) {
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
                        table.get(integer).notReached = false;
                    }
                }

                return ret;
            } else if (varNode.type == _TokenType.BOOLVAR) {
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
                        table.get(integer).notReached = false;
                    }
                }

                return ret;
            }
        } else if (node.type == _TokenType.DECNUM) {
            return true;
        } else if (node.type == _TokenType.STRI) {
            return true;
        } else if (node.type == _TokenType.INPUT_GET) {
            ASTNode varNode = node.children.get(0);

            String nameString = "";

            for (ASTNode astNode : varNode.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            for (Integer integer : ids) {
                if (table.get(integer).name.equals(nameString)
                        && table.get(integer).type.equals(_TokenType.INPUT_GET)) {
                    table.get(integer).hasValue = true;
                    
                    table.get(integer).notReached = false;
                }
            }
        } else if (node.type == _TokenType.NUMEXPR_ADDITION) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);

        } else if (node.type == _TokenType.NUMEXPR_MULTIPLICATION) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.NUMEXPR_DIVISION) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.NUMVAR) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            for (Integer integer : ids) {
                if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.NUMVAR)) {
                    
                    table.get(integer).notReached = false;
                    return table.get(integer).hasValue;
                }
            }
        } else if (node.type == _TokenType.STRINGV) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            for (Integer integer : ids) {
                if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.STRINGV)) {
                    
                    table.get(integer).notReached = false;
                    return table.get(integer).hasValue;
                }
            }
        } else if (node.type == _TokenType.BOOLVAR) {
            String nameString = "";

            for (ASTNode astNode : node.children) {
                if (astNode.type == _TokenType.D) {
                    nameString += astNode.content;
                }
            }

            for (Integer integer : ids) {
                if (table.get(integer).name.equals(nameString) && table.get(integer).type.equals(_TokenType.BOOLVAR)) {
                    
                    table.get(integer).notReached = false;
                    return table.get(integer).hasValue;
                }
            }
        } else if (node.type == _TokenType.NOT) {
            ASTNode varNode1 = node.children.get(0);

            return hasValue(varNode1);
        } else if (node.type == _TokenType.AND) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.OR) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.CMPR_GREATERTHAN) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.CMPR_EQUAL) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.CMPR_LESSTHAN) {
            ASTNode varNode1 = node.children.get(0);
            ASTNode varNode2 = node.children.get(1);

            return hasValue(varNode1) && hasValue(varNode2);
        } else if (node.type == _TokenType.LOGIC_FALSE) {
            return true;
        } else if (node.type == _TokenType.LOGIC_TRUE) {
            return true;
        } else if (node.type == _TokenType.HALT) {
            return false;
        } else if (node.type == _TokenType.BRANCH) {
            ASTNode varNode1 = node.children.get(0);

            return hasValue(varNode1);
        } else if (node.type == _TokenType.LOOP) {
            ASTNode varNode1 = node.children.get(0);

            return hasValue(varNode1);
        } else if (node.type == _TokenType.OUTPUT) {
            ASTNode varNode1 = node.children.get(0);

            return hasValue(varNode1);
        }


        return false;
    }
}
