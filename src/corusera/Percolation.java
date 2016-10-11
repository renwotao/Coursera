package corusera;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF uf = null;
	private WeightedQuickUnionUF uf_copy = null;
	private int N = 0;
	private boolean [] grid = null;
	private int vTop = 0;
	private int vBottom = 0;
	
	private int hash(int i, int j) {
		return (i-1)*N + j;
	}
	
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		
		N= n;
		uf = new WeightedQuickUnionUF(N*N + 2);
		uf_copy = new WeightedQuickUnionUF(N*N + 2);
		grid = new boolean[N*N + 2];
		vTop = 0;
		vBottom = N*N+1;
		for (int i = 1; i <= N*N; i++) {
			grid[i] = false;
		}
	}
	public void open(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException();
		}
		
		int k = hash(i, j);
		grid[k] = true; 
		
		if (i+1 <= N && grid[k + N]) {
			uf.union(k, k + N);
			uf_copy.union(k, k + N);
		}
		if (i-1 >= 1 && grid[k - N]) {
			uf.union(k, k - N);
			uf_copy.union(k, k - N);
		}
		if (j+1 <= N && grid[k + 1]) {
			uf.union(k, k + 1);
			uf_copy.union(k, k + 1);
		}
		if (j-1 >= 1 && grid[k - 1]) {
			uf.union(k, k - 1);
			uf_copy.union(k, k - 1);
		}
		if (k >= 1 && k <= N) {
			uf.union(k, vTop);
			uf_copy.union(k, vTop);
		}
		if (k >= (N - 1)*N + 1 && k <= N*N) {
			uf.union(k, vBottom);
		}
	}
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException();
		}
	
		return grid[hash(i,j)]; 
	}
	public boolean isFull(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException();
		}
		
		return uf_copy.connected(0, hash(i, j));
	}
	public boolean percolates() {
		return uf.connected(vTop, vBottom);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
