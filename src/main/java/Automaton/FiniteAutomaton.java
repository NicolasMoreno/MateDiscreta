package Automaton;

import java.util.*;

public class FiniteAutomaton {

    private FiniteState initialState;
    private Map<String, ArrayList<Integer>> concurrencyMap;

    public FiniteAutomaton(final FiniteState initialState) {
        this.initialState = initialState;
        this.concurrencyMap = new HashMap<>();
    }

    /**
     * Method that adds Non-deterministically values to the automaton.
     */
    public void addNonDeterministically(final String... words){
        for (String word: words){
            word = word.toLowerCase().trim();
            this.concurrencyMap.put(word,new ArrayList<>());
            FiniteState firstChar = new FiniteState(""+word.charAt(0));
            this.initialState.addTransition(firstChar,word.charAt(0));
            FiniteState lastState = firstChar;
            for (int i = 1; i < word.length(); i++) {
                FiniteState otherChar = new FiniteState(""+word.charAt(i));
                if(i == word.length()-1) {
                    otherChar.setFinal();
                    otherChar.setValue(0);
                }
                lastState.addTransition(otherChar, otherChar.getName().charAt(0));
                lastState = otherChar;
            }
        }
    }

    public FiniteState getInitialState() {
        return this.initialState;
    }

    public void evaluate(final String word, final int directoryIndex){
        String loweredWord = word.toLowerCase();
        evaluate(getInitialState(), loweredWord, 0, new StringBuilder(), directoryIndex);
    }

    private void evaluate(FiniteState initialState, String word, int index, StringBuilder sb, int directoryIndex) {
        if(index >= word.length()) return;
        if(initialState.getAllStates().isEmpty()) {
            this.evaluate(getInitialState(),word,index+1, sb.delete(0,sb.length()),directoryIndex);
            return;
        }
        final List<FiniteState> states = initialState.getStates(word.charAt(index));
        if(states.get(0).getName().equals("null")) {
            this.evaluate(getInitialState(),word,index+1, sb.delete(0,sb.length()),directoryIndex);
        }else{
            for (FiniteState state : states){
                if(state.getName().equals(""+word.charAt(index))){
                    if(state.isFinal()){
                        sb.append(state.getName());
                        state.addValue();
                        final ArrayList<Integer> integers = this.concurrencyMap.get(sb.toString());
                        if(directoryIndex == integers.size()) integers.add(directoryIndex,state.getValue());
                        else if(directoryIndex > integers.size()) {
                            for (int i = integers.size(); i < directoryIndex; i++) {
                                integers.add(state.getValue()-1);
                            }
                            integers.add(directoryIndex,state.getValue());
                        }
                        else integers.set(directoryIndex,state.getValue());
                        this.concurrencyMap.put(sb.toString(), integers);
                        this.evaluate(state,word,index+1, sb,directoryIndex);
                    }else {
                        this.evaluate(state,word,index+1, sb.append(state.getName()),directoryIndex);
                    }
                }
            }
        }

    }

    private void emptyAutomaton(){
        this.initialState = new FiniteState("InitialState");
    }

    /**
     * Method that transform this automat to Deterministic
     */
    public void transformToDeterministic(){
        ArrayList<String> dictionary = new ArrayList<>();
        this.initialState.getAllStates().forEach(finiteState -> this.fillDictionary(dictionary,finiteState));
        FiniteState initialState = this.initialState;
        this.emptyAutomaton();
        this.iterateAutomaton(dictionary,this.initialState, initialState);

    }

    /**
     * Method that transform this automaton to Deterministic
     * @param dictionary chars presented in the automaton
     * @param newState new automaton
     * @param oldState old automaton iterating to transform and add it to the new automaton
     */
    private void iterateAutomaton(ArrayList<String> dictionary, FiniteState newState, FiniteState oldState) {
        if(!(oldState.getAllStates().isEmpty())){
            for(String character: dictionary) {
                final List<FiniteState> states = oldState.getStates(character.charAt(0));
                if(!(states.get(0).getName().equals("null"))){
                    FiniteState auxState = new FiniteState(character);
                    states.forEach(state -> {
                        if(state.isFinal()) {
                            auxState.setFinal();//Checkeamos si alguno de los estados encontrados es final...
                        }
                    });
                    if(newState.getStates(character.charAt(0)).get(0).getName().equals("null")) newState.addTransition(auxState, character.charAt(0));
                    else if(auxState.isFinal()) newState.getStates(character.charAt(0)).get(0).setFinal();
                    states.forEach( otherState -> this.iterateAutomaton(dictionary,newState.getStates(character.charAt(0)).get(0), otherState));
                }
            }
        }
    }

    private void fillDictionary(ArrayList<String> dictionary, FiniteState finiteState) {
        if(!(dictionary.contains(finiteState.getName()))) dictionary.add(finiteState.getName());
        finiteState.getAllStates().forEach(finiteState1 -> this.fillDictionary(dictionary,finiteState1)); //May be this could be filled while adding in the automaton
    }

    public Map<String, ArrayList<Integer>> getConcurrencyMap() {
        return concurrencyMap;
    }
}
