import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
  private final double[] results;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Out of bounds");
    }
    // initialize array of Percolation trials and array of results
    results = new double[trials];

    // iterate through trials to perform experiment, then store results in array
    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);
      while (!perc.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        perc.open(row, col);
      }
      this.results[i] = (double) perc.numberOfOpenSites() / Math.pow(n, 2);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(this.results);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(this.results);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - stddev() * 2;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + stddev() * 2;
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = StdIn.readInt();
    int t = StdIn.readInt();

    PercolationStats run = new PercolationStats(n, t);
    StdOut.println("mean                    = " + run.mean());
    StdOut.println("stddev                  = " + run.stddev());
    StdOut.println("95% confidence interval = " + "[" + run.confidenceLo() + ", " + run.confidenceHi() + "]");
  }
}
