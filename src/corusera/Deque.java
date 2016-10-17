package corusera;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node<Item>
	{
		Item item;
		Node<Item> pre, next;
	}
	
	private Node<Item> head = new Node<Item>();
	private int size = 0;
	
	public Deque() {
		head.pre = head.next = head;
	}
	
	public boolean isEmpty() {
		return head.next == head;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item) {
		if (item == null) throw new NullPointerException();
		
		Node<Item> oldfirst = head.next;
		head.next = new Node<Item>();
		head.next.item = item;
		head.next.next = oldfirst;
		head.next.pre = head;
		oldfirst.pre = head.next;
		if (isEmpty()) head.pre = head.next;
		oldfirst.pre = head.next;
		size++;
	}
	
	public void addLast(Item item) {
		if (item == null) throw new NullPointerException ();
		
		Node<Item> oldlast = head.pre;
		head.pre = new Node<Item>();
		head.pre.item = item;
		head.pre.pre = oldlast;
		oldlast.next = head.pre;
		head.pre.next = head;
		if (isEmpty()) head.next = head.pre;
		size++;
	}
	
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException ();
		
		Item item = head.next.item;
		head.next = head.next.next;
		head.next.pre = head;
		if (isEmpty()) head.pre = head;
		size--;
		return item;
	}
	
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException ();
		
		Item item = head.pre.item;
		head.pre = head.pre.pre;
		head.pre.next = head;
		if (isEmpty()) head.next = head;
		size--;
		return item;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new DequeIterator<Item>(head);
	}
	private class DequeIterator<Item> implements Iterator<Item> 
	{
		public DequeIterator(Node<Item> head) {
			current = head.next;
		}
		private Node<Item> current;
	
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != head;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException(); 
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deque<Integer> dq = new Deque<>();
		for (int i = 0; i < 5; i++)
			dq.addFirst(i);
		Iterator<Integer> it = dq.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("-----------");
		for (int i = 0; i < 2; i++) 
			dq.removeFirst();
		for (int i = 5; i >= 0; i--) {
			dq.addLast(i);
		}
		it = dq.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
	}
	

}
