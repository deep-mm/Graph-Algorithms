import java.util.ArrayList;

import baseline.*;

//Class to sort a graph based on an implementation of Boruvka's Algorithm,
//in this case specifically the version with a heap of Edges and lazy deletion
//Author: Deep Mehta (dmmehta2)
public class Boruvkas {

    public static void main(String args[]) {
        Graph g = GraphBuilder.buildGraph(args);

        long comparisons = 0;
        long total_weight = 0;
        long elapsed_time;
        long start_time;
        long finish_time;

        // Now is the time to implement the algorithm, so we can get start
        // time and begin doing work.
        start_time = System.currentTimeMillis();

        ArrayList<Vertex> treeVerts = new ArrayList<Vertex>();
        ArrayList<Edge> treeEdges = new ArrayList<Edge>();
        ArrayList<ArrayList<Vertex>> components = new ArrayList<ArrayList<Vertex>>();
        components.add(new ArrayList<Vertex>());
        int totalComponents = g.getVertices().size();

        // Initialize the components arraylist
        g.getVertices().forEach((v) -> {
            ArrayList<Vertex> temp = new ArrayList<Vertex>();
            temp.add(v);
            components.add(temp);
            v.setConnectedComponent(components.size() - 1);
        });

        // While there are more than one connected components and the heap is not empty
        while (totalComponents > 1) {

            ArrayList<Edge> minEdges = new ArrayList<Edge>();

            // For each connected component
            for (int i = 1; i < components.size(); i++) {
                if (components.get(i) != null) {
                    BinHeap<Long, Edge> heap = new BinHeap<Long, Edge>();

                    // Add all edges to the heap
                    components.get(i).forEach(v -> {
                        v.getIncidentEdges().forEach(e -> {
                            if (!heap.getKeys().contains(e.getWeight()) && e.getEndpoint(0).getConnectedComponent() != e
                                    .getEndpoint(1).getConnectedComponent())
                                heap.addItem(e.getWeight(), e);
                        });
                    });

                    if (heap.isEmpty()) {
                        System.err.println("Graph is not connected");
                        return;
                    }

                    minEdges.add(heap.pullMin());
                    comparisons += heap.getComparisons();
                }
            }

            for (int i=0; i<minEdges.size(); i++) {
                Edge e = minEdges.get(i);

                Vertex v1 = e.getEndpoint(0);
                Vertex v2 = e.getEndpoint(1);

                if (v1.getConnectedComponent() == v2.getConnectedComponent())
                    continue;

                if (!treeVerts.contains(v1))
                    treeVerts.add(v1);
                if (!treeVerts.contains(v2))
                    treeVerts.add(v2);
                treeEdges.add(e);
                total_weight += e.getWeight();
                union(components, v1, v2, g);
                totalComponents--;
            }
        }

        finish_time = System.currentTimeMillis();
        elapsed_time = finish_time - start_time;

        System.out.println("c Boruvka's Algorithm");
        System.out.println("g " + treeVerts.size() + " " + treeEdges.size());
        treeEdges.forEach((e) -> {
            System.out.println(
                    "e " + e.getEndpoint(0).getID() + " " + e.getEndpoint(1).getID() + " " + e.getWeight());
        });

        System.err.println("weight " + total_weight);
        // Get elapsed time to 1/100th of a second and print 2 decimal places
        System.err.printf("runtime %.2f", (float) elapsed_time / 100000);
        System.err.println();
        System.err.println("comparisons " + comparisons);
    }

    // Method to union two connected components
    public static void union(ArrayList<ArrayList<Vertex>> components, Vertex v1, Vertex v2, Graph g) {

        int v1CC = v1.getConnectedComponent();
        int v2CC = v2.getConnectedComponent();

        components.get(v2CC).forEach((v) -> {
            v.setConnectedComponent(v1CC);
        });
        components.get(v1CC).addAll(components.get(v2CC));
        components.set(v2CC, null);
    }

}