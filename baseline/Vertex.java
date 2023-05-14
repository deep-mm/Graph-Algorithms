package baseline;
import java.util.ArrayList;
import java.lang.Math;

//Class to handle creation and behavior of Vertices in graphs
//Author: Gage Fringer(gwfringe)
public class Vertex implements Comparable{
    ArrayList<Edge> incidentEdges;
    private int id;
    private int x_val;
    private int y_val;
    private long distance; 
    private boolean inTree; //Variable for Prim-Jarnik
    private int connectedComponent; //Variable for Boruvka's

    public Vertex(int id){
        this.id = id;
        incidentEdges = new ArrayList<Edge>();
        //If we don't get coordinate values, we can use a dummy value (-1)
        this.x_val = -1;
        this.y_val = -1;
        this.distance = -1;
    }

    public Vertex(int id, int x_val, int y_val){
        this.id = id;
        this.x_val = x_val;
        this.y_val = y_val;
        
        this.distance = (long) Math.sqrt(y_val*y_val + x_val*x_val);

        incidentEdges = new ArrayList<Edge>();
    }


    public Edge getIncidentEdge(int idx){
        if(idx < this.incidentEdges.size()){
            return this.incidentEdges.get(idx);
        } else { return null;}
    }

    public ArrayList<Edge> getIncidentEdges(){return incidentEdges;}

    public int getID(){
        return id;
    }

    public int getX(){
        return x_val;
    }

    public int getY(){
        return y_val;
    }

    public long getDistance(){return distance;}

    public void addIncidentEdge(Edge e){
        this.incidentEdges.add(e);
    }

    //Returns an indicator of which object is SMALLER (pos = rightside[argument], neg = the caller)
    public int compareTo(Object o){
        if(o instanceof Vertex){
            //Then we know that we have 2 edges, and we can compare them based on their weights
            Vertex temp = (Vertex) o;
            if(this.getDistance() < temp.getDistance()){
                return -1;
            } else if(this.getDistance() > temp.getDistance()){
                return 1;
            } else { return 0;}
        } else {
            return Integer.MAX_VALUE; // errror val
        }

    }

    public boolean inTree(){return inTree;}
    public void setInTree(boolean b){ inTree = b;}

    public int getConnectedComponent(){return connectedComponent;}
    public void setConnectedComponent(int i){connectedComponent = i;}
}