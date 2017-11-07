package Graph;

public class Vertex {

    private Vertex value;
    private boolean isAcceptanceState;

    //TODO No estoy seguro si esto es necesario
    public Vertex(Vertex value, boolean isAcceptanceState){
        this.value = value;
        this.isAcceptanceState = isAcceptanceState;
    }

    public Vertex getValue() {
        return this.value;
    }

    public boolean isAcceptanceState() {
        return this.isAcceptanceState;
    }

    public void setValue(Vertex value){
        this.value = value;
    }

    public void setAcceptanceState(boolean isAcceptanceState){
        this.isAcceptanceState = isAcceptanceState;
    }
}
