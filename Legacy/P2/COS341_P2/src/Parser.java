import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Parser {
    String xmlTree = "";
    Queue<Token> inputTokens = new LinkedList<Token>();
    AST tree = new AST();
    ASTNode currentNode = null;
    ASTNode tempNode = null;
    Integer counter = 0;

    public void setInputTokens(List<Token> inputTokens) {
        for (Token token : inputTokens) {
            this.inputTokens.add(token);
        }
    }

    public String SyntacticalAnalysis(List<Token> input) {
        setInputTokens(input);

        currentNode = tree.root;
        PROGR();
        return xmlTree;
    }

    private void PROC() {
        
        if (inputTokens.remove().getType() == _TokenType.PROC) {
            xmlTree += "<PROC>\n";
            tempNode = new ASTNode("PROC", _TokenType.PROC, ++counter);
            currentNode.addChild(tempNode);
            currentNode = tempNode;

            DIGITS();
            if (inputTokens.remove().getType() == _TokenType.OPENBRACKET) {
                xmlTree += "{\n";
            } else {
                throw new RuntimeException("Expected OPENBRACKET, got " + inputTokens.peek().getType());
            }
            PROGR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEBRACKET) {
                xmlTree += "}\n";
            } else {
                throw new RuntimeException("Expected CLOSEBRACKET, got " + inputTokens.peek().getType());
            }
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</PROC>\n";
        } else {
            throw new RuntimeException("Expected PROC, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void PROGR() {
        if (inputTokens.peek().getType() == _TokenType.INPUT_GET || inputTokens.peek().getType() == _TokenType.OUTPUT
                || inputTokens.peek().getType() == _TokenType.TEXT_RESPONSE
                || inputTokens.peek().getType() == _TokenType.NUMVAR
                || inputTokens.peek().getType() == _TokenType.BOOLVAR
                || inputTokens.peek().getType() == _TokenType.STRINGV || inputTokens.peek().getType() == _TokenType.CALL
                || inputTokens.peek().getType() == _TokenType.WHILE || inputTokens.peek().getType() == _TokenType.IF
                || inputTokens.peek().getType() == _TokenType.HALT) {
            xmlTree += "<PROGR>\n";
            
            tempNode = new ASTNode("PROGR", _TokenType.PROGR, ++counter);
            if (currentNode != null) {  
            tempNode.parent = currentNode; 
                currentNode.children.add(tempNode);
                currentNode = tempNode;
            } else {
                tree.root = tempNode;
                currentNode = tempNode;
            }
            

            ALGO();
            
            PROCDEFS();

            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</PROGR>\n";
        } else {
            throw new RuntimeException("Expected PROGR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void PROCDEFS() {
        if (inputTokens.isEmpty()) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.SEPARATOR) {
            xmlTree += "<PROCDEFS>\n";
            tempNode = new ASTNode("PROCDEFS", _TokenType.PROCDEFS, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            inputTokens.remove();
            xmlTree += ",\n";
            PROC();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            PROCDEFS();
if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</PROCDEFS>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CLOSEBRACKET) {
            return;
        } else {
            throw new RuntimeException("Expected PROCDEFS, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void ALGO() {
        if (inputTokens.peek().getType() == _TokenType.INPUT_GET || inputTokens.peek().getType() == _TokenType.OUTPUT
                || inputTokens.peek().getType() == _TokenType.TEXT_RESPONSE
                || inputTokens.peek().getType() == _TokenType.NUMVAR
                || inputTokens.peek().getType() == _TokenType.BOOLVAR
                || inputTokens.peek().getType() == _TokenType.STRINGV || inputTokens.peek().getType() == _TokenType.CALL
                || inputTokens.peek().getType() == _TokenType.WHILE || inputTokens.peek().getType() == _TokenType.IF
                || inputTokens.peek().getType() == _TokenType.HALT) {
            xmlTree += "<ALGO>\n";
            tempNode = new ASTNode("ALGO", _TokenType.ALGO, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            INSTR();
            COMMENT();
            SEQ();
if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ALGO>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CLOSEBRACKET || inputTokens.peek().getType() == _TokenType.SEPARATOR) {
            return;
        } else {
            throw new RuntimeException("Expected ALGO, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void SEQ() {
        if (inputTokens.isEmpty()) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.DIVIDER) {
            xmlTree += "<SEQ>\n";
            inputTokens.remove();
            xmlTree += ";\n";
            ALGO();
           
            xmlTree += "</SEQ>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CLOSEBRACKET) {if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            return;
        } else if (inputTokens.peek().getType() == _TokenType.SEPARATOR) {if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            return;
        } else {
            throw new RuntimeException("Expected SEQ, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void COMMENT() {
        
        if (inputTokens.isEmpty()) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.COMMENT) {
            xmlTree += "<COMMENT>\n";
            tempNode = new ASTNode("COMMENT", _TokenType.COMMENT, ++counter);
            tempNode.content = inputTokens.peek().getValue();
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);

            xmlTree += inputTokens.remove().getValue()+"\n";
            
            xmlTree += "</COMMENT>\n";
        } else {
            return;
        }
        return;
    }

    private void INSTR() {
        if (inputTokens.peek().getType() == _TokenType.INPUT_GET) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                currentNode = tempNode;

            INPUT();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.OUTPUT
                || inputTokens.peek().getType() == _TokenType.TEXT_RESPONSE) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);

            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                currentNode = tempNode;

            OUTPUT();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.NUMVAR
                || inputTokens.peek().getType() == _TokenType.BOOLVAR
                || inputTokens.peek().getType() == _TokenType.STRINGV) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                currentNode = tempNode;

            ASSIGN();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CALL) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            CALL();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.WHILE) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                currentNode = tempNode;

            LOOP();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.IF) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                currentNode = tempNode;

            BRANCH();
            xmlTree += "</INSTR>\n";
        } else if (inputTokens.peek().getType() == _TokenType.HALT) {
            xmlTree += "<INSTR>\n";
            tempNode = new ASTNode("INSTR", _TokenType.INSTR, ++counter);
            tempNode.parent = currentNode;
                currentNode.children.add(tempNode);

                currentNode = tempNode;
            HALT();
            xmlTree += "</INSTR>\n";
        } else {
            throw new RuntimeException("Expected INSTR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void HALT() {
        
        if (inputTokens.remove().getType() == _TokenType.HALT) {
            xmlTree += "<HALT/>\n";
            tempNode = new ASTNode("HALT", _TokenType.HALT, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
        } else {
            throw new RuntimeException("Expected HALT, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void BRANCH() {
        
        if (inputTokens.remove().getType() == _TokenType.IF) {
            xmlTree += "<BRANCH>\n";
            tempNode = new ASTNode("BRANCH", _TokenType.BRANCH, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected PARENTHESIS, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected PARENTHESIS, got " + inputTokens.peek().getType());
            }
            if (inputTokens.remove().getType() == _TokenType.THEN) {
                xmlTree += "<THEN/>\n";
            } else {
                throw new RuntimeException("Expected PARENTHESIS, got " + inputTokens.peek().getType());
            }
            if (inputTokens.remove().getType() == _TokenType.OPENBRACKET) {
                xmlTree += "{\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            ALGO();
            if (inputTokens.remove().getType() == _TokenType.CLOSEBRACKET) {
                xmlTree += "}\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            ELSE();
            xmlTree += "</BRANCH>\n";
        } else {
            throw new RuntimeException("Expected BRANCH, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void ELSE() {
        
        if (inputTokens.peek().getType() == _TokenType.ELSE) {
            xmlTree += "<ELSE>\n";
            tempNode = new ASTNode("ELSE", _TokenType.ELSE, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            inputTokens.remove();
            if (inputTokens.remove().getType() == _TokenType.OPENBRACKET) {
                xmlTree += "{\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            ALGO();
            if (inputTokens.remove().getType() == _TokenType.CLOSEBRACKET) {
                xmlTree += "}\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ELSE>\n";
        } else {if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            return;
        }
        return;
    }

    private void LOOP() {
        currentNode = tempNode;
        
        if (inputTokens.remove().getType() == _TokenType.WHILE) {
            xmlTree += "<LOOP>\n";
            tempNode = new ASTNode("LOOP", _TokenType.LOOP, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);

            currentNode = tempNode;
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            if (inputTokens.remove().getType() == _TokenType.OPENBRACKET) {
                xmlTree += "{\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }
            ALGO();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            if (inputTokens.remove().getType() == _TokenType.CLOSEBRACKET) {
                xmlTree += "}\n";
            } else {
                throw new RuntimeException("Expected BRACKET, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</LOOP>\n";
        } else {
            throw new RuntimeException("Expected LOOP, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void CALL() {
        
        if (inputTokens.remove().getType() == _TokenType.CALL) {
            xmlTree += "<CALL>\n";
            tempNode = new ASTNode("CALL", _TokenType.CALL, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            if (inputTokens.remove().getType() == _TokenType.PROC) {
                xmlTree += "<PROC>\n";
                DIGITS();
                xmlTree += "</PROC>\n";
            } else {
                throw new RuntimeException("Expected PROC, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</CALL>\n";
        } else {
            throw new RuntimeException("Expected CALL, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void ASSIGN() {

        if (inputTokens.peek().getType() == _TokenType.NUMVAR) {
            xmlTree += "<ASSIGN>\n";
            tempNode = new ASTNode("ASSIGN", _TokenType.ASSIGN, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            NUMVAR();
            xmlTree += inputTokens.remove().getValue() + "\n";
            NUMEXPR();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ASSIGN>\n";
        } else if (inputTokens.peek().getType() == _TokenType.BOOLVAR) {
            xmlTree += "<ASSIGN>\n";
            tempNode = new ASTNode("ASSIGN", _TokenType.ASSIGN, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            BOOLVAR();
            xmlTree += inputTokens.remove().getValue() + "\n";
            BOOLEXPR();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ASSIGN>\n";
        } else if (inputTokens.peek().getType() == _TokenType.STRINGV) {
            xmlTree += "<ASSIGN>\n";
            tempNode = new ASTNode("ASSIGN", _TokenType.ASSIGN, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            STRINGV();
            xmlTree += inputTokens.remove().getValue() + "\n";
            STRI();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ASSIGN>\n";
        } else {
            throw new RuntimeException("Expected ASSIGN, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void STRI() {
        
        if (inputTokens.peek().getType() == _TokenType.STRI) {
            tempNode = new ASTNode("STRI", _TokenType.STRI, ++counter);
            tempNode.parent = currentNode;
            tempNode.content = inputTokens.peek().getValue();
            currentNode.children.add(tempNode);
            xmlTree += "<STRI>\n";
            xmlTree += inputTokens.remove().getValue() + "\n";if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</STRI>\n";
        } else {
            throw new RuntimeException("Expected STRI, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void BOOLEXPR() {
        

        if (inputTokens.peek().getType() == _TokenType.BOOLVAR) {
            BOOLVAR();
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_TRUE
                || inputTokens.peek().getType() == _TokenType.LOGIC_FALSE) {
            LOGIC();
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_NOT) {
            LOGIC();
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_AND) {
            LOGIC();
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_OR) {
            LOGIC();
        } else if (inputTokens.peek().getType() == _TokenType.CMPR_EQUAL) {
            CMPR();
        } else if (inputTokens.peek().getType() == _TokenType.CMPR_GREATERTHAN) {
            CMPR();
        } else if (inputTokens.peek().getType() == _TokenType.CMPR_LESSTHAN) {
            CMPR();
        } else {
            throw new RuntimeException("Expected BOOLEXPR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void CMPR() {
        
        if (inputTokens.peek().getType() == _TokenType.CMPR_EQUAL) {
            inputTokens.remove();
            xmlTree += "<EQUAL>\n";
            tempNode = new ASTNode("EQUAL", _TokenType.CMPR_EQUAL, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);

            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</EQUAL>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CMPR_GREATERTHAN) {
            inputTokens.remove();
            xmlTree += "<GREATERTHAN>\n";
            tempNode = new ASTNode("GREATERTHAN", _TokenType.CMPR_GREATERTHAN, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</GREATERTHAN>\n";
        } else if (inputTokens.peek().getType() == _TokenType.CMPR_LESSTHAN) {
            inputTokens.remove();
            xmlTree += "<LESSTHAN>\n";
            tempNode = new ASTNode("LESSTHAN", _TokenType.CMPR_LESSTHAN, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }
            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</LESSTHAN>\n";
        } else {
            throw new RuntimeException("Expected CMPR_EQUAL, CMPR_GREATERTHAN, or CMPR_LESSTHAN, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void LOGIC() {
        
        if (inputTokens.peek().getType() == _TokenType.LOGIC_TRUE
                || inputTokens.peek().getType() == _TokenType.LOGIC_FALSE) {
            if (inputTokens.peek().getType() == _TokenType.LOGIC_TRUE) {
                
                tempNode = new ASTNode("TRUE", _TokenType.LOGIC_TRUE, ++counter);
                tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                inputTokens.remove();
                xmlTree += "<TRUE/>\n";
            } else if (inputTokens.peek().getType() == _TokenType.LOGIC_FALSE) {
                
                tempNode = new ASTNode("FALSE", _TokenType.LOGIC_FALSE, ++counter);
                tempNode.parent = currentNode;
                currentNode.children.add(tempNode);
                inputTokens.remove();
                xmlTree += "<FALSE/>\n";
            } else {
                throw new RuntimeException("Expected LOGIC_TRUE or LOGIC_FALSE, got " + inputTokens.peek().getType());
            }
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_NOT) {
            tempNode = new ASTNode("NOT", _TokenType.LOGIC_NOT, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            inputTokens.remove();
            xmlTree += "<NOT>\n";
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }
            xmlTree += "</NOT>\n";
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_AND) {
            tempNode = new ASTNode("AND", _TokenType.LOGIC_AND, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            inputTokens.remove();
            xmlTree += "<AND>\n";
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</AND>\n";
        } else if (inputTokens.peek().getType() == _TokenType.LOGIC_OR) {
            inputTokens.remove();
            xmlTree += "<OR>\n";
            tempNode = new ASTNode("OR", _TokenType.LOGIC_OR, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }
            BOOLEXPR();
            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</OR>\n";
        } else {
            throw new RuntimeException("Expected LOGIC, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void BOOLVAR() {
        
        if (inputTokens.remove().getType() == _TokenType.BOOLVAR) {
            xmlTree += "<BOOLVAR>\n";
            tempNode = new ASTNode("BOOLVAR", _TokenType.BOOLVAR, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            DIGITS();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</BOOLVAR>\n";
        } else {
            throw new RuntimeException("Expected BOOLVAR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void NUMEXPR() {
        
        if (inputTokens.peek().getType() == _TokenType.NUMEXPR_ADDITION) {
            inputTokens.remove();
            xmlTree += "<ADDITION>\n";
            tempNode = new ASTNode("ADDITION", _TokenType.NUMEXPR_ADDITION, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }

            NUMEXPR();
            

            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }

            NUMEXPR();

            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</ADDITION>\n";
        } else if (inputTokens.peek().getType() == _TokenType.NUMEXPR_DIVISION) {
            inputTokens.remove();
            xmlTree += "<DIVISION>\n";
            tempNode = new ASTNode("DIVISION", _TokenType.NUMEXPR_DIVISION, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }

            NUMEXPR();

            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }

            NUMEXPR();

            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</DIVISION>\n";
        } else if (inputTokens.peek().getType() == _TokenType.NUMEXPR_MULTIPLICATION) {
            inputTokens.remove();
            xmlTree += "<MULTIPLICATION>\n";
            tempNode = new ASTNode("MULTIPLICATION", _TokenType.NUMEXPR_MULTIPLICATION, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            if (inputTokens.remove().getType() == _TokenType.OPENPARENTHESIS) {
                xmlTree += "(\n";
            } else {
                throw new RuntimeException("Expected OPENPARENTHESIS, got " + inputTokens.peek().getType());
            }

            NUMEXPR();
            if (inputTokens.remove().getType() == _TokenType.SEPARATOR) {
                xmlTree += ",\n";
            } else {
                throw new RuntimeException("Expected SEPARATOR, got " + inputTokens.peek().getType());
            }

            NUMEXPR();

            if (inputTokens.remove().getType() == _TokenType.CLOSEPARENTHESIS) {
                xmlTree += ")\n";
            } else {
                throw new RuntimeException("Expected CLOSEPARENTHESIS, got " + inputTokens.peek().getType());
            }if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</MULTIPLICATION>\n";
        } else if (inputTokens.peek().getType() == _TokenType.NUMVAR) {
            NUMVAR();
        } else if (inputTokens.peek().getType() == _TokenType.NEG_MINUS||Integer.parseInt(inputTokens.peek().getValue()) >= 0) {
            DECNUM();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
        } else {
            throw new RuntimeException("Expected NUMEXPR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void DECNUM() {
        if (inputTokens.peek().getType() == _TokenType.D) {
            xmlTree += "<DECNUM>\n";
            tempNode = new ASTNode("DECNUM", _TokenType.DECNUM, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            if (Integer.parseInt(inputTokens.peek().getValue()) == 0) {

                if (Integer.parseInt(inputTokens.peek().getValue()) == 0) {
                    xmlTree += inputTokens.remove().getValue() + "\n"; // 0
                } else {
                    throw new RuntimeException("Expected 0, got " + inputTokens.peek().getType());
                }

                if (inputTokens.peek().getValue().equals(".")) {
                    xmlTree += inputTokens.remove().getValue() + "\n"; // .
                } else {
                    throw new RuntimeException("Expected ., got " + inputTokens.peek().getType());
                }

                if (Integer.parseInt(inputTokens.peek().getValue()) == 0) {
                    xmlTree += inputTokens.remove().getValue() + "\n"; // 0
                } else {
                    throw new RuntimeException("Expected 0, got " + inputTokens.peek().getType());
                }

                if (Integer.parseInt(inputTokens.peek().getValue()) == 0) {
                    xmlTree += inputTokens.remove().getValue() + "\n"; // 0
                } else {
                    throw new RuntimeException("Expected 0, got " + inputTokens.peek().getType());
                }
                tempNode.content = "0.00";
            } else if (Integer.parseInt(inputTokens.peek().getValue()) >= 0) {
                POS();
                if (currentNode.parent != null) {
                    currentNode = currentNode.parent;
                }
            } else {
                throw new RuntimeException("Expected DECNUM, got " + inputTokens.peek().getType());
            }
            xmlTree += "</DECNUM>\n";
        } else if (inputTokens.peek().getType() == _TokenType.NEG_MINUS) {
            xmlTree += "<DECNUM>\n";
            tempNode = new ASTNode("DECNUM", _TokenType.DECNUM, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            NEG();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</DECNUM>\n";
        } else {
            throw new RuntimeException("Expected DECNUM, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void NEG() {
        
        if (inputTokens.peek().getType() == _TokenType.NEG_MINUS) {
            tempNode = new ASTNode("NEG", _TokenType.NEG, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;


            if (inputTokens.peek().getValue().equals("-")) {
                xmlTree += inputTokens.remove().getValue() + "\n"; // -
            } else {
                throw new RuntimeException("Expected -, got " + inputTokens.peek().getType());
            }

            POS();
if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
        } else {
            throw new RuntimeException("Expected NEG, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void POS() {
        
        if (inputTokens.peek().getType() == _TokenType.D) {
            tempNode = new ASTNode("POS", _TokenType.POS, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            INT();
            tempNode.content += ".";
            if (inputTokens.peek().getValue().equals(".")) {
                xmlTree += inputTokens.remove().getValue() + "\n"; // .
            } else {
                throw new RuntimeException("Expected ., got " + inputTokens.peek().getType());
            }

            D();

            D();
        } else {
            throw new RuntimeException("Expected POS, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void INT() {
        
        if (inputTokens.peek().getType() == _TokenType.D) {
            tempNode = new ASTNode("D", _TokenType.D, ++counter);
            tempNode.content = inputTokens.peek().getValue();
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            xmlTree += inputTokens.remove().getValue() + "\n"; // 1-9
            MORE();
        } else {
            throw new RuntimeException("Expected INT, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void OUTPUT() {
        
        if (inputTokens.peek().getType() == _TokenType.OUTPUT) {
            xmlTree += "<OUTPUT>\n";
            inputTokens.remove();
            xmlTree += "<VALUE>\n";
            
            tempNode = new ASTNode("OUTPUT_VALUE", _TokenType.OUTPUT, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            NUMVAR();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</VALUE>\n";
            xmlTree += "</OUTPUT>\n";
        } else if (inputTokens.peek().getType() == _TokenType.TEXT_RESPONSE) {
            xmlTree += "<OUTPUT>\n";
            inputTokens.remove();
            xmlTree += "<TEXT_RESPONSE>\n";

            tempNode = new ASTNode("OUTPUT_TEXT_RESPONSE", _TokenType.OUTPUT, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            STRINGV();
if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</TEXT_RESPONSE>\n";
            xmlTree += "</OUTPUT>\n";
        } else {
            throw new RuntimeException("Expected OUTPUT, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void STRINGV() {
        if (inputTokens.remove().getType() == _TokenType.STRINGV) {
            xmlTree += "<STRINGV>\n";
            tempNode = new ASTNode("STRINGV", _TokenType.STRINGV, ++counter);
            tempNode.content = inputTokens.peek().getValue();
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            DIGITS();if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }

            xmlTree += "</STRINGV>\n";
        } else {
            throw new RuntimeException("Expected STRINGV, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void INPUT() {
        
        if (inputTokens.remove().getType() == _TokenType.INPUT_GET) {
            xmlTree += "<INPUT>\n";
            xmlTree += "<GET>\n";

            tempNode = new ASTNode("INPUT_GET", _TokenType.INPUT, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;

            NUMVAR();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</GET>\n";
            xmlTree += "</INPUT>\n";
        } else {
            throw new RuntimeException("Expected INPUT, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void NUMVAR() {
        if (inputTokens.remove().getType() == _TokenType.NUMVAR) {
            xmlTree += "<NUMVAR>\n";
            tempNode = new ASTNode("NUMVAR", _TokenType.NUMVAR, ++counter);
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            currentNode = tempNode;
            DIGITS();
            if (currentNode.parent != null) {
                currentNode = currentNode.parent;
            }
            xmlTree += "</NUMVAR>\n";
        } else {
            throw new RuntimeException("Expected NUMVAR, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void DIGITS() {
        
        if (inputTokens.peek().getType() == _TokenType.D) {
            
            D();
            MORE();
        } else {
            throw new RuntimeException("Expected D, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void D() {
        if (inputTokens.peek().getType() == _TokenType.D) {
            tempNode = new ASTNode("D", _TokenType.D, ++counter);
            tempNode.content = inputTokens.peek().getValue();
            tempNode.parent = currentNode;
            currentNode.children.add(tempNode);
            xmlTree += inputTokens.remove().getValue() + "\n";
        } else {
            throw new RuntimeException("Expected D, got " + inputTokens.peek().getType());
        }
        return;
    }

    private void MORE() {
        if (inputTokens.isEmpty()) {
            
            return;
        } else if (inputTokens.peek().getType() == _TokenType.D) {
            DIGITS();
            return;
        } else if (inputTokens.peek().getType() == _TokenType.OPENBRACKET) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.COMMENT) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.DIVIDER) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.ASSIGN) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.SEPARATOR) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.CLOSEBRACKET) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.CLOSEPARENTHESIS) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.OPENPARENTHESIS) {
            return;
        } else if (inputTokens.peek().getType() == _TokenType.POS_FLOATPOINT) {
            return;
        } else {
            throw new RuntimeException("Expected D, got " + inputTokens.peek().getType());
        }
    }
}
