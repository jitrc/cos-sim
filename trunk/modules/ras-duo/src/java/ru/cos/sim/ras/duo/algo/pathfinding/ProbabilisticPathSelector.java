package ru.cos.sim.ras.duo.algo.pathfinding;

import java.util.LinkedList;


import ru.cos.sim.ras.duo.Parameters;
import ru.cos.sim.ras.duo.WeightProvider;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.Vertex;
import ru.cos.sim.ras.duo.digraph.roadnet.PathUtils;
import ru.cos.sim.ras.duo.dijkstra.DijkstraProcessor;
import ru.cos.sim.ras.duo.dijkstra.DijkstraProcessor.PathSelector;
import ru.cos.sim.ras.duo.dijkstra.DijkstraVertexInfo.Backtrack;
import ru.cos.sim.ras.duo.utils.Dump;
import ru.cos.sim.ras.duo.utils.Pair;
import ru.cos.sim.ras.duo.utils.SortedList;
import ru.cos.sim.services.ServiceLocator;

public class ProbabilisticPathSelector implements PathSelector {

	public ProbabilisticPathSelector(float temperature) {
		this(new ProbabilisticSelector<Backtrack>(temperature));
	}

	public ProbabilisticPathSelector(ProbabilisticSelector<Backtrack> selector) {
		this.selector = selector;
	}
	
	private ProbabilisticSelector<Backtrack> selector;
	
	@Override
	public Backtrack select(SortedList<Backtrack> variants) {
		for (Backtrack variant : variants)
			selector.addVariant(variant.getTotalWeight(), variant);
			
		Backtrack selectedVariant = selector.select();
		selector.clear();
		return selectedVariant;
	}
	
	public static class UniformFactory implements PathSelector.Factory {
		public UniformFactory(float temperature) {
			this.temperature = temperature;
		}
		
		private float temperature;
		
		@Override
		public PathSelector createPathSelector(DijkstraProcessor processor, Vertex start, Vertex end) {
			return new ProbabilisticPathSelector(temperature);
		}
	}
	
	public static class BestPathSpeedCongestionFactory implements PathSelector.Factory {
		public BestPathSpeedCongestionFactory(WeightProvider travelTimeWeightProvider, Parameters parameters) {
			this.travelTimeWeightProvider = travelTimeWeightProvider;
			this.parameters = parameters;
		}
		
		private WeightProvider travelTimeWeightProvider;
		private Parameters parameters;
		
		@Override
		public PathSelector createPathSelector(DijkstraProcessor processor, Vertex start, Vertex end) {
			// Measure congestion on the best path
			LinkedList<Backtrack> bestPath = processor.collectPath(start, end, new BestOnePathSelector.Factory());
			Iterable<Edge> adaptedPath = DijkstraProcessor.adaptPath(bestPath);

			float congestion = 0;
			if (bestPath.size() > 0) {
				PathUtils bestPathUtils = new PathUtils(adaptedPath);
				float bestPathAverageSpeed = bestPathUtils.getLength() / bestPathUtils.getTravelTime(travelTimeWeightProvider, true);
				
				Dump.PATHFINDING.print("BPAS: " + bestPathAverageSpeed);
				
				congestion = getCongestion(bestPathAverageSpeed);
			}
			
			float temperature = getTemperature(congestion);
			
			Dump.PATHFINDING.print("Congestion: " + congestion);
			Dump.PATHFINDING.print("T=" + temperature);
			
			return new ProbabilisticPathSelector(temperature);
		}
		
		protected float getCongestion(float bestPathAverageSpeed) {
			return Math.max(0, 1 - bestPathAverageSpeed / parameters.getSpeedProfile().getLowCongestionSpeed());
		}
		
		protected float getTemperature(float congestion) {
			return Math.max(0.01f, parameters.getTemperatureProfile().getTemperature(congestion));
		}
	}
	
	public static class ProbabilisticSelector<T> {
		public ProbabilisticSelector(float temperature) {
			this.temperature = temperature;
		}
		
		private float temperature;
		public float getTemperature() {
			return temperature;
		}
		public void setTemperature(float temperature) {
			this.temperature = temperature;
		}
		
		private LinkedList<Pair<Float, T>> variants = new LinkedList<Pair<Float,T>>();  		
		
		public void addVariant(float energy, T item) {
			variants.add(new Pair<Float, T>(energy, item));
		}
		
		public void clear() {
			variants.clear();
		}

		private float getMinimumEnergy() {
			float minimumEnergy = Float.MAX_VALUE;
			for (Pair<Float, T> variant : variants)
				minimumEnergy = Math.min(minimumEnergy, variant.getFirst());
			return minimumEnergy;
		}

		private Pair<LinkedList<Pair<Float, T>>, Float> weightVariantsStandard(float minimumEnergy) {
			LinkedList<Pair<Float, T>> weightedItems = new LinkedList<Pair<Float,T>>();
			float weightSum = 0;
			for (Pair<Float, T> variant : variants) {
				float weight = getWeight(minimumEnergy, variant.getFirst(), temperature);
				weightedItems.add(new Pair<Float, T>(weight, variant.getSecond()));
				weightSum += weight;
			}
			return new Pair<LinkedList<Pair<Float, T>>, Float>(weightedItems, weightSum);
		}

		private Pair<LinkedList<Pair<Float, T>>, Float> weightVariantsZeroesOnly() {
			LinkedList<Pair<Float, T>> weightedItems = new LinkedList<Pair<Float,T>>();
			float weightSum = 0;
			for (Pair<Float, T> variant : variants) {
				if (variant.getFirst() == 0) {
					float weight = 1;
					weightedItems.add(new Pair<Float, T>(weight, variant.getSecond()));
					weightSum += weight;
				}
			}
			return new Pair<LinkedList<Pair<Float, T>>, Float>(weightedItems, weightSum);
		}
		
		private T select(Pair<LinkedList<Pair<Float, T>>, Float> weightedItemsAndSum) {
			LinkedList<Pair<Float, T>> weightedItems = weightedItemsAndSum.getFirst();
			float weightSum = weightedItemsAndSum.getSecond();
			
			double chosenWeight = getRandomFloat() * weightSum;
			for (Pair<Float, T> item : weightedItems) {
				chosenWeight -= item.getFirst();
				if (chosenWeight <= 0)
					return item.getSecond();
			}
			return weightedItems.getLast().getSecond();
		}
		
		private float getRandomFloat() {
			return ServiceLocator.getInstance().getRandomizationService().getRandom().nextFloat();
		}
		
		public T select() {
			if (variants.size() == 0) return null;
		
			float minimumEnergy = getMinimumEnergy();
			
			if (minimumEnergy == 0)
				return select(weightVariantsZeroesOnly());
			else
				return select(weightVariantsStandard(minimumEnergy));
		}
		
		protected float getWeight(float minimumEnergy, float energy, float temperature) {
			return (float)Math.exp(-Math.pow(energy / minimumEnergy - 1, 2) / temperature);
		}
	}
}

