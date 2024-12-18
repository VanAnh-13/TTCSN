package mis;

import java.util.*;

/**
 * A class to analyze independent and maximal independent sets in an undirected
 * graph using Greedy Algorithm.
 */
public class GreedyGraphAnalyzer {
	private final Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
	private final List<Integer> vertices = new ArrayList<>();
	private final Set<Integer> independentSet = new HashSet<>();

	/**
	 * Initializes the graph analyzer.
	 */
	public GreedyGraphAnalyzer() {
	}

	/**
	 * Accepts manual input from the user to construct the graph. The user inputs
	 * the number of vertices and edges, followed by each edge.
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
		adjacencyList.computeIfAbsent(u, k -> new HashSet<>()).add(v);
		adjacencyList.computeIfAbsent(v, k -> new HashSet<>()).add(u);
	}

	/**
	 * Finds the maximal independent set using Greedy Algorithm.
	 */
	public void findMaximalIndependentSet() {
		independentSet.clear();

		// Sort vertices by degree (number of neighbors) in ascending order
		List<Integer> sortedVertices = new ArrayList<>(vertices);
		sortedVertices.sort(Comparator.comparingInt(v -> adjacencyList.getOrDefault(v, Collections.emptySet()).size()));

		// Greedily select vertices
		Set<Integer> visited = new HashSet<>();
		for (int vertex : sortedVertices) {
			if (!visited.contains(vertex)) {
				independentSet.add(vertex);
				visited.add(vertex);
				// Mark all neighbors as visited
				visited.addAll(adjacencyList.getOrDefault(vertex, Collections.emptySet()));
			}
		}
	}
	/**
	 * Prints the results of the greedy algorithm.
	 */
	public void printResults() {
		System.out.println("\n--- Greedy Algorithm Result ---");
		System.out.println("Maximal Independent Set: " + independentSet);
	}

	/**
	 * Main function to demonstrate the greedy algorithm.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		GreedyGraphAnalyzer analyzer = new GreedyGraphAnalyzer();

		analyzer.inputGraphManually();
		analyzer.findMaximalIndependentSet();
		analyzer.printResults();
	}
}
