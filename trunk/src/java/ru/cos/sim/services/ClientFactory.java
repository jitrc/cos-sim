package ru.cos.sim.services;

public interface ClientFactory<T> {

	public T createClient();

	public static class Static<T> implements ClientFactory<T> {
		public Static(T staticClient) {
			this.staticClient = staticClient;
		}
		
		private final T staticClient;
		
		@Override
		public T createClient() {
			return staticClient;
		}
	}
	
}
