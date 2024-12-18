package mis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaxIndependent {
    private Graph graph;
    private int populationSize;
    private int generations;
    private double mutationRate;

    public MaxIndependent(Graph graph, int populationSize, int generations, double mutationRate) {
        this.graph = graph;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
    }

    // Khởi tạo quần thể
    private List<int[]> initializePopulation() {
        List<int[]> population = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < populationSize; i++) {
            int[] individual = new int[graph.numVertices];
            for (int j = 0; j < graph.numVertices; j++) {
                individual[j] = rand.nextInt(2); // 0 hoặc 1
            }
            population.add(individual);
        }
        return population;
    }

    // Tính độ thích nghi
    private int fitness(int[] individual) {
        int fitness = 0;
        for (int i = 0; i < individual.length; i++) {
            if (individual[i] == 1) {
                for (int j = 0; j < individual.length; j++) {
                    if (i != j && individual[j] == 1 && graph.hasEdge(i, j)) {
                        return 0; // Không hợp lệ
                    }
                }
                fitness++;
            }
        }
        return fitness;
    }

    // Chọn lọc cha mẹ
    private int[] selectParent(List<int[]> population) {
        Random rand = new Random();
        int idx1 = rand.nextInt(population.size());
        int idx2 = rand.nextInt(population.size());
        int[] parent1 = population.get(idx1);
        int[] parent2 = population.get(idx2);
        return fitness(parent1) > fitness(parent2) ? parent1 : parent2;
    }

    // Lai ghép
    private int[] crossover(int[] parent1, int[] parent2) {
        Random rand = new Random();
        int[] child = new int[parent1.length];
        int crossoverPoint = rand.nextInt(parent1.length);
        for (int i = 0; i < parent1.length; i++) {
            child[i] = (i < crossoverPoint) ? parent1[i] : parent2[i];
        }
        return child;
    }

    // Đột biến
    private void mutate(int[] individual) {
        Random rand = new Random();
        for (int i = 0; i < individual.length; i++) {
            if (rand.nextDouble() < mutationRate) {
                individual[i] = 1 - individual[i];
            }
        }
    }

    // Thuật toán di truyền
    public int[] findMaxIndependentSet() {
        List<int[]> population = initializePopulation();
        int[] bestSolution = null;
        int bestFitness = 0;

        for (int generation = 0; generation < generations; generation++) {
            List<int[]> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                int[] parent1 = selectParent(population);
                int[] parent2 = selectParent(population);
                int[] child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
                int childFitness = fitness(child);
                if (childFitness > bestFitness) {
                    bestFitness = childFitness;
                    bestSolution = child;
                }
            }
            population = newPopulation;
        }
        return bestSolution;
    }
}
