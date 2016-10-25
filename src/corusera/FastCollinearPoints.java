package corusera;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private LineSegment[] ls = new LineSegment[0];
	
	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new NullPointerException();
		
		Arrays.sort(points);
		Point prePoint = null;
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null) throw new NullPointerException();
			
			if (prePoint != null && prePoint.compareTo(points[i]) == 0) throw new IllegalArgumentException(); 
			prePoint = points[i];
		}
		
		for (int i = 0; i < points.length; i++) {
			Point[] ptCopy =  new Point[points.length];
			System.arraycopy(points, 0, ptCopy, 0, points.length);
			Arrays.sort(ptCopy, ptCopy[i].slopeOrder());
			
			double pre = Double.NEGATIVE_INFINITY;
			boolean flag = false;
			int count = 1;
			
			for (int j = 1; j < points.length; j++) {
				double k = ptCopy[0].slopeTo(ptCopy[j]);
				
				if (Math.abs(pre - k) < Math.pow(10, -5)
					|| pre == Double.POSITIVE_INFINITY && k == Double.POSITIVE_INFINITY) {
					count++;
					
					if (ptCopy[0].compareTo(ptCopy[j-1]) > 0)	flag = true;
				} else {
					if (count >= 3 && !flag) {
						LineSegment[] lsTmp = null;
						if (ls.length == 0) {
							lsTmp = new LineSegment[1];
							lsTmp[0] = new LineSegment(ptCopy[0], ptCopy[j-1]);
						} else {
							lsTmp = new LineSegment[ls.length+1];
							System.arraycopy(ls, 0, lsTmp, 0, ls.length);
							lsTmp[ls.length] = new LineSegment(ptCopy[0], ptCopy[j-1]);
						}
					
						ls = lsTmp;
					}
					count = 1;
				}
				pre = k;
			}
		}

	}
	public int numberOfSegments() {
		return ls.length;
	}
	public LineSegment[] segments() {
		return ls;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}

}
