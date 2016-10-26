package corusera;

import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private LineSegment[] ls = new LineSegment[0];
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null) 
			throw new NullPointerException();
		
		Point[] pts = Arrays.copyOf(points, points.length);
		Arrays.sort(pts);
		Point prePoint = null;
		for (int i = 0; i < pts.length; i++) {
			if (pts[i] == null) 
				throw new NullPointerException();
			if (prePoint != null && prePoint.compareTo(pts[i]) == 0) 
				throw new IllegalArgumentException();
			prePoint = pts[i];
		}
		
		for (int i = 0; i < pts.length; i++) {
			for (int j = i+1; j < pts.length; j++) {
				double s1 = pts[i].slopeTo(pts[j]);
				
				for (int k = j+1; k < pts.length; k++) {
					
					double s2 = pts[i].slopeTo(pts[k]);
				
					for (int l = k+1; l < pts.length; l++) {
				
						double s3 = pts[i].slopeTo(pts[l]);
		
						if (Math.abs(s1-s2) < Math.pow(10, -5) && Math.abs(s2 - s3) < Math.pow(10, -5)
								|| s1 == Double.POSITIVE_INFINITY && s2 == Double.POSITIVE_INFINITY && s3 == Double.POSITIVE_INFINITY) {
							
							if (ls.length == 0) {
								ls = new LineSegment[1];
								ls[0] = new LineSegment(pts[i], pts[l]);
							} else {
								LineSegment[] lsTmp = new LineSegment[ls.length+1];
								System.arraycopy(ls, 0, lsTmp, 0, ls.length);
								lsTmp[ls.length] = new LineSegment(pts[i], pts[l]);
								ls = lsTmp;
								lsTmp = null;
							}
						
						}
						
					}
				}
			}
		}
		
	}
	public int numberOfSegments() {
		return ls.length;
	}
	public LineSegment[] segments() {
		return Arrays.copyOf(ls, ls.length);
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}

}
