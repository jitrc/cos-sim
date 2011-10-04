package ru.cos.sim.visualizer.traffic.graphs.impl;

import ru.cos.sim.meters.framework.MeasuredData;

public interface IDataRequestable<MD extends MeasuredData<MD>> {

	/**
	 * Translates request r to the Simulation Engine.
	 *  
	 * @param r - request
	 */
	public void requestData(IRequest<MD> r);
	
	/**
	 * Rechecks all unfinished requests. 
	 * If data for request received, sends respond to the request; 
	 */
	public void checkRequests();
}
