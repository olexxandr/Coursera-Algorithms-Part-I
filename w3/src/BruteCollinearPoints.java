import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*************************************************************************
 *  BruteCollinearPoints.java
 *
 *  Calculating number of colinear points using bruteforce alg.
 *
 *************************************************************************/
public class BruteCollinearPoints {

    private final List<LineSegment> segments = new ArrayList<LineSegment>();
    
    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null.");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point object is null.");
            }
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                if (points[j] == null) {
                    throw new IllegalArgumentException("Point object is null.");
                }
                if (points[i].compareTo(points[j]) == 0) {
                    System.out.println(points[i]);
                    throw new IllegalArgumentException("Some points are equal.");
                }
            }
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        Arrays.sort(pointsCopy);

        Point [] data = new Point[4];

        combinationUtil(pointsCopy, 0, data, 0);
    }

    /**
     * Recursive way to get n choose k
     * @param arr - array of points
     * @param index
     * @param data - array of permutations
     * @param i
     */
    private void combinationUtil(Point [] arr, int index, Point [] data, int i) {

        if (index == data.length) {

            double sl12, sl23, sl34, sl14;
            sl12 = data[0].slopeTo(data[1]);
            sl23 = data[1].slopeTo(data[2]);
            sl34 = data[2].slopeTo(data[3]);
            sl14 = data[3].slopeTo(data[0]);

            // if collinear => add to the list
            if (sl12 == sl23 && sl34 == sl14 && sl12 == sl34) {
                this.segments.add(new LineSegment(data[0], data[3]));
            }

            return;
        }

        // No more elements
        if (i >= arr.length)
            return;

        // include current and put next at next location
        data[index] = arr[i];
        this.combinationUtil(arr, index + 1, data, i + 1);

        // current is excluded, replace it with
        // next (Note that i+1 is passed, but
        // index is not changed)
        this.combinationUtil(arr, index, data, i + 1);
    }

    /**
     * the number of line segments.
     * @return
     */
    public int numberOfSegments() {
        return this.segments.size();
    }

    /**
     * Get all collinear LineSegments
     * @return
     */
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[0]);
    }

}
