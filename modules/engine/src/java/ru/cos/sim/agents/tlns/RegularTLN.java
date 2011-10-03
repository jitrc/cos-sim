package ru.cos.sim.agents.tlns;

/**
 * Regular Traffic Light Network.
 * @author zroslaw
 */
public class RegularTLN extends AbstractTrafficLightNetwork {

	/**
	 * Regular periods schedule.
	 */
	private ScheduleTable schedule;
	
	/**
	 * Current TLN period.
	 */
	private TimePeriod currentPeriod;
	
	/**
	 * Acting of the regular TLN agent consists of checking on each time step 
	 * if current period is active, and if it is not, then change current period to next one,
	 * according to the schedule.<p>
	 */
	@Override
	public void act(float dt) {
		if (currentPeriod==null){
			currentPeriod = schedule.getNextPeriod();
			setSignals(currentPeriod.getTrafficLightSignals());
		}
		currentPeriod.act(dt);
		if (!currentPeriod.isActive()){
			currentPeriod = schedule.getNextPeriod();
			setSignals(currentPeriod.getTrafficLightSignals());
		}
	}

	@Override
	public final TLNType getTLNType() {
		return TLNType.RegularTrafficLightNetwork;
	}

	public ScheduleTable getSchedule() {
		return schedule;
	}

	public void setSchedule(ScheduleTable schedule) {
		this.schedule = schedule;
	}

	public TimePeriod getCurrentPeriod() {
		return currentPeriod;
	}

}
