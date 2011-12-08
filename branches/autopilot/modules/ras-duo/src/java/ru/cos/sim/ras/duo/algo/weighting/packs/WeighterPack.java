package ru.cos.sim.ras.duo.algo.weighting.packs;

import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.algo.Solution;

public class WeighterPack {
	public WeighterPack(Solution solution) {
		this.solution = solution;
	}
	
	private Solution solution;
	
	protected Parameters getCommonParameters() {
		return solution.getCommonParameters();
	}
}
