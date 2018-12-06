package edu.smith.cs.csc212.p6;
// Future Note: IF IT'S GREEN THAT DOESN'T MEAN IT'S PASSED IT JUST MEANS THAT IT'S RUN SO DON'T OVERLOOK GREEN CODE, OKAY?
import java.util.Iterator;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	@Override
	public T removeBack() {
		Node<T> current = start;
		checkNotEmpty();
		// if there is only one item
		if (start.next == null) {
			T last = start.value;
			start = null; // Not start.next bc start.next IS A NODE so make start = to null and it'll forget start.next
			return last;
		}
		while (current.next != null) {
			if (current.next.next == null) {
				T last = current.next.value;
				current.next = null;
				return last;
			} else {
				current = current.next;
			}
		}
		throw new EmptyListError();
	}

	@Override
	public T removeIndex(int index) {
//		throw new P6NotImplemented();
		checkNotEmpty();
		if (index == 0) {
			return removeFront();
		}
		if (index == size()-1) {
			return removeBack();
		}
		else {
			Node<T> behind = start;
			for (int i = 0; i < index-1; i++) {
				behind = behind.next;
			}
			Node <T> trash = behind.next; // Creates anew node that points at the node being removed
			behind.next = behind.next.next; // Skip over the node we're deleting
			trash.next = null; // Makes the node we're deleting point to nothings
			return trash.value;
		}
		
	}

	@Override
	public void addFront(T item) { // Hey BTW: "this.whatever" is just to note that Java should use a specific version
		if (start == null) {
			start = new Node<T>(item, null);
			
		}
		else {
			start = new Node<T>(item, start); //B/c the start on the right hand side of the equals is the old start value
		}
	}

	@Override
	public void addBack(T item) {
		//if there are no nodes in list
		if (start == null) {
			this.start = new Node<T>(item, start);
		}
		//if there is one node
		else {
			Node<T> last = start;
			while (last.next != null) {
				last = last.next;
			}
			last.next = new Node<T>(item, null);
		}
		
	}

	@Override
	public void addIndex(T item, int index) {
//		throw new P6NotImplemented();
		if (index == 0) {
			addFront(item);
		}
		else if (index < 0) {
			throw new BadIndexError();
		}
		else if (index == size()) { //We have elseif's because
			addBack(item);
		}
		else if (index > size()) {
			throw new BadIndexError();
		}
		else {
			Node<T> behind = start;
			for (int i = 0; i < index-1; i++) {
				behind = behind.next;
			}
			behind.next = new Node<T>(item, behind.next);
		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return getIndex(size()-1); // because size starts counting from 1 so if we want the last index . . . 
	}

	@Override
	public T getIndex(int index) { // we need a counter variable because we want the value and the code will just increment
		int counter = 0;
		for (Node<T> now = this.start; now != null; now = now.next) {
			if (index == counter) {
				return now.value;
			}
			counter++;
		}
		throw new BadIndexError(); // if we get a bad / non existing index, yell	
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (start == null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 *    |  (Arrows makes it clearer)
		 *   \/
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 *    |  (Arrows makes it clearer)
		 *   \/
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop
	 * for {@linkplain ChunkyLinkedList}. This Iterator type is what java uses for
	 * {@code for (T x : list) { }} lops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}
	
	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}
