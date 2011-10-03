/**
 * 
 */
package ru.cos.sim.communication.messages;

/**
 * 
 * @author zroslaw
 */
public class MeterDataRequest extends AbstractMessage {
	
	private int meterId;

	public MeterDataRequest(int meterId) {
		this.meterId = meterId;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.REQUEST_METER_DATA;
	}

	public int getMeterId() {
		return meterId;
	}

}
