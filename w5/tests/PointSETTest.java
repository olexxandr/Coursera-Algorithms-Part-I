import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import junit.framework.TestCase;


import java.util.Iterator;


public class PointSETTest extends TestCase {

    PointSET pointSET;

    public void setUp() throws Exception {
        pointSET = new PointSET();
        assertTrue("pointSet is empty", pointSET.isEmpty());
        assertEquals("pointSet size is 0", 0,  pointSET.size());

        pointSET.insert(new Point2D(.7, .2));

        pointSET.insert(new Point2D(.5, .4));
        pointSET.insert(new Point2D(.2, .3));

        pointSET.insert(new Point2D(.4, .7));
        pointSET.insert(new Point2D(.9, .6));
    }

    public void testIsEmpty() throws Exception {
        assertFalse("pointSet is not empty", pointSET.isEmpty());
    }

    public void testSize() throws Exception {
        assertEquals("pointSet size is 5", 5, pointSET.size());
    }

    public void testInsert() throws Exception {
        assertEquals("pointSET size is 5", 5, pointSET.size());
        pointSET.insert(new Point2D(.9, .62));
        assertEquals("pointSET size is 6", 6, pointSET.size());
    }

    public void testContains() throws Exception {
        assertTrue("pointSET contains (.9. , .6)", pointSET.contains(new Point2D(.9, .6)));
    }

    public void testRange() throws Exception {
        Iterable<Point2D> includedInRectIterable = pointSET.range(new RectHV(0.0, 0.0, 1.0, 1.0));
        int count = 0;
        Iterator<Point2D> it = includedInRectIterable.iterator();
        while (it.hasNext()) {
            count++;
            it.next();
        }
        assertEquals("Number of points in iterator is 5", 5, count);
    }

    public void testNearest() throws Exception {
        Point2D nearest = pointSET.nearest(new Point2D(.9, .5));
        assertEquals("Nearest point is (.9, .6)", new Point2D(.9, .6), nearest);

        Point2D nearest1 = pointSET.nearest(new Point2D(.0, .0));
        assertEquals("Nearest point is (.2, .3)", new Point2D(.2, .3), nearest1);
    }
}