import edu.princeton.cs.algs4.Picture;

/* *****************************************************************************
 *
 *  Description:  SeamCarver implementation.
 *
 *
 **************************************************************************** */
public class SeamCarver {

    private Picture picture;

    private double[][] energy;

    private boolean isTransposed = false;

    /**
     * Create a seam carver object based on the given picture
     * @param pic - picture instance
     */
    public SeamCarver(Picture pic) {
        if (pic == null) {
            throw new IllegalArgumentException("Argument is null.");
        }
        this.picture = new Picture(pic);
        this.calcEnergy();
    }

    /**
     * Caclulate energy matrix.
     */
    private void calcEnergy() {
        int pWidth = this.picture.width();
        int pHeight = this.picture.height();
        this.energy = new double[pWidth][pHeight];

        for (int i = 0; i < pWidth; i++) {
            for (int j = 0; j < pHeight; j++) {
                energy(i, j);
            }
        }
    }

    /**
     * Current picture.
     * @return current picture instance
     */
    public Picture picture() {
        return new Picture(this.picture);
    }

    /**
     * Width of current picture.
     * @return width of current picture
     */
    public int width() {
        return this.picture.width();
    }

    /**
     *  Height of current picture.
     * @return height of current picture.
     */
    public int height() {
        return this.picture.height();
    }

    /**
     * Indicates if matrix is transposed.
     */
    private void transpose() {
        this.isTransposed = !this.isTransposed;
    }

    /**
     * Energy of pixel at column x and row y.
     * @param x - column index
     * @param y - row index
     * @return energy value for pixel associated with column x and row y
     */
    public double energy(int x, int y) {
        int picH = this.picture.height();
        int picW = this.picture.width();
        if (this.isTransposed) {
            picH = picW;
            picW = this.picture.height();
        }

        if (x < 0 || x > picW - 1 || y < 0 || y > picH - 1) {
            throw new IllegalArgumentException("IllegalArgumentException.");
        }

        if (this.isTransposed) {
            return this.energy[y][x];
        }
        if (this.energy[x][y] > 0.0d) {
            return this.energy[x][y];
        }

        if (x == 0 || y == 0 || x == picW - 1 || y == picH - 1) {
            this.energy[x][y] = 1000.0d;
            return 1000.0d;
        }

        int rgbX1 = this.picture.getRGB(x + 1, y);
        int rgbX2 = this.picture.getRGB(x - 1, y);

        double deltaX = this.calcDelta(rgbX1, rgbX2);


        int rgbY1 = this.picture.getRGB(x, y + 1);
        int rgbY2 = this.picture.getRGB(x, y - 1);

        double deltaY = this.calcDelta(rgbY1, rgbY2);

        double energyVal = Math.sqrt(deltaX + deltaY);
        this.energy[x][y] = energyVal;
        return energyVal;
    }

    /**
     * Calculate delta of two pixels.
     * @param rgb1 - RGB of first pixel as int
     * @param rgb1 - RGB of second pixel as int
     * @return
     */
    private double calcDelta(int rgb1, int rgb2) {
        int redCy1, grnCy1, bluCy1, redCy2, grnCy2, bluCy2;
        redCy1 = (rgb1 >> 16) & 0xff;
        grnCy1 = (rgb1 >> 8) & 0xff;
        bluCy1 = rgb1 & 0xff;
        redCy2 = (rgb2 >> 16) & 0xff;
        grnCy2 = (rgb2 >> 8) & 0xff;
        bluCy2 = rgb2 & 0xff;

        return Math.pow(redCy1 - redCy2, 2)
                + Math.pow(grnCy1 - grnCy2, 2)
                + Math.pow(bluCy1 - bluCy2, 2);
    }

    /**
     * sequence of indices for horizontal seam
     * @return
     */
    public int[] findHorizontalSeam() {
        transpose();
        int [] vertSeam = this.findVerticalSeam();
        transpose();
        return vertSeam;
    }

