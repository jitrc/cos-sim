package ru.cos.sim.utils;

import java.util.Iterator;

public abstract class CustomIterator<D> implements Iterator<D> {

	protected abstract CustomIterator<D> createRestartedCopy();

	public Iterable<D> asIterable() {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return createRestartedCopy();
			}
		};
	}
}
