import java.util.ArrayList;

import baseline.BinHeap;
import baseline.Edge;
import baseline.Graph;
import baseline.Subset;

public class Krushkal {
    public static void main(String args[]){
        Graph g = GraphBuilder.buildGraph(args);
        
        BinHeap<Long, Edge> heap = new BinHeap<Long,Edge>();

        long comparisons = 0;
        long total_weight = 0;
        long elapsed_time;
        long start_time;
        long finish_time;

        //Now is the time to implement the algorithm, so we can get start
        //time and begin doing work.
        start_time = System.currentTimeMillis();

        ArrayList<Subset> subsets = new ArrayList<Subset>();
        subsets.add(new Subset(0,0));


        //Initialize the individual subsets C(v) to be singleton sets
        g.getVertices().forEach((v) -> {
            subsets.add(new Subset(v.getID(),0));
        });

        //Add all edges to the heap
        g.getEdges().forEach((e) -> {
            heap.addItem(e.getWeight(), e);
        });

        ArrayList<Edge> MSTEdges = new ArrayList<Edge>();

        while(MSTEdges.size() - 1 < g.getVertices().size()-1 && !heap.isEmpty()){
            Edge minEdge = heap.pullMin();

            int left_parent = findRoot(subsets, minEdge.getEndpoint(0).getID());
            int right_parent = findRoot(subsets, minEdge.getEndpoint(1).getID());

            if(left_parent != right_parent){
                MSTEdges.add(minEdge);
                total_weight = total_weight + minEdge.getWeight();
                union(subsets,left_parent,right_parent);
            }
        }

        if (MSTEdges.size() < g.getNumVertices() - 1) {
            System.err.println("Graph is not connected ");
        }
        else{
            finish_time = System.currentTimeMillis();
            elapsed_time = finish_time - start_time;

            comparisons = heap.getComparisons();
            System.out.println("c Krushkal's Algorithm");
            System.out.println("g " + g.getNumVertices() + " " + MSTEdges.size());
            MSTEdges.forEach((e) -> {
                System.out.println("e " + e.getEndpoint(0).getID() + " " + e.getEndpoint(1).getID() + " " + e.getWeight());
            });

            System.err.println("weight " + total_weight );
            // Get elapsed time to 1/100th of a second and print 2 decimal places
            System.err.printf("runtime %.2f", (float)elapsed_time / 100000);
            System.err.println();
            System.err.println("comparisons " + comparisons);
        }
    }

    public static int findRoot(ArrayList<Subset> subsets, int v){
        if(subsets.get(v).getParent() == v){
            return v;
        }
        
        subsets.get(v).setParent(findRoot(subsets, subsets.get(v).getParent()));
        return subsets.get(v).getParent();
    }

    public static void union(ArrayList<Subset> subsets, int leftParent, int rightParent){
        if(subsets.get(leftParent).getRank() < subsets.get(rightParent).getRank()){
            subsets.get(leftParent).setParent(rightParent);
        }
        else if(subsets.get(rightParent).getRank() < subsets.get(leftParent).getRank()){
            subsets.get(rightParent).setParent(leftParent);
        }
        else{
            subsets.get(rightParent).setParent(leftParent);
            subsets.get(leftParent).incrementRank();
        }
    }
}
