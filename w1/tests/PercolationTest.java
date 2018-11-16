import junit.framework.TestCase;
/* *****************************************************************************
 *
 *  Description:  Percolation unit tests
 *
 **************************************************************************** */

public class PercolationTest extends TestCase {
    Percolation percolation;
    public void setUp() {
        percolation = new Percolation(3);
    }


    public void testOpen() throws Exception {
        assertFalse(percolation.isOpen(1, 1));
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
    }

    public void testIsOpen() throws Exception {
        assertFalse(percolation.isOpen(1, 1));
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
    }

    public void testIsFull() throws Exception {
        assertFalse(percolation.isFull(1, 1));

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        assertTrue(percolation.isFull(1, 1));

    }

    public void testNumberOfOpenSites() throws Exception {
        assertTrue(percolation.numberOfOpenSites() == 0);

        percolation.open(1, 1);
        assertTrue(percolation.numberOfOpenSites() == 1);
    }

    public void testPercolates1() throws Exception {
        assertFalse(percolation.percolates());
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 1);
        assertTrue(percolation.isFull(1, 1));
        assertTrue(percolation.isOpen(1, 1));
        assertTrue(percolation.percolates());
    }

    /**
     *
     * @throws Exception
     */
    public void testPPercolates2() throws Exception {
        assertFalse(percolation.percolates());
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        assertTrue(percolation.isFull(2, 1));
        assertTrue(percolation.isOpen(1, 1));
        assertFalse(percolation.percolates());
    }


    /**
     * @throws Exception
     */
    public void testPercolatesInput6() throws Exception {

        percolation = new Percolation(6);
        assertFalse(percolation.percolates());
        percolation.open(1, 6);
        assertTrue(percolation.isFull(1, 6));
        assertTrue(percolation.isOpen(1, 6));
        assertFalse(percolation.percolates());

        percolation.open(2, 6);
        assertTrue(percolation.isFull(2, 6));
        assertTrue(percolation.isOpen(2, 6));
        assertFalse(percolation.percolates());

        percolation.open(3, 6);
        assertTrue(percolation.isFull(3, 6));
        assertTrue(percolation.isOpen(3, 6));
        assertFalse(percolation.percolates());



        percolation.open(4, 6);
        assertTrue(percolation.isFull(4, 6));
        assertTrue(percolation.isOpen(4, 6));
        assertFalse(percolation.percolates());


        percolation.open(5, 6);
        assertTrue(percolation.isFull(5, 6));
        assertTrue(percolation.isOpen(5, 6));
        assertFalse(percolation.percolates());


        percolation.open(5, 5);
        assertTrue(percolation.isFull(5, 5));
        assertTrue(percolation.isOpen(5, 5));
        assertFalse(percolation.percolates());

        percolation.open(4, 4);
        assertFalse(percolation.isFull(4, 4));
        assertTrue(percolation.isOpen(4, 4));
        assertFalse(percolation.percolates());

        percolation.open(3, 4);
        assertFalse(percolation.isFull(3, 4));
        assertTrue(percolation.isOpen(3, 4));
        assertFalse(percolation.percolates());

        percolation.open(2, 4);
        assertFalse(percolation.isFull(2, 4));
        assertTrue(percolation.isOpen(3, 4));
        assertFalse(percolation.percolates());

        percolation.open(2, 3);
        assertFalse(percolation.isFull(2, 3));
        assertTrue(percolation.isOpen(2, 3));
        assertFalse(percolation.percolates());

        percolation.open(2, 2);
        assertFalse(percolation.isFull(2, 2));
        assertTrue(percolation.isOpen(2, 2));
        assertFalse(percolation.percolates());

        percolation.open(2, 1);
        assertFalse(percolation.isFull(2, 1));
        assertTrue(percolation.isOpen(2, 1));
        assertFalse(percolation.percolates());


        percolation.open(3, 1);
        assertFalse(percolation.isFull(3, 1));
        assertTrue(percolation.isOpen(3, 1));
        assertFalse(percolation.percolates());

        percolation.open(4, 1);
        assertFalse(percolation.isFull(4, 1));
        assertTrue(percolation.isOpen(4, 1));
        assertFalse(percolation.percolates());

        percolation.open(5, 1);
        assertFalse(percolation.isFull(5, 1));
        assertTrue(percolation.isOpen(5, 1));
        assertFalse(percolation.percolates());

        percolation.open(5, 2);
        assertFalse(percolation.isFull(5, 2));
        assertTrue(percolation.isOpen(5, 2));
        assertFalse(percolation.percolates());

        percolation.open(6, 2);
        assertFalse(percolation.isFull(6, 2));
        assertTrue(percolation.isOpen(6, 2));
        assertFalse(percolation.percolates());

        percolation.open(5, 4);
        assertTrue(percolation.isFull(5, 4));
        assertTrue(percolation.isOpen(5, 4));
        assertTrue(percolation.percolates());
    }
}