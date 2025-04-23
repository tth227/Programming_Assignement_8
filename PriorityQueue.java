import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

public class PriorityQueue<E> implements Cloneable{
    // ArrayList where the nodes of the heap are stored
    private ArrayList<E> list;
    private Comparator<E> comp;
    /**
     * Default Constructor
     */
    public PriorityQueue(){
        list = new ArrayList<>();
        comp = null;
    }
    /**
     * Constructor with one arg
     * @param c the comparator object used to order the nodes of the heap
     * Time complexity: O(1)
     */
    public PriorityQueue(Comparator<E> c){
        list = new ArrayList<>();
        comp = c;
    }
    /**
     * private method to compare two E values using natural ordering or the comparator object
     * @param value1 the first value
     * @param value2 the second value
     * @return 0   if value1 and value2 are equal
     *         > 0 if value1 is after value2
     *         < 0 if value1 is before value2
     */
    private int compare(E value1, E value2){
        if(comp == null) {
            return ((Comparable) value1).compareTo(value2);
        }
        else {
            return comp.compare(value1, value2);
        }
    }
    /**
     * Method size
     * @return the number of nodes in the heap
     * Time complexity: O(1)
     */
    public int size(){
        return list.size(); 
    }
    /**
     * Method isEmpty
     * @return true if the heap is empty, false otherwise
     * Time complexity: O(1)
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }
    /**
     * Method to empty the heap
     * Time complexity: O(1)
     */
    public void clear(){
        list.clear(); 
    }
    /**
     * Method toString
     * @return a formatted string containing the values of the nodes of the heap
     * Time complexity: O(n)
     */
    public String toString(){
        return list.toString();
    }
    /**
     * Method to access the value of the root
     * @return the value of the root
     * Time complexity: O(1)
     */
    public E peek(){
        if (list.isEmpty()){
            throw new NoSuchElementException();
        }
        return list.get(0);
    }
    /**
     * Method offer
     * @param value to be added to the heap
     * reconstructs the heap to keep the MinHeap properties
     * Time complexity: O(logn)
     */
    public void offer(E value) {
        list.add(value);
        int currentIndex = list.size()-1; 
        checkHeap(currentIndex, true);
    }
    /**
     * Method pop
     * @return the value of the root, null if the heap is empty
     * reconstructs the heap to keep the MinHeap properties
     * Time complexity: O(logn)
     */
    public E poll() {
        if(list.isEmpty()) 
            return null;
        E removedItem = list.get(0);
        list.set(0, list.get(list.size()-1));
        list.remove(list.size()-1);
        int currentIndex = 0;
        checkHeap(currentIndex, false);
        return removedItem;
    }
    /**
     * checkHeap method
     * @param nodeIndex node where the checking should start
     * @param upDown true if checking from nodeIndex up to the root
     *               false if checking from nodeIndex down to the end of the heap 
     */
    private void checkHeap(int nodeIndex, boolean upDown) {
        if (upDown) {
            // base case
            if (nodeIndex == 0) {
            //reach the root, nothing to check;
            }

            int parentIndex = (nodeIndex - 1) /2;

            if (compare(list.get(nodeIndex),list.get(parentIndex)) < 0) {
                // Swaping
                E temp = list.get(nodeIndex);
                list.set(nodeIndex, list.get(parentIndex));
                list.set(parentIndex, temp);

                //recursively checking = offer()
                checkHeap(parentIndex, true); 
            }
        } 
        else {
            int left = 2 * nodeIndex + 1;
            int right = 2 * nodeIndex + 2;
            int min = nodeIndex;

        if (left < list.size() && compare(list.get(left),list.get(min)) < 0) {
            min = left;
        }
        if (right < list.size() && compare(list.get(right),list.get(min)) < 0) {
            min = right;
        }

        if (min != nodeIndex) {
            //swapping
            E temp = list.get(nodeIndex);
            list.set(nodeIndex, list.get(min));
            list.set(min, temp);

            //recursively checking = poll()
            checkHeap(min, false); 
        }
    }
}

