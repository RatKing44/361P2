package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

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
        Set<NFAState> currentStates = eClosure(startState);
        for (char symbol : s.toCharArray()) {
            Set<NFAState> nextStates = new LinkedHashSet<>();
            for (NFAState state : currentStates) {
                nextStates.addAll(getToState(state, symbol));
            }
            currentStates = new LinkedHashSet<>();
            for (NFAState state : nextStates) {
                currentStates.addAll(eClosure(state));
            }
        }
        for (NFAState state : currentStates) {
            if (isFinal(state.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return language;
    }

    @Override
    public NFAState getState(String name) {
        NFAState returnState = null;
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
        if (transition != null) {
            toStates.addAll(transition);
        }
        return toStates;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        // loop for checking the state name against the parameter
        Stack<NFAState> stack = new Stack<>();
        Set<NFAState> closure = new LinkedHashSet<>();
        NFAState tempstate = null;
        stack.push(s);
        closure.add(s);
        while (!stack.isEmpty()) {
            tempstate = stack.pop();
            Set<NFAState> epstrans = tempstate.getTransitions('e');
            if (epstrans != null) {
                for (NFAState state : epstrans) {
                    if (!closure.contains(state)) {
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
        if(this.startState == null) {
            return 0;
        }
        //gets the input as an array of characters
        char[] symbols = s.toCharArray();
        int pos = 0;
        Set<NFAState> currentStates = eClosure(startState);
        int max = currentStates.size();

        //loops through the input string
        while(pos != symbols.length){
            if(!language.contains(symbols[pos])){
                return max;
            }

            Set<NFAState> nextStates = new LinkedHashSet<>();
            Set<NFAState> eclosures = new LinkedHashSet<>();
            //loops through the current states and gets the transitions for the current symbol
            for(NFAState state : currentStates){
                Set<NFAState> temp = state.getTransitions(symbols[pos]);
                if(temp != null){
                    nextStates.addAll(temp);
                }
            }

            //loops through the next states and gets the epsilon closures
            for(NFAState state : nextStates){
                eclosures.addAll(eClosure(state));
            }
            //adds the epsilon closures to the next states
            nextStates.addAll(eclosures);

            //if the next states are empty, return the max
            if(nextStates.isEmpty()){
                return max;
            }

            //if the next states are greater than the max, set the max to the next states size
            if(nextStates.size() > max){
                max = nextStates.size();
            }

            //sets the current states to the next states and increments the position
            currentStates = nextStates;
            pos++;
        }

        return max;
    }

    


    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        boolean addTransition = true;
        if (!language.contains(onSymb) && onSymb != 'e') {
            addTransition = false;
        } else if (getState(fromState) == null) {
            addTransition = false;
        }
        for (String state : toStates) {
            if (getState(state) == null) {
                addTransition = false;
            }
        }
        if (addTransition) {
            for (String s : toStates) {
                ((NFAState) getState(fromState)).addTransition(onSymb, (NFAState) getState(s));
            }
        }
        return addTransition;

    }

    @Override
    public boolean isDFA() {
        boolean isDFA = true;
        for (NFAState n : states) {
            if (n.getTransitions('e') != null) {
                isDFA = false;
            }
            for (Character l : language) {
                if (n.getTransitions(l) == null) {
                    isDFA = false;
                } else if (n.getTransitions(l).size() > 1) {
                    isDFA = false;
                }
            }
        }
        return isDFA;

    }

}
