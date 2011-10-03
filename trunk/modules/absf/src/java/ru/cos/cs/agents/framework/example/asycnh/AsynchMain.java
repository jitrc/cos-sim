/**
 * 
 */
package ru.cos.cs.agents.framework.example.asycnh;

import ru.cos.cs.agents.framework.asynch.Message;
import ru.cos.cs.agents.framework.example.SimpleAgent;
import ru.cos.cs.agents.framework.example.asycnh.messages.AgentsListMessage;

/**
 * 
 * @author zroslaw
 */
public class AsynchMain {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		System.out.println("Start");
		
		SimpleAsynchSE ase = new SimpleAsynchSE();
		ase.init();
		ase.setTimeWrapFactor(10);
		
		Thread aseThread = new  Thread(ase);
		aseThread.setName("Simulation Engine");
		
		aseThread.start();
		Thread.sleep(500);
		ase.pauseSimulation();
		ase.sendMessage(new Message() {});
		ase.sendMessage(new Message() {});
		
		processIncomingMessages(ase);
		ase.resumeSimulation();
		Thread.sleep(5000);
		ase.sendMessage(new Message() {});

		Thread.sleep(50);

		processIncomingMessages(ase);
		ase.stopSimulation();

		
		
		Thread.sleep(50);
		System.out.println(aseThread.isAlive());

	}

	private static void processIncomingMessages(SimpleAsynchSE ase) {
		Message message;
		while((message=ase.receiveMessage())!=null){
			if (message instanceof  AgentsListMessage){
				AgentsListMessage agentsListMessage = (AgentsListMessage)message;
				for (SimpleAgent agent:agentsListMessage.getAgents()){
					System.out.print(agent+" ");
				}
				System.out.println();
			}
		}
	}

}
