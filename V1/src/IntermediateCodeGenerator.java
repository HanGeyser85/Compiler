
import java.util.ArrayList;
import java.util.List;

public class IntermediateCodeGenerator {

    sTable symbolTable;
    vTable variableTable;
    List<String> output = new ArrayList<>();
    Integer counter = 0;
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
                case PROGR -> {
                    for (ASTNode object : node.children) {
                        generate(object);
                    }
                }
                case ALGO -> {
                    for (ASTNode object : node.children) {
                        generate(object);
                    }
                }
                case INSTR -> {
                    for (ASTNode object : node.children) {
                        generate(object);
                    }
                }
                case INPUT -> {
                    counter++;
                    output.add(counter + " INPUT " + generate(node.children.get(0)));
                }
                case NUMVAR -> {
                    if (node.parent.type == _TokenType.INPUT) {
                        String temp = "";
                        for (ASTNode object : node.children) {
                            temp += object.content;
                        }

                        return "\"Please input a number: \"; N" + temp;
                    } else {
                        String temp = "";
                        for (ASTNode object : node.children) {
                            temp += object.content;
                        }

                        return "N" + temp;
                    }
                }
                case OUTPUT -> {
                    counter++;
                    output.add(counter + " PRINT " + generate(node.children.get(0)));
                }
                case STRINGV -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }

                    return "S" + temp + "$";
                }
                case BOOLVAR -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }

                    return "B" + temp;
                }
                case ASSIGN -> {
                    counter++;
                    output.add(counter + " LET " + generate(node.children.get(0)) + " = " + generate(node.children.get(1)));
                    if (node.children.get(1).type == _TokenType.LOGIC_OR) {

                        counter++;
                        output.add(counter + " IF " + generate(node.children.get(0)) + " = 2 THEN LET " + generate(node.children.get(0)) + " = 1");
                    }
                }
                case DECNUM -> {
                    if ("0.00".equals(node.content)) {
                        return "0.00";
                    } else {
                        return generate(node.children.get(0));
                    }
                }
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
                case STRI -> {
                    return node.content;
                }
                case LOGIC_TRUE -> {
                    return "1";
                }
                case LOGIC_FALSE -> {
                    return "0";
                }
                case LOGIC_AND -> {
                    if (node.parent.type == _TokenType.ASSIGN) {
                        return generate(node.children.get(0)) + " * " + generate(node.children.get(1));
                    }
                }
                case LOGIC_NOT -> {
                    if (node.parent.type == _TokenType.ASSIGN) {
                        return "1 - " + generate(node.children.get(0));
                    }
                }
                case LOGIC_OR -> {
                    if (node.parent.type == _TokenType.ASSIGN) {
                        return generate(node.children.get(0)) + " + " + generate(node.children.get(1));
                    }
                }
                case HALT -> {
                    counter++;
                    output.add(counter + " STOP");
                }
                case CMPR_GREATERTHAN -> {
                    if (null != node.parent.type) {
                        switch (node.parent.type) {
                            case ASSIGN -> {
                                return generate(node.children.get(0)) + " > " + generate(node.children.get(1));
                            }
                            case LOOP ->
                                output.add(whiles[3] + " IF " + generate(node.children.get(0)) + " < " + generate(node.children.get(1)) + " THEN GOTO " + whiles[2].toString());
                            case BRANCH ->
                                output.add(ifs[4] + " IF " + generate(node.children.get(0)) + " < " + generate(node.children.get(1)) + " THEN GOTO " + ifs[1].toString());
                            default -> {
                            }
                        }
                    }
                }
                case CMPR_LESSTHAN -> {
                    if (null != node.parent.type) {
                        switch (node.parent.type) {
                            case ASSIGN -> {
                                return generate(node.children.get(0)) + " < " + generate(node.children.get(1));
                            }
                            case LOOP ->
                                output.add(whiles[3] + " IF " + generate(node.children.get(0)) + " > " + generate(node.children.get(1)) + " THEN GOTO " + whiles[2].toString());
                            case BRANCH ->
                                output.add(ifs[4] + " IF " + generate(node.children.get(0)) + " > " + generate(node.children.get(1)) + " THEN GOTO " + ifs[1].toString());
                            default -> {
                            }
                        }
                    }
                }
                case CMPR_EQUAL -> {
                    if (null != node.parent.type) {
                        switch (node.parent.type) {
                            case ASSIGN -> {
                                return generate(node.children.get(0)) + " = " + generate(node.children.get(1));
                            }
                            case LOOP ->
                                output.add(whiles[3] + " IF " + generate(node.children.get(0)) + " <> " + generate(node.children.get(1)) + " THEN GOTO " + whiles[2].toString());
                            case BRANCH ->
                                output.add(ifs[4] + " IF " + generate(node.children.get(0)) + " <> " + generate(node.children.get(1)) + " THEN GOTO " + ifs[1].toString());
                            default -> {
                            }
                        }
                    }
                }
                case LOOP -> {
                    whiles[0] += 4;
                    whiles[1] += 4;
                    whiles[2] += 4;
                    whiles[3] += 4;
                    output.add((whiles[0]).toString() + " REM " + whiles[0]);
                    generateCond(node.children.get(0), whiles[1], whiles[2], whiles[3]);
                    output.add((whiles[1]).toString() + " REM " + whiles[1]);
                    generate(node.children.get(1));
                    counter++;
                    output.add(counter + " GOTO " + whiles[0].toString());
                    output.add((whiles[2]).toString() + " REM " + whiles[2]);
                }
                case BRANCH -> {
                    ifs[0] += 5;
                    ifs[1] += 5;
                    ifs[2] += 5;
                    ifs[3] += 5;
                    ifs[4] += 5;
                    if (node.children.size() == 2) {

                        generateCond(node.children.get(0), ifs[0], ifs[1], ifs[4]);
                        output.add((ifs[0]).toString() + " REM " + ifs[0]);
                        generate(node.children.get(1));
                        output.add((ifs[1]).toString() + " REM " + ifs[1]);
                    } else if (node.children.size() == 3) {

                        generateCond(node.children.get(0), ifs[0], ifs[1], ifs[4]);
                        output.add((ifs[0]).toString() + " REM " + ifs[0]);
                        generate(node.children.get(1));

                        counter++;
                        output.add(counter + " GOTO " + ifs[3]);
                        output.add((ifs[1]).toString() + " REM " + ifs[1]);
                        generate(node.children.get(2));
                        output.add((ifs[3]).toString() + " REM " + ifs[3]);
                    }
                }
                case NUMEXPR_ADDITION -> {
                    return generate(node.children.get(0)) + " + " + generate(node.children.get(1));
                }
                case NUMEXPR_DIVISION -> {
                    return generate(node.children.get(0)) + " / " + generate(node.children.get(1));
                }
                case NUMEXPR_MULTIPLICATION -> {
                    return generate(node.children.get(0)) + " * " + generate(node.children.get(1));
                }
                case CALL -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        temp += object.content;
                    }
                    counter++;
                    output.add(counter + " GOSUB " + (Integer.parseInt(temp) + 5000));
                }
                case PROCDEFS -> {
                    for (ASTNode object : node.children) {
                        generate(object);
                    }
                }
                case PROC -> {
                    String temp = "";
                    for (ASTNode object : node.children) {
                        if (object.type == _TokenType.D) {
                            temp += object.content;
                        }
                    }
                    counter++;
                    output.add(counter + " END");
                    counter++;
                    output.add(counter + " REM The end is th ensure that the program does not run into the subroutine unless called!");
                    output.add((Integer.parseInt(temp) + 5000) + " REM " + (Integer.parseInt(temp) + 5000));
                    generate(node.children.get(1));
                    counter++;
                    output.add(counter + " RETURN");
                }
                default -> {
                }
            }
        }

        return "";
    }

    public String generateCond(ASTNode node, Integer LableT, Integer LableF, Integer LableStart) {
        if (null != node.type) {
            switch (node.type) {
                case LOGIC_TRUE -> {
                    counter++;
                    output.add(counter + " GOTO " + LableT);
                }
                case LOGIC_FALSE -> {
                    counter++;
                    output.add(counter + " GOTO " + LableF);
                }
                case LOGIC_AND -> {
                    // if ((node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.BOOLVAR)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.LOGIC_TRUE)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.BOOLVAR)
                    //     ||(node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.LOGIC_TRUE)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.BOOLVAR)
                    //     ||(node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    //     ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.LOGIC_TRUE)) {
                    //     output.add(LableStart+" IF ("+generate(node.children.get(0))+" AND "+generate(node.children.get(1))+") AND 0 THEN GOTO "+LableF);
                    // } else {
                    Integer temp = args += 10;
                    generateCond(node.children.get(0), temp, LableF, LableStart);
                    output.add(temp + " REM " + temp);
                    generateCond(node.children.get(0), LableT, LableF, LableStart);
                    // }
                }
                case LOGIC_NOT ->
                    generateCond(node.children.get(0), LableF, LableT, LableStart);
                case LOGIC_OR -> {
                    // if ((node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.BOOLVAR)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.LOGIC_TRUE)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.BOOLVAR)
                    // ||(node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.LOGIC_TRUE)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.BOOLVAR)
                    // ||(node.children.get(0).type == _TokenType.BOOLVAR && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_TRUE && node.children.get(1).type == _TokenType.LOGIC_FALSE)
                    // ||(node.children.get(0).type == _TokenType.LOGIC_FALSE && node.children.get(1).type == _TokenType.LOGIC_TRUE)) {
                    //     output.add(LableStart+" IF ("+generate(node.children.get(0))+" OR "+generate(node.children.get(1))+") AND 0 THEN GOTO "+LableF);
                    // } else {
                    Integer temp = args += 10;
                    generateCond(node.children.get(0), LableT, temp, LableStart);
                    output.add(temp + " REM " + temp);
                    generateCond(node.children.get(0), LableT, LableF, LableStart);
                    // }
                }
                case CMPR_GREATERTHAN ->
                    output.add(LableStart + " IF " + generate(node.children.get(0)) + " < " + generate(node.children.get(1)) + " THEN GOTO " + LableF);
                case CMPR_LESSTHAN ->
                    output.add(LableStart + " IF " + generate(node.children.get(0)) + " > " + generate(node.children.get(1)) + " THEN GOTO " + LableF);
                case CMPR_EQUAL ->
                    output.add(LableStart + " IF " + generate(node.children.get(0)) + " <> " + generate(node.children.get(1)) + " THEN GOTO " + LableF);
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
