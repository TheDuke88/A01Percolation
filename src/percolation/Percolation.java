/***************************************** 
* Author : Lonnie Horlacher and Ryan Wheeler
* Date : 02/03/2018
* Assignment: Percolation
******************************************/

package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	boolean[][] grid;
	WeightedQuickUnionUF wqu;
	WeightedQuickUnionUF wqu2;
	int N;
	int topNode;
	int botNode;

	public Percolation(int N) { // create N­by­N grid, with all sites blocked
		if (N < 1) {
			throw new IllegalArgumentException("N must be greater than 0");
		} else {
			grid = new boolean[N][N];
			wqu = new WeightedQuickUnionUF(N * N + 2);
			wqu2 = new WeightedQuickUnionUF(N * N + 2);
			this.N = N;
			this.topNode = N * N;
			this.botNode = N * N + 1;
		}
	}

	public void open(int i, int j) { // open site (row i, column j) if it is not
		isOutOfBounds(i, j); // open already
		if (grid[i][j] == true)
			return;
		else
			grid[i][j] = true;

		if (i == 0) { // if top, connect to top virtual node
			wqu.union(convert(i, j), topNode);
			wqu2.union(convert(i, j), topNode);

		} else if (i == (N - 1)) { // if bottom, connect to bottom virtual node
			wqu.union(convert(i, j), botNode);
		}

		if (isNotTooLow(i) && isOpen(i + 1, j)) { // if not on bottom row,
			// connect to site below
			unionGrid(i, j, i + 1, j);
		}
		if (isNotTooHigh(i) && isOpen(i - 1, j)) { // if not on top row, connect
			// site above
			unionGrid(i, j, i - 1, j);
		}
		if (isNotTooLow(j) && isOpen(i, j + 1)) { // if not on left row, connect
			// to left site
			unionGrid(i, j, i, j + 1);
		}
		if (isNotTooHigh(j) && isOpen(i, j - 1)) { // if not on right row,
			// connect to right site
			unionGrid(i, j, i, j - 1);
		}
	}

	private boolean isNotTooLow(int a) { // checks if site is on grid
		return a <= N - 2;
	}

	private boolean isNotTooHigh(int a) { // checks if site is on grid
		return a >= 1;
	}

	public boolean isOpen(int i, int j) { // is site (row i, column j) open?
		isOutOfBounds(i, j);
		return grid[i][j];
	}

	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		isOutOfBounds(i, j);
		return wqu2.connected(convert(i, j), topNode);
	}

	public boolean percolates() { // does the system percolate?
		return wqu.connected(topNode, botNode);
	}

	private int convert(int i, int j) { // convert coordinates i, j to
		// QuickUnion reference
		return (N * i + j);
	}

	private void unionGrid(int i1, int j1, int i2, int j2) { // so that we don't have to write convert many times
		wqu.union(convert(i1, j1), convert(i2, j2));
		wqu2.union(convert(i1, j1), convert(i2, j2));
	}

	private void isOutOfBounds(int i, int j) {
		if (i >= N || j >= N || i < 0 || j < 0)
			throw new IndexOutOfBoundsException("Invalid coordinates provided");
	}
}