package ru.cos.sim.visualizer.traffic.parser.trace.location;


public class GlobalLocation extends Location {
	
	public GlobalLocation() {
		super(Type.Global);
		this.hasPosition = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isHasPosition() {
		return false;
	}

	@Override
	public void setPosition(float position) {
	}
}
