package ru.cos.sim.parameters;

import ru.cos.sim.parameters.data.ModelParametersData;

public class ModelParameters {

	public ModelParameters(ModelParametersData data) {
		this.randomSeed = data.getRandomSeed();
	}
	
	private final long randomSeed;
	public long getRandomSeed() {
		return randomSeed;
	}
}
