
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class COMPDI_V1 {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        File inputFile = new File("INPUT.txt");
        try (Scanner inputReader = new Scanner(inputFile); FileWriter outputWriterParseTree = new FileWriter("OUTPUT_PARSE_TREE.xml")) {
            FileWriter outputWriterSymbolTable;
            try (FileWriter outputWriterAST = new FileWriter("OUTPUT_AST.xml")) {
                outputWriterSymbolTable = new FileWriter("OUTPUT_SYMBOL_TABLE.html");
                FileWriter outputIntermediate = new FileWriter("OUTPUT.txt");
                String inputSPL = "";
                Lexer lexer = new Lexer();
                Parser parser = new Parser();
                vTable variableTable = new vTable();
                fTable functionTable = new fTable();
                sTable symbolTable = new sTable();
                List<Token> test;

                while (inputReader.hasNextLine()) {
                    inputSPL += inputReader.nextLine();
                }

                try {
                    test = lexer.lexicalAnalysis(inputSPL);
                    System.out.println("Lexing finished");
                } catch (Exception e) {
                    outputWriterParseTree.write(e.getMessage());
                    outputWriterAST.write(e.getMessage());
                    outputWriterParseTree.close();
                    outputWriterAST.close();
                    inputReader.close();
                    outputWriterSymbolTable.close();
                    return;
                }
                try {
                    String tree = parser.SyntacticalAnalysis(test);
                    System.out.println("Parsing finished");
                    outputWriterParseTree.write(tree);
                    outputWriterAST.write(parser.tree.root.toString());
                    outputWriterParseTree.close();
                    outputWriterAST.close();
                } catch (IOException e) {
                    outputWriterParseTree.write(e.getMessage());
                    outputWriterAST.write(e.getMessage());
                    outputWriterParseTree.close();
                    outputWriterAST.close();
                    inputReader.close();
                    outputWriterSymbolTable.close();
                    return;
                }
                try {
                    variableTable.populateTable(parser.tree.root);
                    functionTable.populateTable(parser.tree.root);
                    symbolTable.functionTable = functionTable;
                    symbolTable.variableTable = variableTable;
                    symbolTable.tree = parser.tree;
                    symbolTable.defineMainScope(parser.tree.root);
                    symbolTable.addSiblings();
                    symbolTable.semanticAnalysis(parser.tree.root);
                    System.out.println("Semantic Analysis finished");
                    outputWriterSymbolTable.write(variableTable.toHTML());
                    outputWriterSymbolTable.write(functionTable.toHTML());
                    outputWriterSymbolTable.write("<h1>Symbol Table</h1>");
                    outputWriterSymbolTable.write(symbolTable.toHTML());
                    outputWriterSymbolTable.write("<h1>Calls</h1>");
                    outputWriterSymbolTable.write(symbolTable.checkForCalls());
                    outputWriterSymbolTable.write(symbolTable.checkIfCalledProcExists());
                    outputWriterSymbolTable.write("<p>" + symbolTable.synAl + "</p>");
                } catch (IOException e) {
                    try {
                        outputWriterSymbolTable.write(e.getMessage());
                    } catch (IOException ee) {
                        outputWriterSymbolTable.write(ee.getMessage());
                    }

                    inputReader.close();
                    outputWriterSymbolTable.close();
                    return;
                }
                try {
                    IntermediateCodeGenerator generator = new IntermediateCodeGenerator();
                    generator.variableTable = variableTable;
                    generator.table = functionTable;
                    generator.generate(parser.tree.root);
                    outputIntermediate.write(generator.toString());
                    outputIntermediate.close();
                    System.out.println("Intermediate Code Generation finished");
                } catch (IOException e) {
                    outputIntermediate.close();
                    inputReader.close();
                }
            }
        }
    }
}
