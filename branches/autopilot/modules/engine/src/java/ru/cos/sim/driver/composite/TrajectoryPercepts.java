/**
 * 
 */
package ru.cos.sim.driver.composite;

import java.util.List;

/**
 * 
 * @author zroslaw
 */
public class TrajectoryPercepts {
	
	private Perception frontObstacle;
	
	private Perception backObstacle;

	private Perception frontJoin;
	
	private Perception frontFork;
	
	private List<Perception> frontTrafficLights;
	
	private List<Perception> roadSigns;

	public Perception getFrontObstacle() {
		return frontObstacle;
	}

	public void setFrontObstacle(Perception frontObstacle) {
		this.frontObstacle = frontObstacle;
	}

	public Perception getBackObstacle() {
		return backObstacle;
	}

	public void setBackObstacle(Perception backObstacle) {
		this.backObstacle = backObstacle;
	}

	public Perception getFrontJoin() {
		return frontJoin;
	}

	public void setFrontJoin(Perception frontJoin) {
		this.frontJoin = frontJoin;
	}

	public Perception getFrontFork() {
		return frontFork;
	}

	public void setFrontFork(Perception frontFork) {
		this.frontFork = frontFork;
	}

	public void setFrontTrafficLights(List<Perception> frontTrafficLights) {
		this.frontTrafficLights = frontTrafficLights;
	}

	public List<Perception> getFrontTrafficLights() {
		return frontTrafficLights;
	}

	public List<Perception> getRoadSigns() {
		return roadSigns;
	}

	public void setRoadSigns(List<Perception> roadSigns) {
		this.roadSigns = roadSigns;
	}

}
