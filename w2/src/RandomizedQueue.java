import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *
 *  Description:  RandomizedQueue implementation.
 *
 *
 **************************************************************************** */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr;

    private int size = 0;

    public RandomizedQueue() {
        this.arr = (Item[]) new Object[2];
    }

    /**
     * Returns boolean indicating if deque is empty
     *
     * @return true if deque is not empty otherwise false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of items on the deque
     *
     * @return number of item on deque
     */
    public int size() {
        return this.size;
    }

    /**
     * Adds item to the queue
     * @throws IllegalArgumentException if the item is null
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw  new IllegalArgumentException("Null item is not allowed.");
        }

        if (this.arr.length == this.size) {
            resize(2 * this.arr.length);
        }

        this.arr[this.size++] = item;
    }


    /**
     * Resize array.
     */
    private void resize(int len) {
        Item[] resizedArr = (Item[]) new Object[len];
        int i = 0;
        int j = 0;
        while (i < this.arr.length) {
            if (this.arr[i] != null) {
                resizedArr[j++] = this.arr[i];
            }
            i++;

        }
        this.arr = resizedArr;
    }

    /**
     * Remove and return a random item.
     * @throws NoSuchElementException if the item is null
     * @return item from the queue
     */
    public Item dequeue() {
        if (this.size == 0) {
            throw  new NoSuchElementException("Queue is empty.");
        }
        if (this.size > 0 && this.arr.length/4 == this.size) {
            resize(this.arr.length/2);
        }
        int randomIndex = StdRandom.uniform(this.size);
        while (this.arr[randomIndex] == null) {
            randomIndex = StdRandom.uniform(this.size);
        }

        Item item = this.arr[randomIndex];
        this.arr[randomIndex] = this.arr[this.size - 1];
        this.arr[this.size - 1] = null;
        this.size--;

        return item;
    }
    /**
     * Return a random item but does not remove it from the queue.
     *
     * @throws NoSuchElementException if the item is null
     * @return a random item (but do not remove it)
     */
    public Item sample()    {
        if (this.size == 0) {
            throw  new NoSuchElementException("Queue is empty.");
        }

        int randomIndex = StdRandom.uniform(this.size);
        while (this.arr[randomIndex] == null) {
            randomIndex = StdRandom.uniform(this.size);
        }
        return this.arr[randomIndex];
    }

    /**
     * Creates an independent iterator over items in random order
     *
     * @return DequeueIter instance
     */
    public Iterator<Item> iterator() {

        return new DequeueIter();
    }

    /**
     * Class {@code DequeueIter} class iterator.
     */
    private class DequeueIter implements Iterator<Item> {

        private int iterSize = 0;
        private Item[] iterArr;

        public DequeueIter() {
            this.iterArr = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                this.iterArr[i] = arr[i];
            }
        }

        @Override
        public boolean hasNext() {

            return this.iterSize < size && size > 0;
        }

        @Override
        public Item next() {
            if (size == 0 || this.iterSize >= size) {
                throw  new NoSuchElementException("Queue is empty.");
            }
            int randomIndex = size - this.iterSize;
            int lastIndex = 0;
            if (randomIndex > 0) {
                randomIndex = StdRandom.uniform(randomIndex);
                Item item = this.iterArr[randomIndex];
                lastIndex = size - this.iterSize - 1;
                this.iterArr[randomIndex] = this.iterArr[lastIndex];
                iterArr[lastIndex] = item;
            }
            this.iterSize++;

            return iterArr[lastIndex];
        }
    }
}
