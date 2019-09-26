import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node start;
    private Node end;
    private int size;

    // construct an empty deque
    public Deque() {
        start = null;
        end = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return start == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkItem(item);
        Node node = new Node(item);

        if (start != null) {
            node.next = start;
            start.prev = node;
            start = node;
        } else {
            start = node;
            end = node;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkItem(item);

        Node node = new Node(item);
        if (end != null) {
            node.prev = end;
            end.next = node;
            end = node;
        } else {
            addFirstElement(node);
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkNodeExist(start);

        Item value = start.value;
        if (start.next != null) {
            start.next.prev = null;
            start = start.next;
        } else {
            clearLastElement();
        }
        size--;
        return value;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkNodeExist(end);

        Item value = end.value;
        if (end.prev != null) {
            end.prev.next = null;
            end = end.prev;
        } else {
            clearLastElement();
        }
        size--;
        return value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        assert deque.isEmpty();
        assert deque.size() == 0;
        deque.addFirst("test1");
        deque.addLast("test2");
        assert deque.size() == 2;

        String val = deque.removeFirst();
        assert val.equals("test1");

        deque.addFirst("test3");
        val = deque.removeLast();
        assert val.equals("test2");

        for (String str : deque) {
            assert str.equals("test3");
        }
        assert !deque.isEmpty();

        Iterator<String> iterator = deque.iterator();
        int count = 0;
        if (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assert count == 1;
    }

    private void checkItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkNodeExist(Node node) {
        if (node == null) {
            throw new NoSuchElementException();
        }
    }

    private void addFirstElement(Node node) {
        start = node;
        end = node;
    }

    private void clearLastElement() {
        start = null;
        end = null;
    }

    private class Node {
        Node prev = null;
        Node next = null;
        Item value;

        private Node(Item v) {
            value = v;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode;

        DequeIterator() {
            currentNode = start;
        }

        public boolean hasNext() {
            return currentNode != null;
        }

        public Item next() {
            if (currentNode == null) throw new NoSuchElementException();
            Item value = currentNode.value;
            currentNode = currentNode.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
