import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

/**
 * Solver class.
 */
public class Solver {

    private ArrayList<Board> solution;

    private int moves;

    private boolean isSolvable = false;

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;

        private final SearchNode prevSearchNode;

        private final int moves;

        private final int manhattan;

        public SearchNode(SearchNode prev, Board board, int moves) {

            this.board = board;

            this.prevSearchNode = prev;

            this.moves = moves;

            this.manhattan = board.manhattan();
        }

        public SearchNode getPrevSearchNode() {
            return this.prevSearchNode;
        }

        public int getMoves() {
            return this.moves;
        }

        public Board getBoard() {
            return this.board;
        }

        public int getPriority() {
            return this.manhattan + this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.getPriority(), that.getPriority());
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     *
     * @param initial
     */
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException("Initial board is null.");
        }

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(null, initial, 0));
        minPQTwin.insert(new SearchNode(null, initial.twin(), 0));

        this.solution = new ArrayList<>();
        this.moves = 0;
        SearchNode lsn = this.solve(minPQ, minPQTwin);

        if (this.isSolvable) {
            while (lsn.getPrevSearchNode() != null) {
                this.solution.add(0, lsn.getBoard());
                lsn = lsn.getPrevSearchNode();
            }
            this.solution.add(0, initial);
        } else {
            // if queues are empty => board is not solvable
            this.moves = -1;
            this.solution = null;
        }

    }

    /**
     * Process queues
     */
    private SearchNode solve(MinPQ<SearchNode> minPQ, MinPQ<SearchNode> minPQTwin) {
        while (!minPQ.isEmpty() && !minPQTwin.isEmpty()) {
            SearchNode sn = minPQ.delMin();
            Board currBoard = sn.getBoard();
            this.moves = sn.getMoves();
            if (currBoard.isGoal()) {
                this.isSolvable = true;
                return sn;
            }

            for (Board b : sn.getBoard().neighbors()) {
                if (b.isGoal()) {
                    this.isSolvable = true;
                    this.moves = sn.getMoves() + 1;
                    this.solution.add(b);
                    return sn;
                }


                SearchNode prevSearchNode = sn.getPrevSearchNode();
                if (prevSearchNode == null || !prevSearchNode.getBoard().equals(b)) {
                    minPQ.insert(new SearchNode(sn, b, sn.moves + 1));
                }
            }

            // process twin queue
            SearchNode snTwin = minPQTwin.delMin();
            Board currTwinBoard = snTwin.getBoard();
            int twinMoves = snTwin.getMoves();
            if (currTwinBoard.isGoal()) {
                this.isSolvable = false;
                this.moves = -1;
                this.solution = null;
                return null;
            }
            for (Board twinBoard : currTwinBoard.neighbors()) {

                if (twinBoard.isGoal()) {
                    this.isSolvable = false;
                    this.moves = -1;
                    this.solution = null;
                    return null;
                }

                SearchNode prevSearchNode = snTwin.getPrevSearchNode();
                if (prevSearchNode == null || !prevSearchNode.getBoard().equals(twinBoard)) {
                    minPQTwin.insert(new SearchNode(snTwin, twinBoard, twinMoves + 1));
                }
            }
        }


        return null;
    }

    /**
     * is the initial board solvable?
     *
     * @return
     */
    public boolean isSolvable() {
        return this.isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     *
     * @return
     */
    public int moves() {
        return this.moves;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     *
     * @return
     */
    public Iterable<Board> solution() {
        return this.solution;
    }
}
