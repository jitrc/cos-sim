package ru.cos.sim.ras.duo.utils;

import java.util.Map;

public class CachingMap<K, V> {
	public CachingMap(Map<K, V> backingMap, Map<K, V> cache) {
		this.backingMap = backingMap;
		this.cache = cache;
	}
	
	private Map<K, V> backingMap;
	private Map<K, V> cache;
	
	public V get(K key) {
		V value = cache.get(key);
		if (value == null) {
			value = backingMap.get(key);
			cache.put(key, value);
		}
		return value;
	}
}

