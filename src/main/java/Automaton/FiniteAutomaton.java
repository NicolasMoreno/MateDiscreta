package Automaton;

import java.util.*;

public class FiniteAutomaton {

    private FiniteState initialState;
    private Map<String, ArrayList<Integer>> concurrencyMap;

    public FiniteAutomaton() {
        this.initialState = new FiniteState("InitialState");
        this.concurrencyMap = new HashMap<>();
    }

    public FiniteAutomaton(final FiniteState initialState) {
        this.initialState = initialState;
        this.concurrencyMap = new HashMap<>();
    }

    /**
     * Method that adds deterministically values to the automaton.
     * @param words
     */
    public void addDeterministically(final String... words){
        for(String word: words){
            this.concurrencyMap.put(word,new ArrayList<>());
            word = word.toLowerCase().trim();
            this.add(this.initialState,word,0);
        }
    }

    private void add(FiniteState actualState, String word, int index) {
        if (index >= word.length()) return;
        final char actualChar = word.charAt(index);
        final List<FiniteState> states = actualState.getStates(actualChar);
        if (states.get(0).getName().equals("null")) {
            FiniteState finiteState = new FiniteState("" + actualChar);
            if (index == word.length() - 1) finiteState.setFinal();
            actualState.addTransition(finiteState, actualChar);
            this.add(finiteState, word, index + 1);
        } else {
            for (FiniteState state : states) {
                if (state.getName().equals("" + actualChar)) {
                    this.add(state, word, index + 1);
                }
            }
        }
    }

    /**
     * Method that adds Non-deterministically values to the automaton.
     * @param words
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
        evaluate(getInitialState(), word, 0, new StringBuilder(), directoryIndex);
    }

    private void evaluate(FiniteState initialState, String word, int index, StringBuilder sb, int directoryIndex) {
        if(index >= word.length()) return;
        if(initialState.getAllStates().isEmpty()) {
            this.evaluate(getInitialState(),word,index+1, sb.delete(0,sb.length()),directoryIndex);
            return;
        }
        final List<FiniteState> states = initialState.getStates(word.charAt(index));
        if(states.get(0).getName().equals("null")) {
            this.evaluate(getInitialState(),word,index, sb.delete(0,sb.length()),directoryIndex);
        }else{
            for (FiniteState state : states){
                if(state.getName().equals(""+word.charAt(index))){
                    if(state.isFinal()){
                        sb.append(state.getName());
                        state.addValue();
                        final ArrayList<Integer> integers = this.concurrencyMap.get(sb.toString());
                        integers.add(directoryIndex,state.getValue());
                        this.concurrencyMap.put(sb.toString(), integers);
                        this.evaluate(state,word,index+1, sb,directoryIndex);
                    }else {
                        this.evaluate(state,word,index+1, sb.append(state.getName()),directoryIndex);
                    }
                }
            }
        }

    }

    public void emptyAutomaton(){
        this.initialState = new FiniteState("Initial State");
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
                        if(state.isFinal()) auxState.setFinal(); //Checkeamos si alguno de los estados encontrados es final...
                    });
                    if(newState.getStates(character.charAt(0)).get(0).getName().equals("null")) newState.addTransition(auxState, character.charAt(0));
                    states.forEach( otherState -> this.iterateAutomaton(dictionary,newState.getStates(character.charAt(0)).get(0), otherState));
                }
            }
        }
    }

    private void fillDictionary(ArrayList<String> dictionary, FiniteState finiteState) {
        if(!(dictionary.contains(finiteState.getName()))) dictionary.add(finiteState.getName());
        finiteState.getAllStates().forEach(finiteState1 -> this.fillDictionary(dictionary,finiteState1)); //May be this could be filled while adding in the automaton
    }


    public List<FiniteState> getAllStates() {
        List<FiniteState> result = new ArrayList<>();
        addAllStates(result, initialState);
        return result;
    }

    private void addAllStates(List<FiniteState> list, FiniteState finiteState) {
        if (finiteState != null) {
            list.add(finiteState);
            for (FiniteTransition finiteTransition : finiteState.getTransitions())
                addAllStates(list, finiteTransition.getState());

        }
    }

    public class Result {
        private String word;
        private List<FiniteState> states;

        public Result(String word) {
            this.word = word;
            this.states = new LinkedList<>();
        }

        public boolean isValid() {
            return this.states.get(this.states.size() - 1).isFinal();
        }

        public void addState(FiniteState state) {
            this.states.add(state);
        }

        public String getWord() {
            return word;
        }

    }
}
