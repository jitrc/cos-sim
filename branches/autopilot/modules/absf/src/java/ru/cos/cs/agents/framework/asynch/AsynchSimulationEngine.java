package ru.cos.cs.agents.framework.asynch;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.print.attribute.standard.Finishings;

import ru.cos.cs.agents.framework.SimulationEngine;

/**
 * Asynchronous version of simulation engine.<p>
 * Asynchronous means that simulation process will run in a separate thread and 
 * therefore application program thread will not be affected(at least explicitly) by performance of simulation process.<br>
 * It is very useful for application programs with GUI that will never hangs up while simulation step is calculated.
 * Asynchronous Simulation Engine implements behavior of simulation simulation and provides its all synchronization
 * functionality to prevent "race conditions".<p>
 * Communication between application program and simulation engine is implemented by asynchronous sending and receiving messages.
 * @author zroslaw
 */
public abstract class AsynchSimulationEngine<SE extends SimulationEngine> implements Runnable{
	
	/**
	 * Time step value.
	 */
	protected float dt = 0.1f;
	
	/**
	 * {@link SimulationEngine}
	 */
	protected SE simulationEngine;
	
	/**
	 * Incoming messages queue
	 */
	protected Queue<Message> inQueue = new ConcurrentLinkedQueue<Message>();
	
	/**
	 * Outgoing messages queue
	 */
	protected Queue<Message> outQueue = new ConcurrentLinkedQueue<Message>();

	/**
	 * Is simulation process stopped flag.
	 */
	private boolean isStopped = false;
	/**
	 * Is simulation process paused.
	 */
	private boolean isPaused = true;
	/**
	 * Command to stop simulation process.
	 */
	private boolean stopFlag = false;
	/**
	 * Command to pause simulation process.
	 */
	private boolean pauseFlag = false;

	/**
	 * Time wrapper.
	 */
	private TimeWrapper timeWrapper = new TimeWrapper();
	
	private Thread seThread = null;

	/**
	 * Construct asynchronous SE on the base of synchronous one
	 * @param simulationEngine
	 */
	public AsynchSimulationEngine(SE simulationEngine){
		this.simulationEngine = simulationEngine;
	}
	
	
	/**
	 * Run thread of simulation.
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		seThread = Thread.currentThread();
		while(stopFlag==false){
			startStep();
			if (pauseFlag){
				synchronized (this) {
					try {
						processAllIncomingMessages(); // in some cases there can be some messages
						isPaused = true;
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// step of simulation
			simulationEngine.step(dt);
			// process control messages
			processAllIncomingMessages();
			// wait a bit if needed
			timeWrapper.respectTimeWrapFactor(dt);
			finishStep();
		}
		
		synchronized (this) {
			isStopped = true;
			processAllIncomingMessages(); // in some cases there can be some messages
		}
		
	}

	/**
	 * Methods invoked each time simulation step finishes
	 */
	protected void finishStep() {}

	/**
	 * Method invoked each time new simulation step starts
	 */
	protected void startStep() {}


	/**
	 * Iterate through controller messages and process them all
	 */
	private void processAllIncomingMessages(){
		Message message;
		while((message=inQueue.poll())!=null){
			processIncomingMessage(message);
		}
	}
	
	protected abstract void processIncomingMessage(Message message);

	/** 
	 * Stop simulation process.
	 * Simulation engine thread will finish current and last step and stop.
	 * Simulation thread will be stopped.
	 */
	public final void stopSimulation() {
		stopFlag=true;
	}

	/**
	 * Pause simulation engine.
	 * Simulation engine thread will finish current step and suspend.
	 * Simulation thread will be alive.
	 */
	public final void pauseSimulation() {
		pauseFlag=true;
	}

	/**
	 * Set time wrap factor.
	 * @param timeWrapFactor time wrap factor
	 */
	public final void setTimeWrapFactor(float timeWrapFactor){
		this.timeWrapper.setTimeWrapFactor(timeWrapFactor);
	}

	/**
	 * Resume simulation after it was paused by pauseSimulation() invocation.
	 */
	public void resumeSimulation() {
//		if (seThread!=null && !seThread.isAlive()){
//			return;
//		}
		pauseFlag=false;
		synchronized (this) {
			this.notifyAll();
		}
		isPaused = false;
	}

	/**
	 * Send message to simulation engine.
	 * It will be processed by thread of simulation engine if it is alive and running, else
	 * by thread that invokes this method.
	 * <p>
	 * Notice in some, very rare cases, message could not be processed. 
	 * This case is when stop or pause commands are sent right after request message.
	 * @param request controller message
	 */
	public void sendMessage(Message message) {
		synchronized (this) {
			if (isPaused || isStopped){
				processIncomingMessage(message);
				return;
			}
		}
		inQueue.add(message);
	}
	
	/**
	 * Receive message from simulation engine, if any.
	 * Received message will be removed from internal AsynchSE queue.
	 * @return message from SE or null, if there are no messages.
	 */
	public Message receiveMessage(){
		return outQueue.poll();
	}

	/**
	 * Return status of isPaused flag.
	 * Generally, if it is true, then simulation is paused, but it is not guaranteed. 
	 * In some cases simulation process may finishes it's last step before pausing.
	 * @return isPaused flag status
	 */
	public boolean isPaused(){
		return isPaused;
	}
	
	/**
	 * Return status of isStopped flag.
	 * Generally, if it is true, then simulation is stopped, but it is not guaranteed. 
	 * In some cases simulation process may finishes it's last step before stopping.
	 * @return isStopped flag status
	 */
	public boolean isStopped(){
		return isStopped;
	}
	
}
