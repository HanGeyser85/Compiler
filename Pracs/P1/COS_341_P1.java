
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * COS_341_P1
 */
public class COS_341_P1 {

	private static NFA nfa;

	private static DFA dfa;

	public static void main(String[] args) {
		File inputFile = new File("Input.txt");
		String name = "";
		Scanner myReader;
		try {
			myReader = new Scanner(inputFile);
			while (myReader.hasNextLine()) {
				name = myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		setNfa(RegularExpression.generateNFA(name));

		setDfa(NFA_To_DFA.generate(nfa));

		FileWriter myWriter;
		try {
			myWriter = new FileWriter("test.xml");
			myWriter.write(getDfa().toString());
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setDfa(DFA generateDFA) {
		dfa = generateDFA;
	}

	public static NFA getNfa() {
		return nfa;
	}

	public static void setNfa(NFA nfa) {
		COS_341_P1.nfa = nfa;
	}

	public static DFA getDfa() {
		return dfa;
	}
}
