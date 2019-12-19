import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final WeightedQuickUnionUF wqu;
  private final int side;
  private boolean[][] grid;
  private final int ceiling;
  private final int floor;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Out of bounds");
    }
    this.grid = new boolean[n][n];
    this.wqu = new WeightedQuickUnionUF((int) Math.pow(n, 2) + 2);
    this.side = n;
    this.ceiling = 0;
    this.floor = (int) Math.pow(n, 2) + 1;
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (row <= 0 || col <= 0 || row > this.side || col > this.side) {
      throw new IllegalArgumentException("Out of bounds");
    }
    if (this.side == 1) {
      this.grid[0][0] = true;
      this.wqu.union(this.floor, getGridPosition(row, col));
      this.wqu.union(this.ceiling, getGridPosition(row, col));
      return;
    }
    if (!isOpen(row, col)) {
      this.grid[row - 1][col - 1] = true;

      // Union for ceiling and floor
      if (row == 1) {
        this.wqu.union(0, getGridPosition(row, col));
      } else if (row == this.side) {
        this.wqu.union(this.floor, getGridPosition(row, col));
      }

      // Union for neighbor grids
      for (int i = -1; i <= 1; i += 2) {
        if (row > 1 && row < this.side || row == 1 && i == 1 || row == this.side && i == -1) {
          if (this.grid[row - 1 + i][col - 1] && !(row + i < 1 && row + i > this.side)) {
            this.wqu.union(getGridPosition(row, col), getGridPosition(row + i, col));
          }
        }
        if (col > 1 && col < this.side || col == 1 && i == 1 || col == this.side && i == -1) {
          if (this.grid[row - 1][col - 1 + i] && !(col + i < 1 && col + i > this.side)) {
            this.wqu.union(getGridPosition(row, col), getGridPosition(row, col + i));
          }
        }
      }
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (row <= 0 || col <= 0 || row > this.side || col > this.side) {
      throw new IllegalArgumentException("Out of bounds");
    }
    return this.grid[row - 1][col - 1];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (row <= 0 || col <= 0 || row > this.side || col > this.side) {
      throw new IllegalArgumentException("Out of bounds");
    }
    return this.wqu.find(getGridPosition(row, col)) == this.wqu.find(this.ceiling);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    int openSites = 0;
    for (int i = 0; i < this.side; i++) {
      for (int j = 0; j < this.side; j++) {
        if (this.grid[i][j]) {
          openSites++;
        }
      }
    }
    return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return this.wqu.find(this.floor) == this.wqu.find(this.ceiling);
  }

  // obtain the position of the grid block
  private int getGridPosition(int row, int col) {
    return (row * this.side) - (this.side - col);
  }

  // test client (optional)
  public static void main(String[] args) {
    Percolation pTest = new Percolation(10);
    System.out.println(pTest.isFull(1, 1));
  }

}
