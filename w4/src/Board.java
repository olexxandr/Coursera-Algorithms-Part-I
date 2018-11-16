
import java.util.ArrayList;

/**
 * Board class.
 */
public class Board {

    private final int [][] board;

    private final int dimension;

    private final int manhattan;

    private final int hamming;

    private Board twin = null;

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {

        if (blocks == null) {
            throw new IllegalArgumentException("Board is null.");
        }
        this.dimension = blocks.length;
        this.board = getBoardClone(blocks, blocks.length);

        this.manhattan = this.calcManhattan();
        this.hamming = this.calcHamming();
    }

    /**
     * board dimension
     * @return
     */
    public int dimension() {
        return this.dimension;
    }

    /**
     * number of blocks out of place
     * @return
     */
    private int calcHamming() {
        int count = 0;
        int goalVal = 0;
        for (int  i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {

                goalVal++;
                if (this.board[i][j] == 0) {
                    continue;
                }
                if (this.board[i][j] != goalVal) {
                    count++;
                }

            }
        }
        return count;
    }

    /**
     * number of blocks out of place
     * @return
     */
    public int hamming() {
        return this.hamming;
    }


    /**
     * sum of Manhattan distances between blocks and goal
     * @return
     */
    private int calcManhattan() {
        int  count = 0;
        int goalVal = 1;
        for (int  i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {

                if (goalVal >= this.dimension * this.dimension) {
                    continue;
                }


                if (this.board[i][j] != goalVal) {
                    count += this.numOfMoves(i, j, goalVal);
                }
                goalVal++;
            }
        }
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
        return this.manhattan;
    }

    private int numOfMoves(int goalI, int goalJ, int goalEl) {

        int curIndexI = 0, curIndexJ = 0;
        outerloop:
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.board[i][j] == goalEl) {
                    curIndexI = i;
                    curIndexJ = j;
                    break outerloop;
                }
            }

        }

        return Math.abs((curIndexI - goalI)) + Math.abs((curIndexJ - goalJ));
    }

    /**
     * is this board the goal board.
     * @return
     */
    public boolean isGoal() {
        int goalVal = 1;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {

                if (goalVal < this.dimension * this.dimension && this.board[i][j] != goalVal) {
                    return false;
                }
                if (goalVal >= this.dimension * this.dimension && this.board[i][j] != 0) {
                    return false;
                }
                goalVal++;
            }
        }

        return true;
    }

    /**
     * Generating twin
     */
    private Board generaterTwin() {
        int [][] boardClone = getBoardClone(this.board, this.dimension);


        int i = 0, j = 0;
        int el;
        if (boardClone[i][j] != 0) {

            if (boardClone[i][j + 1] != 0) {
                el = boardClone[i][j];
                boardClone[i][j] = boardClone[i][j + 1];
                boardClone[i][j + 1] = el;
            } else {
                el = boardClone[i][j];
                boardClone[i][j] = boardClone[i + 1][j];
                boardClone[i + 1][j] = el;
            }
        } else {
            el = boardClone[i][j + 1];
            boardClone[i][j + 1] = boardClone[i + 1][j];
            boardClone[i + 1][j] = el;
        }

        return new Board(boardClone);
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return
     */
    public Board twin() {
        if (this.twin == null) {
            this.twin = this.generaterTwin();
        }
        return this.twin;
    }

    /**
     * does this board equal y?
     * @param other - object to compare current instance with
     * @return
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;

        if (that.dimension != this.dimension) {
            return false;
        }

        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighbors = new ArrayList<>();

        int indI = 0, indJ = 0;
        outerloop:
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.board[i][j] == 0) {
                    indI = i;
                    indJ = j;

                    break outerloop;
                }
            }
        }

        int [][] neighbor;

        // there is a tile on the right
        if (indI + 1 < this.dimension) {
            neighbor = getBoardClone(this.board, this.dimension);
            neighbor[indI][indJ] = neighbor[indI + 1][indJ];
            neighbor[indI + 1][indJ] = 0;
            neighbors.add(new Board(neighbor));
        }

        // there is a tile on the left
        if (indI - 1 >= 0) {
            neighbor = getBoardClone(this.board, this.dimension);
            neighbor[indI][indJ] = neighbor[indI - 1][indJ];
            neighbor[indI - 1][indJ] = 0;
            neighbors.add(new Board(neighbor));
        }

        // there is a tile below
        if (indJ + 1 < this.dimension) {
            neighbor = getBoardClone(this.board, this.dimension);
            neighbor[indI][indJ] = neighbor[indI][indJ + 1];
            neighbor[indI][indJ + 1] = 0;
            neighbors.add(new Board(neighbor));
        }

        // there is a tile above
        if (indJ > 0 && indJ - 1 < this.dimension) {
            neighbor = getBoardClone(this.board, this.dimension);
            neighbor[indI][indJ] = neighbor[indI][indJ - 1];
            neighbor[indI][indJ - 1] = 0;
            neighbors.add(new Board(neighbor));
        }

        return neighbors;

    }

    private int [][] getBoardClone(int [][] arr, int dim) {
        int [][] boardClone = new int [dim][dim];
        for (int i = 0; i < dim; i++) {
            boardClone[i] = arr[i].clone();
        }
        return boardClone;
    }

    /**
     * string representation of this board (in the output format specified below)
     * @return
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.dimension + "\n");
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                s.append(String.format("%2d ", this.board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
