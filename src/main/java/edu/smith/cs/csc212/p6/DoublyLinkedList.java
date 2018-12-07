package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;
import edu.smith.cs.csc212.p6.errors.BadIndexError;



public class DoublyLinkedList<T> implements P6List<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		Node<T> removedValue = start;
		Node<T> afterRemove = start.after;
		removedValue.before = afterRemove;
		start = afterRemove;
		return removedValue.value; 
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		System.out.println("inside removeBack");
		// if there is only one item
		if (start == end) {
			Node<T> removedValue = start;
//			System.out.println ("start == end" + removedValue);
			start = removedValue.after;
			
			return removedValue.value;
		}
//		else {
			Node<T> removedValue = end;
//			System.out.println ("else" + removedValue);
			Node<T> beforeRemove = end.before;
			beforeRemove.after = null;
			end = beforeRemove;
			System.out.println("inside elseeeee ");
			return removedValue.value;
//		}
	}

	@Override
	public T removeIndex(int index) {
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
				behind = behind.after;
			}
			Node <T> trash = behind.after; // Creates anew node that points at the node being removed
			behind.after = behind.after.after; // Skip over the node we're deleting
			trash.after = null; // Makes the node we're deleting point to nothings
			return trash.value;
		}
	}

	@Override
	public void addFront(T item) {
		if (isEmpty()) {
			start = new Node<T>(item);
		}
		else {
			Node<T> addingIn = new Node<T>(item);
			start.before = addingIn;
			addingIn.after = start;
			start = addingIn;
		}
	}

	@Override
	public void addBack(T item) {
		//if there are no nodes in list
		Node<T> addingIn = new Node<T>(item);
		if (isEmpty()) {
			start = addingIn;
		}
		//if there is one node
		else {
			end.after = addingIn;
			addingIn.before = end;
			
		}
		end = addingIn;
	}

	@Override
	public void addIndex(T item, int index) {
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
				behind = behind.after;
			}
			behind.after = new Node<T>(item);
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
		return end.value;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int counter = 0;
		for (Node<T> now = this.start; now != null; now = now.after) {
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
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if(start == null && end == null) {
			return true;
		}
		return false;

	}
	
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
