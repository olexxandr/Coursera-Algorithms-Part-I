import edu.princeton.cs.algs4.StdIn;

/* *****************************************************************************
 *
 *  Description:  Permutation implementation.
 *
 *
 **************************************************************************** */
public class Permutation {
    private Permutation() { }
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        int n = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        while (n > 0) {
            System.out.println(q.dequeue());
            n--;
        }
    }
}
