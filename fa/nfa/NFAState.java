package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fa.State;

public class NFAState extends State {

    // keep track of transitions for each state and where they go to
    protected HashMap<Character, Set<NFAState>> transitions;

    public NFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
    }

    /**
     * Adds a transition from itself to another specified state
     * 
     * @param key       the character in which the transition takes place on
     * @param stateName the state that the transition is going to
     */
    public void addTransition(Character key, NFAState stateName) {
        if (!transitions.containsKey(key)) {
            transitions.put(key, new HashSet<>());
        }
        transitions.get(key).add(stateName);
    }
    

    /**
     * Gets the transition for this state that uses the specified key
     * 
     * @param key the character in which we want the transition for
     * @return this returns the name of the toState transition in string form
     */
    public String getTransition(Character key) {
       
      Set<NFAState> retVal =  transitions.get(key);
      String retString = "";
       for(NFAState state : retVal){
              retString += state.getName() + ",";
        
        }
        return retString;
    }
}
