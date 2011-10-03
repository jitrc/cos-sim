package ru.cos.sim.ras.duo.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class SortedList<T extends Comparable<T>> implements Iterable<T> {

	private LinkedList<T> values = new LinkedList<T>();

	public void add(T value) {
		ListIterator<T> iter = values.listIterator();
		while (iter.hasNext()) {
			if (iter.next().compareTo(value) > 0) {
				iter.previous();
				iter.add(value);
				return;
			}
		}
		iter.add(value);
	}

	public T peekMinimum() {
		return values.size() > 0 ? values.peekFirst() : null;
	}
	
	public T popMinimum() {
		return values.pollFirst();
	}

	public T peekMaximum() {
		return values.size() > 0 ? values.peekLast() : null; 
	}
	
	public T popMaximum() {
		return values.pollLast();
	}
	
	public int size() {
		return values.size();
	}
	
	public void clear() {
		values.clear();
	}

	public void remove(T value) {
		values.remove(value);
	}
	
	@Override
	public Iterator<T> iterator() {
		return values.iterator();
	}
}
