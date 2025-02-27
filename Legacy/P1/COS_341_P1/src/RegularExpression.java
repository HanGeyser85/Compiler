import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class RegularExpression {
	private static int stateID = 0;

	private static Stack<NFA> stackNfa = new Stack<NFA>();
	private static Stack<Character> operator = new Stack<Character>();

	private static Set<State> set1 = new HashSet<State>();
	private static Set<State> set2 = new HashSet<State>();

	// Set of inputs
	private static Set<Character> input = new HashSet<Character>();

	// Generates NFA using the regular expression
	public static NFA generateNFA(String regular) {
		// Generate regular expression with the concatenation
		regular = AddConcat(regular);

		// Only inputs available
		input.add('a');
		input.add('b');
		input.add('c');
		input.add('d');
		input.add('e');
		input.add('f');
		input.add('g');
		input.add('h');
		input.add('i');
		input.add('j');
		input.add('k');
		input.add('l');
		input.add('m');
		input.add('n');
		input.add('o');
		input.add('p');
		input.add('q');
		input.add('r');
		input.add('s');
		input.add('t');
		input.add('u');
		input.add('v');
		input.add('w');
		input.add('x');
		input.add('y');
		input.add('z');
		input.add('0');
		input.add('1');
		input.add('2');
		input.add('3');
		input.add('4');
		input.add('5');
		input.add('6');
		input.add('7');
		input.add('8');
		input.add('9');
		input.add('A');
		input.add('B');
		input.add('C');
		input.add('D');
		input.add('E');
		input.add('F');
		input.add('G');
		input.add('H');
		input.add('I');
		input.add('J');
		input.add('K');
		input.add('L');
		input.add('M');
		input.add('N');
		input.add('O');
		input.add('P');
		input.add('Q');
		input.add('R');
		input.add('S');
		input.add('T');
		input.add('U');
		input.add('V');
		input.add('W');
		input.add('X');
		input.add('Y');
		input.add('Z');

		stackNfa.clear();
		operator.clear();

		for (int i = 0; i < regular.length(); i++) {

			if (isInputCharacter(regular.charAt(i))) {
				pushStack(regular.charAt(i));

			} else if (operator.isEmpty()) {
				operator.push(regular.charAt(i));

			} else if (regular.charAt(i) == '(') {
				operator.push(regular.charAt(i));

			} else if (regular.charAt(i) == ')') {
				while (operator.get(operator.size() - 1) != '(') {
					doOperation();
				}

				// Pop the '(' left parenthesis
				operator.pop();

			} else {
				while (!operator.isEmpty() &&
						Priority(regular.charAt(i), operator.get(operator.size() - 1))) {
					doOperation();
				}
				operator.push(regular.charAt(i));
			}
		}

		// Clean the remaining elements in the stack
		while (!operator.isEmpty()) {
			doOperation();
		}

		// Get the complete nfa
		NFA completeNfa = stackNfa.pop();

		// add the accpeting state to the end of NFA
		completeNfa.getNfa().get(completeNfa.getNfa().size() - 1).setAcceptState(true);

		// return the nfa
		return completeNfa;
	}

	public static DFA generateDFA(NFA nfa) {
		// Create a new DFA
		DFA dfa = new DFA();

		// Clearing all the states ID for the DFA
		stateID = 0;

		// Create an arrayList of unprocessed States
		LinkedList<State> unprocessed = new LinkedList<State>();

		// Create sets
		set1 = new HashSet<State>();
		set2 = new HashSet<State>();

		// Add first state to the set1
		set1.add(nfa.getNfa().getFirst());

		// Run the first remove Epsilon the get states that
		// run with epsilon
		removeEpsilonTransition();

		// Create the start state of DFA and add to the stack
		State dfaStart = new State(set2, stateID++);

		dfa.getDfa().addLast(dfaStart);
		unprocessed.addLast(dfaStart);

		// While there is elements in the stack
		while (!unprocessed.isEmpty()) {
			// Process and remove last state in stack
			State state = unprocessed.removeLast();

			// Check if input symbol
			for (Character symbol : input) {
				set1 = new HashSet<State>();
				set2 = new HashSet<State>();

				moveStates(symbol, state.getStates(), set1);
				removeEpsilonTransition();

				boolean found = false;
				State st = null;

				for (int i = 0; i < dfa.getDfa().size(); i++) {
					st = dfa.getDfa().get(i);

					if (st.getStates().containsAll(set2)) {
						found = true;
						break;
					}
				}

				// Not in the DFA set, add it
				if (!found) {
					State p = new State(set2, stateID++);
					unprocessed.addLast(p);
					dfa.getDfa().addLast(p);
					state.addTransition(p, symbol);

					// Already in the DFA set
				} else {
					state.addTransition(st, symbol);
				}
			}
		}
		// Return the complete DFA
		return dfa;

	}

	// Priority of operands
	private static boolean Priority(char first, Character second) {
		if (first == second) {
			return true;
		}
		if (first == '*') {
			return false;
		}
		if (second == '*') {
			return true;
		}
		if (first == '.') {
			return false;
		}
		if (second == '.') {
			return true;
		}
		if (first == '|') {
			return false;
		} else {
			return true;
		}
	}

	// Do the desired operation based on the top of stackNfa
	private static void doOperation() {
		if (RegularExpression.operator.size() > 0) {
			char charAt = operator.pop();
			switch (charAt) {
				case ('|'):
					union();
					break;

				case ('.'):
					concatenation();
					break;

				case ('*'):
					star();
					break;

				default:
					System.out.println("Unknown Symbol !");
					System.exit(1);

			}
		}
	}

	// Do the star operation
	private static void star() {
		// Retrieve top NFA from Stack
		NFA nfa = stackNfa.pop();

		// Create states for star operation
		State start = new State(stateID++);
		State end = new State(stateID++);

		// Add transition to start and end state
		start.addTransition(end, '>');
		start.addTransition(nfa.getNfa().getFirst(), '>');

		nfa.getNfa().getLast().addTransition(end, '>');
		nfa.getNfa().getLast().addTransition(nfa.getNfa().getFirst(), '>');

		nfa.getNfa().addFirst(start);
		nfa.getNfa().addLast(end);

		// Put nfa back in the stackNfa
		stackNfa.push(nfa);
	}

	// Do the concatenation operation
	private static void concatenation() {
		// retrieve nfa 1 and 2 from stackNfa
		NFA nfa2 = stackNfa.pop();
		NFA nfa1 = stackNfa.pop();

		// Add transition to the end of nfa 1 to the begin of nfa 2
		// the transition uses empty string
		nfa1.getNfa().getLast().addTransition(nfa2.getNfa().getFirst(), '>');

		// Add all states in nfa2 to the end of nfa1
		for (State s : nfa2.getNfa()) {
			nfa1.getNfa().addLast(s);
		}

		// Put nfa back to stackNfa
		stackNfa.push(nfa1);
	}

	// Makes union of sub NFA 1 with sub NFA 2
	private static void union() {
		// Load two NFA in stack into variables
		NFA nfa2 = stackNfa.pop();
		NFA nfa1 = stackNfa.pop();

		// Create states for union operation
		State start = new State(stateID++);
		State end = new State(stateID++);

		// Set transition to the begin of each subNFA with empty string
		start.addTransition(nfa1.getNfa().getFirst(), '>');
		start.addTransition(nfa2.getNfa().getFirst(), '>');

		// Set transition to the end of each subNfa with empty string
		nfa1.getNfa().getLast().addTransition(end, '>');
		nfa2.getNfa().getLast().addTransition(end, '>');

		// Add start to the end of each nfa
		nfa1.getNfa().addFirst(start);
		nfa2.getNfa().addLast(end);

		// Add all states in nfa2 to the end of nfa1
		// in order
		for (State s : nfa2.getNfa()) {
			nfa1.getNfa().addLast(s);
		}
		// Put NFA back to stack
		stackNfa.push(nfa1);
	}

	// Push input symbol into stackNfa
	private static void pushStack(char symbol) {
		State s0 = new State(stateID++);
		State s1 = new State(stateID++);

		// add transition from 0 to 1 with the symbol
		s0.addTransition(s1, symbol);

		// new temporary NFA
		NFA nfa = new NFA();

		// Add states to NFA
		nfa.getNfa().addLast(s0);
		nfa.getNfa().addLast(s1);

		// Put NFA back to stackNfa
		stackNfa.push(nfa);
	}

	// add "." when is concatenation between to symbols that
	// concatenates to each other
	private static String AddConcat(String regular) {
		String newRegular = new String("");

		for (int i = 0; i < regular.length() - 1; i++) {
			if (isInputCharacter(regular.charAt(i)) && isInputCharacter(regular.charAt(i + 1))) {
				newRegular += regular.charAt(i) + ".";

			} else if (isInputCharacter(regular.charAt(i)) && regular.charAt(i + 1) == '(') {
				newRegular += regular.charAt(i) + ".";

			} else if (regular.charAt(i) == ')' && isInputCharacter(regular.charAt(i + 1))) {
				newRegular += regular.charAt(i) + ".";

			} else if (regular.charAt(i) == '*' && isInputCharacter(regular.charAt(i + 1))) {
				newRegular += regular.charAt(i) + ".";

			} else if (regular.charAt(i) == '*' && regular.charAt(i + 1) == '(') {
				newRegular += regular.charAt(i) + ".";

			} else if (regular.charAt(i) == ')' && regular.charAt(i + 1) == '(') {
				newRegular += regular.charAt(i) + ".";

			} else {
				newRegular += regular.charAt(i);
			}
		}
		newRegular += regular.charAt(regular.length() - 1);
		return newRegular;
	}

	// Return true if is part of the automata Language else is false
	private static boolean isInputCharacter(char charAt) {
		switch (charAt) {
			case 'a':
				return true;

			case 'b':
				return true;

			case 'c':
				return true;

			case 'd':
				return true;

			case 'e':
				return true;

			case 'f':
				return true;

			case 'g':
				return true;

			case 'h':
				return true;

			case 'i':
				return true;

			case 'j':
				return true;

			case 'k':
				return true;

			case 'l':
				return true;

			case 'm':
				return true;

			case 'n':
				return true;

			case 'o':
				return true;

			case 'p':
				return true;

			case 'q':
				return true;

			case 'r':
				return true;

			case 's':
				return true;

			case 't':
				return true;

			case 'u':
				return true;

			case 'v':
				return true;

			case 'w':
				return true;

			case 'x':
				return true;

			case 'y':
				return true;

			case 'z':
				return true;

			case 'A':
				return true;

			case 'B':
				return true;

			case 'C':
				return true;

			case 'D':

				return true;

			case 'E':
				return true;

			case 'F':
				return true;

			case 'G':
				return true;

			case 'H':
				return true;

			case 'I':
				return true;

			case 'J':
				return true;

			case 'K':
				return true;

			case 'L':
				return true;

			case 'M':
				return true;

			case 'N':
				return true;

			case 'O':
				return true;

			case 'P':
				return true;

			case 'Q':
				return true;

			case 'R':
				return true;

			case 'S':
				return true;

			case 'T':
				return true;

			case 'U':
				return true;

			case 'V':
				return true;

			case 'W':
				return true;

			case 'X':
				return true;

			case 'Y':
				return true;

			case 'Z':
				return true;

			case '0':
				return true;

			case '1':
				return true;

			case '2':
				return true;

			case '3':
				return true;

			case '4':
				return true;

			case '5':
				return true;

			case '6':
				return true;

			case '7':
				return true;

			case '8':
				return true;

			case '9':
				return true;

			default:
				return false;
		}
	}

	// Remove the epsilon transition from states
	private static void removeEpsilonTransition() {
		Stack<State> stack = new Stack<State>();
		set2 = set1;

		for (State st : set1) {
			stack.push(st);
		}

		while (!stack.isEmpty()) {
			State st = stack.pop();

			ArrayList<State> epsilonStates = st.getAllTransitions('>');

			for (State p : epsilonStates) {
				// Check p is in the set otherwise Add
				if (!set2.contains(p)) {
					set2.add(p);
					stack.push(p);
				}
			}
		}
	}

	// Move states based on input symbol
	private static void moveStates(Character c, Set<State> states, Set<State> set) {
		ArrayList<State> temp = new ArrayList<State>();

		for (State st : states) {
			temp.add(st);
		}
		for (State st : temp) {
			ArrayList<State> allStates = st.getAllTransitions(c);

			for (State p : allStates) {
				set.add(p);
			}
		}
	}
}
