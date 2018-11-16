/* *****************************************************************************
 *
 *  Description:  Percolation calcualtion
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int n; // size of the grid
    private final int virtualSiteBottomIndex;
    private final int virtualSiteTopIndex;
    private int numOfOpenSites = 0;

    /**
     * @param n - dimension of a grid (n * n)
     */
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n should not be less or equal 0.");
        }
        this.n = n;
        this.grid = new boolean[n][n];

        this.weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        this.virtualSiteBottomIndex = n * n;
        this.virtualSiteTopIndex = (n * n) + 1;
    }

    /**
     * @param row - row index
     * @param col - col index
     */
    public void open(int row, int col) {
        this.checkIfIndexOutOfRange(row, col);

        if (!this.grid[row - 1][col - 1]) {
            this.grid[row - 1][col - 1] = true;
            this.numOfOpenSites++;
        } else {
            return;
        }

        // check a site above curr site (row - 1, col)
        int currentIndex = this.toIndexInOneDimArray(row, col);
        if (row - 2 >= 0) {
            if (this.grid[row - 2][col - 1]) {
                int aboveSiteIndex = this.toIndexInOneDimArray(row - 1, col);
                this.weightedQuickUnionUF.union(currentIndex, aboveSiteIndex);
            }
        }

        // check a site below curr site (row + 1, col)
        if (row < this.n) {
            if (this.grid[row][col - 1]) {
                int belowSiteIndex = this.toIndexInOneDimArray(row + 1, col);
                this.weightedQuickUnionUF.union(currentIndex, belowSiteIndex);
            }
        }

        // check a site on the left (row, col - 1)
        if (col - 2 >= 0) {
            if (this.grid[row - 1][col - 2]) {
                int leftSiteIndex = this.toIndexInOneDimArray(row, col - 1);
                this.weightedQuickUnionUF.union(currentIndex, leftSiteIndex);
            }
        }

        // check a site on the right (row, col + 1)
        if (col < this.n) {
            if (this.grid[row - 1][col]) {
                int rightSiteIndex = this.toIndexInOneDimArray(row, col + 1);
                this.weightedQuickUnionUF.union(currentIndex, rightSiteIndex);
            }
        }

        int index = this.toIndexInOneDimArray(row, col);


        // if a top-row cell is opened then connect it to the top
        if (row == 1) this.weightedQuickUnionUF.union(this.virtualSiteTopIndex, index);

        // if a bottom-raw cell is opened then connect it to the bottom
        if (row == n) this.weightedQuickUnionUF.union(this.virtualSiteBottomIndex, index);

    }

    /**
     * @param row - row index
     * @param col - col index
     * @return index of onedimensional array
     */
    private int toIndexInOneDimArray(int row, int col) {
        if (row < 1) {
            row = 1;
        }

        return ((row - 1) * this.n) + col - 1;
    }

    /**
     * @param row - site row index
     * @param col - site col index
     * @return true if site is open otherwise false
     */
    public boolean isOpen(int row, int col) {
        this.checkIfIndexOutOfRange(row, col);

        return this.grid[row - 1][col - 1];
    }

    /**
     * @param row - site row index
     * @param col - site col index
     * @return true if site percolates otherwise false
     */
    public boolean isFull(int row, int col) {
        this.checkIfIndexOutOfRange(row, col);
        int index = this.toIndexInOneDimArray(row, col);
//
//        return  this.weightedQuickUnionUF.connected(this.virtualSiteTopIndex, index) &&
//                this.weightedQuickUnionUF.connected(this.virtualSiteBottomIndex, index);
        return this.grid[row - 1][col - 1] && this.weightedQuickUnionUF.connected(index, this.virtualSiteTopIndex);
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {

        return this.numOfOpenSites;
    }

    /**
     * @return true if system percolates otherwise false
     */
    public boolean percolates() {
        return this.numOfOpenSites > 0 &&
                this.weightedQuickUnionUF.connected(this.virtualSiteTopIndex, this.virtualSiteBottomIndex);
    }

    private void checkIfIndexOutOfRange(int row, int col) {
        if ((row < 1 || row > this.n) || (col < 1 || col > this.n)) {
            throw new IllegalArgumentException("Index must be > 0 and <= grid size ");
        }
    }
}
