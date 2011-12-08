package ru.cos.sim.services;

import ru.cos.sim.engine.Clock;

public interface TimeService {

	public float getAbsoluteTime();

	public static class Default implements TimeService {
		public Default(Clock timeProvider) {
			this.timeProvider = timeProvider;
		}
		
		private final Clock timeProvider;
		
		@Override
		public float getAbsoluteTime() {
			return timeProvider.getCurrentTime();
		}
	}
}
