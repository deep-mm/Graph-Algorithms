package baseline;

import java.util.ArrayList;

//Class to handle creation and behavior of Graphs
//Author: Gage Fringer(gwfringe)
public class Graph{

    ArrayList<Edge> edges;
    ArrayList<Vertex> vertices;
    int num_vertices;
    int num_edges;


    public Graph(int num_vertices, int num_edges){
        this.edges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.num_edges = num_edges;
        this.num_vertices = num_vertices;
    }

    public Vertex getVertex(int idx){
        if(idx <= vertices.size() && idx > 0){

            return vertices.get(idx-1);
        } else { return null;}
    }

    public void addEdge(Edge newEdge){
 
            if(!edges.contains(newEdge)){
                edges.add(newEdge);      
            }    
    }

    public void addVertex(Vertex v){
        if(v != null){
            vertices.add(v);
        }

    }

    public int getNumEdges(){return num_edges;}
    public void setNumEdges(int edges){ this.num_edges = edges;}

    public int getNumVertices(){return num_vertices;}
    public void setNumVertices(int v){ this.num_vertices = v;}


    public ArrayList<Vertex> getVertices(){ return vertices;}
    public ArrayList<Edge> getEdges(){return edges;}

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        ret.append("Total Graph Statistics:\n");
        ret.append("No. Vertices: " + getNumVertices()+"\n");
        ret.append("No. Edges: " + getNumEdges() + "\n");
        System.out.println("addingVertices");
        ret.append("Vertices:\n");
        for(Vertex v : vertices){
            ret.append("Vertex No.: " + v.getID() + "\n");
            ret.append("\tX-coord: " + v.getX() + "\n");
            ret.append("\tY-coord: " + v.getY() + "\n");
            ret.append("\tIncident Edges:\n");
            
            for(Edge e : v.incidentEdges){
                if(v.getID() == e.getEndpoint(0).getID()){
                    ret.append("\t\tEdge to " + e.getEndpoint(1).getID() + "\n");
                } else if(v.getID() == e.getEndpoint(1).getID()){
                    ret.append("\t\tEdge to " + e.getEndpoint(0).getID() + "\n");
                }
            }

        }
        ret.append("Edges:\n");
        for(Edge e : edges){
            ret.append("\tEdge from " + e.getEndpoint(0).getID() + " to " + e.getEndpoint(1).getID() + "; weight " + e.getWeight() + "\n");
        }
        return ret.toString();
    }
}