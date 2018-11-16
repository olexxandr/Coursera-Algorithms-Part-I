import junit.framework.TestCase;


public class BruteCollinearPointsTest extends TestCase {

    BruteCollinearPoints bcp;

    public void setUp() throws Exception {
        super.setUp();
        Point[] points = new Point[4];

        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(9, 9);
        points[3] = new Point(3, 3);
        this.bcp = new BruteCollinearPoints(points);
    }
    public void testNumberOfSegments() throws Exception {
        assertNotNull(this.bcp);
        assertEquals(this.bcp.numberOfSegments(), 1);
    }

    public void testSegments() throws Exception {
        assertNotNull(this.bcp);
        LineSegment [] segments = this.bcp.segments();
        assertEquals(segments.length, bcp.numberOfSegments());
        assertEquals(segments[0].toString(), new LineSegment(new Point(1, 1), new Point(9, 9)).toString());
    }

    public void testSegmentsMultipleColinearPoints() throws Exception {
        Point[] points = new Point[5];

        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(10, 10);
        points[3] = new Point(9, 9);
        points[4] = new Point(3, 30);
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        LineSegment [] segments = bcp.segments();
        assertEquals(1, segments.length);
        assertEquals(segments.length, bcp.numberOfSegments());
        assertEquals(segments[0].toString(), new LineSegment(new Point(1, 1), new Point(10, 10)).toString());
    }

    public void testSegmentsNoColinearPoints() throws Exception {
        Point[] points = new Point[5];

        points[0] = new Point(1, 5);
        points[1] = new Point(4, 2);
        points[2] = new Point(10, 10);
        points[3] = new Point(19, 9);
        points[4] = new Point(3, 30);
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        LineSegment [] segments = bcp.segments();
        assertEquals(segments.length, 0);
        assertEquals(segments.length, bcp.numberOfSegments());
    }

    public void testIllegalArgumentExceptionIfPontsArrayIsNull() {
        try {
            new BruteCollinearPoints(null);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Points array is null.");
        }
    }

    public void testIllegalArgumentExceptionIfAnyOfPointsAreEqual() {
        Point[] points = new Point[5];

        points[0] = new Point(1, 5);
        points[1] = new Point(4, 2);
        points[2] = new Point(10, 10);
        points[3] = new Point(19, 9);
        points[4] = new Point(1, 5);
        boolean isExceptionThrown = false;

        try {
            new BruteCollinearPoints(points);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Some points are equal.");
            isExceptionThrown = true;
        }
        assertTrue(isExceptionThrown);

    }

    public void testIllegalArgumentExceptionIfAnyOfPointsIsNull() {
        Point[] points = new Point[5];

        points[0] = new Point(1, 5);
        points[1] = new Point(4, 2);
        points[2] = new Point(10, 10);
        points[3] = new Point(19, 9);
        points[4] = null;
        boolean isExceptionThrown = false;


        try {
            new BruteCollinearPoints(points);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Point object is null.");
            isExceptionThrown = true;
        }

        assertTrue(isExceptionThrown);

    }
}