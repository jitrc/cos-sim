package ru.cos.sim.ras.duo.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ru.cos.sim.utils.CustomIterator;

public class ConcatIterator<E> extends CustomIterator<E> {
	public ConcatIterator() {
		this(new LinkedList<Iterable<? extends E>>());
	}
	
	public ConcatIterator(List<Iterable<? extends E>> iterables) {
		this.iterables = iterables;
	}
	
	private List<Iterable<? extends E>> iterables;

	private Iterator<? extends E> currentIterator = null;
	private int currentIterable = -1;

	@Override 
	protected ConcatIterator<E> createRestartedCopy() {
		return new ConcatIterator<E>(iterables);
	}
	
	public ConcatIterator<E> add(Iterable<? extends E> iterable) {
		LinkedList<Iterable<? extends E>> copy = new LinkedList<Iterable<? extends E>>(iterables);
		copy.add(iterable);
		return new ConcatIterator<E>(copy);
	}
	
	private void forward() {
		while ((currentIterator == null || !currentIterator.hasNext()) && currentIterable + 1 < iterables.size())
			currentIterator = iterables.get(++currentIterable).iterator();
	}
	
	@Override
	public boolean hasNext() {
		forward();
		return currentIterator != null && currentIterator.hasNext();
	}

	@Override
	public E next() {
		forward();
		return currentIterator.next();
	}

	@Override
	public void remove() {
		if (currentIterator != null)
			currentIterator.remove();
	}
}
