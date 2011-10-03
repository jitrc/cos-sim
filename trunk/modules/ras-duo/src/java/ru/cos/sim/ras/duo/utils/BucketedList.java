package ru.cos.sim.ras.duo.utils;

import java.util.LinkedList;
import java.util.List;

public class BucketedList<K extends Comparable<K>, C> {

	private boolean isExclusive = true;
	public boolean isExclusive() {
		return isExclusive;
	}
	public void setExclusive(boolean isExclusive) {
		this.isExclusive = isExclusive;
	}

	private List<Bucket<K, C>> buckets = new LinkedList<Bucket<K, C>>();
	public Iterable<Bucket<K, C>> getBuckets() {
		return buckets;
	}
	
	public void addBucket(Bucket<K, C> bucket) {
		buckets.add(bucket);
	}
	
	public void addItem(K value, Putter<C> putter) {
		for (Bucket<K, C> bucket : buckets)
			if (bucket.tryPut(value, putter))
				if (isExclusive())
					return;
	}
	
	public interface Putter<C> {
		public void putTo(C container);
	}
	
	public static class Bucket<K extends Comparable<K>, C> {
		public Bucket(K min, K max, C container) {
			this.min = min;
			this.max = max;
			this.container = container;
		}
		
		private final K min;
		public K getMin() {
			return min;
		}
		
		private final K max;
		public K getMax() {
			return max;
		}

		private C container;
		public C getContainer() {
			return container;
		}
		
		public boolean tryPut(K value, Putter<C> putter) {
			if (value.compareTo(min) > 0 && value.compareTo(max) < 0) {
				putter.putTo(container);
				return true;
			} else 
				return false;
		}
	}
}
