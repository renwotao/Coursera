package corusera;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double [] openSites = null;
	
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		
		openSites = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			int cnt = 0;
			while (!perc.percolates()) {
				int x = StdRandom.uniform(1, n+1);
				int y = StdRandom.uniform(1, n+1);
				
				if (!perc.isOpen(x, y)) {
					perc.open(x, y);
					cnt++;
				}
			}
			openSites[i] = (double)cnt/(n*n);
		}
	}
	public double mean() {
		return StdStats.mean(openSites);
	}
	public double stddev() {
		return StdStats.stddev(openSites);
	}
	public double confidenceLo() {
		
		return mean() - 1.96*stddev()/Math.sqrt(openSites.length);
	}
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(openSites.length);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PercolationStats perc = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean\t\t\t= " + perc.mean());
		System.out.println("stddev\t\t\t= " + perc.stddev());
		System.out.println("95% confidence interval\t= " + perc.confidenceLo() + "," + perc.confidenceHi());
	}

}
