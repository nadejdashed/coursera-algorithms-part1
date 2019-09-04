/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] x;
    private final int t;
    private double mean = Double.NaN;
    private double stddev = Double.NaN;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        t = trials;
        x = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            x[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = Double.isNaN(mean) ? StdStats.mean(x) : mean;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = Double.isNaN(stddev) ? StdStats.stddev(x) : stddev;
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // test client (described below)
    public static void main(String[] args) {
        int n, t;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
            PercolationStats stats = new PercolationStats(n, t);

            System.out.println("mean                    = " + stats.mean());
            System.out.println("stddev                  = " + stats.stddev());
            System.out.println(
                    "95% confidence interval = [" + stats.confidenceLo() + ", " + stats
                            .confidenceHi() + "]");
        }
    }
}
