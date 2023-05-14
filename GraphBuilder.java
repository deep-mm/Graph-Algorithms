import java.util.Scanner;

import baseline.Edge;
import baseline.Graph;
import baseline.Vertex;

//Class to handle the input of information to construct a graph
//Author: Gage Fringer (gwfringe)
public class GraphBuilder{

    public static Graph buildGraph(String args[]){
        
        Graph g = null;        
        Scanner s = new Scanner(System.in);

            while(s.hasNextLine()){
                String temp = s.nextLine();
                String[] items = temp.split(" ");
                String first = items[0];
                

                //We have c, g, n, and e to choose from
                if(first.equalsIgnoreCase("c")){ // This is a comment, do nothing
                    continue;
                } else if (first.equalsIgnoreCase("n")){
                    
                    Vertex v = null;
                    int id = Integer.parseInt(items[1]);

                    if(items.length > 2){
                        int x = Integer.parseInt(items[2]);
                        int y = Integer.parseInt(items[3]);
                        v = new Vertex(id, x, y);
                    } else {
                        v = new Vertex(id);
                    }

                    g.addVertex(v);
                } else if(first.equalsIgnoreCase("e")){
                    if(g.getVertices().size() == 0){
                        //Input file where we got no nodes written
                        for(int i = 1; i <= g.getNumVertices(); i++){
                            Vertex v = new Vertex(i);
                            g.addVertex(v);
                            //System.out.println("Add node " + i);
                        }
                    }
                    
                    int v1num = Integer.parseInt(items[1]);
                    int v2num = Integer.parseInt(items[2]);
                    long wt = Long.parseLong(items[3]);

                    Vertex v1 = g.getVertex(v1num);
                    Vertex v2 = g.getVertex(v2num);

                    Edge e = new Edge(v1, v2, wt);

                    v1.addIncidentEdge(e);
                    v2.addIncidentEdge(e);
                    g.addEdge(e);
                } else if(first.equalsIgnoreCase("g")){ //Initialize the graph
                    int num_nodes = Integer.parseInt(items[1]);
                    int num_edges = Integer.parseInt(items[2]);

                    g = new Graph(num_nodes, num_edges);
                }
            }
            s.close();

            //Should have read in all of the graph
            //System.out.println(g.toString());
            return g;
    }

}

