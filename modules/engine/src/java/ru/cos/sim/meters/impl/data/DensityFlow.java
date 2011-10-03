/**
 * 
 */
package ru.cos.sim.meters.impl.data;

import ru.cos.sim.meters.framework.MeasuredData;


/**
 * Measured state of traffic flow on a road.
 * State of the flow on the fundamental traffic diagram.
 * Such state is measured as pair of two values - density and flow
 * @author zroslaw
 */
public class DensityFlow implements MeasuredData<DensityFlow> {
	private Density density;
	private Flow flow;
	
	public DensityFlow(Density density, Flow flow) {
		super();
		this.density = density;
		this.flow = flow;
	}
	
	public Density getDensity() {
		return density;
	}
	
	public Flow getFlow() {
		return flow;
	}

	public void setDensity(Density density) {
		this.density = density;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DensityFlow clone(){
		DensityFlow result;
		try {
			result = (DensityFlow) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		result.density = (Density) this.density.clone();
		result.flow = (Flow) this.flow.clone();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return density+"/"+flow;
	}

	@Override
	public void normalize(DensityFlow norma) {
		throw new UnsupportedOperationException();
	}

	
	
}
