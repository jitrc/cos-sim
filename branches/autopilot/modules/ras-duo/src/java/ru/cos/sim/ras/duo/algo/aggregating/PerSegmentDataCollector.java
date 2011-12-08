package ru.cos.sim.ras.duo.algo.aggregating;

import ru.cos.sim.ras.duo.DataCollector;
import ru.cos.sim.ras.duo.DataSources;
import ru.cos.sim.ras.duo.Segmentator;
import ru.cos.sim.ras.duo.utils.BucketedList;

public class PerSegmentDataCollector<T extends DataCollector<DataSources>> implements DataCollector<DataSources> {

	private BucketedList<Float, T> segments = new BucketedList<Float, T>();
	protected BucketedList<Float, T> getBuckets() {
		return segments;
	}
	
	public void add(Segmentator.Segment segment, T segmentAggregator) {
		segments.addBucket(new BucketedList.Bucket<Float, T>(segment.getStart(), segment.getEnd(), segmentAggregator));
	}

	@Override
	public void collectData(final DataSources medium) {
		segments.addItem(medium.getPosition(), new BucketedList.Putter<T>() {
			@Override
			public void putTo(T aggregator) {
				aggregator.collectData(medium);
			}
		});  
	}
}
