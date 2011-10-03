package ru.cos.sim.ras.duo.algo.weighting.packs;

import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Segmentator;
import ru.cos.sim.ras.duo.Weighter;
import ru.cos.sim.ras.duo.algo.Solution;
import ru.cos.sim.ras.duo.algo.StandardWeightProvider;
import ru.cos.sim.ras.duo.algo.aggregating.AverageSpeedAggregator;
import ru.cos.sim.ras.duo.algo.aggregating.PerItemDataCollector;
import ru.cos.sim.ras.duo.algo.aggregating.PerSegmentDataCollector;
import ru.cos.sim.ras.duo.algo.weighting.AdditiveWeighter;
import ru.cos.sim.ras.duo.algo.weighting.AverageSpeedWeighter;
import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.digraph.roadnet.LinkEdge;

public class SegmentedAverageSpeedLinkWeighterPack extends WeighterPack implements StandardWeightProvider.LinkWeighterPack {

	public SegmentedAverageSpeedLinkWeighterPack(Solution solution) {
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

	public float getSegmentSize() {
		return getCommonParameters().getSegmentation().getSegmentSize();
	}
	
	protected AverageSpeedAggregator createSpeedAggregator() {
		return new AverageSpeedAggregator(getCommonParameters());
	}
	
	protected Weighter createSegmentWeighter(float segmentLength, AverageSpeedAggregator speedAggregator) {
		return new AverageSpeedWeighter(segmentLength, speedAggregator);
	}
	
	@Override
	public Weighter createWeighter(LinkEdge link) {
		AdditiveWeighter weighter = new AdditiveWeighter();
		PerSegmentDataCollector<AverageSpeedAggregator> speedAggregator = new PerSegmentDataCollector<AverageSpeedAggregator>();
		for (Segmentator.Segment segment : new Segmentator().fromTail(link.getLinkData().getLength(), getSegmentSize())) {
			AverageSpeedAggregator segmentAggregator = createSpeedAggregator();
			weighter.add(createSegmentWeighter(segment.getLength(), segmentAggregator));
			speedAggregator.add(segment, segmentAggregator);
		}
		addAverageSpeedCollector(link, speedAggregator);
		return weighter;
	}
}
