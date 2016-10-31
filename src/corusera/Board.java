package corusera;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[] bk;
	private int n = 0;
	private int hamming = 0;
	private int manhattan = 0;
	private int blankIdx = 0;
	
	public Board(int[][] blocks) {
		n = blocks.length;
		this.bk = new int[n*n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				this.bk[i*n+j] = blocks[i][j];
				if (blocks[i][j] == 0) {
					blankIdx = i*n+j;
					continue;
				}
				if (blocks[i][j] != i*n+j+1) {
					hamming++;
					int m = blocks[i][j]-1;
					manhattan += Math.abs(m/n - i) + Math.abs(m%n - j);
				}
			}
		}
	}
	
	public int dimension() {
		return n;
	}
	public int hamming() {
		return hamming;
	}
	public int manhattan() {
		return manhattan;
	}
	public boolean isGoal() {
		return hamming == 0;
	}
	public Board twin() {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copy[i][j] = bk[i*n+j];
			}
		}
		int p1, p2;
		do {
			p1 = StdRandom.uniform(n*n);
		} while (copy[p1/n][p1%n] == 0);
			
		do {
			p2 = StdRandom.uniform(n*n);
		} while (copy[p2/n][p2%n] == 0 || p2 == p1);
		
		int tmp = copy[p1/n][p1%n];
		copy[p1/n][p1%n] = copy[p2/n][p2%n];
		copy[p2/n][p2%n] = tmp;
		
		return new Board(copy);
	}
	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null ) return false;
		if (y.getClass() != this.getClass()) return false;
		
		Board that = (Board)y;
		if (that.dimension() != this.dimension()) return false;
		for (int i = 0; i < n*n; i++) {
			if (bk[i] != that.bk[i]) 
				return false;
		}
		return true;
	}
	private void addNeighborToQueue(int [][] copy, int neighborIdx, LinkedQueue<Board> queue) {
		int tmp = copy[neighborIdx/n][neighborIdx%n];
		copy[neighborIdx/n][neighborIdx%n] = 0;
		copy[blankIdx/n][blankIdx%n] = tmp;
		Iterator<Board> it = queue.iterator();
		Board board = new Board(copy);
		while (it.hasNext()) {
			if (it.next().equals(board)) {
				copy[neighborIdx/n][neighborIdx%n] = tmp;
				copy[blankIdx/n][blankIdx%n] = 0;
				return;
			}
		}
		queue.enqueue(board);
		copy[neighborIdx/n][neighborIdx%n] = tmp;
		copy[blankIdx/n][blankIdx%n] = 0;
	}
	public Iterable<Board> neighbors() {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copy[i][j] = bk[i*n+j];
			}
		}
		LinkedQueue<Board> queue = new LinkedQueue<>();
		int posX = blankIdx/n, posY = blankIdx%n;
		if (posY+1 < n) {
			addNeighborToQueue(copy, blankIdx+1, queue);
		}
		if (posY-1 >= 0) {
			addNeighborToQueue(copy, blankIdx-1, queue);
		}
		if (posX + 1 < n) {
			addNeighborToQueue(copy, blankIdx+n, queue);
		}
		if (posX - 1 >= 0) {
			addNeighborToQueue(copy, blankIdx-n, queue);
		}
		return queue;
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n*n; i++) {
	        s.append(String.format("%2d ", bk[i]));
	        if (i%n == n-1)
	        	s.append("\n");
	    }
	    return s.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);
	    StdOut.println(initial.manhattan());
	}

	
}
