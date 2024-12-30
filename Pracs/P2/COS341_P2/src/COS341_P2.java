import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class COS341_P2 {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("INPUT.txt");
        Scanner inputReader = new Scanner(inputFile);
        FileWriter outputWriterParseTree = new FileWriter("OUTPUT_PARSE_TREE.xml");
        FileWriter outputWriterAST = new FileWriter("OUTPUT_AST.xml");
        String inputSPL = "";
        Lexer lexer = new Lexer();

        while (inputReader.hasNextLine()) {
            inputSPL += inputReader.nextLine(); 
        }

        List<Token> test =  lexer.lexicalAnalysis(inputSPL);
        System.out.println("Lexing finished");

        Parser parser = new Parser();
        
        try {
            String tree = parser.SyntacticalAnalysis(test);
            System.out.println("Parsing finished");
            outputWriterParseTree.write(tree);
            outputWriterAST.write(parser.tree.root.toString());
        } catch (Exception e) {
            outputWriterParseTree.write(e.getMessage());
            outputWriterAST.write(e.getMessage());
        }

        outputWriterParseTree.close();
        outputWriterAST.close();
        inputReader.close();
    }
}
