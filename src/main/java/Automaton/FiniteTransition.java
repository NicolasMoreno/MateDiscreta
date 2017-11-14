package Automaton;

public class FiniteTransition {

    private final FiniteState state;
    private final char c;

    FiniteTransition(final FiniteState state, final char c){
        this.state = state;
        this.c = c;
    }

    public FiniteState getState() {
        return state;
    }

    char getChar() {
        return c;
    }
}
