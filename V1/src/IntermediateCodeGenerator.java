
import java.util.ArrayList;
import java.util.List;

public class IntermediateCodeGenerator {

    sTable symbolTable;
    vTable variableTable;
    fTable table;
    List<String> output = new ArrayList<>();
    Integer counter = 1;
    Integer args = 9000;
    Integer[] ifs = {6999, 7000, 7001, 7002, 8000};
    Integer[] whiles = {2999, 3000, 3001, 4000};

    public sTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(sTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public vTable getVariableTable() {
        return variableTable;
    }

    public void setVariableTable(vTable variableTable) {
        this.variableTable = variableTable;
    }

    IntermediateCodeGenerator() {
    }

    public String generate(ASTNode node) {
        if (node == null) {
            return "";
        }

        if (null != node.type) {
            switch (node.type) {
                case PROGR, PROCDEFS, ALGO -> {
                    for (ASTNode object : node.children) {
                        generate(object);
                    }
                }
                case INSTR -> {
                    for (ASTNode object : node.children) {
                        generateStat(object);
                    }
                }
                case PROC -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        if (object.type == _TokenType.D) {
                            temp += object.content;
                        }
                    }
                    output.add(counter++ + " END");
                    output.add(counter++ + " REM The end is th ensure that the program does not run into the subroutine unless called!");
                    output.add((Integer.parseInt(temp) + 5000) + " REM " + (Integer.parseInt(temp) + 5000));
                    generate(node.children.get(1));
                    counter++;
                }
                default -> {
                }
            }
        }

