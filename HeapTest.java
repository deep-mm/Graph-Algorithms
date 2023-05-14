import baseline.*;
import java.util.ArrayList;

//Class file to handle the testing of behavior when creating
// a binary MinHeap based on a read in Graph
// Author: Gage Fringer (gwfringe)
public class HeapTest {
    
    //Since this gets built off of STD input, we need to specify
    //which file this is expected to work with, which is currently input/bt7.gph
    //This also has a LOT of outputs of the heap structure (i.e. the current array listing)
    //To allow for visual diagnostics of the heap behavior, since testing only tests expected
    //functionality.
    public static void main(String args[]) throws Exception{
        Graph g = GraphBuilder.buildGraph(args);

        BinHeap<Long, Edge> heap = new BinHeap<Long, Edge>();

        int size = 0;
        if(heap.size() != 0){
            throw new Exception("This should be empty right now!");
        }


        //This tests the building of the heap to be a proper minheap.
        for(Edge e : g.getEdges()){
            long weight = e.getWeight();
            heap.addItem(weight, e);
            size++;
            if(heap.size() != size){
                throw new Exception("Incorrect size");
            }
            ArrayList<Long> items = heap.getKeys();

            for(Long i : items){
                System.out.println(i);
            }
            System.out.println("----");

            if(size == 1){
                //Weight 8
                Edge temp = heap.getMin();
                if(temp.getWeight() != 8){throw new Exception("Incorrect Weight, expected 8 and got " + temp.getWeight());}
            } else if(size == 2){
                //Weight 7
                Edge temp = heap.getMin();
                if(temp.getWeight() != 7){throw new Exception("Incorrect Weight, expected 7 and got " + temp.getWeight());}
            } else if(size == 3){
                //Weight 4
                Edge temp = heap.getMin();
                if(temp.getWeight() != 4){throw new Exception("Incorrect Weight, expected 4 and got " + temp.getWeight());}
            } else if(size == 4){
                //Weight 3
                Edge temp = heap.getMin();
                if(temp.getWeight() != 3){throw new Exception("Incorrect Weight, expected 3 and got " + temp.getWeight());}
            } else if(size == 5){
                //Weight 2
                Edge temp = heap.getMin();
                if(temp.getWeight() != 2){throw new Exception("Incorrect Weight, expected 2 and got " + temp.getWeight());}
            } else if(size == 6){
                //Weight 1
                Edge temp = heap.getMin();
                if(temp.getWeight() != 1){throw new Exception("Incorrect Weight, expected 1 and got " + temp.getWeight());}
            } else {
                throw new Exception("Too many edges!");
            }
        }



        //Now we can test that the removal behaves correctly
        Edge rem = heap.pullMin();
        if(heap.size() != 5 || rem.getWeight() != 1){
            throw new Exception("The current root for size 6 should have been 1");
        }
        ArrayList<Long> items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");


        rem = heap.pullMin();
        if(heap.size() != 4 || rem.getWeight() != 2){
            throw new Exception("The current root for size 5 should have been 2");
        }
        items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");

        rem = heap.pullMin();
        if(heap.size() != 3 || rem.getWeight() != 3){
            throw new Exception("The current root for size 4 should have been 3");
        }
        items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");

        rem = heap.pullMin();
        if(heap.size() != 2 || rem.getWeight() != 4){
            throw new Exception("The current root for size 3 should have been 4");
        }
        items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");

        rem = heap.pullMin();
        if(heap.size() != 1 || rem.getWeight() != 7){
            throw new Exception("The current root for size 2 should have been 7");
        }
        items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");

        rem = heap.pullMin();
        if(heap.size() != 0 || rem.getWeight() != 8){
            throw new Exception("The current root for size 1 should have been 8");
        }
        items = heap.getKeys();

        for(Long i : items){
            System.out.println(i);
        }
        System.out.println("----");

    }

}
