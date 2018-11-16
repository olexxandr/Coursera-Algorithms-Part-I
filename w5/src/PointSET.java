import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class PointSET {

    private final TreeSet<Point2D> treeSet;
    /**
     * construct an empty set of point
     */
    public PointSET() {
        this.treeSet = new TreeSet<>();
    }

    /**
     * Is the set empty.
     *
     * @return
     */
    public boolean isEmpty() {

        return this.treeSet.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return this.treeSet.size();
    }


    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p - point to be inserted
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        if (!this.treeSet.contains(p)) {
            this.treeSet.add(p);
        }
    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        return this.treeSet.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        for (Point2D p : this.treeSet) {
            StdDraw.point(p.x(), p.y());
        }
        StdDraw.show();
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        double minX = rect.xmin();
        double maxX = rect.xmax();
        double minY = rect.ymin();
        double maxY = rect.ymax();


        List<Point2D> points = new ArrayList<>();
        for (Point2D p : this.treeSet) {
            if (p.x() >= minX && p.y() >= minY && p.x() <= maxX && p.y() <= maxY) {
                points.add(p);
            }
        }
        return points;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        if (this.treeSet.isEmpty()) {
            return null;
        }
        Point2D nearest = this.treeSet.first();
        double distAct = p.distanceSquaredTo(nearest);
        for (Point2D point : this.treeSet) {

            double distCurr = p.distanceSquaredTo(point);
            int res = Double.compare(distCurr, distAct);
            if (res == -1) {
                distAct = distCurr;
                nearest = point;
            }
        }

        return nearest;
    }
}

