/**
 * 
 */
package ru.cos.sim.meters.framework;


/**
 *
 * @author zroslaw
 */
public abstract class Meter<T extends MeasuredData<T>> implements Cloneable{
	
	protected Measurer<T> measurer;
	
	protected T instantMeasuredData;
	protected HistoryDataCollector<T> historyDataCollector;
	protected AverageDataCollector<T> averageDataCollector;
	protected ScheduledAverageDataCollector<T> scheduledAverageDataCollector;
	protected ScheduledHistoryDataCollector<T> scheduledHistoryDataCollector;
	
	protected AverageDataCollectorFactory<T> averageDataCollectorFactory;
	
	public Meter(ModesInitData modesInitData, Measurer<T> measurer, AverageDataCollectorFactory<T> averageDataCollectorFactory){
		// create measurer
		this.measurer = measurer;
		this.averageDataCollectorFactory = averageDataCollectorFactory;
		
		// create history data collector
		if (modesInitData.isHistoryMeasured()){
			this.historyDataCollector = new HistoryDataCollector<T>(
				modesInitData.getHistoryLogInterval()	
				);
		}
		
		// create average data collector
		if (modesInitData.isAverageDataMeasured()){
			this.averageDataCollector = averageDataCollectorFactory.getInstance();
		}
		
		// create scheduled average data collector
		if (modesInitData.getAverageDataCollectorSchedule()!=null){
			this.scheduledAverageDataCollector = 
				new ScheduledAverageDataCollector<T>(
						0, 
						modesInitData.getAverageDataCollectorSchedule(), 
						averageDataCollectorFactory);
		}
		
		// create scheduled history data collector
		if (modesInitData.getHistoryDataCollectorSchedule()!=null){
			this.scheduledHistoryDataCollector = 
				new ScheduledHistoryDataCollector<T>(
						0, 
						modesInitData.getHistoryDataCollectorSchedule(), 
						modesInitData.getScheduledHistoryLogInterval());
		}
	}
	
	public void measure(float dt){
		measurer.measure(dt);
		instantMeasuredData = measurer.getInstantData();
		if(historyDataCollector!=null){
			historyDataCollector.considerInstantData(instantMeasuredData.clone(), dt);
		}
		if(averageDataCollector!=null){
			averageDataCollector.considerInstantData(instantMeasuredData.clone(), dt);
		}
		if(scheduledAverageDataCollector!=null){
			scheduledAverageDataCollector.considerInstantData(instantMeasuredData.clone(), dt);
		}
		if(scheduledHistoryDataCollector!=null){
			scheduledHistoryDataCollector.considerInstantData(instantMeasuredData.clone(), dt);
		}
	}
	
	
	/**
	 * Set measurer
	 * @param measurer
	 */
	public void setMeasurer(Measurer<T> measurer) {
		this.measurer = measurer;
	}

	/**
	 * Get instant measured value
	 * @return instant data measured by measurer
	 */
	public T getInstantMeasuredData(){
		return instantMeasuredData;
	}
	
	/**
	 * Get measurement history, if any
	 * @return measured history
	 */
	public MeasurementHistory<T> getMeasurementHistory(){
		if (historyDataCollector==null)
			return null;
		else
			return historyDataCollector.getPeriodData();
	}
	
	/**
	 * Get average data calculated during period of measurement, if any
	 * @return
	 */
	public T getAverageData(){
		if (averageDataCollector==null)
			return null;
		else
			return averageDataCollector.getPeriodData();
	}

	/**
	 * Get measurements of average data on scheduled time periods, if any
	 * @return
	 */
	public ScheduledData<T> getScheduledAverageData(){
		if (scheduledAverageDataCollector==null)
			return null;
		else
			return scheduledAverageDataCollector.getOutput();
	}
	
	/**
	 * Get history records of measured datas on scheduled time periods, if any  
	 * @return
	 */
	public ScheduledData<MeasurementHistory<T>> getScheduledHistoryData(){
		if (scheduledHistoryDataCollector==null)
			return null;
		else
			return scheduledHistoryDataCollector.getOutput();
	}

	public ScheduledHistoryDataCollector<T> getScheduledHistoryDataCollector() {
		return scheduledHistoryDataCollector;
	}

	public void setScheduledHistoryDataCollector(
			ScheduledHistoryDataCollector<T> scheduledHistoryDataCollector) {
		this.scheduledHistoryDataCollector = scheduledHistoryDataCollector;
	}

}
