package mis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    int numVertices;
    List<Set<Integer>> adjList;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new HashSet<>());
        }
    }

    // Thêm cạnh vào đồ thị
    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    // Kiểm tra xem hai đỉnh có cạnh nối không
    public boolean hasEdge(int u, int v) {
        return adjList.get(u).contains(v);
    }
}
