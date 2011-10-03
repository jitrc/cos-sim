package ru.cos.trace.item.base;

import ru.cos.math.Vector2f;
import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.parser.trace.base.SideableStaff.Side;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.nissan.parser.trace.location.LinkLocation;
import ru.cos.nissan.parser.trace.location.Location;
import ru.cos.nissan.parser.trace.location.SegmentLocation;
import ru.cos.nissan.parser.trace.location.Location.Type;
import ru.cos.trace.TraceHandler;
import ru.cos.trace.item.Lane;
import ru.cos.trace.item.Segment;

public abstract class LocatableItem extends StaticEntity{

	protected static float defaultDistance = 5f;
	protected static Side defaultSide = Side.Left;
	
	protected Segment segment;
	protected Vector2f position;
	protected Vector2f direction = null;
	protected Vector2f laneposition = null;
	
	protected Vector2f getPosition()
	{
		return this.position;
	}
	
	public LocatableItem(int id,Location location) {
		this(id,location,null);
	}
	
	public LocatableItem(int id ,Location location, Side side)
	{
		this(id,location,side,false,false);
	}
	
	protected LocatableItem(int id ,Location location, Side side, 
			boolean ignoreLaneLocation,boolean ignoreSegmentLocation)
	{
		super(id);
		if (location == null) return;
		if (side == null) side = defaultSide;
		Lane lane = null;
		Segment s = null;
		
		float position = 0;
		
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		if (location.getType() == Type.Lane && !ignoreLaneLocation) {
			LaneLocation l = (LaneLocation) location;
			s = handler.getLink(l.getLinkId()).getSegment(l.getSegmentId());
			lane = s.getLane(l.getLaneId());
		} else {
			if (location.getType() == Type.Lane) {
				LaneLocation l  = (LaneLocation) location;
				location = new SegmentLocation(l.getSegmentId(), l.getLinkId());
			}
		}
		
		if (location.getType() == Type.Segment && !ignoreSegmentLocation) {
			SegmentLocation l = (SegmentLocation) location;
			s = handler.getLink(l.getLinkId()).getSegment(l.getSegmentId());
		} else {
			if (location.getType() == Type.Segment) {
				SegmentLocation l  = (SegmentLocation) location;
				location = new LinkLocation(l.getLinkId());
			}
		}
		
		if (location.getType() == Type.Link) {
			LinkLocation l = (LinkLocation) location;
			s = handler.getLink(l.getLinkId()).getFirst();
		}
		
		if (s == null) {
			return;
		}
		
		this.segment = s;
		
		if (location.getType() == Type.Link || location.getType() == Type.Segment) {
			if (side == Side.Left) lane = s.getLeftLane();
			if (side == Side.Right) lane = s.getRightLane();
		}
		
		if (lane == null) {
			return;
		}
		
		if (location.isHasPosition()) position = location.getPosition();
		
		if (side == Side.Left) {
			direction = lane.getEnd().subtract(lane.getBegin());
			laneposition = direction.normalize().multLocal(position).addLocal(lane.getBegin());
			direction.rotate90();
		}
		if (side == Side.Right){
			direction = lane.getEnd().subtract(lane.getBegin());
			laneposition = direction.normalize().multLocal(position).addLocal(lane.getBegin());
			direction.rotate90right();
		}
		
	}

	public Segment getSegment() {
		return segment;
	}
	
	public Vector2f getLanePosition()
	{
		return laneposition;
	}
	
	public Vector2f getDirection()
	{
		return direction;
	}
	
}
