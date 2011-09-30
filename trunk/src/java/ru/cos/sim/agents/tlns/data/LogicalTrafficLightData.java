/**
 * 
 */
package ru.cos.sim.agents.tlns.data;

/**
 * 
 * @author zroslaw
 */
public class LogicalTrafficLightData {
	
	private int id;
	
	private String name;
	
	private PlacementData placement;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlacementData getPlacement() {
		return placement;
	}

	public void setPlacement(PlacementData placement) {
		this.placement = placement;
	}
	
}
