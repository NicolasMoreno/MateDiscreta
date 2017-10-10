package Automaton;

import java.util.LinkedList;
import java.util.List;

public class FiniteState {

    private boolean isFinal = false;
    private String name;
    private List<FiniteTransition> transitions;

    public FiniteState(String name){
        this.name = name;
        this.transitions = new LinkedList<>();
    }

    public void addTransition(FiniteState finiteState, char... charList){
        for(char c : charList){
            FiniteTransition finiteTransition = new FiniteTransition(finiteState,c);
            if(this.transitions.contains(finiteTransition)){
                continue;
            }
            this.transitions.add(finiteTransition);
        }
    }

    public List<FiniteState> getStates(char c){
        List<FiniteState> states = new LinkedList<>();
        for(FiniteTransition finiteTransition: this.transitions){
            if(finiteTransition.getChar() == c){
                states.add(finiteTransition.getState());
            }
        }
        if(states.isEmpty()) states.add(new FiniteState("null")); //Here goes the error, where no state to the transition is found
        return states;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal() {
        isFinal = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
