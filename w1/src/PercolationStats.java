/* *****************************************************************************
 *
 *  Description:  PercolationStat class
 *
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double meanVal;
    private final double stddevVal;
    private final double[] numOfOpenSitesArr;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("either n ≤ 0 or trials ≤ 0");
        }
        this.trials = trials;
        this.numOfOpenSitesArr = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int[] siteIndex = new int[2];

            while (!p.percolates()) {
                siteIndex[0] = StdRandom.uniform(1, n + 1);
                siteIndex[1] = StdRandom.uniform(1, n + 1);
                p.open(siteIndex[0], siteIndex[1]);
            }
            double fracionOfnumberOfOpenSites = p.numberOfOpenSites() / (double) (n * n);
            this.numOfOpenSitesArr[i] = fracionOfnumberOfOpenSites;
        }
        this.meanVal = StdStats.mean(this.numOfOpenSitesArr);
        this.stddevVal = StdStats.stddev(this.numOfOpenSitesArr);
    }

    /**
     * mean of percolation threshold
     * @return mean of percolation threshold value
     */
    public double mean() {

        return this.meanVal;
    }

    /**
     *  standard deviation of percolation threshold
     * @return standard deviation of percolation threshold value
     */
    public double stddev() {
        return this.stddevVal;
    }

    /**
     * Calculates ratio of stddev ratio
     * @return value of stddev ratio
     */
    private double getConstantStdDevRatio() {
        double stdDev = this.stddev();
        return ((1.96 * stdDev) / Math.sqrt(this.trials));
    }

    /**
     * low  endpoint of 95% confidence interval
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        double mean = this.mean();
        double stdDevConstant = this.getConstantStdDevRatio();

        return mean - stdDevConstant;
    }

    /**
     * high endpoint of 95% confidence interval
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {

        double mean = this.mean();
        double stdDevConstant = this.getConstantStdDevRatio();


        return mean + stdDevConstant;
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trial);

        System.out.printf("mean                    = %8.6f\n", percolationStats.mean());
        System.out.printf("stddev                  = %10.17f\n", percolationStats.stddev());
        System.out.printf("%s" + " confidence interval = [%10.17f, %10.17f] \n", "95%", percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
    }
}
