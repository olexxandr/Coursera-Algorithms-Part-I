import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree {

    private Node root;

    private int size = 0;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;         // the right/top subtree
    }

    /**
     * construct an empty set of point
     */
    public KdTree() {

    }

    /**
     * is the set empty?
     *
     * @return
     */
    public boolean isEmpty() {

        return this.size == 0;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @param n
     * @param parent
     * @param p
     * @param isX
     * @return Node that
     */
    private Node insert(Node n, Node parent, Point2D p, boolean isX) {


        if (n == null) {

            RectHV rectHV;
            if (parent == null) {
                rectHV = new RectHV(0, 0, 1, 1);
            } else {
                double xMin = parent.rect.xmin();
                double yMin = parent.rect.ymin();
                double xMax = parent.rect.xmax();
                double yMax = parent.rect.ymax();


                if (isX) {
                    double parentY = parent.p.y();
                    int pointsCmpRes = Double.compare(p.y(), parentY);
                    if (pointsCmpRes == 1) {
                        // top rect
                        rectHV = new RectHV(xMin, parentY, xMax, yMax);
                    } else {
                        // bottom rect
                        rectHV = new RectHV(xMin, yMin, xMax, parentY);
                    }
                } else {
                    double parentX = parent.p.x();
                    int pointsCmpRes = Double.compare(p.x(), parentX);
                    if (pointsCmpRes == 1) {
                        // right rect
                        rectHV = new RectHV(parentX, yMin, xMax, yMax);
                    } else {
                        // left rect
                        rectHV = new RectHV(xMin, yMin, parentX, yMax);
                    }
                }
            }
            Node newNode = new Node();
            newNode.p = p;
            newNode.rect = rectHV;
            return newNode;
        }

        if (isX) {
            int cmp = Double.compare(p.x(), n.p.x());
            if (cmp == 1) {
                n.rt = insert(n.rt, n, p, !isX);
            } else {
                n.lb = insert(n.lb, n, p, !isX);
            }

        } else {
            int cmpRes = Double.compare(p.y(), n.p.y());
            if (cmpRes == 1) {
                n.rt = insert(n.rt, n, p, !isX);
            } else {
                n.lb = insert(n.lb, n, p, !isX);
            }
        }

        return n;
    }
    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Arg is null.");
        }

        if (!this.contains(p)) {
            this.root = this.insert(this.root, null, p, true);
            this.size++;
        }
    }

    /**
     * Check if the set contain point p
     *
     * @param p - Point instance to be checked
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        return this.contains(this.root, p);
    }

    /**
     * Private method to suport recursion
     *
     * @param p - point
     * @param node - tree node
     * @return
     */
    private boolean contains(Node node, Point2D p) {
        if (node == null || !node.rect.contains(p)) {
            return false;
        }
        if (node.p.equals(p)) {
            return true;
        }

        boolean isLeft = this.contains(node.lb, p);
        boolean isRight = this.contains(node.rt, p);

        return isLeft || isRight;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.01);

        this.draw(this.root, true);
        StdDraw.show();
    }

    /**
     * Private method to support recursion
     * @param n -  currently visiting node
     * @param isX - flag to determine if current level is horizontal or vertical
     */
    private void draw(Node n, boolean isX) {
        if (n == null) {
            return;
        }

        if (isX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(),  n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(),  n.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(n.p.x(), n.p.y());
        this.draw(n.lb, !isX);
        this.draw(n.rt, !isX);
    }

    /**
     * All points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Arg is null.");
        }

        ArrayList<Point2D> points = new ArrayList<>();
        range(this.root, points, rect);
        return points;
    }

    /**
     * Private method to support recursion
     * @param node
     * @param points
     * @param rect
     */
    private void range(Node node, ArrayList<Point2D> points, RectHV rect) {
        if (node == null || !node.rect.intersects(rect)) {
            return;
        } else {
            if (rect.contains(node.p)) {
                if (!points.contains(node.p)) {
                    points.add(node.p);
                }
            }
        }
        range(node.lb, points, rect);
        range(node.rt, points, rect);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param queryPoint - point to find a nearest neighbor for
     * @return
     */
    public Point2D nearest(Point2D queryPoint) {
        if (queryPoint == null) {
            throw new IllegalArgumentException("Arg is null.");
        }
        if (this.size == 0) {
            return null;
        }
        Point2D closestPoint = nearest(this.root, this.root.p, queryPoint);

        return closestPoint;
    }

    /**
     * Private method to support reqursion
     * @param node - node to inspect if containing point is nearest neighbor
     * @param closestPoint - current closest point
     * @param queryPoint - query point
     */
    private Point2D nearest(Node node, Point2D closestPoint, Point2D queryPoint) {

        if (node == null ||
                (Double.compare(node.rect.distanceSquaredTo(queryPoint), 0.0) != 0 &&
                Double.compare(closestPoint.distanceSquaredTo(queryPoint), node.rect.distanceSquaredTo(queryPoint)) == -1)) {
            return closestPoint;
        } else {
            if (Double.compare(closestPoint.distanceSquaredTo(queryPoint), node.p.distanceSquaredTo(queryPoint)) == 1) {
                closestPoint = node.p;
            }
        }

        Point2D lClosest, rClosest;
        rClosest = nearest(node.rt, closestPoint, queryPoint);
        lClosest = nearest(node.lb, closestPoint, queryPoint);

        if (lClosest == null) {
            return rClosest;
        } if (rClosest == null) {
            return lClosest;
        }
        if (Double.compare(lClosest.distanceSquaredTo(queryPoint), rClosest.distanceSquaredTo(queryPoint)) == -1) {
            return lClosest;
        } else {
            return rClosest;
        }
    }

    public static void main(String [] args) {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.7, 0.2));

        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));

        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        kdTree.draw();
    }
}
