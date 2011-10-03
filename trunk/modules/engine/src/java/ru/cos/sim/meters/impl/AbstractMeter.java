/**
 * 
 */
package ru.cos.sim.meters.impl;

import ru.cos.sim.agents.TrafficAgent;
import ru.cos.sim.meters.framework.AverageDataCollectorFactory;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.Measurer;
import ru.cos.sim.meters.framework.Meter;
import ru.cos.sim.meters.framework.ModesInitData;

/**
 *
 * @author zroslaw
 */
public abstract class AbstractMeter<T extends MeasuredData<T>> extends Meter<T> implements TrafficAgent{

	protected int meterId;
	protected MeterType type;
	protected String name;
	
	protected int agentId;
	protected boolean isAlive = true;
	
	public AbstractMeter(int id, MeterType type, ModesInitData modesInitData, Measurer<T> measurer,
			AverageDataCollectorFactory<T> averageDataCollectorFactory) {
		super(modesInitData, measurer, averageDataCollectorFactory);
		this.meterId = id;
		this.type = type;
	}

	@Override
	public void act(float dt) {
		measure(dt);
	}

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	@Override
	public int getAgentId() {
		return agentId;
	}

	@Override
	public void kill() {
		isAlive = false;
	}

	@Override
	public TrafficAgentType getTrafficAgentType() {
		return TrafficAgentType.Meter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMeterId() {
		return meterId;
	}

	public MeterType getType() {
		return type;
	}

}
