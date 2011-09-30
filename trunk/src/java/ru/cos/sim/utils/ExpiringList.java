package ru.cos.sim.utils;

import java.util.Iterator;
import java.util.LinkedList;

public class ExpiringList<T> implements Iterable<T> {
	public ExpiringList() {
		this(new LinkedList<Timestamped<T>>());
	}
	
	private ExpiringList(LinkedList<Timestamped<T>> values) {
		this.values = values;
	}
	
	private LinkedList<Timestamped<T>> values;
	
	public void add(float timestamp, T value) {
		values.addLast(new Timestamped<T>(timestamp, value));
	}
	
	public int size() {
		return values.size();
	}
	
	public ExpiringList<T> extractAllOlderThan(float timestamp) {
		LinkedList<Timestamped<T>> extractedItems = new LinkedList<Timestamped<T>>();
		
		Iterator<Timestamped<T>> iter = values.iterator();
		while (iter.hasNext()) {
			Timestamped<T> item = iter.next();
			if (item.getTimestamp() >= timestamp) 
				break;
		
			extractedItems.add(item);
			iter.remove();
		}
		
		return new ExpiringList<T>(extractedItems);
	}
	
	public Iterable<Timestamped<T>> getTimestampedValues() {
		return values;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new AdaptIterator<Timestamped<T>, T>(values, new Adapter<Timestamped<T>, T>() {
			@Override
			public T adapt(Timestamped<T> source) {
				return source.getValue();
			}
		});
	}
	
	@Override
	public String toString() {
		String ret = "";
		for (Timestamped<T> value : values)
			ret += (ret.length() > 0 ? ", " : "") + value.getValue();
		return "[ " + ret + " ]";
	}
	
	public static class Timestamped<T> {

		public Timestamped(float timestamp, T value) {
			this.timestamp = timestamp;
			this.value = value;
		}
		
		private final float timestamp;
		public float getTimestamp() {
			return timestamp;
		}
		
		private final T value;
		public T getValue() {
			return value;
		}
	}
}
