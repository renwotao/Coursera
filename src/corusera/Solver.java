package corusera;

import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private Board board;
	private LinkedQueue<Board> resQueue = new LinkedQueue<>();
	private int steps = 0;
	
	public Solver(Board initial) {
		if (initial == null) throw new NullPointerException();
		board = initial;
		resQueue.enqueue(board);
		
		if (!board.isGoal()) {
			int cnt = 0;
			MinPQ<Node> minPQ = new MinPQ<>();
			LinkedQueue<Board> queue = new LinkedQueue<>();
			Stack<Board> stack = new Stack<>();
			
			minPQ.insert(new Node(null, board, cnt+board.manhattan()));
			
			while (!minPQ.isEmpty()) {
				Node searchNode = minPQ.delMin();
				Board board = searchNode.getBoard();
				queue.enqueue(board);
			
				cnt++;
				if (board.isGoal()){
					Node curNode = searchNode;
					while (curNode.getParent() != null) {
						stack.push(curNode.getBoard());
						curNode = curNode.getParent();
						steps++;
					}
					break;
				}
				
				Board parent = null;
			    if (searchNode.getParent() != null) 
			    	parent = searchNode.getParent().getBoard();
				Iterator<Board> it = board.neighbors().iterator();
				while (it.hasNext()) {
					Board bd = it.next();
					if (parent == null || !bd.equals(parent))
						minPQ.insert(new Node(searchNode, bd, cnt+bd.manhattan()));
				}
			}
			
			Iterator<Board> boardIt = stack.iterator();
			while (boardIt.hasNext()) {
				resQueue.enqueue(boardIt.next());
			}
		}
		
	}
	/*
	private boolean hasContains(LinkedQueue<Board> queue, Board board) {
		Iterator<Board> it = queue.iterator();
		while (it.hasNext()) {
			if (it.next().equals(board)) 
				return true;
		}
		return false;
	}*/
	private class Node implements Comparable<Node>{
		private Node parent;
		private int priority = 0;
		private Board board;
		public Node(Node parent, Board board, int priority) {
			this.board = board;
			this.parent = parent;
			this.priority = priority;
		}
		
		public Board getBoard() {
			return board;
		}
		public Node getParent() {
			return parent;
		}
		
		@Override
		public int compareTo(Node that) {
			// TODO Auto-generated method stub
			if (this.priority < that.priority) return -1;
			else if (this.priority > that.priority) return 1;
			return 0;
		}
		
	}
	
	public boolean isSolvable() {
		return board.isGoal() || steps != 0;
	}
	public int moves() {
		return steps;
	}
	
	public Iterable<Board> solution() {
		return resQueue;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}

}