        return "";
    }

    public String generateStat(ASTNode node) {
        if (null != node.type) {
            switch (node.type) {
                case INPUT -> {
                    output.add(counter++ + " INPUT \"Please input a number\"; " + generateExpr(node.children.get(0)));
                }
                case OUTPUT -> {
                    output.add(counter++ + " PRINT " + generateExpr(node.children.get(0)));
                }
                case ASSIGN -> {
                    output.add(counter++ + " LET " + generateExpr(node.children.get(0)) + " = " + generateExpr(node.children.get(1)));
                }
                case CALL -> {
                    output.add(counter++ + " GOSUB " + (Integer.parseInt(lookUpF(table, node.children.get(0).number)) + 5000));
                }
                case LOOP -> {
                    String Lable1 = String.valueOf(whiles[0] += 4);
                    String Lable2 = String.valueOf(whiles[1] += 4);
                    String Lable3 = String.valueOf(whiles[2] += 4);

                    output.add(Lable1 + " REM " + Lable1);
                    generateCond(node.children.get(0), Lable2, Lable3);
                    output.add(Lable2 + " REM " + Lable2);
                    generateStat(node.children.get(1));
                    output.add(counter++ + " GOTO " + Lable1);
                    output.add(Lable3 + " REM " + Lable3);

                }
                case BRANCH -> {
                    if (node.children.size() == 2) {
                        String Lable1 = String.valueOf(ifs[0] += 5);
                        String Lable2 = String.valueOf(ifs[1] += 5);

                        generateCond(node.children.get(0), Lable1, Lable2);
                        output.add(Lable1 + " REM " + Lable1);

                        generateStat(node.children.get(1));
                        output.add(Lable2 + " REM " + Lable2);
                    } else if (node.children.size() == 3) {
                        String Lable1 = String.valueOf(ifs[0] += 5);
                        String Lable2 = String.valueOf(ifs[1] += 5);
                        String Lable3 = String.valueOf(ifs[2] += 5);

                        generateCond(node.children.get(0), Lable1, Lable2);
                        output.add(Lable1 + " REM " + Lable1);

                        generateStat(node.children.get(1));

                        output.add(counter++ + " GOTO " + Lable3);
                        output.add(Lable2 + " REM " + Lable2);
                        generateStat(node.children.get(2));
                        output.add(Lable3 + " REM " + Lable3);
                    }
                }
                case HALT -> {
                    output.add(counter++ + " RETURN");
                }
                default -> {
                    generate(node);
                }
            }
        }

        return "";
    }

    public String generateCond(ASTNode node, String LabelT, String LabelF) {
        if (null != node.type) {
            switch (node.type) {
                case LOGIC_FALSE -> {
                    return counter++ + " GOTO " + LabelT;
                }
                case LOGIC_TRUE -> {
                    return counter++ + " GOTO " + LabelF;
                }
                case BOOLVAR -> {
                    output.add(counter++ + " IF " + generateExpr(node) + " = 1 THEN GOTO " + LabelT);
                    output.add(counter++ + " GOTO " + LabelF);
                }
                case NUMVAR -> {
                    return generateExpr(node);
                }
                case LOGIC_NOT -> {
                    output.add(generateCond(node.children.get(1), LabelF, LabelT));
                }
                case LOGIC_AND -> {
                    output.add(generateCond(node.children.get(0), String.valueOf(args++), LabelF));
                    output.add((args - 1) + " REM " + (args - 1));
                    output.add(generateCond(node.children.get(1), LabelT, LabelF));
                }
                case LOGIC_OR -> {
                    output.add(generateCond(node.children.get(0), LabelT, String.valueOf(args++)));
                    output.add((args - 1) + " REM " + (args - 1));
                    output.add(generateCond(node.children.get(1), LabelT, LabelF));
                }
                case NUMEXPR_MULTIPLICATION -> {
                    output.add(generateExpr(node.children.get(0)) + " * " + generateExpr(node.children.get(1)));
                }
                case NUMEXPR_ADDITION -> {
                    output.add(generateExpr(node.children.get(0)) + " + " + generateExpr(node.children.get(1)));
                }
                case NUMEXPR_DIVISION -> {
                    output.add(generateExpr(node.children.get(0)) + " / " + generateExpr(node.children.get(1)));
                }
                case CMPR_EQUAL -> {
                    output.add(counter++ + " IF " + generateExpr(node.children.get(0)) + " = " + generateExpr(node.children.get(1)) + " THEN GOTO " + LabelT);
                    output.add(counter++ + " GOTO " + LabelF);
                }
                case CMPR_GREATERTHAN -> {
                    output.add(counter++ + " IF " + generateExpr(node.children.get(0)) + " > " + generateExpr(node.children.get(1)) + " THEN GOTO " + LabelT);
                    output.add(counter++ + " GOTO " + LabelF);
                }
                case CMPR_LESSTHAN -> {
                    output.add(counter++ + " IF " + generateExpr(node.children.get(0)) + " < " + generateExpr(node.children.get(1)) + " THEN GOTO " + LabelT);
                    output.add(counter++ + " GOTO " + LabelF);
                }
                default -> {
                }
            }
        }
        return "";
    }

    public String generateExpr(ASTNode node) {
        if (null != node.type) {
            switch (node.type) {
                case LOGIC_FALSE -> {
                    return "0";
                }
                case LOGIC_TRUE -> {
                    return "1";
                }
                case STRI -> {
                    return node.content;
                }
                case DECNUM -> {
                    return getValue(node.children.get(0));
                }
                case BOOLVAR, NUMVAR, STRINGV -> {
                    return lookUpV(variableTable, node.number, node.type);
                }
                case LOGIC_NOT -> {
                    return "1 - " + generateExpr(node.children.get(0));
                }
                case LOGIC_AND, NUMEXPR_MULTIPLICATION -> {
                    return generateExpr(node.children.get(0)) + " * " + generateExpr(node.children.get(1));
                }
                case LOGIC_OR, NUMEXPR_ADDITION -> {
                    return generateExpr(node.children.get(0)) + " + " + generateExpr(node.children.get(1));
                }
                case CMPR_EQUAL -> {
                    return generateExpr(node.children.get(0)) + " = " + generateExpr(node.children.get(1));
                }
                case CMPR_GREATERTHAN -> {
                    return generateExpr(node.children.get(0)) + " > " + generateExpr(node.children.get(1));
                }
                case CMPR_LESSTHAN -> {
                    return generateExpr(node.children.get(0)) + " < " + generateExpr(node.children.get(1));
                }
                case NUMEXPR_DIVISION -> {
                    return generateExpr(node.children.get(0)) + " / " + generateExpr(node.children.get(1));
                }
                default -> {
                }
            }
        }

        return "";
    }

    public String getValue(ASTNode node) {
        if (null != node.type) {
            switch (node.type) {
                case NEG -> {
                    return generate(node.children.get(0));
                }
                case POS -> {
                    if (node.parent.type == _TokenType.NEG) {
                        String temp = "";
                        for (ASTNode object : node.children) {
                            temp += object.content;
                        }

                        return "-" + temp;
                    } else if (node.parent.type == _TokenType.DECNUM) {
                        String temp = "";
                        for (ASTNode object : node.children) {
                            temp += object.content;
                        }

                        return temp;
                    }
                }
                default -> {
                }
            }
        }

        return "";
    }

    public String lookUpV(vTable table, Integer number, _TokenType type) {
        for (Integer i : table.ids) {
            Variable tempNumber = table.table.get(i);

            if (tempNumber.id.equals(number) && tempNumber.type == type) {
                switch (tempNumber.type) {
                    case NUMVAR -> {
                        return "N" + table.table.get(i).name;
                    }
                    case BOOLVAR -> {
                        return "B" + table.table.get(i).name;
                    }
                    case STRINGV -> {
                        return "S" + table.table.get(i).name + "$";
                    }
                    default -> {
                    }
                }
            }

            if (tempNumber.getUsedAtIds().contains(number) && tempNumber.type == type) {
                switch (tempNumber.type) {
                    case NUMVAR -> {
                        return "N" + table.table.get(i).name;
                    }
                    case BOOLVAR -> {
                        return "B" + table.table.get(i).name;
                    }
                    case STRINGV -> {
                        return "S" + table.table.get(i).name + "$";
                    }
                    default -> {
                    }
                }
            }
        }

        return "";
    }

    public String lookUpF(fTable table, Integer number) {
        for (Integer i : table.ids) {
            Function tempNumber = table.table.get(i);

            if (tempNumber.id.equals(number)) {
                return table.table.get(i).name;
            }

            if (tempNumber.getCalledAtIds().contains(number)) {
                return table.table.get(i).name;
            }
        }

        return "";
    }

    public String getName(ASTNode node) {
        if (null != node.type) {
            switch (node.type) {
                case NUMVAR -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }

                    return temp;
                }
                case BOOLVAR -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }

                    return temp;
                }
                case STRINGV -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }

                    return temp;
                }
                default -> {
                }
            }
        }

        return "";
    }

    @Override

    public String toString() {
        String temp = "";
        for (String object : output) {
            temp += object + "\n";
        }

        return temp;
    }
}
