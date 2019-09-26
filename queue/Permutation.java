import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        int n = 1;

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            int ind = StdRandom.uniform(0, n);
            if (k > 0 && n > k && ind < k) {
                queue.dequeue();
            }
            if (k > 0 && ind < k) {
                queue.enqueue(str);
            }
            n++;
        }

        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
