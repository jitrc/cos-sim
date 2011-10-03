package ru.cos.sim.ras.duo.algo.weighting.packs;

import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.algo.Solution;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider;
import ru.cos.sim.ras.duo.algo.aggregating.AverageSpeedAggregator;
import ru.cos.sim.ras.duo.algo.aggregating.PerItemDataCollector;
import ru.cos.sim.ras.duo.algo.weighting.AverageSpeedWeighter;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;

public class PlainAverageSpeedLinkWeighterPack extends WeighterPack implements StandardWeightProvider.LinkWeighterPack {
	public PlainAverageSpeedLinkWeighterPack(Solution solution) {
		super(solution);
	}
	
	private PerItemDataCollector<Edge, DataSources> speedAggregators = new PerItemDataCollector<Edge, DataSources>();
	
	@Override
	public void collectData(DataSources medium) {
		speedAggregators.collectData(medium.getEdge(), medium);
	}
	
	private void addAverageSpeedCollector(Edge edge, DataCollector<DataSources> collector) {
		speedAggregators.add(edge, collector);
	}
	
	protected AverageSpeedAggregator createSpeedAggregator() {
		return new AverageSpeedAggregator(getCommonParameters());
	}
	
	protected Weighter createWeighter(float linkLength, AverageSpeedAggregator speedAggregator) {
		return new AverageSpeedWeighter(linkLength, speedAggregator);
	}
	
	@Override
	public Weighter createWeighter(LinkEdge link) {
		AverageSpeedAggregator speedAggregator = createSpeedAggregator();
		addAverageSpeedCollector(link, speedAggregator);
		return createWeighter(link.getLinkData().getLength(), speedAggregator);
	}
}
