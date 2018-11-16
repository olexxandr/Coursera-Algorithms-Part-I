import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *
 *  Description:  Deque implementation.
 *
 **************************************************************************** */

public class Deque<Item> implements Iterable<Item> {

    private Node front = null;

    private Node tail = null;

    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    /**
     * Class {@code Node} helper class to create doubly-linked list.
     */
    private class Node {
        private final Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    /**
     * Returns boolean indicating if deque is empty
     *
     * @return true if deque is not empty otherwise false
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Returns the number of items on the deque
     *
     * @return number of item on deque
     */
    public int size() {

        return size;
    }

    /**
     * Adds item in the front of the deque
     *
     * @throws IllegalArgumentException if the item is null
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw  new IllegalArgumentException("Null is not allowed.");
        }

        Node newNode = new Node(item);

        if (front == null) {
            front = newNode;
            tail = front;
        } else {
            front.prev = newNode;
            newNode.next = front;

            front = newNode;
        }
        size++;
    }

    /**
     * Adds item at the end of the deque
     *
     * @throws IllegalArgumentException if the item is null
     */
    public void addLast(Item item) {
        if (item == null) {
            throw  new IllegalArgumentException("Null is not allowed.");
        }

        Node newNode = new Node(item);

        if (tail == null) {
            tail = newNode;
            front = tail;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes first item from the front of the deque
     *
     * @throws NoSuchElementException if the deque is empty
     */
    public Item removeFirst() {

        if (size == 0) {
            throw  new NoSuchElementException("Deque is empty.");
        }
        Item item = front.item;
        if (size == 1) {
            front = null;
            front = null;
            tail = null;
        } else {
            front = front.next;
            front.prev = null;
        }

        size--;
        return item;
    }

    /**
     * Removes last item from the tail of the deque
     *
     * @throws NoSuchElementException if the deque is empty
     */
    public Item removeLast() {
        if (size == 0) {
            throw  new NoSuchElementException("Deque is empty.");
        }

        Item item = tail.item;

        if (size == 1) {
            tail = null;
            front = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return item;
    }

    /**
     * Creates deque iterator
     *
     * @throws NoSuchElementException if the deque is empty
     * @return DequeueIter instance
     */
    public Iterator<Item> iterator() {

        return new DequeueIter();
    }

    /**
     * Class {@code DequeueIter} class iterator.
     */
    private class DequeueIter implements Iterator<Item> {

        private Node current = front;
        private int iterSize = 0;

        @Override
        public boolean hasNext() {
            return this.iterSize < size && size > 0;
        }

        @Override
        public Item next() {
            if (size == 0 || this.iterSize >= size) {
                throw  new NoSuchElementException("Deque is empty.");
            }
            this.iterSize++;
            Node currentNode = this.current;
            this.current = currentNode.next;
            Item item = currentNode.item;
            return item;
        }
    }
}