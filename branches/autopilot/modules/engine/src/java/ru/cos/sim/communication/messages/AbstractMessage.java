/**
 * 
 */
package ru.cos.sim.communication.messages;

import ru.cos.cs.agents.framework.asynch.Message;

/**
 * Abstract communication message between client thread and thread of traffic simulation engine.
 * @author zroslaw
 */
public abstract class AbstractMessage implements Message {

	/**
	 * Types of messages between client thread and traffic simulation engine.
	 * @author zroslaw
	 */
	public enum MessageType{
		REQUEST_FRAME_DATA, 
		FRAME_DATA, 
		REQUEST_AVAILABLE_METERS, 
		AVAILABLE_METERS, 
		REQUEST_METER_DATA, 
		METER_DATA,
		FRAME_PROPERTIES
	}
	
	/**
	 * Check concrete type of the message.
	 * @return type of the message
	 */
	public abstract MessageType getMessageType();
	
}
