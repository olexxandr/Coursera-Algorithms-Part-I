import junit.framework.TestCase;

import java.util.Iterator;


public class RandomizedQueueTest extends TestCase {
    RandomizedQueue<Integer> queue;

    public void setUp() throws Exception {
        super.setUp();
        queue = new RandomizedQueue<>();
    }

    public void testIsEmpty() throws Exception {
        assertTrue(queue.isEmpty());

        queue.enqueue(2);
        assertFalse(queue.isEmpty());
    }

    public void testSize() throws Exception {

        assertEquals(queue.size(), 0);

        queue.enqueue(2);
        assertEquals(queue.size(), 1);

        queue.dequeue();
        assertEquals(queue.size(), 0);
    }

    public void testEnqueue() {

        queue.enqueue(2);
        int el = queue.dequeue();
        assertEquals(el, 2);

        queue.enqueue(2);
        queue.enqueue(3);
        el = queue.dequeue();
        assertTrue(el == 2 || el == 3);

        el = queue.dequeue();
        assertTrue(el == 2 || el == 3);
    }

    public void testResize() {
        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.size() == 2);
    }

    public void testDequeue() throws Exception {
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);

        assertTrue(queue.size() == 3);

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        assertTrue(queue.size() == 0);

    }

    public void testSample() throws Exception {

        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        assertTrue(queue.size() == 5);

        int v1 = queue.sample();
        int v2 = queue.sample();
        assertTrue(v1 != v2);
        assertTrue(queue.size() == 5);

        queue.dequeue();
        assertTrue(queue.size() == 4);
    }

    public void testIterator() throws Exception {
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        assertTrue(queue.size() == 5);
        Iterator<Integer> it = queue.iterator();
        while (it.hasNext()) {
            it.next();
        }

        it = queue.iterator();
        while (it.hasNext()) {
            it.next();
        }

        assertTrue(queue.size() == 5);
    }

    public void testIteratorNext() throws Exception {

        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        assertTrue(queue.size() == 5);
        Iterator<Integer> it = queue.iterator();

        it.next();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();


        assertTrue(queue.size() == 1);
    }

    public void testTwoSyncIterators() throws Exception {

        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();

        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(1);
        q.enqueue(0);
        q.enqueue(4);
        q.enqueue(5);

        Iterator<Integer> it = q.iterator();
        Iterator<Integer> it2 = q.iterator();
        while (it.hasNext()) {
            System.out.println("Iterator it " + it.next());
            System.out.println("Iterator it2 " + it2.next());
        }
    }
}