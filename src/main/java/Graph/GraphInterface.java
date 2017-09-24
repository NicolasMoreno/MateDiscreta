package Graph;

import java.util.List;

public interface GraphInterface<T> {

    void addVertex(T vertex);
    void addEdge(int from, int to);
    void deleteEdge(int from, int to);
    void deleteVertex(int vertexPosition);
    boolean hasEdge(int from, int to);
    int order();
    int edgeLength();
    T getVertex(int vertexPosition);

    /**
     * Gets the adjacent list of vertex, given the vertex's position
     * @param vertexPosition vertex's position you want to get its other adjacent vertex
     * @return list of vertex's position number
     */
    List<Integer> getAdjacentList(int vertexPosition);

}
