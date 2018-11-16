import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import junit.framework.TestCase;

import java.util.Iterator;


public class KdTreeTest extends TestCase  {


    KdTree kdTree;

    public void setUp() throws Exception {
        kdTree = new KdTree();
        assertTrue("KdTree is empty", kdTree.isEmpty());
        assertEquals("KdTree size is 0", 0, kdTree.size());

        kdTree.insert(new Point2D(.7, .2));

        kdTree.insert(new Point2D(.5, .4));
        kdTree.insert(new Point2D(.2, .3));

        kdTree.insert(new Point2D(.4, .7));
        kdTree.insert(new Point2D(.9, .6));
    }

    public void testIsEmpty() throws Exception {
        assertFalse("KdTree is not empty", kdTree.isEmpty());
    }

    public void testSize() throws Exception {
        assertEquals("KdTree size is 5", 5, kdTree.size());
    }

    public void testInsert() throws Exception {
        assertEquals("KdTree size is 5", 5, kdTree.size());
        kdTree.insert(new Point2D(.9, .62));
        assertEquals("KdTree size is 6", 6, kdTree.size());
    }

    public void testContains() throws Exception {
        assertTrue("KdTree contains (.9. , .6)", kdTree.contains(new Point2D(.9, .6)));
    }

    public void testContains_() throws Exception {
        /* - failed on trial 5 of 10000
    - query point          = (0.5, 0.75)
    - student   contains() = false
    - reference contains() = true
    - sequence of points inserted:
      A  0.25 0.5
      B  0.0 0.25
      C  0.75 0.75
      D  0.5 0.75
      E  0.75 0.0
      F  0.5 0.0
      G  0.75 0.5
      H  0.75 1.0
      I  0.5 0.5
      J  0.0 1.0
      */
        assertTrue("KdTree contains (.9. , .6)", kdTree.contains(new Point2D(.9, .6)));
    }

    public void testContainswithDuplicates() throws Exception {
        /** - failed after inserting point 8 of 10
         - student   size()    = 7
         - reference size()    = 6
         - student   isEmpty() = false
         - reference isEmpty() = false
         - sequence of points inserted:
         A  0.5 0.75
         B  0.0 0.5
         C  0.0 0.25
         D  0.75 0.5
         E  0.0 0.5
         F  1.0 0.5
         G  0.25 0.5
         H  1.0 0.5 */
        kdTree = new KdTree();
        assertTrue("KdTree is empty", kdTree.isEmpty());
        assertEquals("KdTree size is 0", 0, kdTree.size());

        kdTree.insert(new Point2D(0.5, 0.75));

        kdTree.insert(new Point2D(0.0, 0.5)); //d1
        kdTree.insert(new Point2D(0.0, 0.25));

        kdTree.insert(new Point2D(0.75, 0.5));

       assertTrue(kdTree.contains(new Point2D(0.0, 0.5)));

        kdTree.insert(new Point2D(0.0, 0.5)); //d1
        System.out.println("STR: " + kdTree.toString());

        assertEquals("KdTree size 2", 4, kdTree.size());

        kdTree.insert(new Point2D(1.0, 0.5)); //d2
        System.out.println("STR: " + kdTree.toString());

        kdTree.insert(new Point2D(0.25, 0.5));
        System.out.println("STR: " + kdTree.toString());
        assertTrue(kdTree.contains(new Point2D(1.0, 0.5)));
        kdTree.insert(new Point2D(1.0, 0.5)); //d2
        assertEquals("KdTree size 6", 6, kdTree.size());
    }

    public void testRange() throws Exception {
        Iterable<Point2D> includedInRectIterable = kdTree.range(new RectHV(0.0, 0.0, 1.0, 1.0));
        int count = 0;
        Iterator<Point2D> it = includedInRectIterable.iterator();
        while (it.hasNext()) {
            count++;
            it.next();
        }
        assertEquals("Number of points in iterator is 5", 5, count);
    }

    public void testNearest() throws Exception {
        Point2D nearest = kdTree.nearest(new Point2D(.9, .5));
        assertEquals("Nearest point is (.9, .6)", new Point2D(.9, .6), nearest);

        Point2D nearest1 = kdTree.nearest(new Point2D(.0, .0));
        assertEquals("Nearest point is (.2, .3)", new Point2D(.2, .3), nearest1);
    }

    public void testNearestSequence() throws Exception {

      /* A  0.7 0.2
         B  0.5 0.4
         C  0.2 0.3
         D  0.4 0.7
         E  0.9 0.6
         Seq: A E B C
         My: A B C E
      */

        kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        Point2D nearest = kdTree.nearest(new Point2D(0.72, 0.03));
        assertEquals("Nearest point is (.2, .3)", new Point2D(0.7, 0.2), nearest);
    }
}