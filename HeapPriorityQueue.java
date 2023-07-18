/**
 * Author: Ryan Yu, Rachel Holman, Ben Collinsworth
 * Date: 5/3/22
 * CSE 274 Project 4
 * Implementation of a heap using a priority queue for Dijkstra Algorithm
 */

import java.util.Arrays;
import java.util.NoSuchElementException;

public class HeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {

	//--------------------------------------------------------------------- Properties
	private static final int DEFAULT_CAPACITY = 10;
	private T[] elements;
	private int size;
	private int capacity = DEFAULT_CAPACITY;

	//--------------------------------------------------------------------- Constructors
	/**
	 * Default constructor for HeapPriorityQueue
	 */
	public HeapPriorityQueue() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Partial constructor for HeapPriorityQueue when taking in initalCapacity
	 * 
	 * @param initialCapacity
	 */
	public HeapPriorityQueue(int initialCapacity) {
		elements = (T[]) new Comparable[initialCapacity + 1];
		size= 0;
	}
	
	/**
	 * Partial constructor for HeapPriorityQueue that takes in an array of entries
	 * 
	 * @param entries
	 */
	public HeapPriorityQueue(T[] entries) {
		this(entries.length + 1);
		size = entries.length;
		
		for (int index = 0; index < entries.length; index++)
			elements[index + 1] = entries[index];
		
		for (int rIndex = size / 2; rIndex > 0; rIndex--) 
			reheapDown(rIndex);
	}

	//--------------------------------------------------------------------- Methods
	/**
	 * Checks if the array is empty
	 */
	@Override
	public boolean isEmpty() {
		return elements[1]== null;
	}

	/**
	 * Checks if array is full (or at capacity)
	 */
	@Override
	public boolean isFull() {
		return size == capacity;
	}

	/**
	 * clears the whole array and resets size to zero
	 */
	@Override
	public void clear() {
		Arrays.fill(elements, 0, size, null);
		size = 0;
		
	}

	/**
	 * returns the size of the priority queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * adds a new entry to the priority queue
	 * @param T newEntry the element to be added
	 */
	@Override
	public void add(T newEntry) { 
		verifyCapacity();
		elements[++size] = newEntry;
		reheapUp(size);
		
	}

	/**
	 * returns the highest priority item
	 * @return the highest priority item
	 */
	@Override
	public T peek() { 
		return isEmpty() ? null : elements[1];
	}

	/**
	 * removes the highest priority item
	 * @return the removed item
	 */
	@Override
	public T remove() {
		if (isEmpty()) throw new NoSuchElementException();
		
		T ret = elements[1];
		elements[1] = elements[size];
		elements[size] = null;
		reheapDown(1);
		size--;
		return ret;
	}
	
	/**
	 * creates a string representing the priority queue
	 * @return the created string
	 */
	@Override
	public String toString() {
		String ret = "null";
		if (size == 0) return "[" + ret + "]";
		for(int i =1; i<size+1; i++) 
			ret += ", " + elements[i].toString();
		return "[" + ret + "]";
		
	}

	//-------------------------------------------------- Helper Methods
	/**
	 * checks the size of the priority queue to determine if we need to resize
	 */
	private void verifyCapacity() { 
		if(size == capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
	}
	
	/**
	 * helper method for add method, moves element from bottom to correct position
	 * @param index the starting index to be moved
	 */
	private void reheapUp(int index) {
		int parentIndex = index /2;
		while(parentIndex >0 && elements[index].compareTo(elements[parentIndex])>0) {
			swap(index, parentIndex);
			index = parentIndex;
			parentIndex = index/2;
		}
	}
	
	/**
	 * helper method for remove, moves first element down to correct position
	 * @param index the starting index to be moved
	 */
	private void reheapDown(int index) {
		// find nodes to left and right below top one
		int left = 2*index;
		int right = 2*index + 1;
		
		// if there are no nodes to swap with, exit
		if (left >= size ) return;
		
		// determine whether the right or left child is bigger
		int bigger = left;
		if (right < size && elements[right].compareTo(elements[left]) > 0)
			bigger = right;
		
		// if the bigger child is larger than top node, swap it!
		if (elements[index].compareTo(elements[bigger])<0){
			swap(index, bigger);
			reheapDown(bigger);
		}
	}
	
	/**
	 * swaps the values at two indices
	 * @param index the first index whose value should be swapped
	 * @param parentIndex the second index whose value should be swapped
	 */
	private void swap(int index, int parentIndex) {
		T tmp = elements[index];
		elements[index] = elements[parentIndex];
		elements[parentIndex] = tmp;
	}
	
}
