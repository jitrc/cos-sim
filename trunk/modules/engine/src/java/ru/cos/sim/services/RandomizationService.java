package ru.cos.sim.services;

import java.util.Random;

public interface RandomizationService {

	public Random getRandom();

	public static class Default implements RandomizationService {
		public Default(long seed) {
			this.random = new Random(seed);
		}
		
		private Random random;
		
		@Override
		public Random getRandom() {
			return random;
		}
	}
}
