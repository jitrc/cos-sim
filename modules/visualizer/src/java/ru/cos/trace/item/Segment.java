package ru.cos.trace.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import ru.cos.frame.FrameDataHandler;
import ru.cos.frame.ViewableObjectInformation;
import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.geometry.SegmentGeometry;
import ru.cos.nissan.parser.geometry.Side;
import ru.cos.nissan.parser.geometry.Waypoint;
import ru.cos.nissan.parser.trace.Segment.SegmentType;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.scene.impl.IPlaceable;
import ru.cos.trace.item.base.StaticEntity;

public class Segment extends StaticEntity {

	protected Segment next;
	protected Segment previous;
	protected HashMap<Integer, Lane> lanes;
	protected ArrayList<Integer> laneslist;
	protected long lanesCount = 0;
	protected ArrayList<IPlaceable> staff;
	protected Waypoint begin;
	protected Waypoint end;
	protected SegmentGeometry geometry;
	protected Link link;
	protected SegmentType segmentType;
	private boolean lanesInited = false;
	private ViewableObjectInformation frameInfo;
	private static Logger logger = Logger.getLogger(Segment.class.getName());
	
	public Segment(ru.cos.nissan.parser.trace.Segment segment,Link link) {
		super(segment.id());
		this.renderMode = RenderMode.Simple;
		this.geometry = segment.getGeometry();
		this.lanesCount = 0;
		this.lanes = new HashMap<Integer, Lane>();
		this.laneslist = new ArrayList<Integer>();
		this.staff = new ArrayList<IPlaceable>();
		this.link = link;
		this.begin = segment.getBeginWaypoint();
		this.end = segment.getEndWaypoint();
		this.frameInfo = new ViewableObjectInformation(this.uid);
		this.frameInfo.linkData = this.link.getFrameData();
	}
	
	public boolean postComplete()
	{
		
		if (!lanesInited) {
			// if this segment - segment with lane narrows 
			//this.calculateLanes(previous.geometry.endSide, next.geometry.beginSide);
			this.postcompleteLanes();
			return true;
			//
		}
		return false;
	}
	
	public Segment getNext() {
		return next;
	}

	public void setNext(Segment next) {
		this.next = next;
	}

	public Segment getPrevious() {
		return previous;
	}

	public void setPrevious(Segment previous) {
		this.previous = previous;
	}
	
	public void addLane(Lane l)
	{
		this.lanes.put(l.id(), l);
		this.laneslist.add(l.id());
	}
	
	public Lane getLane(Integer id)
	{
		return this.lanes.get(id);
	}
	
	public ArrayList<Integer> getLanes()
	{
		return this.laneslist;
	}
	
	public long getLanesCount()
	{
		if (laneslist != null) lanesCount = laneslist.size();
		return lanesCount;
	}

	public Waypoint getBegin() {
		return begin;
	}

	public Waypoint getEnd() {
		return end;
	}
	
	public void setWaypoints(Waypoint begin , Waypoint end)
	{
		this.begin = begin;
		this.end = end;
	}
	
	public Lane getLeftLane()
	{
		Integer leftId = this.laneslist.get(0);
		if (leftId == null) return null;
		return this.lanes.get(leftId);
	}
	
	public Lane getRightLane()
	{
		Integer rightId = this.laneslist.get(this.laneslist.size() - 1);
		if (rightId == null) return null;
		return this.lanes.get(rightId);
	}
	
	public void addStaff(IPlaceable staff) {
		this.staff.add(staff);
	}
	
	public ArrayList<IPlaceable> getStaff() {
		return staff;
	}
	
	public boolean precomplete()
	{
		this.segmentType = SegmentType.Narrow;
		acceptLanes();
		if (!this.geometry.leftSide.overrided && !this.geometry.rightSide.overrided){
			calculateLanes(this.geometry.beginSide,this.geometry.endSide);
			this.segmentType = SegmentType.Simple;
			return true;
		} return false;
					
	}
	
