import java.util.Comparator;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Collection;

public class Test{
    public static void main(String[] args){
        Collection<Pair<String,ArrayList<String>>> c = new ArrayList<>();
        c.add(new Pair<>("Lily", null));
        c.add(new Pair<>("Lily", null));
        c.add(new Pair<>("Lily", null));
        c.add(new Pair<>("Lily", null));
        c.add(new Pair<>("Lily", null));
        // Testing the PriorityQueue
        System.out.println("Priority Queue with flowers ordered by name");
        class ComparatorByName implements Comparator<Pair<String, ArrayList<String>>>{
            public int compare(Pair<String, ArrayList<String>> p1,
                               Pair<String, ArrayList<String>> p2){
                return p1.getFirst().compareTo(p2.getFirst());
            }
        };
        PriorityQueue<Pair<String, ArrayList<String>>> pq = new PriorityQueue<>(new ComparatorByName());
        
        
        System.out.print("\nTest case 1: PriorityQueue constructued correctly \n");
        readFile(pq, "flowers.txt");
        System.out.println(pq.size() + " flowers read from the file and added to the priority queue");
        print(pq.iterator());

        System.out.print("\nTest case 2: Method contains (successful)\n");
        System.out.println("Tree contains \"Rose\"? " + pq.contains(new Pair<String, ArrayList<String>>("Rose", null)));
        System.out.print("\nTest case 3: Method contains (fail)\n");
        System.out.println("Tree contains \"Coquelicot\"? " + pq.contains(new Pair<String, ArrayList<String>>("Coquelicot", null)));
        System.out.print("\nTest case 4: Method remove (successful)\n");
        System.out.println("\"Hyacinth\" removed? " + pq.remove(new Pair<String, ArrayList<String>>("Hyacinth", null)));
        System.out.print("\nTest case 5: Method remove (fail)\n");
        System.out.println("\"Musk\" removed? " + pq.remove(new Pair<String, ArrayList<String>>("Musk", null)));
       
        System.out.print("\nTest case 6: Method addAll\n");
        PriorityQueue<Pair<String,ArrayList<String>>> pqCopy = (PriorityQueue<Pair<String,ArrayList<String>>>) (pq.clone());
       pqCopy.addAll(c);
        print(pqCopy.iterator()); 
        System.out.print("\nTest case 7: Method retainAll\n");
        pqCopy = (PriorityQueue<Pair<String,ArrayList<String>>>) (pq.clone());
        pqCopy.retainAll(c);
        print(pqCopy.iterator());
        System.out.print("\nTest case 8: Method removeAll\n");
        pqCopy = (PriorityQueue<Pair<String,ArrayList<String>>>) (pq.clone());
        pqCopy.removeAll(c);
        print(pqCopy.iterator()); 
    
        

        System.out.println("\nPriority Queue with flowers ordered by their number of colors");
        class ComparatorByColors implements Comparator<Pair<String, ArrayList<String>>>{
            public int compare(Pair<String, ArrayList<String>> p1,
                               Pair<String, ArrayList<String>> p2){
                return p1.getSecond().size() - p2.getSecond().size();
            }
        };
        pq = new PriorityQueue<>(new ComparatorByColors());
        
        readFile(pq, "flowers.txt");
        System.out.print("\nTest case 9: PriorityQueue constructued using the number of colors \n");
        print(pq.iterator());

        /////////////////////////////////////
        // Testing the TreeSet
        ////////////////////////////////////
        System.out.println("\nTreeSet with flowers ordered by name");
        TreeSet<Pair<String, ArrayList<String>>> tree = new TreeSet<>(new ComparatorByName());
        
        
        System.out.print("\nTest case 10: Tree constructued correctly \n");
        readFile(tree, "flowers.txt");
        System.out.println(tree.size() + " flowers read from the file and added to the binary search tree");
        print(tree.iterator());
        System.out.println();

        System.out.print("\nTest case 11: Method contains (successful)\n");
        System.out.println("Tree contains \"Rose\"? " + tree.contains(new Pair<String, ArrayList<String>>("Rose", null)));
        System.out.print("\nTest case 12: Method contains (fail)\n");
        System.out.println("Tree contains \"Coquelicot\"? " + tree.contains(new Pair<String, ArrayList<String>>("Coquelicot", null)));
        System.out.print("\nTest case 13: Method remove (successful)\n");
        System.out.println("\"Hyacinth\" removed? " + tree.remove(new Pair<String, ArrayList<String>>("Hyacinth", null)));
        System.out.print("\nTest case 14: Method remove (fail)\n");
        System.out.println("\"Musk\" removed? " + tree.remove(new Pair<String, ArrayList<String>>("Musk", null)));
        System.out.print("\nTest case 15: Method addAll\n");
        TreeSet<Pair<String,ArrayList<String>>> treeCopy = (TreeSet<Pair<String,ArrayList<String>>>) (tree.clone());
        //treeCopy.addAll(c);
        //print(treeCopy.iterator());
        System.out.print("\nTest case 16: Method retainAll\n");
        //treeCopy = (TreeSet<Pair<String,ArrayList<String>>>) (tree.clone());
        //treeCopy.retainAll(c);
        //print(treeCopy.iterator());
        System.out.print("\nTest case 17: Method removeAll\n");
        //treeCopy = (TreeSet<Pair<String,ArrayList<String>>>) (tree.clone());
        //treeCopy.removeAll(c);
        //print(treeCopy.iterator());
        System.out.print("\nTest case 18: Method first\n");
        System.out.println("First: " + tree.first());
        System.out.print("\nTest case 19: Method last\n");
        System.out.println("Last : " + tree.last());
        System.out.print("\nTest case 20: Method ceiling (succesful: key is the ceiling)\n");
        System.out.println("Ceiling(\"Daisy\") : " + tree.ceiling(new Pair<String, ArrayList<String>>("Daisy", null)));
        System.out.print("\nTest case 21: Method ceiling (succesful: ceiling found)\n");
        System.out.println("Ceiling(\"Dandrum\") : " + tree.ceiling(new Pair<String, ArrayList<String>>("Dandrum", null)));
        System.out.print("\nTest case 22: Method ceiling (fail: no ceiling)\n");
        System.out.println("Ceiling(\"Zebra\") : " + tree.ceiling(new Pair<String, ArrayList<String>>("Zebra", null)));
        System.out.print("\nTest case 23: Method floor (successful: key is the floor)\n");
        System.out.println("floor(\"Iris\") : " + tree.floor(new Pair<String, ArrayList<String>>("Iris", null)));
        System.out.print("\nTest case 24: Method floor (successful: floor found)\n");
        System.out.println("floor(\"Boba\") : " + tree.floor(new Pair<String, ArrayList<String>>("Boba", null)));
        System.out.print("\nTest case 25: Method floor (fail: no floor)\n");
        System.out.println("floor(\"Art\") : " + tree.floor(new Pair<String, ArrayList<String>>("Art", null)));


        System.out.println("\n\nTreeSet with flowers orderd by their number of colors");
        tree.clear();
        tree = new TreeSet<>(new ComparatorByColors());
        readFile(tree, "flowers.txt");
        System.out.print("\nTest case 26: Tree constructued correctly\n");
        print(tree.iterator());
        System.out.println();
    }
    
    public static void readFile(TreeSet<Pair<String,ArrayList<String>>> tree, String filename){
        try{
            Scanner read = new Scanner(new File(filename));
            while(read.hasNextLine()){
                String line = read.nextLine();
                String[] items = line.split(",");
                ArrayList<String> colors = new ArrayList<>();
                for(int i=1; i<items.length; i++){
                    colors.add(items[i]);
                }
                tree.add(new Pair<String, ArrayList<String>>(items[0], colors));
            }
            read.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
    }
    public static void readFile(PriorityQueue<Pair<String,ArrayList<String>>> pq, String filename){
        try{
            Scanner read = new Scanner(new File(filename));
            while(read.hasNextLine()){
                String line = read.nextLine();
                String[] items = line.split(",");
                ArrayList<String> colors = new ArrayList<>();
                for(int i=1; i<items.length; i++){
                    colors.add(items[i]);
                }
                pq.offer(new Pair<String, ArrayList<String>>(items[0], colors));
            }
            read.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found.");
        }
    }
    public static <E> void print(Iterator<E> iter){
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }
}