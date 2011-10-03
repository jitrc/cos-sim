package ru.cos.trace.item;

import java.util.HashMap;

import ru.cos.frame.FrameDataHandler;
import ru.cos.frame.LinkFrameData;
import ru.cos.trace.item.base.Entity;

public class Link extends Entity {

	protected HashMap<Integer, Segment> segments;
	
	protected Segment first;
	protected Segment last;
	protected LinkFrameData lfdata;
	
	public Link(int uid) {
		super(uid);
		
		this.lfdata = new LinkFrameData(this.id());
		FrameDataHandler.getInstance().addLink(this.id(), lfdata);
		this.segments = new HashMap<Integer, Segment>();
	}
	
	public LinkFrameData getFrameData()
	{
		return this.lfdata;
	}

	public Segment getFirst() {
		return first;
	}

	public Segment getLast() {
		return last;
	}

	public void setFirst(Segment first) {
		this.first = first;
	}

	public void setLast(Segment last) {
		this.last = last;
	}
	
	public void addSegment(Segment s)
	{
		this.segments.put(s.id(), s);
	}
	
	public Segment getSegment(int id)
	{
		return this.segments.get(id);
	}
	
}
