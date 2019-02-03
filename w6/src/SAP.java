import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;


/* *****************************************************************************
 *
 *  Description:  SAP implementation.
 *
 *
 **************************************************************************** */
public class SAP {

    private final Digraph digraph;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph g) {
        this.digraph = new Digraph(g);

    }

    /**
     * Calc distance to ancestor
     * @param anc - ancestor vertex
     * @param vertex - vertex
     * @return
     */
    private int getDistanceToAncestor(int anc, int vertex) {
        int dist = 0;
        if (anc != vertex) {
            BreadthFirstDirectedPaths depthFirstDirectedPathsW = new BreadthFirstDirectedPaths(this.digraph, vertex);
            dist = depthFirstDirectedPathsW.distTo(anc);
        }

        return dist;
    }

    /**
     * Get min length and ancestor
     * @param v - word id
     * @param w - word id
     * @return
     */
    private int[] getLengthAndAncestor(int v, int w) {
        int ancestor = ancestor(v, w);

        if (ancestor == -1) {
            return new int[] {-1, -1};
        }
        int distV = getDistanceToAncestor(ancestor, v);
        int distW = getDistanceToAncestor(ancestor, w);

        return new int[] {distV + distW, ancestor};
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     *
     * @param v - word id
     * @param w - word id
     * @return
     */
    public int length(int v, int w) {

        int[] data = getLengthAndAncestor(v, w);

        return data[0];
    }

    /**
     *
     * @param ancestors - all ancestors
     * @param v - word id
     * @param w - word id
     * @return
     */
    private int getMinAncestor(List<Integer> ancestors, int v, int w) {
        int minPath = Integer.MAX_VALUE;
        int minAnc = ancestors.get(0);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsV = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsW = new BreadthFirstDirectedPaths(this.digraph, w);

        for (int anc : ancestors) {
            int distV = breadthFirstDirectedPathsV.distTo(anc);
            int distW = breadthFirstDirectedPathsW.distTo(anc);
            int totalDist = distV + distW;
            if (minPath > totalDist) {
                minAnc = anc;
                minPath = totalDist;
            }
        }
        return minAnc;
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     *
     * @param v - word id
     * @param w - word id
     * @return
     */
    public int ancestor(int v, int w) {

        HashMap<Integer, Integer> hashMapV = new HashMap<>();
        Queue<Integer> queueV = new LinkedList<>();
        queueV.add(v);

        while (!queueV.isEmpty()) {
            int cur = queueV.remove();
            if (!hashMapV.containsKey(cur)) {
                hashMapV.put(cur, cur);
                Iterable<Integer> vAdj = this.digraph.adj(cur);
                for (int node : vAdj) {
                    queueV.add(node);
                }
            }
        }

        Queue<Integer> queueW = new LinkedList<>();
        queueW.add(w);
        HashMap<Integer, Integer> hashMapW = new HashMap<>();

        List<Integer> ancestors = new ArrayList<>();
        while (!queueW.isEmpty()) {
            int cur = queueW.remove();
            if (hashMapV.containsKey(cur) && !ancestors.contains(cur)) {
                ancestors.add(cur);
            }
            if (!hashMapW.containsKey(cur)) {
                hashMapW.put(cur, cur);
                Iterable<Integer> wAdj = this.digraph.adj(cur);
                for (int node : wAdj) {
                    queueW.add(node);
                }
            }
        }
        if (ancestors.isEmpty()) {
            return -1;
        }

        return getMinAncestor(ancestors, v, w);
    }


    /**
     * Shortest length for iterables
     * @param v - collection of vertexes
     * @param w - collection of vertexes
     * @return
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        int[] data = getLengthAndAncestorForIterables(v, w);
        return data[0];
    }

    /**
     * Get length and ancestors for iterables
     * @param v - collection of vertexes
     * @param w - collection of vertexes
     * @return
     */
    private int[] getLengthAndAncestorForIterables(Iterable<Integer> v, Iterable<Integer> w) {
        int shortest = Integer.MAX_VALUE;
        int [] minData = {-1, -1};
        int anc, distV, distW;
        for (Integer wEl : w) {
            if (wEl == null) {
                throw new IllegalArgumentException("Argument is null");
            }
        }

        for (Integer vEl : v) {
            if (vEl == null) {
                throw new IllegalArgumentException("Argument is null");
            }
        }
        BreadthFirstDirectedPaths depthFirstDirectedPathsV = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths depthFirstDirectedPathsW = new BreadthFirstDirectedPaths(this.digraph, w);
        for (Integer vEl : v) {
            for (Integer wEl : w) {
                anc = ancestor(vEl, wEl);
                distV = depthFirstDirectedPathsV.distTo(anc);
                distW = depthFirstDirectedPathsW.distTo(anc);

                int curLength = distV + distW;
                if (anc != -1) {
                    if (curLength < shortest) {
                        shortest = curLength;
                        minData[0] = curLength;
                        minData[1] = anc;
                    }
                }
            }
        }
        return minData;
    }

    /**
     *  a common ancestor that participates in shortest ancestral path; -1 if no such path.
     *
     * @param v - collection of vertexes
     * @param w - collection of vertexes
     * @return
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        int [] data = getLengthAndAncestorForIterables(v, w);
        if (data[0] == -1) {
            return -1;
        }
        return data[1];
    }
}
