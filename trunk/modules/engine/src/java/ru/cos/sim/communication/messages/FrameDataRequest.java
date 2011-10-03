/**
 * 
 */
package ru.cos.sim.communication.messages;

/**
 * Request for frame data to traffic simulation engine.
 * @author zroslaw
 */
public class FrameDataRequest extends AbstractMessage {

	@Override
	public final MessageType getMessageType() {
		return MessageType.REQUEST_FRAME_DATA;
	}

}