	public void complete() {
		if (this.previous == null) {
			link.setFirst(this);
		}
		
		if (this.next == null){
			link.setLast(this);
		}
	}
	
	protected void postcompleteLanes()
	{
		//Segment s = (next.getLanes().size() > previous.getLanes().size()) ? next : previous;
		ArrayList<Integer> lanes = getLanes();
		
		boolean chooseRightSide = false;
		if (this.next.getLanesCount() > this.previous.getLanesCount()) chooseRightSide = true;
		Vector2f dir = null;
		for (Integer l : lanes)
		{
			Lane lane  = getLane(l);
			
			if (lane.getNext() != null && lane.getPrevious() != null) {
				dir = lane.getNext().getBegin().subtract(lane.getPrevious().getEnd());
			}
		}
		
		
		dir.normalizeLocal();
		
		if (chooseRightSide) {
			Vector2f rightSide = new Vector2f(this.next.getGeometry().beginSide.startX,
					this.next.getGeometry().beginSide.startY);
			Vector2f rightVector = this.next.getGeometry().beginSide.getVector().normalize();
			Vector2f ort = dir.clone();
			ort.rotate90();
			float sin1 = Math.abs(rightVector.mult(ort));
			if (sin1 == 0) sin1 = 1f;
			Vector2f leftVector = this.previous.getGeometry().endSide.getVector().normalize();
			float sin2 = Math.abs(leftVector.mult(ort));
			if (sin2 == 0) sin2 = 1f;
			//dir.negateLocal();
			for (Integer l : lanes)
			{
				Lane lane  = getLane(l);
				float width1  = lane.getWidth()/ sin1;
				float width2 = lane.getWidth()/ sin2;
				leftVector.normalizeLocal().multLocal(width2/2.0f);
				rightVector.normalizeLocal().multLocal(width1/2.0f);
				
				lane.setEnd(rightSide.add(rightVector));
				Vector2f leftPoint = lane.getEnd().subtract(dir.mult(lane.getDesiredLength())); 
				lane.setBegin(leftPoint);
				lane.setLength(lane.getDesiredLength());
				lane.normalVector = lane.getEnd().subtract(lane.getBegin()).normalizeLocal();
				Side s = new Side(leftPoint.x + leftVector.x, leftPoint.y + leftVector.y,
						lane.getEnd().x + rightVector.x,lane.getEnd().y + rightVector.y);
				lane.setRightSide(s);
				rightSide.addLocal(rightVector).addLocal(rightVector);
			}
		} else {
			Vector2f leftSide = new Vector2f(this.previous.getGeometry().endSide.startX,
					this.previous.getGeometry().endSide.startY);
			Vector2f leftVector = this.previous.getGeometry().endSide.getVector().normalize();
			Vector2f ort = dir.clone();
			ort.rotate90();
			float cos1 = Math.abs(leftVector.mult(ort));
			if (cos1 == 0) cos1 = 1f;
			Vector2f rightVector = this.next.getGeometry().beginSide.getVector().normalize();
			float cos2 = Math.abs(rightVector.mult(ort));
			if (cos2 == 0) cos2 = 1f;
			//dir.negateLocal();
			for (Integer l : lanes)
			{
				Lane lane  = getLane(l);
				float width1  = lane.getWidth()/ cos1;
				float width2 = lane.getWidth()/ cos2;
				leftVector.normalizeLocal().multLocal(width1/2.0f);
				rightVector.normalizeLocal().multLocal(width2/2.0f);
				
				lane.setBegin(leftSide.add(leftVector));
				Vector2f rightPoint = lane.getBegin().add(dir.mult(lane.getDesiredLength())); 
				lane.setEnd(rightPoint);
				lane.setLength(lane.getDesiredLength());
				lane.normalVector = lane.getEnd().subtract(lane.getBegin()).normalizeLocal();
				Side s = new Side(
									lane.getBegin().x + leftVector.x, 
									lane.getBegin().y + leftVector.y,
									rightPoint.x + rightVector.x,
									rightPoint.y + rightVector.y
									);
				lane.setRightSide(s);
				s = new Side(
						lane.getBegin().x - leftVector.x, 
						lane.getBegin().y - leftVector.y,
						rightPoint.x - rightVector.x,
						rightPoint.y - rightVector.y
						);
				lane.leftSide = s;
				leftSide.addLocal(leftVector).addLocal(leftVector);
			}
		}
	}
	
