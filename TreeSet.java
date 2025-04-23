import java.util.Comparator;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Class to implement a binary search tree data structure using linked nodes
 */
public class TreeSet<E> implements Cloneable{
    // data members
    private TreeNode root;
    private int size;
    private Comparator<E> comparator;
    // Inner class for the tree nodes
    private class TreeNode{
        E value;
        TreeNode left, right;
        TreeNode(E val){
            value = val;
            left = right = null;
        }
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
        if(comparator == null){
            return ((Comparable) value1).compareTo(value2);
        }
        else{
            return comparator.compare(value1, value2);
        }
    }
    /**
     * Default constructor
     * Create an empty binary search tree
     */
    public TreeSet(){
        root = null;
        size = 0;
        comparator = null;
    }
    /**
     * Constructor with a comparator
     * Create an empty binary search tree
     */
    public TreeSet(Comparator<E> c){
        root = null;
        size = 0;
        comparator = c;
    }
    /**
     * Getter of the size of the tree
     * @return the number of nodes in the tree
     */
    public int size(){
        return size;
    }
    /**
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return (size == 0);
    }
    /**
     * clears the tree
     */
    public void clear(){
        root = null;
        size = 0;
    }
    /**
     * Search method
     * @param element the value being looked up
     * @return true if element is found in the tree, false otherwise
     */
    public boolean contains(E element){
        return containsRecursive(root, element); 
    }

    //helper method
    private boolean containsRecursive(TreeNode node, E element) {
        //base case
        if (node == null) {
            return false;
        } 

        //recursive call
        int comp = compare(element, node.value);
        if (comp == 0) {
            return true;
        }
        else if (comp < 0) {
            return containsRecursive(node.left, element);
        }
        else {
            return containsRecursive(node.right, element);
        }
    }
    /**
     * Ading a new element to the tree
     * @param element the value to add in the tree
     * @return true if element was added succesfully, false if element already exists in the tree
     */
    public boolean add(E element){
        if (contains(element)) {
            return false;
        }
    
        root = addRecursive(root,element);
        size++;
        return true;
    }

    //helper method
    private TreeNode addRecursive(TreeNode node, E element) {
        //base case
        if (node == null) {
            return new TreeNode(element);
        }

        //resursive call
        int comp = compare(element,node.value);
        if (comp < 0) {
            node.left = addRecursive(node.left,element);
        }
        else if (comp > 0) {
            node.right = addRecursive(node.right,element);
        }

        return node;
    }

    /**
     * Removing an element from the tree
     * @param element the value to be removed from the tree
     * @return true if element was found and removed, false if element was not found
     */
    public boolean remove(E element){
        if (!contains(element)) {
            return false;
        }
        
        root = removeRecursive(root, element);
        size--;
        return true;
    }

    //helper methof
    private TreeNode removeRecursive(TreeNode node, E element) {
        //base case
        if (node == null) {
            return null;
        }

        int comp = compare(element, node.value);
        if (comp < 0) {
            node.left = removeRecursive(node.left, element);
        } 
        else if (comp > 0) {
            node.right = removeRecursive(node.right, element);
        }
        else {
        //node with 1 child or one child
            if (node.left == null) {
                return node.right;
            } 
            
            if (node.right == null) {
                return node.left;
            }

        //node with 2 children
        //find the rightmost node of the left subtree
        TreeNode parent = node;
        TreeNode pre = node.left;
        while (pre.right != null) {
            parent = pre;
            pre = pre.right;
        }

        //replace node's value with predecessor's value
        node.value = pre.value;

        // Delete the predecessor node from left subtree
        if (parent == node) {
            // Predecessor is direct left child
            parent.left = pre.left;
        } else {
            parent.right = pre.left;
        }
    }
    return node;
}
   
