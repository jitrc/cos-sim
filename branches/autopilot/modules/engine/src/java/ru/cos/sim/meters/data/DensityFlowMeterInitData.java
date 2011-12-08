/**
 * 
 */
package ru.cos.sim.meters.data;

/**
 *
 * @author zroslaw
 */
public class DensityFlowMeterInitData extends MeterData {
	
	private DensityMeterInitData densityMeterInitData;
	
	private FlowMeterInitData flowMeterInitData;
	
	/**
	 * @return the densityMeterInitData
	 */
	public DensityMeterInitData getDensityMeterInitData() {
		return densityMeterInitData;
	}
	
	/**
	 * @param densityMeterInitData the densityMeterInitData to set
	 */
	public void setDensityMeterInitData(DensityMeterInitData densityMeterInitData) {
		this.densityMeterInitData = densityMeterInitData;
	}
	
	/**
	 * @return the flowMeterInitData
	 */
	public FlowMeterInitData getFlowMeterInitData() {
		return flowMeterInitData;
	}
	
	/**
	 * @param flowMeterInitData the flowMeterInitData to set
	 */
	public void setFlowMeterInitData(FlowMeterInitData flowMeterInitData) {
		this.flowMeterInitData = flowMeterInitData;
	}
}
