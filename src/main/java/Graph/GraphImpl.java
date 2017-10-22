package Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphImpl<T> implements GraphInterface<T>  {
    private ArrayList<T> vertexArray;
    private boolean[][] edges;
    private int index;
    private int edgesindex;

    public GraphImpl() {
        //Utilizo un ArrayList para no manejar el tema de cuando se llena un Array y extenderlo y bla bla
        this.vertexArray = new ArrayList<>(10);
        this.edges = new boolean[10][10];
        this.index = 0;
        this.edgesindex = 0;
    }

    public GraphImpl(int initialCapacity){
        this.vertexArray = new ArrayList<>(initialCapacity);
        this.edges = new boolean[initialCapacity][initialCapacity];
        this.index = 0;
        this.edgesindex = 0;
    }

    @Override
    public void addVertex(T vertex) {
        this.vertexArray.set(index,vertex);
        this.index++;
    }

    @Override
    public void addBidirectionalEdge(int from, int to) {
        this.edges[from][to] = this.edges[to][from] = true;
        this.edgesindex++;
    }

    @Override
    public void addUnidirectionalEdge(int from, int to) {
        this.edges[from][to] = true;
        this.edgesindex++;
    }

    @Override
    public void deleteEdge(int from, int to) {
        this.edges[from][to] = this.edges[to][from] = false;
        this.edgesindex--;
    }

    @Override
    public void deleteVertex(int vertexPosition) {
        //TODO
    }

    @Override
    public boolean hasEdge(int from, int to) {
        return this.edges[from][to];
    }

    @Override
    public int order() {
        return this.index;
    }

    @Override
    public int edgeLength() {
        return this.edgesindex;
    }

    @Override
    public T getVertex(int vertexPosition) {
        return this.vertexArray.get(vertexPosition);
    }

    @Override
    public List<Integer> getAdjacentList(int vertexPosition) {
        ArrayList<Integer> auxList = new ArrayList<>();
        for (int i = 0; i < this.index; i++) {
            if(this.hasEdge(vertexPosition,i)) auxList.add(i);
        }
        return auxList;
    }
}
