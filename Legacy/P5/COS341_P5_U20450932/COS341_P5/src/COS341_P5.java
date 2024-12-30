import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class COS341_P5 {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("INPUT.txt");
        Scanner inputReader = new Scanner(inputFile);
        FileWriter outputWriterParseTree = new FileWriter("OUTPUT_PARSE_TREE.xml");
        FileWriter outputWriterAST = new FileWriter("OUTPUT_AST.xml");
        FileWriter outputWriterSymbolTable = new FileWriter("OUTPUT_SYMBOL_TABLE.html");
        FileWriter outputIntermediate = new FileWriter("OUTPUT.txt");
        String inputSPL = "";
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        // vTable variableTable = new vTable();
        // sTable scope = new sTable();
        List<Token> test = new ArrayList<Token>();

        while (inputReader.hasNextLine()) {
            inputSPL += inputReader.nextLine(); 
        }

        try {
            test =  lexer.lexicalAnalysis(inputSPL);
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
            IntermediateCodeGenerator generator = new IntermediateCodeGenerator();
            generator.generate(parser.tree.root);
            outputIntermediate.write(generator.toString());
            outputIntermediate.close();
            System.out.println("Intermediate Code Generation finished");
        } catch (Exception e) {
            outputIntermediate.close();
            inputReader.close();
            outputWriterSymbolTable.close();
            return;
        }

        // try {
        //     variableTable.populateTable(parser.tree.root);
        //     scope.variableTable = variableTable;
        //     scope.tree = parser.tree;
        //     scope.defineMainScope(parser.tree.root);
        //     scope.addSiblings();
        //     scope.semanticAnalysis(parser.tree.root);
        //     System.out.println("Semantic Analysis finished");
        //     outputWriterSymbolTable.write(variableTable.toHTML());
        //     outputWriterSymbolTable.write("<h1>Symbol Table</h1>");
        //     outputWriterSymbolTable.write(scope.toHTML());
        //     outputWriterSymbolTable.write(scope.checkForCalls());
        //     outputWriterSymbolTable.write(scope.checkIfCalledProcExists());
        //     outputWriterSymbolTable.write("<p>"+scope.synAl+"</p>");
        // } catch (Exception e) {
        //     try {
        //         outputWriterSymbolTable.write(e.getMessage());
        //     } catch (Exception ee) {
        //         outputWriterSymbolTable.write(ee.getMessage());
        //     }
            
        //     inputReader.close();
        //     outputWriterSymbolTable.close();
        //     return;
        // }

        outputWriterParseTree.close();
        outputWriterAST.close();
        outputWriterSymbolTable.close();
        inputReader.close();
    }
}
