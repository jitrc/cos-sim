/**
 * 
 */
package ru.cos.sim.driver.composite;


/**
 * General driver percepts.<br>
 * Contains perceptions for current, left and right lanes.
 * @author zroslaw
 */
public class Percepts {

	private TrajectoryPercepts leftPercepts = null;
	
	private TrajectoryPercepts currentPercepts = null;
	
	private TrajectoryPercepts rightPercepts = null;

	public TrajectoryPercepts getLeftPercepts() {
		return leftPercepts;
	}

	public void setLeftPercepts(TrajectoryPercepts leftPercepts) {
		this.leftPercepts = leftPercepts;
	}

	public TrajectoryPercepts getCurrentPercepts() {
		return currentPercepts;
	}

	public void setCurrentPercepts(TrajectoryPercepts currentPercepts) {
		this.currentPercepts = currentPercepts;
	}

	public TrajectoryPercepts getRightPercepts() {
		return rightPercepts;
	}

	public void setRightPercepts(TrajectoryPercepts rightPercepts) {
		this.rightPercepts = rightPercepts;
	}
	
}
