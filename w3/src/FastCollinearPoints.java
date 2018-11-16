import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*************************************************************************
 *  FastCollinearPoints.java
 *
 *  Calculating number of colinear points.
 *
 *************************************************************************/
public class FastCollinearPoints {

    private final List<LineSegment> segments = new ArrayList<>();

    /**
     * finds all line segments containing 4 and more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point object is null");
            }
        }

        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        for (int i = 0; i < pointsCopy.length; i++) {
            Comparator<Point> comparator = pointsCopy[i].slopeOrder();

            Point[] copy = Arrays.copyOf(pointsCopy, pointsCopy.length);
            Arrays.sort(copy, comparator);
            double [] slopesList = new double[copy.length - 1];
            double prevSlope;

            int index = 0;
            ArrayList<ArrayList<Point>> collOfCollinLists = new ArrayList<>();
            ArrayList<Point> collList = new ArrayList<>();

            int numOfDuplicates = 0;
            for (int ii = 0; ii < copy.length; ii++) {
                if (ii == 0) {
                    collList = new ArrayList<>();
                }
                if (pointsCopy[i].compareTo(copy[ii]) == 0) {
                    numOfDuplicates += 1;
                    if (numOfDuplicates > 1) {
                        throw new IllegalArgumentException("Points must be unique.");
                    }
                    continue;
                }

                double sl = pointsCopy[i].slopeTo(copy[ii]);

                slopesList[index] = sl;
                if (index >= 1) {
                    prevSlope = slopesList[index - 1];
                    if (Double.compare(sl, prevSlope) == 0) {
                        collList.add(copy[ii]);
                    } else {
                        if (collList.size() >= 3) {
                            collOfCollinLists.add(collList);
                            collList = new ArrayList<>();
                            collList.add(copy[ii]);
                        } else {
                            collList.clear();
                            collList.add(copy[ii]);
                        }
                    }
                } else {
                    collList.add(copy[ii]);
                }
                index += 1;
            }

            if (collList.size() >= 3) {
                collOfCollinLists.add(collList);
            }

            for (ArrayList<Point> colList : collOfCollinLists) {
                colList.add(pointsCopy[i]);
                colList.sort(Point::compareTo);

                if (pointsCopy[i].equals(colList.get(0))) {
                    segments.add(new LineSegment(pointsCopy[i], colList.get(colList.size() - 1)));
                }
            }
        }
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
