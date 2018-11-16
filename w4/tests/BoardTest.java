import junit.framework.TestCase;


public class BoardTest extends TestCase {

    int [][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

    Board board;

    public void setUp() throws Exception {
        this.board = new Board(this.tiles);
    }

    public void testDimension() throws Exception {
        assertEquals("Dimension equal 3", 3, this.board.dimension());
    }

    public void testHamming() throws Exception {
        assertEquals("Hamming value", 5, this.board.hamming());
    }

    public void testManhattan() throws Exception {
        int [][] tiles = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}};
        Board board = new Board(tiles);
        assertEquals("Manhattan value 4", 4, board.manhattan());
    }

    public void testIsGoal() throws Exception {
        assertFalse("Not a goal board", this.board.isGoal());

        int [][] goalTiles = {{ 1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goalTiles);
        assertTrue("Is a goal board", goalBoard.isGoal());
    }

    public void testTwin() throws Exception {

    }

    public void testEquals() throws Exception {
        int [][] goalTiles = {{ 1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goalTiles);

        assertFalse("Not equal boards", goalBoard.equals(this.board));

        int [][] anotherGoalTiles = {{ 1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board anotherGoalBoard = new Board(anotherGoalTiles);

        assertTrue("Boards are equal", goalBoard.equals(anotherGoalBoard));
    }

    public void testNeighbors() throws Exception {
        Iterable<Board> neighbors = this.board.neighbors();

        int numOfNeighbors = 0;
        for (Board b : neighbors) {
            numOfNeighbors++;
        }
        assertEquals("Should have 4 neighbors", 4, numOfNeighbors);
    }

    public void testToString() throws Exception {
        String goalToStringExpectedResult = "3\n 1  2  3 \n 4  5  6 \n 7  8  0 \n";
        int [][] goalTiles = {{ 1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board goalBoard = new Board(goalTiles);
        assertEquals("toString() results are equal", goalToStringExpectedResult, goalBoard.toString());

        String goalToStringExpectedResult1 = "4\n 1  2  3  4 \n 5  6  7  8 \n 9 10 11 12 \n13 14 15 16 \n";
        int [][] goalTiles1 = {{ 1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        Board goalBoard1 = new Board(goalTiles1);
        assertEquals("toString() results are equal", goalToStringExpectedResult1, goalBoard1.toString());
    }
}