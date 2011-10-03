package ru.cos.sim.ras.duo;

import ru.cos.sim.ras.duo.algo.DefaultSolution;
import ru.cos.sim.ras.duo.algo.Solution;

public class Configuration {
	
	private static Configuration instance;
	public static Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}
	
	public static void setInstance(Configuration configuration) {
		instance = configuration;
	}
	
	public Solution.Factory getSolutionFactory() {
		return new DefaultSolution.Factory();
	}
	
	public Parameters getDefaultParameters() {
		return new Parameters();
	}
}
