package Automaton;

public class FiniteTransition {

    private final FiniteState state;
    private final char c;

    public FiniteTransition(final FiniteState state,final char c){
        this.state = state;
        this.c = c;
    }

    public FiniteState getState() {
        return state;
    }

    public char getChar() {
        return c;
    }
}
