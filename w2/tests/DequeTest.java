import junit.framework.TestCase;

import java.util.Iterator;

public class DequeTest extends TestCase {

    Deque<Integer> deque = new Deque<>();
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testIsEmpty() throws Exception {
        assertTrue(deque.isEmpty());

        deque.addFirst(2);
        deque.addFirst(3);

        assertFalse(deque.isEmpty());

        deque.removeFirst();
        assertFalse(deque.isEmpty());

        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }

    public void testSize() throws Exception {
        assertEquals(deque.size(), 0);
        deque.addFirst(2);
        assertEquals(deque.size(), 1);

        deque.addFirst(2);
        assertEquals(deque.size(), 2);

        deque.removeFirst();
        assertEquals(deque.size(), 1);

        deque.removeLast();
        assertEquals(deque.size(), 0);
    }

    public void testAddFirst() throws Exception {
        assertEquals(deque.size(), 0);
        deque.addFirst(2);
        assertEquals(deque.size(), 1);
    }

    public void testAddLast() throws Exception {
        assertEquals(deque.size(), 0);
        deque.addLast(2);
        assertEquals(deque.size(), 1);

    }

    public void testRemoveFirst() throws Exception {
        assertEquals(deque.size(), 0);
        deque.addLast(2);
        assertEquals(deque.size(), 1);
        int item = deque.removeFirst();
        assertEquals(item, 2);
    }

    public void testRemoveLast() throws Exception {
        assertEquals(deque.size(), 0);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(deque.size(), 2);
        int item = deque.removeLast();
        assertEquals(item, 2);

        item = deque.removeLast();
        assertEquals(item, 3);
    }

    public void testIterator() throws Exception {
        Iterator<Integer> it =  deque.iterator();
        assertEquals(it.hasNext(), false);

        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);

        it =  deque.iterator();
        assertEquals(it.hasNext(), true);
        int val = it.next();
        assertEquals(val, 4);

        val = it.next();
        assertEquals(val, 3);

        val = it.next();
        assertEquals(val, 2);

        assertEquals(it.hasNext(), false);
    }

    public void testTwoIterators() throws Exception {
        Iterator<Integer> it =  deque.iterator();

        assertEquals(it.hasNext(), false);

        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);


        it =  deque.iterator();
        int counter = 0;
        while(it.hasNext()) {
            it.next();
            counter++;
        }
        assertTrue(counter == 3);

        Iterator<Integer> anotherIt =  deque.iterator();
        counter = 0;
        while(anotherIt.hasNext()) {
            anotherIt.next();
            counter++;
        }
        assertTrue(counter == 3);
    }
}