package mis;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Nhập số đỉnh và cạnh
        System.out.print("Nhập số đỉnh: ");
        int numVertices = scanner.nextInt();
        System.out.print("Nhập số cạnh: ");
        int numEdges = scanner.nextInt();

        // Tạo đồ thị
        Graph graph = new Graph(numVertices);

        System.out.println("Nhập các cạnh (dưới dạng u v): ");
        for (int i = 0; i < numEdges; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }

        // Thông số thuật toán di truyền
        int populationSize = 100;
        int generations = 200;
        double mutationRate = 0.05;

        // Tạo đối tượng MaxIndependent và tìm tập độc lập lớn nhất
        MaxIndependent solver = new MaxIndependent(graph, populationSize, generations, mutationRate);
        int[] solution = solver.findMaxIndependentSet();

        // In kết quả
        System.out.println("Tập độc lập lớn nhất tìm được:");
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}
