/***************************************** 
* Author : Lonnie Horlacher and Ryan Wheeler
* Date : 02/03/2018
* Assignment: Percolation
******************************************/

package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] results;
	private int T;

	public PercolationStats(int N, int T) {
		this.T = T;

		if (N < 0 || 0 >= T) {
			throw new IndexOutOfBoundsException("row index must be greater than 0");
		}

		int i = 0;
		int j = 0;
		int count = 0;
		results = new double[T];// stores results for each experiment

		for (int a = 0; a < T; a++) {// loops through grid T times.
			count = 0;

			Percolation perc = new Percolation(N);
			while (!perc.percolates()) {// while sites don't percolate continues
				// to generate random numbers for
				// coordinates.

				i = StdRandom.uniform(N);
				j = StdRandom.uniform(N);
				if (!perc.isOpen(i, j)) {// if i, j are not already used opens
					// new site, adds to count
					perc.open(i, j);
					count++;
				}

			}

			results[a] = (double) count / (N * N);// finds the average and
			// stores them in array.

		}

	}

	public double mean() {
		return StdStats.mean(results);
	}

	public double stddev() {
		return StdStats.stddev(results);
	}

	public double confidenceLow() {
		return mean() - ((1.96 * stddev()) / (Math.sqrt(T)));
	}

	public double confidncehigh() {
		return mean() + ((1.96 * stddev()) / (Math.sqrt(T)));
	}

	public static void main(String[] args) {
		PercolationStats pStats = new PercolationStats(200, 100);
		System.out.println("mean() = " + pStats.mean());
		System.out.println("stdev() = " + pStats.stddev());
		System.out.println("confidenceLow() = " + pStats.confidenceLow());
		System.out.println("confidenceHigh() = " + pStats.confidncehigh());
	}

}