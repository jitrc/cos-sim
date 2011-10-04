package ru.cos.sim.visualizer.trace.item;

import java.util.logging.Level;
import java.util.logging.Logger;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.trace.TraceHandler;
import ru.cos.sim.visualizer.trace.item.base.StaticEntity;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.parser.trace.location.LinkLocation;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location;
import ru.cos.sim.visualizer.traffic.parser.trace.location.Location.Type;

public class Gradient extends StaticEntity {

	private static Logger logger = Logger.getLogger(Gradient.class.getName());
	
	protected GradientPosition beginPosition;
	protected GradientPosition endPosition;
	
	public Gradient(ru.cos.sim.visualizer.traffic.parser.trace.staff.Gradient gradient)
	{
		super(gradient.id());
		
		Location location = gradient.getLocation();
		if (location.getType() == Type.Link) {
			
		} else {
			logger.log(Level.WARNING, "Gradient must have link location");
			return;
		}
		
		int linkId = ((LinkLocation)location ).getLinkId();
		
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		Link link = handler.getLink(linkId);
		
		this.beginPosition = calculatePosition(gradient.getStartPosition(), link);
		this.endPosition =calculatePosition(gradient.getEndPosition(), link);
		
	}
	
	private GradientPosition calculatePosition(float length,Link link)
	{
		Segment cs = link.getFirst();
		float clength = 0;
		
		Lane leftLane = null;
		while (cs != null) {
			leftLane = cs.getLeftLane();
			if (clength + leftLane.getLength() > length) {
				return new GradientPosition(cs, calculatePosition(leftLane, length - clength),
						calculateDirection(leftLane, length - clength));
			}
			clength += leftLane.getLength();
			cs = cs.next;
		}
		
		return null;
	}
	
	private Vector2f calculatePosition(Lane lane, float pos)
	{
		Vector2f d = lane.getEnd().subtract(lane.getBegin());
		d.normalizeLocal().multLocal(pos);
		return d;
	}
	
	private Vector2f calculateDirection(Lane lane, float pos)
	{
		Vector2f d = lane.getEnd().subtract(lane.getBegin());
		d.normalizeLocal().rotate90();
		return d;
	}
	
	public GradientPosition getBeginPosition() {
		return beginPosition;
	}

	public GradientPosition getEndPosition() {
		return endPosition;
	}

	public class GradientPosition {
		public Segment segment;
		public Vector2f position;
		public Vector2f direction;
		
		public GradientPosition(Segment segment, Vector2f position,
				Vector2f direction) {
			super();
			this.segment = segment;
			this.position = position;
			this.direction = direction;
		}
		
	}
	
}