	protected void calculateLanes(Side begin, Side end)
	{
		lanesInited = true;
		ArrayList<Integer> lanes = getLanes();
		Vector2f dir = new Vector2f(end.endX - begin.endX, end.endY - begin.endY);
		Vector2f beginSide = begin.getVector();
		Vector2f endSide = end.getVector();
		// Sets vectors of the left side and right side - parts of the rectangle, that are on 
		// the borders, between segments
		dir.rotate90();
		if (dir.mult(beginSide) < 0) dir.negateLocal();
		//Calculates cos between perpendicular and sides - 
		//We need know projections of lane's width on the sides.
		float cos1 = dir.mult(beginSide)/ (dir.length()*beginSide.length());
		float cos2 = dir.mult(endSide)/ (dir.length()*endSide.length());
		if (cos1 <= 0.0001f ) {
			cos1 = 1;
			logger.warning("Strange segment detected , can not properly calculate lanes");
		}
		if (cos2 <= 0.0001f ) {
			cos2 = 1;
			logger.warning("Strange segment detected , can not properly calculate lanes");
		}
		//Calculates points on the each side, from which we are begin move along the side.
		Vector2f leftPoint  = new Vector2f(begin.startX,begin.startY);
		Vector2f rightPoint  = new Vector2f(end.startX,end.startY);
		//
		beginSide.normalizeLocal();
		endSide.normalizeLocal();
		System.out.println("<Segment id = " + this.toString() + " >");
		for (Integer l: lanes){
			Lane lane  = getLane(l);
			float width1 = lane.getWidth()/cos1;
			float width2 = lane.getWidth()/cos2;
			lane.setBegin(leftPoint.add(beginSide.mult(width1/2.0f)));
			lane.setEnd(rightPoint.add(endSide.mult(width2/2.0f)));
			lane.setLength(lane.getEnd().subtract(lane.getBegin()).length());
			lane.normalVector = lane.getEnd().subtract(lane.getBegin()).normalizeLocal();
			Vector2f s1 = beginSide.mult(width1);
			Vector2f s2 = endSide.mult(width2);
			leftPoint.addLocal(s1);
			rightPoint.addLocal(s2);
			lane.setRightSide(new Side(leftPoint.x, leftPoint.y ,
					rightPoint.x, rightPoint.y));
			
			System.out.println("Lane id = " + lane.id() +" - Desired length " + lane.getDesiredLength() + " current " + lane.getLength());
		}
		System.out.println("</Segment>");
		
	}

	private void acceptLanes()
	{
		Object[] array = this.laneslist.toArray();
		Arrays.sort(array);
		this.laneslist = new ArrayList<Integer>();
		for (int i = 0 ; i < array.length; i++)
		{
			Integer l = (Integer) array[i];
			this.laneslist.add(l);
		}
	}

	public SegmentGeometry getGeometry() {
		return geometry;
	}

	@Override
	public void draw(RenderType mode) {
		if (this.form.getLastFrustrumState() == FrustrumState.OutOfView){
			FrameDataHandler.getInstance().changeObjectToInvisible(frameInfo);
		} else {
			FrameDataHandler.getInstance().changeObjecttoVisible(frameInfo);
			super.draw(mode);
		}
		
	}
	
	public void checkFrustum()
	{
		this.form.checkFrustum();
		this.frustrumState = this.form.getLastFrustrumState();
		
		if (this.frustrumState == FrustrumState.OutOfView){
			FrameDataHandler.getInstance().changeObjectToInvisible(frameInfo);
		} else {
			FrameDataHandler.getInstance().changeObjecttoVisible(frameInfo);
		}
	}
	
	public SegmentType getSegmentType()
	{
		return this.segmentType;
	}
	
}