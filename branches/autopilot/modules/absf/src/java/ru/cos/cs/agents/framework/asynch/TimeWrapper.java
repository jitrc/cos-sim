/**
 * 
 */
package ru.cos.cs.agents.framework.asynch;

/**
 * Magic class for virtual time wrapping.
 * @author zroslaw
 */
public class TimeWrapper {
	
	/**
	 * Time wrap factor.
	 * We have real time and time in the simulated model.
	 * realTime = (1/k) modelTime, where k is time wrap factor
	 */
	protected float timeWrapFactor = 1f;
	/**
	 * Period of virtual time (simulated time) to synchronize virtual and real time
	 * according to time wrap factor
	 */
	protected float timeSyncronizePeriod = 0.1f;
	
	/**
	 * Virtual time passed from last virtual and real time synchronization 
	 */
	protected float virtualTimePassedFromLastSynch = 0;
	
	/**
	 * Real time passed from last virtual and real time synchronization 
	 */
	protected long realTimeTagFromLastSynch;
	
	
	/**
	 * Respect time wrap factor.
	 * Method calculates time of real execution and model time and sleeps
	 * current thread to obey time wrap factor.
	 * Time to sleep is calculated as (virtualTime/k - executionTime)
	 * @param timeStep
	 */
	public void respectTimeWrapFactor(float dt) {
		virtualTimePassedFromLastSynch+=dt;
		if (virtualTimePassedFromLastSynch>timeSyncronizePeriod){
			
			float executionTime = (System.currentTimeMillis() - realTimeTagFromLastSynch)/1000f;
			float delayTime = virtualTimePassedFromLastSynch/timeWrapFactor-executionTime;
			if(delayTime>0){
				try {
					long sleepTime = (long)(delayTime*1000);
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			realTimeTagFromLastSynch=System.currentTimeMillis();
			virtualTimePassedFromLastSynch=0f;
		}
	}

	public final void setTimeWrapFactor(float timeWrapFactor){
		this.timeWrapFactor = timeWrapFactor;
	}
	
}
