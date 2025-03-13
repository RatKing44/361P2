package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;
import fa.State;

public class NFA implements NFAInterface {

    // instance variables
    Set<NFAState> states;
    Set<Character> language;
    Set<NFAState> finalStates;
    NFAState startState;

    public NFA() {
        this.states = new LinkedHashSet<>();
        this.language = new LinkedHashSet<>();
        this.finalStates = new LinkedHashSet<>();
        this.startState = null;
    }

    @Override
    public boolean addState(String name) {
        boolean foundState = false;
        // looks to see if the state name already exists in the set
        for (NFAState s : states) {
            if (s.getName().equals(name)) {
                foundState = true;
            }
        }
        // adds a new state with the name to the set
        if (foundState == false) {
            states.add(new NFAState(name));
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean setFinal(String name) {
        boolean addedFinal = false;
        for (NFAState s : states) {
            // checks to see if the given name exists as a state, and if it does, adds it to
            // the final state set
            if (s.getName().equals(name)) {
                finalStates.add(s);
                addedFinal = true;
            }
        }
        return addedFinal;
    }

    @Override
    public boolean setStart(String name) {
        boolean setStart = false;
        for (NFAState s : states) {
            // checks to see if the name given exists and sets that state to the start
            if (s.getName().equals(name)) {
                startState = s;
                setStart = true;
            }
        }
        return setStart;
    }

    @Override
    public void addSigma(char symbol) {
        // adds a character to the set if it doesn't already exist and it isnt the
        // reserved 'e'
        if (!language.contains(symbol) && symbol != 'e') {
            language.add(symbol);
        }
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
    }

    @Override
    public Set<Character> getSigma() {
        return language;
    }

    @Override
    public State getState(String name) {
        State returnState = null;
        // loop for checking the state name against the parameter
        for (NFAState s : states) {
            if (s.getName().equals(name)) {
                returnState = s;
            }
        }
        return returnState;
    }

    @Override
    public boolean isFinal(String name) {
        boolean isFinal = false;
        // loop for checking if the state exists in the final state set
        for (NFAState s : finalStates) {
            if (s.getName().equals(name)) {
                isFinal = true;
            }
        }
        return isFinal;
    }

    @Override
    public boolean isStart(String name) {
        return startState.getName().equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> toStates = new LinkedHashSet<>();
        Set<NFAState> transition = from.getTransitions(onSymb);
        if(transition != null){
            toStates.addAll(transition);
        }
       return toStates;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        
        // loop for checking the state name against the parameter
        Stack <NFAState> stack = new Stack<>();
        Set<NFAState> closure = new LinkedHashSet<>();
        NFAState tempstate = null;
        stack.push(s);
        closure.add(s);
        while (!stack.isEmpty()){
            tempstate = stack.pop();
            Set<NFAState> epstrans = tempstate.getTransitions('e');
            if (epstrans != null){
                for (NFAState state : epstrans){
                    if (!closure.contains(state)){
                        closure.add(state);
                        stack.push(state);
                    }
                }
            }
        }
        
        return closure;
        
    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        boolean addTransition = true;
        if(!language.contains(onSymb)){
            addTransition = false;
        }
        else if(getState(fromState) == null){
            addTransition = false;
        }
        for(String state : toStates){
            if(getState(state) == null){
                addTransition = false;
            }
        }
        if(addTransition){
            for(String s : toStates){
                fromState.addTransition(onSymb, getState(s));
            }
        }
        return addTransition;

    }

    @Override
    public boolean isDFA() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDFA'");
    }

}
