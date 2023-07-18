/**
 * Author: Ryan Yu, Rachel Holman, Ben Collinson
 * Date: 5/3/22
 * CSE 274 Project 4
 */

public interface PriorityQueueInterface<T extends Comparable<? super T>> {
	boolean isEmpty();
	boolean isFull();
	void clear();
	int size();
	void add(T newEntry);
	T peek();      // returns null if empty
	T remove();    // throws NoSuchElementException if empty
}