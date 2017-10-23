package Automaton;

/**
 * class with the functionality of building the automaton giving the list of words you want to iterate or 'check'
 *
 */
public class FiniteAutomatonBuilder {


    /**
     * Method that builds NFA automaton giving all the words
     * @param words
     * @return FiniteAutomaton with all states and transitions
     */
    public static void buildAutomaton(FiniteAutomaton automaton,String... words) {
        for(String word: words){
            FiniteState firstChar = new FiniteState(""+word.charAt(0));
            automaton.getInitialState().addTransition(firstChar,word.charAt(0));
            FiniteState lastState = firstChar;
            for (int i = 1; i < word.length(); i++) {
                FiniteState otherChar = new FiniteState(""+word.charAt(i));
                if(i == word.length()-1) {
                    otherChar.setFinal();
                    otherChar.setValue(0);
                }
                otherChar.addTransition(firstChar,firstChar.getName().charAt(0)); //TODO preguntar si hace falta esto.
                lastState.addTransition(otherChar, otherChar.getName().charAt(0));
                lastState = otherChar;
            }
        }
    }

}
