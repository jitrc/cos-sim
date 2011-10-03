/**
 * 
 */
package ru.cos.sim.communication.messages;

import java.util.Set;

import ru.cos.sim.communication.dto.MeterShortData;

/**
 * 
 * @author zroslaw
 */
public class AvailableMetersMessage extends AbstractMessage {

	private Set<MeterShortData> meterShortData;
	
	@Override
	public MessageType getMessageType() {
		return MessageType.AVAILABLE_METERS;
	}

	public void setMeterShortData(Set<MeterShortData> meterShortData) {
		this.meterShortData = meterShortData;
	}

	public Set<MeterShortData> getMeterShortData() {
		return meterShortData;
	}

}
