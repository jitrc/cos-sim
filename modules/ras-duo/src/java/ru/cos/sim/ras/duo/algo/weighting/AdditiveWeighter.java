package ru.cos.sim.ras.duo.algo.weighting;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ru.cos.sim.ras.duo.PathExtensions;
import ru.cos.sim.ras.duo.Weighter;

public class AdditiveWeighter implements Weighter {
	public AdditiveWeighter() {
	}
	
	public AdditiveWeighter(Collection<Weighter> weighters) {
		this();
		this.weighters.addAll(weighters);
	}
	
	private List<Weighter> weighters = new LinkedList<Weighter>();
	
	public void add(Weighter weighter) {
		weighters.add(weighter);
	}
	
	@Override
	public float getWeight(PathExtensions extensions) {
		float weight = 0;
		for (Weighter weighter : weighters)
			weight += weighter.getWeight(extensions);
		return weight;
	}
}
