import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
/* *****************************************************************************
 *
 *  Description:  SeamCarverTest
 *
 *
 **************************************************************************** */
public class SeamCarverTest {

    SeamCarver seamCarver;


    private String getResourcePath(String fileName) {
        URL url = SeamCarverTest.class.getResource(fileName);

        return url.getPath();
    }

    @Test
    public void energy() throws Exception {

        Picture p = new Picture(getResourcePath("3x4.png"));
        seamCarver = new SeamCarver(p);
        double energy0_0 = seamCarver.energy(0, 0);
        Assert.assertTrue(Double.valueOf(1000).equals(energy0_0));
        double energy1_1 = seamCarver.energy(1, 1);
        Assert.assertTrue(Double.valueOf(228.52789764052878).equals(energy1_1));
    }

    @Test
    public void findHorizontalSeam() throws Exception {
        Picture p = new Picture(getResourcePath("3x4.png"));
        seamCarver = new SeamCarver(p);
        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {1, 2, 1}, horizontalSeam);

        p = new Picture(getResourcePath("3x7.png"));
        seamCarver = new SeamCarver(p);
        horizontalSeam = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {1, 2, 1}, horizontalSeam);

        p = new Picture(getResourcePath("stripes.png"));
        seamCarver = new SeamCarver(p);
        int [] horizontalSeam1 = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 1, 1, 1, 1, 1, 0}, horizontalSeam1);

        p = new Picture(getResourcePath("diagonals.png"));
        seamCarver = new SeamCarver(p);
        horizontalSeam1 = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 1, 1, 1, 1, 1, 0}, horizontalSeam1);

        p = new Picture(getResourcePath("10x10.png"));
        seamCarver = new SeamCarver(p);
        horizontalSeam1 = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 2, 3, 3, 3, 3, 2, 1, 0}, horizontalSeam1);

        p = new Picture(getResourcePath("12x10.png"));
        seamCarver = new SeamCarver(p);
        horizontalSeam1 = seamCarver.findHorizontalSeam();
        Assert.assertArrayEquals(new int [] {7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3}, horizontalSeam1);
    }

    @Test
    public void findVerticalSeam() throws Exception {
        Picture p = new Picture(getResourcePath("3x4.png"));
        seamCarver = new SeamCarver(p);

        int[] verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 0}, verticalSeam);

        p = new Picture(getResourcePath("3x7.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 1, 1, 1, 0},verticalSeam);

        p = new Picture(getResourcePath("4x6.png"));
        seamCarver = new SeamCarver(p);
        int[] verticalSeam1 = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {1, 2, 1, 1, 2, 1}, verticalSeam1);

        p = new Picture(getResourcePath("10x12.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5}, verticalSeam);

        p = new Picture(getResourcePath("stripes.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, verticalSeam);

        p = new Picture(getResourcePath("diagonals.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, verticalSeam);

        p = new Picture(getResourcePath("10x10.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {6, 7, 7, 7, 7, 7, 8, 8, 7, 6}, verticalSeam);

        p = new Picture(getResourcePath("12x10.png"));
        seamCarver = new SeamCarver(p);
        verticalSeam = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int [] {6, 7, 7, 6, 6, 7, 7, 7, 8, 7}, verticalSeam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeamInvalidSeam() throws Exception {
        Picture p = new Picture(getResourcePath("7x3.png"));
        seamCarver = new SeamCarver(p);
        seamCarver.removeHorizontalSeam(new int[] { 2, 2, 2, 2, 2, 2, 3 });
    }

    @Test
    public void resizeTest() {
        Picture p = new Picture(getResourcePath("6x5.png"));
        seamCarver = new SeamCarver(p);
        seamCarver.removeHorizontalSeam(new int[] {  3, 2, 3, 4, 4, 4  });
    }

    @Test
    public void removeVerticalSeam() throws Exception {
        Picture p = new Picture(getResourcePath("4x6.png"));
        seamCarver = new SeamCarver(p);
        int[] verticalSeam1 = seamCarver.findVerticalSeam();
        Assert.assertArrayEquals(new int[]{1, 2, 1, 1, 2, 1}, verticalSeam1);

        seamCarver.removeVerticalSeam(verticalSeam1);

    }
}