/**
 * 
 */
package ru.cos.sim.meters.framework;

/**
 *
 * @author zroslaw
 */
public class NormalizedHistoryCollectorFactory<T extends MeasuredData<T>> extends HistoryDataCollectorFactory<T> {

	protected HistoryDataCollectorFactory<T> hdcf = new HistoryDataCollectorFactory<T>(logInterval);
	
	public NormalizedHistoryCollectorFactory(float logInterval) {
		super(logInterval);
	}

	@Override
	public PeriodDataCollector<T,MeasurementHistory<T>> getInstance() {
		return new NormalizedHistoryCollector<T>(hdcf.getInstance());
	}

}
