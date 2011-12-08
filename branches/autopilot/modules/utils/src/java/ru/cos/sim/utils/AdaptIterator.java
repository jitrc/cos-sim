package ru.cos.sim.utils;

import java.util.Iterator;

public class AdaptIterator<S, D> extends CustomIterator<D> {
	public AdaptIterator(Iterable<? extends S> baseIterable, Adapter<S, D> adapter) {
		this.baseIterable = baseIterable;
		this.base = baseIterable.iterator();
		this.adapter = adapter;
	}
	
	private Iterable<? extends S> baseIterable;
	
	private Iterator<? extends S> base;
	private Adapter<S, D> adapter;
	
	@Override
	protected AdaptIterator<S, D> createRestartedCopy() {
		return new AdaptIterator<S, D>(baseIterable, adapter);
	}
	
	@Override
	public boolean hasNext() {
		return base.hasNext();
	}

	@Override
	public D next() {
		return adapter.adapt(base.next());
	}

	@Override
	public void remove() {
		base.remove();
	}
}
