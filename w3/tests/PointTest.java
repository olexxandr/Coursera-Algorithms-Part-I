import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Comparator;


public class PointTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testSlopeTo() throws Exception {
        Point p = new Point(2, 3);
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 4);
        Point p3 = new Point(3, 4);
        Point p5 = new Point(3, 3);

        Double negativeInfinitySlope = p.slopeTo(p1);
        assertEquals(negativeInfinitySlope, Double.NEGATIVE_INFINITY );

        // vertical line
        Double verticalSlopeValue = p.slopeTo(p2);
        assertEquals(verticalSlopeValue, Double.POSITIVE_INFINITY);

        //horizontal line
        Double horizSlopeValue = p.slopeTo(p5);
        assertEquals(horizSlopeValue, 0.0);

        Double slopeValue = p.slopeTo(p3);
        assertEquals(slopeValue, 1.0);


        // colinear points
        Point [] points = new Point[4];
        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(3, 3);

        points[3] = new Point(9, 9);

        Double sl12, sl23, sl34,  sl41;
        sl12 = points[0].slopeTo(points[1]);
        sl23 = points[1].slopeTo(points[2]);
        sl34 = points[2].slopeTo(points[3]);
        sl41 = points[3].slopeTo(points[0]);
        assertEquals(sl12, sl41);
        assertEquals(sl23, sl12);
        assertEquals(sl34, sl41);

    }

    public void testCompareTo() throws Exception {

        Point p = new Point(2, 3);
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 4);
        Point p3 = new Point(2, 2);

        // equal points
        assertEquals(p.compareTo(p1), 0);

        // smaller points
        assertEquals(p.compareTo(p2), -1);

        // smaller points
        assertEquals(p.compareTo(p3), 1);
    }

    public void testSlopeOrder() throws Exception {

        Point p = new Point(2, 3);
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 3);
        Point p3 = new Point(3, 5);
        Point p4 = new Point(1, 1);
        Point p6 = new Point(5, 5);

        Point [] points = {p4, p3, p1, p2, p, p6};

        Comparator<Point> comparator = p4.slopeOrder();

        Arrays.sort(points, comparator);
        System.out.println("\nComparator: \n");
        for(int i = 0; i < points.length; i++) {

            System.out.println(points[i]);
        }

        System.out.println("\nNatural: \n");

        Arrays.sort(points, Point::compareTo);
        for(int i = 0; i < points.length; i++) {
            System.out.println(points[i]);
        }

    }

    public void testToString() throws Exception {
        Point p = new Point(2, 3);

        String pointAsString = p.toString();
        assertEquals(pointAsString, "(" + Integer.toString(2) + ", " + Integer.toString(3) + ")");
    }
}