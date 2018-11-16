import junit.framework.TestCase;


public class FastCollinearPointsTest extends TestCase {

    FastCollinearPoints fcp;

    public void setUp() throws Exception {
        super.setUp();
        Point[] points = new Point[7];

        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(9, 9);
        points[3] = new Point(3, 3);
        points[4] = new Point(8, 8);
        points[5] = new Point(10, 10);
        points[6] = new Point(13, 13);
        this.fcp = new FastCollinearPoints(points);
    }
    public void testNumberOfSegments() throws Exception {
        assertNotNull(this.fcp);
        assertEquals(this.fcp.numberOfSegments(), 1);
    }

    public void testSegments() throws Exception {
        Point[] points = new Point[10];

        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(10, 10);
        points[3] = new Point(9, 9);
        points[4] = new Point(13, 13);
        points[5] = new Point(10, 1);
        points[6] = new Point(0, 1);
        points[7] = new Point(1, 2);
        points[8] = new Point(2, 3);
        points[9] = new Point(3, 4);

        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment [] segments = fcp.segments();
        assertEquals(2, fcp.numberOfSegments());
        assertEquals(new LineSegment(new Point(1, 1), new Point(13, 13)).toString(), segments[0].toString());
        assertEquals(new LineSegment(new Point(0, 1), new Point(3, 4)).toString(), segments[1].toString());
    }

    public void testSegmentsMultipleColinearPoints() throws Exception {
        Point[] points = new Point[5];

        points[0] = new Point(1, 1);
        points[1] = new Point(4, 4);
        points[2] = new Point(10, 10);
        points[3] = new Point(9, 9);
        points[4] = new Point(3, 30);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment [] segments = fcp.segments();
        assertEquals(1, segments.length);
        assertEquals(segments.length, fcp.numberOfSegments());
        assertEquals(new LineSegment(new Point(1, 1), new Point(10, 10)).toString(), segments[0].toString());
    }

    public void testSegmentsNoColinearPoints() throws Exception {
        Point[] points = new Point[5];

        points[0] = new Point(1, 5);
        points[1] = new Point(4, 2);
        points[2] = new Point(10, 10);
        points[3] = new Point(19, 9);
        points[4] = new Point(3, 30);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        LineSegment [] segments = fcp.segments();
        assertEquals(segments.length, 0);
        assertEquals(segments.length, fcp.numberOfSegments());
    }

    public void testIllegalArgumentExceptionIfPontsArrayIsNull() {
        boolean isExceptionThrown = false;

        try {
            new BruteCollinearPoints(null);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Points array is null.");
            isExceptionThrown = true;
        }

        assertTrue(isExceptionThrown);


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
            new FastCollinearPoints(points);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Points must be unique.");
            isExceptionThrown = true;
        }
        assertTrue(isExceptionThrown);

    }

    public void testIllegalArgumentExceptionIfAnyOfPointsIsEqualNull() {
        Point[] points = new Point[5];

        points[0] = new Point(1, 5);
        points[1] = new Point(4, 2);
        points[2] = new Point(10, 10);
        points[3] = new Point(19, 9);
        points[4] = null;
        boolean isExceptionThrown = false;
        try {
            new FastCollinearPoints(points);
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Point object is null");
            isExceptionThrown = true;
        }
        assertTrue(isExceptionThrown);

    }
}