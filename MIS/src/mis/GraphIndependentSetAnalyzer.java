package mis;

import java.util.*;
import java.util.stream.Collectors;


/**
 * A class to analyze independent and maximal independent sets in an undirected graph.
 */
public class GraphIndependentSetAnalyzer {
    private final Map < Integer, Set < Integer >> adjacencyList = new HashMap < > ();
    private final List < Integer > vertices = new ArrayList < > ();
    private final Set < Set < Integer >> independentSets = new HashSet < > ();
    private final Set < Set < Integer >> maximalIndependentSets = new HashSet < > ();


    /**
     * Initializes the graph analyzer.
     */
    public GraphIndependentSetAnalyzer() {}


    /**
     * Accepts manual input from the user to construct the graph.
     * The user inputs the number of vertices and edges, followed by each edge.
     */
    public void inputGraphManually() {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();
        if (numVertices <= 0) {
            System.out.println("Number of vertices must be positive.");
            return;
        }


        for (int i = 1; i <= numVertices; i++) {
            vertices.add(i);
        }


        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();
        if (numEdges < 0) {
            System.out.println("Number of edges cannot be negative.");
            return;
        }


        System.out.println("Enter edges (format: u v, one per line):");
        for (int i = 0; i < numEdges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();


            if (u == v || u < 1 || v < 1 || u > numVertices || v > numVertices) {
                System.out.println("Invalid edge. Try again.");
                i--;
                continue;
            }


            addEdge(u, v);
        }
    }


    /**
     * Adds an undirected edge between two vertices.
     *
     * @param u the first vertex
     * @param v the second vertex
     */
    public void addEdge(int u, int v) {
        adjacencyList.computeIfAbsent(u, k -> new HashSet < > ()).add(v);
        adjacencyList.computeIfAbsent(v, k -> new HashSet < > ()).add(u);
    }


    /**
     * Checks if a given set of vertices is an independent set.
     *
     * @param vertexSet the set of vertices to check
     * @return true if the set is independent, false otherwise
     */
    public boolean isIndependentSet(Set < Integer > vertexSet) {
        for (Integer v1: vertexSet) {
            for (Integer v2: vertexSet) {
                if (!v1.equals(v2) && adjacencyList.getOrDefault(v1, Collections.emptySet()).contains(v2)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Finds all independent sets in the graph using an optimized backtracking algorithm.
     *
     * @param maxTimeSeconds the maximum time to run the algorithm (in seconds)
     */
    public void findIndependentSetsOptimized(double maxTimeSeconds) {
        independentSets.clear();
        List < Integer > sortedVertices = vertices.stream()
                .sorted(Comparator.comparingInt(v -> adjacencyList.getOrDefault(v, Collections.emptySet()).size()))
                .collect(Collectors.toList());


        long startTime = System.currentTimeMillis();


        backtrackOptimized(sortedVertices, new HashSet < > (), 0, maxTimeSeconds, startTime);
    }


    /**
     * Backtracking function to explore potential independent sets.
     *
     * @param sortedVertices the list of vertices sorted by degree
     * @param currentSet the current independent set being constructed
     * @param index the current index in the vertex list
     * @param maxTimeSeconds the maximum time allowed for the computation
     * @param startTime the start time of the algorithm
     */
    private void backtrackOptimized(List < Integer > sortedVertices, Set < Integer > currentSet, int index, double maxTimeSeconds, long startTime) {
        if ((System.currentTimeMillis() - startTime) / 1000.0 > maxTimeSeconds) {
            return;
        }


        if (isIndependentSet(currentSet)) {
            independentSets.add(new HashSet < > (currentSet));
        }


        for (int i = index; i < sortedVertices.size(); i++) {
            Integer vertex = sortedVertices.get(i);
            if (currentSet.stream().noneMatch(v -> adjacencyList.getOrDefault(v, Collections.emptySet()).contains(vertex))) {
                currentSet.add(vertex);
                backtrackOptimized(sortedVertices, currentSet, i + 1, maxTimeSeconds, startTime);
                currentSet.remove(vertex);
            }
        }
    }


    /**
     * Finds all maximal independent sets in the graph.
     */
    public void findMaximalIndependentSets() {
        if (independentSets.isEmpty()) {
            findIndependentSetsOptimized(10.0);
        }


        int maxSize = independentSets.stream().mapToInt(Set::size).max().orElse(0);
        maximalIndependentSets.addAll(independentSets.stream()
                .filter(set -> set.size() == maxSize)
                .collect(Collectors.toSet()));
    }


    /**
     * Prints the results of the graph analysis, including all independent sets
     * and maximal independent sets.
     */
    public void printResults() {
        System.out.println("\n--- Graph Analysis Results ---");


        System.out.println("\nAll Independent Sets:");
        for (Set < Integer > set: independentSets) {
            System.out.println(set);
        }


        System.out.println("\nMaximal Independent Sets:");
        for (Set < Integer > set: maximalIndependentSets) {
            System.out.println(set);
        }
    }


    /**
     * Main function to demonstrate graph analysis.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        GraphIndependentSetAnalyzer analyzer = new GraphIndependentSetAnalyzer();


        analyzer.inputGraphManually();
        analyzer.findIndependentSetsOptimized(10.0);
        analyzer.findMaximalIndependentSets();
        analyzer.printResults();
    }
}

