package baseline;

//Class to handle creation and behavior of Edges in graphs
//Author: Gage Fringer(gwfringe)
public class Edge implements Comparable{
    private Vertex v1;
    private Vertex v2;
    private long weight;

    public Edge(Vertex v1, Vertex v2, long weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public Vertex getEndpoint(int selection){ //0 is a, 1 is b
        if(selection == 0){
            return this.v1;
        } else if (selection == 1){
            return this.v2;
        } else {
            return null;
        }
    }
    
    public long getWeight(){
        return weight;
    }

    //Returns an indicator of which object is SMALLER (pos = rightside[argument], neg = the caller)
    public int compareTo(Object o){
        if(o instanceof Edge){
            //Then we know that we have 2 edges, and we can compare them based on their weights
            Edge temp = (Edge) o;
            if(this.getWeight() < temp.getWeight()){
                return -1;
            } else if(this.getWeight() > temp.getWeight()){
                return 1;
            } else { return 0;}
        } else {
            return Integer.MAX_VALUE; // errror val
        }

    }

    @Override
    public String toString(){
        return "Vertex " + this.v1.getID() + " -> Vertex " + this.v2.getID() + " with weight = " + this.weight;
    }
}