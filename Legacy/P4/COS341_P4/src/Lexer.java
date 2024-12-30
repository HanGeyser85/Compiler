import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private List<Token> lexicalTokens = new ArrayList<Token>();

    public List<Token> lexicalAnalysis(String input) throws Exception {
        String builder = "";

        System.out.println(input);

        for (int i = 0; i < input.length(); ) {
            builder += input.charAt(i);
            
            if (builder.equals(" ")) {
                builder = "";
                i++;                
            } 
            else if (builder.equals("\n")) {
                builder = "";
                i++;
            } 
            else if (builder.equals("\t")) {
                builder = "";
                i++;
            } 
            else if (builder.equals("\r")) {
                builder = "";
                i++;
            }
            else if (builder.equals(",")) {
                Token tempToken = new Token(builder, _TokenType.SEPARATOR);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("p")) {
                Token tempToken = new Token(builder, _TokenType.PROC);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("1")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("2")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("3")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("4")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("5")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("6")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("7")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("8")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("9")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("0")) {
                Token tempToken = new Token(builder, _TokenType.D);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals(";")) {
                Token tempToken = new Token(builder, _TokenType.DIVIDER);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("h")) {
                Token tempToken = new Token(builder, _TokenType.HALT);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            }
            else if (builder.equals("c")) {
                Token tempToken = new Token(builder, _TokenType.CALL);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("w")) {
                Token tempToken = new Token(builder, _TokenType.WHILE);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("i")) {
                Token tempToken = new Token(builder, _TokenType.IF);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("t")) {
                Token tempToken = new Token(builder, _TokenType.THEN);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("e")) {
                Token tempToken = new Token(builder, _TokenType.ELSE);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("n")) {
                Token tempToken = new Token(builder, _TokenType.NUMVAR);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("b")) {
                Token tempToken = new Token(builder, _TokenType.BOOLVAR);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("s")) {
                Token tempToken = new Token(builder, _TokenType.STRINGV);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("a")) {
                Token tempToken = new Token(builder, _TokenType.NUMEXPR_ADDITION);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("m")) {
                Token tempToken = new Token(builder, _TokenType.NUMEXPR_MULTIPLICATION);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("d")) {
                Token tempToken = new Token(builder, _TokenType.NUMEXPR_DIVISION);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("-")) {
                Token tempToken = new Token(builder, _TokenType.NEG_MINUS);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals(".")) {
                Token tempToken = new Token(builder, _TokenType.POS_FLOATPOINT);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("T")) {
                Token tempToken = new Token(builder, _TokenType.LOGIC_TRUE);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("F")) {
                Token tempToken = new Token(builder, _TokenType.LOGIC_FALSE);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("^")) {
                Token tempToken = new Token(builder, _TokenType.LOGIC_AND);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("!")) {
                Token tempToken = new Token(builder, _TokenType.LOGIC_NOT);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("v")) {
                Token tempToken = new Token(builder, _TokenType.LOGIC_OR);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("E")) {
                Token tempToken = new Token(builder, _TokenType.CMPR_EQUAL);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("<")) {
                Token tempToken = new Token(builder, _TokenType.CMPR_LESSTHAN);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals(">")) {
                Token tempToken = new Token(builder, _TokenType.CMPR_GREATERTHAN);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }
                
                builder = "";
                i++;
            } 
            else if (builder.equals("\"")) {
                String SPLString = "";
                SPLString += input.charAt(i++); // First quote

                for (int j = 0; j < 15; j++) {
                    SPLString += input.charAt(i++);
                }

                SPLString += input.charAt(i++); // Last quote

                Token tempToken = new Token(SPLString, _TokenType.STRI);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
            } 
            else if (builder.equals("*")) {
                String SPLString = "";
                SPLString += input.charAt(i++); // First quote

                for (int j = 0; j < 15; j++) {
                    SPLString += input.charAt(i++);
                }

                SPLString += input.charAt(i++); // Last quote

                Token tempToken = new Token(SPLString, _TokenType.COMMENT);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
            } 
            else if (builder.equals("g")) {
                Token tempToken = new Token(builder, _TokenType.INPUT_GET);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("o")) {
                Token tempToken = new Token(builder, _TokenType.OUTPUT);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("r")) {
                Token tempToken = new Token(builder, _TokenType.TEXT_RESPONSE);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("{")) {
                Token tempToken = new Token(builder, _TokenType.OPENBRACKET);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("}")) {
                Token tempToken = new Token(builder, _TokenType.CLOSEBRACKET);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals("(")) {
                Token tempToken = new Token(builder, _TokenType.OPENPARENTHESIS);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals(")")) {
                Token tempToken = new Token(builder, _TokenType.CLOSEPARENTHESIS);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (builder.equals(":=")) {
                Token tempToken = new Token(builder, _TokenType.ASSIGN);

                if (lexicalTokens.isEmpty()) {
                    lexicalTokens.add(tempToken);
                } else {
                    lexicalTokens.add(tempToken);
                    lexicalTokens.get(lexicalTokens.size() - 1).setNext(tempToken);
                }

                builder = "";
                i++;
            } 
            else if (i == input.length()-1 && builder != "") {
                throw new Exception("Syntax error");
            }
            else {
                i++;
            }
        }

        return lexicalTokens;
    }

    public List<Token> getTokens() {
        return lexicalTokens;
    }

    public void setTokens(List<Token> tokens) {
        this.lexicalTokens = tokens;
    }

    public String toString() {
        String output = "";
        for (Token token : (Token[]) lexicalTokens.toArray()) {
            output += "Token {" + token.toString() + "}\n";
        }
        
        return output;                                              
    }
}