    /**
     * Search method
     * @param o the object to lookup
     * @return true of o is found, false otherwise
     */
    public boolean contains(Object o){
        Iterator<E> iter = list.iterator();

        while(iter.hasNext()) {
            E element = iter.next();

            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }
    
    /**
        iterator() method
        @return iterator object pointing to the first element in the list
     */
    public Iterator<E> iterator(){
        //cloning the heap
        ArrayList<E> sortedList = new ArrayList<> (list);

        //sorted 
        if (comp != null) {
            sortedList.sort(comp); 
        }
        else {
            sortedList.sort(null); 
        }

    return new PriorityQueueIterator(sortedList);
}
   /*******************************************************
    * Inner class to implement the interface Iterator<E>  *
    * (ascending order implemented by the iterator)       *
    *******************************************************/
    private class PriorityQueueIterator implements Iterator<E>{
        private final ArrayList<E> list;
        private int size;
        /**
         * Default constructor
         */

        public PriorityQueueIterator(ArrayList<E> sortedList) {
            this.list = sortedList;
            this.size = 0;
        }

        /**
         *  @return true if current did not reach the end of the list, false otherwise
         */
        public boolean hasNext(){
            return size < list.size();
        }
        /**
         *    @return the value of the current element and moves to the next element
         *    @throws ArrayIndexOutOfBoundsException if no element can be accessed
         */
        public E next(){
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException("No more elements.");
            }
        return list.get(size++);
        }
    }

    /**
     * Method to remove a given object
     * @param o the object to find and remove
     * @return true if o was found and removed, false if o was not found
     */
    public boolean remove(Object o){
        for (int i = 0; i < list.size(); i++) {
            //key found
            if (list.get(i).equals(o)) {
                int lastIndex = list.size() - 1;
                
                //replace the current with the last element if it is not the last element
                if (i != lastIndex) {
                    list.set(i, list.get(lastIndex));
                }

                //remove the last element
                list.remove(lastIndex);

                //checking head
                if (i < list.size()) {
                    checkHeap(i, true);  //checking moving up
                    checkHeap(i, false); //checking moving down
                }

            return true;
        }
    }
    //key not found
    return false; 
}
    /**  
     * clone this heap
     * @return a deep copy of this heap
     */
    public Object clone(){
        PriorityQueue<E> cloned = new PriorityQueue<>();

        for (E element : this.list) {
            cloned.list.add(element);
        }

    return cloned;
    }

    /**
     * Adds the elements of the collection c to this list
     * @param c the collection to be added to this list
     * @return true if the addition was successful
     * Time complexity: O(n), where n is the size of the collection c
     */
    public boolean addAll(Collection<?> c){
        Iterator<?> iter = c.iterator();
        
        while (iter.hasNext()) {
            Object element = iter.next();
                offer((E) element);
            }
        
        return true;
    }


    /**
     * removes the elements of this heap that are not in the collection c
     * @param c the collection to be compared to this heap
     * @return true if the intersection was successful
     */
    public boolean retainAll(Collection<?> c){
        boolean flag = false;
        Iterator<E> iter = list.iterator();
        
        while (iter.hasNext()) {
            E element = iter.next();
                if (!c.contains(element)) {
                list.remove(element);
                flag = true;
            }
        }

        return flag;
    }
    /**
     * removes the elements of this heap that are in the collection c
     * @param c the collection to be compared to this heap
     * @return true if the difference was successful
     */
    public boolean removeAll(Collection<?> c){
       boolean flag = false;
        Iterator<E> iter = list.iterator();
        
        while (iter.hasNext()) {
            E element = iter.next();
                if (c.contains(element)) {
                list.remove(element);
                flag = true;
            }
        }

        return flag;
    }
}
