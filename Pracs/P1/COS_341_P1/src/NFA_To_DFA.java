import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class NFA_To_DFA {
	public static List<Character> input = new ArrayList<Character>();

	private static void setInput() {
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
	}

	public static Set<State> epsilonClosure(Set<State> states) {
		Set<State> retSet = new HashSet<State>();
		Stack<State> stateStack = new Stack<State>();

		retSet.addAll(states);

		Iterator<State> it = states.iterator();

		while (it.hasNext()) {
			stateStack.push(it.next());
		}

		while (!stateStack.isEmpty()) {
			State st = stateStack.pop();
			List<State> epsilonStates = st.getAllTransitions('>');
			Iterator<State> it2 = epsilonStates.iterator();

			while (it2.hasNext()) {
				State temp = it2.next();
				if (!retSet.contains(temp)) {
					retSet.add(temp);
					stateStack.push(temp);
				} else {
					continue;
				}
			}
		}

		return retSet;
	}

	public static DFA generate(NFA nfa) {
		boolean ifFound = false;
		Integer iCount = 0;
		DFA ret = new DFA();
		Set<State> nfaStart = new HashSet<State>();
		setInput();

		nfaStart.add(nfa.getNfa().getFirst());
		State newStartState = new State(epsilonClosure(nfaStart), iCount++);

		ret.getDfa().addLast(newStartState);

		LinkedList<State> toBeProcessedList = new LinkedList<State>();
		toBeProcessedList.add(newStartState);

		while (toBeProcessedList.peek() != null) {
			State st = toBeProcessedList.poll();
			for (Character c : input) {
				System.out.println("Processing: " + st.getStateID() + " with " + c);
				Set<State> temp = new HashSet<State>();
				for (State s : st.getStates()) {
					temp.addAll(s.getAllTransitions(c));
				}
				temp = epsilonClosure(temp);
				State newState = new State(temp, iCount);

				ifFound = false;

				for (State state : ret.getDfa()) {
					if (state.getStates().containsAll(newState.getStates())) {
						st.addTransition(state, c);
						ifFound = true;
						break;
					}
				}

				if (!ifFound) {
					iCount++;
					ret.getDfa().add(newState);
					toBeProcessedList.addLast(newState);
					st.addTransition(newState, c);
				}
			}
		}

		try {
			FileWriter myWriter = new FileWriter("test2.xml");
			myWriter.write(ret.toString());
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Minimize the DFA

		Set<State> acceptingSet = new HashSet<State>();
		Set<State> nonAcceptingSet = new HashSet<State>();

		for (State s : ret.getDfa()) {
			if (s.isAcceptState()) {
				acceptingSet.add(s);
			} else {
				nonAcceptingSet.add(s);
			}
		}

		Set<Set<State>> minPartition = new HashSet<Set<State>>();
		ArrayList<ArrayList<State>> transitionMatrixAccepting = new ArrayList<ArrayList<State>>();
		ArrayList<ArrayList<State>> transitionMatrixNonAccepting = new ArrayList<ArrayList<State>>();
		minPartition.add(acceptingSet);
		minPartition.add(nonAcceptingSet);

		boolean isMinimized = false;

		int numberofstates = 0;

		for (State set2 : acceptingSet) {
			for (Character c : input) {
				transitionMatrixAccepting.add(numberofstates++,set2.getAllTransitions(c));
			}
			numberofstates = 0;
		}

		numberofstates = 0;

		for (State set2 : nonAcceptingSet) {
			for (Character c : input) {
				transitionMatrixNonAccepting.add(numberofstates++, set2.getAllTransitions(c));
			}
			numberofstates = 0;
		}

		numberofstates = 0;

		Iterator<Set<State>> it2 = minPartition.iterator();
		while (!isMinimized) {
			Set<Set<State>> newPartition = new HashSet<Set<State>>();
			for (Character c : input) {
				while (it2.hasNext()) {

					Set<State> help = it2.next();
					if (help.size() == 1) {
						continue;
					}
	
					Set<State> temp = new HashSet<State>();
					Iterator<State> it = help.iterator();
					temp.add(it.next());
	
					while (it.hasNext()) {
						State st = it.next();
						boolean isSame = true;
						for (State s : temp) {
							if (!s.getAllTransitions(c).containsAll(st.getAllTransitions(c))) {
								isSame = false;
								break;
							}
						}
	
						if (isSame) {
							temp.add(st);
						} else {
							newPartition.add(temp);
							temp = new HashSet<State>();
							temp.add(st);
						}
					}
	
					newPartition.add(temp);
				}
	
				if (newPartition.size() == minPartition.size()) {
					isMinimized = true;
				} else {
					minPartition = newPartition;
				}
			}
		}

		DFA newdfa = new DFA();

		boolean found = false;

		for (Set<State> set : minPartition) {
			State newstate = new State(set, iCount++);
			for (Character c : input) {
				Iterator it = set.iterator();

				while (it.hasNext()) {
					State st = (State) it.next();
					Set<State> temp = new HashSet<State>();
					temp.addAll(st.getAllTransitions(c));

					for (Set<State> next : minPartition) {
						if (next.containsAll(temp)) {
							for (State state : newdfa.getDfa()) {
								if (state.getStates().containsAll(next)) {
									newstate.addTransition(state, c);
									found = true;
									break;
								}
							}
							if (!found) {
								State tempNew = new State(next, iCount++);
								newstate.addTransition(tempNew, c);
								found = false;
							}
							break;
						}
					}
				}
			}
			newdfa.getDfa().add(newstate);
		}

		return newdfa;
	}
}
