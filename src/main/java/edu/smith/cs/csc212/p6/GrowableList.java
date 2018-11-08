package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

public class GrowableList<T> implements P6List<T> {
	public static final int START_SIZE = 32;
	private Object[] array;
	private int fill;
	
	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	@Override
	public T removeFront() {
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		T removed = this.getIndex(0);
		fill--; // fill is the size of the list
		for (int i=0; i<fill; i++) {
			this.array[i] = this.array[i+1];
		}
		this.array[fill] = null;
		return removed;
	}

	@Override
	public T removeBack() {
		return removeIndex(fill-1); // B/c It wants anode back and not a void
	}

	@Override
	public T removeIndex(int index) {
		if (this.size() == 0) {
			throw new EmptyListError();
		}
		T removed = this.getIndex(index);
		fill--; // fill is the size of the list
		for (int i=index; i<fill; i++) {
			this.array[i] = this.array[i+1];
		}
		this.array[fill] = null;
		return removed;
	}

	@Override
	public void addFront(T item) {
		
		if (fill > array.length) {
			Object[] newList = new Object[(2*(this.array.length))]; //Creates an new list and makes it double the length
			for (int i=0;i<array.length; i++) {
				newList[i+1] = this.array[i]; //Also noting that "newList" is an array
			}
			array = newList;
		}
		array[0] = item;
		 
	}

	@Override
	public void addBack(T item) {
		// I've implemented part of this for you.
		if (fill >= this.array.length) {
			Object[] newArray = new Object[(2*(this.array.length))]; //Creates an new list and makes it double the length
			for (int i=0;i<array.length; i++) {
				newArray[i] = this.array[i];
			}
			array = newArray;	
		}
		this.array[fill++] = item;
	}

	@Override
	public void addIndex(T item, int index) {
		//I know we don't have to Really do this but we should check if it's full before we add things
		if (fill >= this.array.length) {
			Object[] newArray = new Object[(2*(this.array.length))]; //Creates an new list and makes it double the length
			for (int i=0;i<array.length; i++) { //
				newArray[i] = this.array[i];
			}
			array = newArray;	
		}
		for (int j=fill; j>index; j--) {
			array[j] = array[j-1];
		}
		array[index] = item;
		fill++;
	}
	
	@Override
	public T getFront() {
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		return this.getIndex(this.fill-1);
	}

	/**
	 * Do not allow unchecked warnings in any other method.
	 * Keep the "guessing" the objects are actually a T here.
	 * Do that by calling this method instead of using the array directly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getIndex(int index) {
		return (T) this.array[index];
	}

	@Override
	public int size() {
		return fill;
	}

	@Override
	public boolean isEmpty() {
		return fill == 0;
	}


}
