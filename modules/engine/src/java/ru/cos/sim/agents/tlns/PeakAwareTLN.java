/**
 * 
 */
package ru.cos.sim.agents.tlns;


/**
 * Peak Aware Traffic Light Network.
 * @author zroslaw
 */
public class PeakAwareTLN extends AbstractTrafficLightNetwork {

	private ScheduleTable regularSchedule;
	
	private ScheduleTable peakSchedule;
	
	private float peakPeriodStart;
	
	private float peakPeriodDuration;
	
	/**
	 * Internal timer to detect peak period
	 */
	private float timer = 0;
	
	/**
	 * Current TLN period.
	 */
	private TimePeriod currentPeriod;
	
	/**
	 * Current schedule - regular or peak
	 */
	private ScheduleTable currentSchedule;
	
	@Override
	public void act(float dt) {
		timer+=dt;
		if (timer>peakPeriodStart && timer<peakPeriodStart+peakPeriodDuration){
			currentSchedule=peakSchedule;
		}else
			currentSchedule=regularSchedule;

		if (currentPeriod==null){
			currentPeriod = currentSchedule.getNextPeriod();
			setSignals(currentPeriod.getTrafficLightSignals());
		}
		currentPeriod.act(dt);
		if (!currentPeriod.isActive()){
			currentPeriod = currentSchedule.getNextPeriod();
			setSignals(currentPeriod.getTrafficLightSignals());
		}
		
	}

	public void shiftSchedule(float scheduleTimeShift) {
		regularSchedule.shift(scheduleTimeShift);
	}

	public ScheduleTable getRegularSchedule() {
		return regularSchedule;
	}

	public void setRegularSchedule(ScheduleTable regularSchedule) {
		this.regularSchedule = regularSchedule;
	}

	public ScheduleTable getPeakSchedule() {
		return peakSchedule;
	}

	public void setPeakSchedule(ScheduleTable peakSchedule) {
		this.peakSchedule = peakSchedule;
	}

	public float getPeakPeriodStart() {
		return peakPeriodStart;
	}

	public void setPeakPeriodStart(float peakPeriodStart) {
		this.peakPeriodStart = peakPeriodStart;
	}

	public float getPeakPeriodDuration() {
		return peakPeriodDuration;
	}

	public void setPeakPeriodDuration(float peakPeriodDuration) {
		this.peakPeriodDuration = peakPeriodDuration;
	}

	@Override
	public final TLNType getTLNType() {
		return TLNType.PeakAwareTrafficLightNetwork;
	}

}