    /**
     *  sequence of indices for vertical seam
     * @return
     */
    public  int[] findVerticalSeam() {
        int pWidth = this.width();
        int pHeight = this.height();


        if (this.isTransposed) {
            pWidth = pHeight;
            pHeight = this.width();
        }

        int[][] edgeTo = new int[pWidth][pHeight];
        double[][] distTo = new double[pWidth][pHeight];
        for (int row = 0; row < pHeight; row++) {
            for (int col = 0; col < pWidth; col++) {
                distTo[col][row] = Double.POSITIVE_INFINITY;
                if (row == 0) distTo[col][row] = energy(col, row);
            }
        }

        double energyVal;
        for (int row = 0; row < pHeight - 1; row++) {
            for (int col = 0; col < pWidth; col++) {

                energyVal = distTo[col][row] + energy(col, row + 1);
                if (distTo[col][row + 1] > energyVal) {
                    distTo[col][row + 1] = energyVal;
                    edgeTo[col][row + 1] = col;
                }

                if (col + 1 < pWidth) {
                    energyVal = distTo[col][row] + energy(col + 1, row + 1);
                    if (distTo[col + 1][row + 1] > energyVal) {
                        distTo[col + 1][row + 1] = energyVal;
                        edgeTo[col + 1][row + 1] = col;
                    }
                }
                if (col - 1 >= 0) {
                    energyVal = distTo[col][row] + energy(col - 1, row + 1);
                    if (distTo[col - 1][row + 1] > energyVal) {
                        distTo[col - 1][row + 1] = energyVal;
                        edgeTo[col - 1][row + 1] = col;
                    }
                }
            }
        }

        // find min energy
        int minColInd = 0;
        double minEnergyVal = Double.POSITIVE_INFINITY;
        double curEnergy;
        for (int col = 0; col < pWidth; col++) {
            curEnergy = distTo[col][pHeight - 1];
            if (minEnergyVal > curEnergy) {
                minEnergyVal = curEnergy;
                minColInd = col;
            }
        }

        int[] seam = new int[pHeight];
        int row = pHeight - 1;

        // form the seam
        while (row >= 0) {
            seam[row] = minColInd;
            minColInd = edgeTo[minColInd][row--];
        }

        return seam;
    }

    /**
     * remove horizontal seam from current picture
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        int picH = this.picture.height();
        int picW = this.picture.width();

        if (seam == null || seam.length < picW || seam.length > picW
                || this.picture.height() <= 1 || !this.validateSeam(seam, true)) {
            throw new IllegalArgumentException("IllegalArgumentException.");
        }

        Picture p = new Picture(picW, picH - 1);
        double[][] newEnergy = new double[picW][picH - 1];

        int count = 0, delta;
        for (int i = 0; i < picW; i++) {
            delta = 0;
            int colIndToBeRemoved = seam[count];
            for (int j = 0; j < picH; j++) {

                if (j == colIndToBeRemoved && count == i) {
                    delta++;
                    count++;

                } else {

                    p.set(i, j - delta, this.picture.get(i, j));
                    newEnergy[i][j - delta] = this.energy(i, j);
                }
            }
        }
        this.energy = newEnergy;
        this.picture = p;
        this.recalcEnergy(seam, false);
    }

    /**
     * Recalculate energy matrix.
     * @param seam - seam removed from the picture
     * @param isVertical - indicating if removed seam was vertical
     */
    private void recalcEnergy(int[] seam, boolean isVertical) {

        for (int row = 0; row < this.height(); row++) {
            for (int col = 0; col < this.width(); col++) {
                if ((!isVertical && row == seam[col]) || (isVertical && col == seam[row])) {

                    // left
                    if (col - 1 >= 0) {
                        this.energy[col - 1][row] = 0.0d;
                        energy(col - 1, row);
                    }

                    // above
                    if (row - 1 >= 0) {
                        this.energy[col][row - 1] = 0.0d;
                        energy(col, row - 1);
                    }
                    if (!isVertical) {

                        // right
                        if (col + 1 < this.width()) {
                            this.energy[col + 1][row] = 0.0d;
                            energy(col + 1, row);
                        }

                        if (row < this.height()) {
                            this.energy[col][row] = 0.0d;
                            energy(col, row);
                        }
                    } else {
                        if (col < this.width()) {
                            this.energy[col][row] = 0.0d;
                            energy(col, row);
                        }

                        // below
                        if (row + 1 < this.height()) {
                            this.energy[col][row + 1] = 0.0d;
                            energy(col, row + 1);
                        }
                    }

                }
            }
        }
    }

    /**
     * Validate seam.
     * @param seam
     */
    private boolean validateSeam(int[] seam, boolean isHorizontal) {
        int curPix, curPicW = this.picture.width(), curPicH = this.picture.height();

        for (int i = 0; i < seam.length; i++) {
            curPix = seam[i];
            if (i > 0 && (Math.abs(curPix - seam[i - 1]) > 1)) {
                return false;
            }
            if (isHorizontal && curPix > curPicH - 1) {
                return false;
            }
            if (!isHorizontal && curPix > curPicW - 1) {
                return false;
            }
            if (curPix < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * remove vertical seam from current picture.
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
        int picW = this.picture.width();
        int picH = this.picture.height();

        if (seam == null || seam.length < picH || seam.length > picH
                || this.picture.width() <= 1 || !this.validateSeam(seam, false)) {
            throw new IllegalArgumentException("IllegalArgumentException.");
        }

        Picture p = new Picture(picW - 1, picH);
        double[][] newEnergy = new double[picW - 1][picH];

        int count = 0, delta;
        for (int i = 0; i < picH; i++) {
            delta = 0;
            int colIndToBeRemoved = seam[count];
            for (int j = 0; j < picW; j++) {

                if (j == colIndToBeRemoved && count == i) {
                    delta++;
                    count++;
                } else {
                    p.set(j - delta, i, this.picture.get(j, i));
                    newEnergy[j - delta][i] = this.energy(j, i);
                }
            }
        }
        this.picture = p;
        this.energy = newEnergy;
        this.recalcEnergy(seam, true);
    }
}