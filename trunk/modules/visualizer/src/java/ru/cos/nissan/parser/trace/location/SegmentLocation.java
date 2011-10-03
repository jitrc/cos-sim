package ru.cos.nissan.parser.trace.location;

import org.jdom.Element;

import ru.cos.nissan.parser.trace.Segment;
import ru.cos.nissan.parser.utils.ItemParser;

public class SegmentLocation extends LinkLocation {
	
	protected int segmentId;
	protected Segment segment;
	
	public SegmentLocation(Element e)
	{
		super(Type.Segment);
		read(e);
	}
	
	protected SegmentLocation(Type type)
	{
		super(type);
	}
	
	public SegmentLocation(int segmentId,int linkId) {
		super(Type.Segment,linkId);
		this.segmentId = segmentId;
	}
	
	public SegmentLocation(int segmentId,int linkId,float position) {
		super(Type.Segment,linkId,position);
		this.segmentId = segmentId;
	}
	
	public SegmentLocation(Type type,int segmentId,int linkId) {
		super(type,linkId);
		this.segmentId = segmentId;
	}
	
	public SegmentLocation(Type type,int segmentId,int linkId, float position) {
		super(type,linkId,position);
		this.segmentId = segmentId;
	}

	public int getSegmentId() {
		return segmentId;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}
	
	public void read(Element e)
	{
		this.segmentId = ItemParser.getInteger(e, Fields.SegmentId.name());
		super.read(e);
	}
}
