/**
 * 
 */
package ru.cos.sim.meters.framework;

import java.util.List;

/**
 *
 * @author zroslaw
 */
public class ModesInitData {
	// is average value measured during measurement time period
	private boolean isAverageDataMeasured = false;
	// is history of measurement data logged 
	private boolean isHistoryMeasured = false;
	private boolean isNormalizedHistory = false;
	private float historyLogInterval = 10; // time interval for history logging
	// schedule of average data gathering
	private List<TimePeriod> averageDataCollectorSchedule = null;
	// schedule for creating history set of measurement data on different time periods
	private List<TimePeriod> historyDataCollectorSchedule = null;
	private float scheduledHistoryLogInterval = 10; // log interval for scheduled histories
	 
	public boolean isHistoryMeasured() {
		return isHistoryMeasured;
	}
	public void setHistoryMeasured(boolean isHistoryMeasured) {
		this.isHistoryMeasured = isHistoryMeasured;
	}
	public boolean isAverageDataMeasured() {
		return isAverageDataMeasured;
	}
	public void setAverageDataMeasured(boolean isAverageDataMeasured) {
		this.isAverageDataMeasured = isAverageDataMeasured;
	}
	public List<TimePeriod> getAverageDataCollectorSchedule() {
		return averageDataCollectorSchedule;
	}
	public void setAverageDataCollectorSchedule(
			List<TimePeriod> averageDataCollectorSchedule) {
		this.averageDataCollectorSchedule = averageDataCollectorSchedule;
	}
	public List<TimePeriod> getHistoryDataCollectorSchedule() {
		return historyDataCollectorSchedule;
	}
	public void setHistoryDataCollectorSchedule(
			List<TimePeriod> historyDataCollectorSchedule) {
		this.historyDataCollectorSchedule = historyDataCollectorSchedule;
	}
	public void setHistoryLogInterval(float historyLogInterval) {
		this.historyLogInterval = historyLogInterval;
	}
	public float getHistoryLogInterval() {
		return historyLogInterval;
	}
	public void setScheduledHistoryLogInterval(float scheduledHistoryLogInterval) {
		this.scheduledHistoryLogInterval = scheduledHistoryLogInterval;
	}
	public float getScheduledHistoryLogInterval() {
		return scheduledHistoryLogInterval;
	}
	public void setNormalizedHistory(boolean isNormalizedHistory) {
		this.isNormalizedHistory = isNormalizedHistory;
	}
	public boolean isNormalizedHistory() {
		return isNormalizedHistory;
	}
	
}
