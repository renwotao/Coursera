package corusera;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
	
	private Item[] items = null;
	private int capacity = 1;
	private int size = 0;
	
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		this.capacity = capacity;
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) 
			copy[i] = items[i];
		items = copy;
	}
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		items = (Item[]) new Object[capacity];
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
		if (items.length == size) resize(2 * items.length);
		items[size++] = item;
	}
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException();
		int index = StdRandom.uniform(0, size);
		Item item = items[index];
		if (size > 1) {
			items[index] = items[size-1];
			items[size-1] = null;
		} else {
			items[index] = null;
		}
		size--;
		
		if (size > 1 && size == 1/4*capacity)
			resize(1/2*capacity);
		
		return item;
		
	}
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException();
		
		int index = StdRandom.uniform(0, size);
		return items[index];
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new RandomizedQueueIterator<Item>();
	}
	
	private class RandomizedQueueIterator<Item> implements Iterator<Item> {
		
		private int current = 0;
		private Item[] copy = null;
		
		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator() {
			copy = (Item[]) new Object[size]; 
			for (int i = 0; i < size; i++) 
				copy[i] = (Item) items[i];
			StdRandom.shuffle(copy);
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != size;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (!hasNext()) throw new NoSuchElementException();
		
			return copy[current++];
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException(); 
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
		rq.enqueue(206);
		StdOut.println(rq.dequeue());
		for (int i = 0; i < 10; i++)
			rq.enqueue(i);
		StdOut.println(rq.size);
		int n = rq.size();
		for (int i = 0; i < n; i++)
			StdOut.println(rq.dequeue());
	}

}
