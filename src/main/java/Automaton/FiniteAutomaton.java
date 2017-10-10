package Automaton;

import java.util.LinkedList;
import java.util.List;

public class FiniteAutomaton {
    private final FiniteState initialState;

    public FiniteAutomaton(final FiniteState initialState) {
        this.initialState = initialState;
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
