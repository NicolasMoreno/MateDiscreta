package Automaton;

import java.util.LinkedList;
import java.util.List;

public class FiniteAutomaton {
    private final FiniteState initialState;

    public FiniteAutomaton(){
        this.initialState = new FiniteState("InitialState");
    }

    public FiniteAutomaton(final FiniteState initialState) {
        this.initialState = initialState;
    }

    /**
     * Method that adds deterministically values to the automaton.
     * @param words
     */
    public void add(final String... words){
        for(String word: words){
            this.add(this.initialState,word,0);
        }
    }

    private void add(FiniteState actualState, String word, int index){
        if(index>= word.length()) return;
        final char actualChar = word.charAt(index);
        final List<FiniteState> states = actualState.getStates(actualChar);
        if(states.get(0).getName().equals("null")){
            FiniteState finiteState = new FiniteState(""+actualChar);
            if(index == word.length()-1) finiteState.setFinal();
            actualState.addTransition(finiteState, actualChar);
            this.add(finiteState, word, index+1);
        }
        else{
            for (FiniteState state : states){
                if(state.getName().equals(""+actualChar)){
                    this.add(state, word, index+1);
                }
            }
        }
    }

    public FiniteState getInitialState() {
        return this.initialState;
    }

    public Result evaluate(final String word){
        return evaluate(getInitialState(), word, 0, new Result(word));
    }

    private Result evaluate(FiniteState initialState, String word, int index, Result result) {
        result.addState(initialState);
        if(index >= word.length()) return result;
        final List<FiniteState> states = initialState.getStates(word.charAt(index));
        if(states.get(0).getName().equals("null")) {
            result.addState(states.get(0));
            return result;
        }
        Result auxResult = null;
        for (FiniteState state : states){
            auxResult = evaluate(state,word,index+1, result);
            if(auxResult.isValid()) return auxResult;
        }
        return auxResult;

    }

    /**
     * Method that checks if the automaton is nonDeterministic
     * @return boolean representing if is non-deterministic-automaton
     * TODO
     */
    private boolean isNonDeterministic(){
        return true;
    }

    public FiniteAutomaton transformToDeterministic(){
        if(this.isNonDeterministic()){
            FiniteAutomaton deterministicAutomaton = new FiniteAutomaton();

        }
        return this;
    }

    public class Result{
        private String word;
        private List<FiniteState> states;

        public Result(String word) {
            this.word = word;
            this.states = new LinkedList<>();
        }

        public boolean isValid(){
            return this.states.get(this.states.size()-1).isFinal();
        }

        public void addState(FiniteState state){
            this.states.add(state);
        }

        public String getWord() {
            return word;
        }

    }
}
