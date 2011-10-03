package ru.cos.sim.ras.duo.utils;

import java.util.HashMap;
import java.util.Map;

public class ExtensionCollection {
	
	private static int nextExtensionNumber = 0;
	public static int getFreeExtensionNumber() {
		return nextExtensionNumber++;
	}
	
	private Map<Object, Object> extensions = new HashMap<Object, Object>();
	
	public <T> T get(int extensionNumber, Class<T> type, SimpleFactory<T> factory) {
		T  extension = get(extensionNumber, type);
		if (extension == null) {
			extension = factory.createNew();
			put(extensionNumber, extension);
		}
		return extension;
	}

	//TODO YP: test only, remove
	protected Iterable<Map.Entry<Object, Object>> getAll() {
		return extensions.entrySet();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int extensionNumber, Class<T> type) {
		Object extension = extensions.get(extensionNumber);
		if (extension != null && type.isAssignableFrom(extension.getClass()))
			return (T)extension;
		else
			return null;
	}
	
	public void put(int extensionNumber, Object extension) {
		extensions.put(extensionNumber, extension);
	}
	
	public void free(int extensionNumber) {
		extensions.remove(extensionNumber);
	}
}
