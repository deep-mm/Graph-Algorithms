package baseline;
import java.util.ArrayList; 
import java.lang.Long;
//Since we know that a heap can be implemented as an array,
//and this saves us the trouble of having to resize with more elements.

//In this case the heap is going to be a min-heap, but I'm sure that this code could be generalized to
//work as a max-heap if desired
// Author: Gage Fringer (gwfringe)
public class BinHeap<K, V> {
    //Need to create a binary heap to use for things like Prim-Jarnik Alg

    //Given that prim-jarnik could include Edges or Nodes in this, we will generalize
    //it so that either can use this codebase.
    private ArrayList<HeapEntry<Long,V>> items;

    private long comparisons;

    //We can establish a heap after we determine the number of edges/nodes that exist

    public BinHeap(){
        items = new ArrayList<HeapEntry<Long, V>>();
        comparisons = 0;
    }

    public ArrayList<Long> getKeys(){ 
        ArrayList<Long> keys = new ArrayList<Long>();

        for(HeapEntry<Long, V> entry: items){
            keys.add(entry.getKey());
        }
        return keys;
    }

    public int size(){return items.size();}
    public boolean isEmpty(){ return items.size() == 0;}

    public V getMin(){
        if(!isEmpty())
            return items.get(0).getValue();
        return null;
    }

    //Same value as getMin, but takes it off the heap
    public V pullMin(){
        V temp = null;
        if(!isEmpty()){
            temp = items.get(0).getValue();
        } else {
            return null;
        }

        //Restructure the heap by moving the furthest leaf to the root
        HeapEntry<Long,V> newRoot = items.get(items.size() - 1);
        items.remove(items.size() - 1);
        if(items.size() > 0){
            items.set(0, newRoot);

            //Heapify time
            heapify(0);
        }
        return temp;

    }

    public void addItem(Long key, V value){
        int item_pos = items.size();
        HeapEntry<Long, V> temp = createEntry(key, value, item_pos);
        

        items.add(temp);
        //Add to the heap

        //We would not restructure when it is in the correct position or at the root (i.e. has nowhere to go)
        //This is basically our buildheap
        while(temp.getPosition() != 0){
            comparisons+=1;
            //System.out.println("Comparison of node: " + temp.getKey().longValue() + " against parent " + items.get(parent(temp.getPosition())).getKey().longValue());
            if(temp.getKey().longValue() < items.get(parent(temp.getPosition())).getKey().longValue()){
                //System.out.println("Swapping to position " + parent(temp.getPosition()));
                swap(temp.getPosition(), parent(temp.getPosition()));
                temp.setPosition(parent(temp.getPosition()));
            } else {
                break;
            }
        }
    }

    //Functions below are modeled from the generic priority queue implementation in CSC316
    public int parent(int idx){
        return (idx-1)/2;
    }

    public boolean hasLeft(int idx){
        return left(idx) < items.size(); //Check to make sure that the array is large enough
    }

    public int left(int idx){
            return 2*idx + 1;
    }

    public boolean hasRight(int idx){
        return right(idx) < items.size(); //Check to make sure that the array is large enough
    }

    public int right(int idx){
            return 2*idx + 2;
    }

    public void swap(int idx_a, int idx_b){
        HeapEntry<Long,V> temp = items.get(idx_a);
        items.set(idx_a, items.get(idx_b));
        items.set(idx_b, temp);

        //Make sure that their positions are updated for later references.
        //items.get(idx_b).setPosition(idx_a);
        //items.get(idx_a).setPosition(idx_b);
    }


    public long getComparisons(){
        return comparisons;
    }

    /*public void buildheap(){
        for(int i = items.size() - 1; i < 0; i--){
            heapify(i);
        }
    }*/

    public void decreaseKey(Long old_val, Long new_val){
        
        HeapEntry<Long,V> target = null;

        for(HeapEntry<Long, V> he : items){
            if(he.getKey() == old_val){
                he.setKey(new_val);
                target = he;
                break; //No need to iterate the rest of the list
            }
        }

        //Now we need to readjust as needed
        if(target != null){
            while(target.getPosition() != 0){
                comparisons+=1;
                if(target.getKey().compareTo(items.get(parent(target.getPosition())).getKey()) == -1){
                    swap(target.getPosition(), parent(target.getPosition()));

                    target.setPosition(parent(target.getPosition()));
                } else {
                    break;
                }
            }
        }

    }

    //Private method to handle recursion of creating the new heap after a removal
    public void heapify(int idx){
       /* ArrayList<Long> keys = getKeys();

        for(Long i : keys){
            System.out.println(i);
        }
        System.out.println("----");*/

        //Gather the children of the observed parent if possible
        HeapEntry<Long, V> l = null;
        if(hasLeft(idx)){
            l = items.get(left(idx));
        }
        HeapEntry<Long, V> r = null;
        if(hasRight(idx)){
            r = items.get(right(idx));
        }

        int smallest = idx; //this will swap to l or r if they are smaller

        HeapEntry<Long, V> smallNode = null;
        int smallNodeIdx = 0;
        //Compare against both children
        if(r != null && l != null){
            //comparisons += 1;
            if(l.getKey().compareTo(r.getKey()) <= 0){
                smallNode = l;
                smallNodeIdx = left(idx);
            } else {
                smallNode = r;
                smallNodeIdx = right(idx);
            }

            comparisons += 1;
            if(items.get(smallest).getKey().compareTo(smallNode.getKey()) == 1){
                smallest = smallNodeIdx;
            }
        } else if(r != null){
            comparisons += 1;
            if(items.get(smallest).getKey().compareTo(r.getKey()) == 1){
                smallest = right(idx);
            }
        } else if(l != null){
            comparisons += 1;
            if(items.get(smallest).getKey().compareTo(l.getKey()) == 1){
                smallest = left(idx);
            
            }
        }


        if(smallest != idx){
            swap(idx, smallest);
            heapify(smallest);
        }

    }

    //Helper method to take Keys and Values from input and turn them into a format that can be stored in the heap.
    private HeapEntry<Long, V> createEntry(Long key, V value, int pos){ return new HeapEntry<Long, V>(key, value, pos);}

    //To avoid warnings, the generic types of K/V in the private inner class are swapped to A/B, but mean the same thing
    private class HeapEntry<A, B>{

        Long key;
        B value;
        int position;

        HeapEntry(Long key, B value, int position){ this.key = key; this.value = value; this.position = position;}

        Long getKey(){ return key;}
        void setKey(Long val){ this.key = val;}
        B getValue(){ return value;}
        int getPosition(){return position;}
        void setPosition(int pos){this.position = pos;}
        //B setValue(B val){ this.value = val;}
    }
}
