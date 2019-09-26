import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) doubleArray();
        queue[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        checkSize();
        int ind = StdRandom.uniform(0, size);
        Item value = queue[ind];
        queue[ind] = queue[size - 1];
        queue[size - 1] = null;
        size--;
        if (size < (queue.length / 4)) devideArray();
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkSize();
        int ind = StdRandom.uniform(0, size);
        return queue[ind];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new IteratorRandomizedQueue();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");

        for (String str : queue) {
            StdOut.println(str);
        }
        StdOut.println("-----");
        for (String str : queue) {
            StdOut.println(str);
        }
        StdOut.println("-----");

        StdOut.println(queue.sample());
        assert queue.size() == 4;
        StdOut.println(queue.dequeue());
        assert queue.size() == 3;
        assert !queue.isEmpty();

        queue.dequeue();
        StdOut.println(queue);
        queue.dequeue();
        StdOut.println(queue);
        queue.dequeue();
        StdOut.println(queue);
        assert queue.isEmpty();
    }

    private class IteratorRandomizedQueue implements Iterator<Item> {
        Item[] snapshot;
        int ind;

        private IteratorRandomizedQueue() {
            snapshot = (Item[]) new Object[size];
            copyArray(snapshot);
            StdRandom.shuffle(snapshot);
            ind = 0;
        }

        public boolean hasNext() {
            return ind < size;
        }

        public Item next() {
            if (ind == snapshot.length) throw new NoSuchElementException();
            return snapshot[ind++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void doubleArray() {
        Item[] temp = (Item[]) new Object[queue.length * 2];
        copyArray(temp);
        queue = temp;
    }

    private void devideArray() {
        Item[] temp = (Item[]) new Object[queue.length / 2];
        copyArray(temp);
        queue = temp;
    }

    private void copyArray(Item[] arr) {
        for (int i = 0; i < size; i++) {
            arr[i] = queue[i];
        }
    }

    private void checkSize() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }
}
