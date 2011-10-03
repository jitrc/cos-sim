/**
 * 
 */
package ru.cos.sim.communication.messages;

import ru.cos.sim.communication.dto.FrameData;

/**
 * Frame data message
 * @author zroslaw
 */
public class FrameDataMessage extends AbstractMessage {

	private FrameData frameData;
	
	@Override
	public final MessageType getMessageType() {
		return MessageType.FRAME_DATA;
	}

	public void setFrameData(FrameData frameData) {
		this.frameData = frameData;
	}

	public FrameData getFrameData() {
		return frameData;
	}

}