    /**
     * Preorder Traversal method
     */
    public void preorder(){
        System.out.print("[");
        preorder(root);
        System.out.println("]");
    }
    /**
     * Preorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void preorder(TreeNode node){
        if(node != null){
            System.out.print(node.value + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }
    /**
     * Inorder Traversal method
     */
    public void inorder(){
        System.out.print("[");
        inorder(root);
        System.out.println("]");
    }
    /**
     * Inorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void inorder(TreeNode node){
        if(node != null){
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }
    }
    /**
     * Postorder Traversal method
     */
    public void postorder(){
        System.out.print("[");
        postorder(root);
        System.out.println("]");
    }
    /**
     * Postorder Traversal recursive helper method
     * @param node the node where the traversal starts
     */
    public void postorder(TreeNode node){
        if(node != null){
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.value + " ");
        }
    }  
    /**
     * Adds the elements of the collection c to this tree
     * @param c the collection to be added to this tree
     * @return true if the addition was successful
     */
    public boolean addAll(Collection<? extends E> c){
        boolean flag = false;
        Iterator<? extends E> iter = c.iterator();
        
        while (iter.hasNext()) {
            if (add(iter.next())) {
                flag = true;
            }
        }
    return flag;
    }
    /**
     * removes the elements of this tree that are not in the collection c
     * @param c the collection to be compared to this tree
     * @return true if the intersection was successful
     */
    public boolean retainAll(Collection<?> c){
        boolean flag = false;
        Iterator<?> iter = c.iterator();
        
        while (iter.hasNext()) {
            Object element = iter.next();
            if (!contains((E) element)) {
                if (remove((E) element)) {
                    flag = true;
                }
            }
        }
    return flag;
       
    }

    /**
     * removes the elements of this tree that are in the collection c
     * @param c the collection to be compared to this tree
     * @return true if the difference was successful
     */
    public boolean removeAll(Collection<?> c){
        boolean flag = false;
        Iterator<?> iter = c.iterator();
        
        while (iter.hasNext()) {
            Object element = iter.next();
            if (contains((E) element)) {
                if (remove((E) element)) {
                    flag = true;
                }
            }
        }
    return flag;
    }

    /**
     * first method
     * @return the lowest value in the tree
     */
    public E first(){
        TreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        
    return current.value;
    }
    /**
     * last method
     * @return the highest value in the tree
     */
    public E last(){
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        
    return current.value;
    }
    /**
     * ceiling method
     * @return the least element in this tree greater than or equal to the given value, or
     *         null if there is no such element
     */
    public E ceiling(E value){
        TreeNode current = root;
        E ceil = null;
        
        while (current != null) {
            int comp = compare(value,current.value);
            if (comp == 0) {
                return current.value;
            } 
            else if (comp < 0) {
                ceil = current.value;  
                current = current.left;
            } 
            else {
                current = current.right;
            }
        }
    return ceil;
    }
    /**
     * floor method
     * @return the greatest element in this tree less than or equal to the given value, or 
     *         null if there is no such element
     */
    public E floor(E value){
        TreeNode current = root;
        E floor = null;
        
        while (current != null) {
            int comp = compare(value,current.value);
            if (comp == 0) {
                return current.value;
            } 
            else if (comp > 0) {
                floor = current.value;  
                current = current.right;
            } 
            else {
                current = current.left;
            }
        }
    return floor;
    }

    /**
     *   iterator() method
     *   @return iterator object pointing to the first element in the inorder traversal
     */
    public Iterator<E> iterator(){
        return  new TreeSetIterator();
    }
    /*******************************************************
     * Inner class to implement the interface Iterator<E>  *
     * (inorder traversal implemented by the iterator)     *
     *******************************************************/
    private class TreeSetIterator implements Iterator<E>{
        private ArrayList<E> list;
        private int index;

    /**
     * Default constructor
     */
    public TreeSetIterator() {
        list = new ArrayList<>();
        index = 0;
        inorderTraversal(root); 
    }

    private void inorderTraversal(TreeNode node) {
        if (node == null) return;
        inorderTraversal(node.left);
        list.add(node.value);
        inorderTraversal(node.right);
    }

        /**
         *    @return true if there is a next element to access in the tree, false otherwise
         */
        public boolean hasNext(){
            return index < list.size();
        }
        
        /**
         * @return the value of the current element and moves to the next element
         * @throws ArrayIndexOutOfBoundsException if there is no current element
         */
        public E next(){
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException("No more elements in the tree.");
            }
        return list.get(index++); 
    }
}
    /**
     * clone this tree
     * @return a deep copy of this tree
     */
    public Object clone(){
        TreeSet<E> cloned = new TreeSet<>();
        Iterator<E> iter = this.iterator();
        
        while (iter.hasNext()) {
            cloned.add(iter.next());
        }
    return cloned;
    }
}