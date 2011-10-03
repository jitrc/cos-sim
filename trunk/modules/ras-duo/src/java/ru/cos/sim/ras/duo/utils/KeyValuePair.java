package ru.cos.sim.ras.duo.utils;

public class KeyValuePair<K extends Comparable<K>, V> extends Pair<K, V> implements Comparable<KeyValuePair<K, V>> {
	public KeyValuePair(K key, V value) {
		super(key, value);
	}
	
	public K getKey() {
		return getFirst();
	}
	
	public V getValue() {
		return getSecond();
	}
	
	@Override
	public int compareTo(KeyValuePair<K, V> other) {
		return getKey().compareTo(other.getKey());
	}
}
