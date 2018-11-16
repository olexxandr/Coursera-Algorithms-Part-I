import junit.framework.TestCase;


public class SolverTest extends TestCase {

    public void testIsSolvable0() throws Exception {
        int [][] tiles = {{ 1, 0}, {3, 2}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertTrue("Board is solvable", s.isSolvable());
        assertEquals("Number of moves 1", 1, s.moves());
    }

    public void testAnotherIsSolvable1() throws Exception {
        int [][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertTrue("Board is solvable", s.isSolvable());
        assertEquals("Number of moves 4", 4, s.moves());
    }
    public void testAnotherIsUnSolvable1() throws Exception {
        int [][] tiles = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertFalse("Board is solvable", s.isSolvable());
        assertEquals("Number of moves 0", -1, s.moves());
    }

    public void testAnotherIsUnSolvable2() throws Exception {
        int [][] tiles = {{1, 0}, {2, 3}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertFalse("Board is solvable", s.isSolvable());
        assertEquals("Number of moves 0", -1, s.moves());
    }

    public void testAnotherIsUnSolvable3() throws Exception {

        int [][] tiles = {{2,  3,  4,  8}, {1, 6, 0, 12}, {5, 10, 7, 11}, {9, 13, 14, 15}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertTrue("Board is solvable", s.isSolvable());
        for(Board b : s.solution()) {
            System.out.println(b);
        }
        assertEquals("Number of moves 13", 13, s.moves());
    }

    public void testAnotherIsUnSolvable4() throws Exception {

        int [][] tiles = {{0, 3}, {2, 1}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertTrue("Board is solvable", s.isSolvable());
        int count = 0;
        for(Board b : s.solution()) {
            count++;
            System.out.println(b);
        }
        assertEquals("Number of moves 6", 6, s.moves());
        System.out.println("solutions: " + count);
    }

    public void testIsSolvable2() throws Exception {
        int [][] tiles = {{ 4, 1, 3}, {0, 2, 6}, {7, 5, 8}};
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        assertTrue("Board is solvable", s.isSolvable());
        assertEquals("Number of moves 5", 5, s.moves());
    }

    public void moves() throws Exception {

    }

    public void solution() throws Exception {

    }
}