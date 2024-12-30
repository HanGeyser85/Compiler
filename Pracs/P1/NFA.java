import java.util.LinkedList;

public class NFA {
	private LinkedList<State> nfa;

	public NFA() {
		this.setNfa(new LinkedList<State>());
		this.getNfa().clear();
	}

	public LinkedList<State> getNfa() {
		return nfa;
	}

	public void setNfa(LinkedList<State> nfa) {
		this.nfa = nfa;
	}

	@Override
	public String toString() {
		String retString = "";
        retString += "<Transitions>\n";
		for (State state : this.getNfa()) {
            retString += "<State-"+state.getStateID()+">\n";
			retString += state.toString() + "\n";
            retString += "</State-"+state.getStateID()+">\n";
		}
        retString += "</Transitions>\n";
		return retString;

	}
}
