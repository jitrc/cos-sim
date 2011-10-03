/**
 * 
 */
package ru.cos.sim.communication.messages;

import ru.cos.sim.communication.FrameProperties;

/**
 * Frame properties message.<br>
 * Usually it is sent by client thread to TSE to specify area on 
 * the road network from which client need to receive information.
 * @author zroslaw
 */
public class FramePropertiesMessage extends AbstractMessage {
	
	private FrameProperties frameProperties;

	public FramePropertiesMessage(FrameProperties frameProperties) {
		this.frameProperties = frameProperties;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.FRAME_PROPERTIES;
	}

	public FrameProperties getFrameProperties() {
		return frameProperties;
	}

}
