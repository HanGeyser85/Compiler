import java.util.LinkedList;

public class DFA {
    private LinkedList<State> Dfa;

	public DFA() {
		this.setDfa(new LinkedList<State>());
		this.getDfa().clear();
	}

	public LinkedList<State> getDfa() {
		return Dfa;
	}

	public void setDfa(LinkedList<State> Dfa) {
		this.Dfa = Dfa;
	}

	@Override
	public String toString() {
		String retString = "";
        retString += "<Transitions>\n";
		for (State state : this.getDfa()) {
            retString += "<State-"+state.getStateID()+">\n";
			retString += state.toString() + "\n";
            retString += "</State-"+state.getStateID()+">\n";
		}
        retString += "</Transitions>\n";
		return retString;
	}
}
