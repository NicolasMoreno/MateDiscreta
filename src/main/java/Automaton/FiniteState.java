package Automaton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FiniteState {

    private boolean isFinal = false;
    private Integer value;
    private String name;
    private List<FiniteTransition> transitions;

    public FiniteState(String name){
        this.name = name;
        this.value = null;
        this.transitions = new LinkedList<>();
    }

    void addTransition(FiniteState finiteState, char... charList){
        for(char c : charList){
            FiniteTransition finiteTransition = new FiniteTransition(finiteState,c);
            if(this.transitions.contains(finiteTransition)){
                continue;
            }
            this.transitions.add(finiteTransition);
        }
    }

    List<FiniteState> getAllStates(){
        ArrayList<FiniteState> states = new ArrayList<>();
        for (FiniteTransition transition: this.transitions){
            states.add(transition.getState());
        }
        return states;
    }

    List<FiniteState> getStates(char c){
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

    void setFinal() {
        isFinal = true;
        this.setValue(0);
    }

    public String getName() {
        return name;
    }

    Integer getValue() {
        return value;
    }

    void setValue(Integer value) {
        this.value = value;
    }

    public List<FiniteTransition> getTransitions() {
        return transitions;
    }

    void addValue() {this.value = value+1; }
}
