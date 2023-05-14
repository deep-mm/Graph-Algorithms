import java.text.DecimalFormat;
import java.util.ArrayList;
import baseline.*;

//Class to sort a graph based on an implementation of Prim-Jarnik's Algorithm,
//in this case specifically the version with a heap of Edges and lazy deletion
//Author: Gage Fringer (gwfringe)
public class PrimJarnik {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static void main(String args[]){
        Graph g = GraphBuilder.buildGraph(args);

        BinHeap<Long, Edge> heap = new BinHeap<Long,Edge>();

        long comparisons = 0;
        long total_weight = 0;
        long elapsed_time;
        long start_time;
        long finish_time;

        //Now is the time to implement the algorithm, so we can get start
        // time and begin doing work.
        start_time = System.currentTimeMillis();

        //In this case we'll just get the vertex added to the graph first, which is Node 1.
        ArrayList<Vertex> treeVerts = new ArrayList<Vertex>();
        ArrayList<Edge> treeEdges = new ArrayList<Edge>();
        Vertex firstV = g.getVertex(1);

        //For all edges that exist on our first vertex, we add them to the heap.
        for(Edge e : firstV.getIncidentEdges()){
            //In this case, the key we pull on is the weight of the
            heap.addItem(e.getWeight(), e);
            //System.out.println("To heap: " + e.getWeight());
        }

        //Set this vertex to be on the tree
        //System.out.println("Add " + firstV.getID() + " toTree");
        treeVerts.add(firstV);        

        while(!heap.isEmpty()){
            Edge curr = heap.pullMin();

            //Get the IDs of vertices on this edge (should be our first )
            //IDs should be the index 
            int v1 = curr.getEndpoint(0).getID();
            int v2 = curr.getEndpoint(1).getID();
            
            //Have booleans to keep track of if a vertex is already in the MST
            boolean v1_in = false;
            boolean v2_in = false;

            for(Vertex v : treeVerts){
                if(v.getID() == v1){
                    v1_in = true;
                } else if(v.getID() == v2){
                    v2_in = true;
                }

                //If we find both of these to be in the graph at an early point, no need to iterate
                //over the rest of the tree
                if(v1_in && v2_in){
                    break;
                }
            }

            if(v1_in && !v2_in){
                //System.out.println("Adding " + v2 + "to Tree");

                //Add v2
                Vertex toAdd = g.getVertex(v2);
                treeVerts.add(toAdd);

                //Get a list of all incident edges for this new vertex in the MST
                ArrayList<Edge> v2_incidents = toAdd.getIncidentEdges();

                //Iterate over all of the incident edges to add new edges to the heap.
                for(Edge incident : v2_incidents){
                    //Find out which vertex is on the other side of the incident edge
                    int otherVertex;
                    if(incident.getEndpoint(0).getID() == v2){
                        otherVertex = incident.getEndpoint(1).getID();
                    } else {
                        otherVertex = incident.getEndpoint(0).getID();
                    }


                    boolean otherInTree = false;
                    for(Vertex v : treeVerts){
                        if(v.getID() == otherVertex){
                            otherInTree = true;
                            break;
                        }
                    }

                    if(!otherInTree){
                        heap.addItem(incident.getWeight(), incident);
                        //System.out.println("Add edge weight " + incident.getWeight() + " to heap");
                    }
                }

            } else if(v2_in && !v1_in){
                //System.out.println("Adding " + v1 + "to tree");

                //Add v1
                Vertex toAdd = g.getVertex(v1);
                treeVerts.add(toAdd);

                //Get a list of all incident edges for this new vertex in the MST
                ArrayList<Edge> v1_incidents = toAdd.getIncidentEdges();

                //Iterate over all of the incident edges to add new edges to the heap.
                for(Edge incident : v1_incidents){
                    //Find out which vertex is on the other side of the incident edge
                    int otherVertex;
                    if(incident.getEndpoint(0).getID() == v1){
                        otherVertex = incident.getEndpoint(1).getID();
                    } else {
                        otherVertex = incident.getEndpoint(0).getID();
                    }


                    boolean otherInTree = false;
                    for(Vertex v : treeVerts){
                        if(v.getID() == otherVertex){
                            otherInTree = true;
                            break;
                        }
                    }

                    if(!otherInTree){
                        heap.addItem(incident.getWeight(), incident);
                        //System.out.println("Add edge weight " + incident.getWeight() + " to heap");
                    }
                }

            } else if(v1_in && v2_in){
                //System.out.println("Edge not needed");
                //Both already in
                continue;
            } else {
                //This would be a problem if neither vertex is in the tree
            }

            //Add the edge used right now into the final mst set
            treeEdges.add(curr);

            total_weight += curr.getWeight();
            
        }

        finish_time = System.currentTimeMillis();
        elapsed_time = finish_time - start_time;

        comparisons = heap.getComparisons();

        //Do some output to check which edges are picked
        System.out.println("g " + treeVerts.size() + " " + treeEdges.size());
        for(Edge e : treeEdges){
            System.out.println("e " + e.getEndpoint(0).getID() + " " + e.getEndpoint(1).getID() + " " + e.getWeight());
        }

        //Write to stderr the target information (weight, time, comparisons)
        System.err.println("weight " + total_weight );
        System.err.println("runtime " + df.format(elapsed_time / (long) 1000) + " seconds");
        System.err.println("comparisons " + comparisons);

    }

}
