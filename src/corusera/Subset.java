package corusera;

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> randomStrs = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			randomStrs.enqueue(s);
		}
		Iterator<String> it = randomStrs.iterator();
		while (it.hasNext() && k-- > 0) {
			StdOut.println(it.next());
		}
	}

}
