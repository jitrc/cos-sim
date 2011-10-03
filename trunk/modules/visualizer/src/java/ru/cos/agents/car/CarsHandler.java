package ru.cos.agents.car;

import java.util.ArrayList;
import java.util.HashMap;

import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.parser.trace.location.LaneLocation;
import ru.cos.nissan.parser.trace.location.RuleLocation;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.renderer.impl.IRenderable;
import ru.cos.sim.communication.dto.VehicleDTO;
import ru.cos.sim.road.init.data.LinkLocationData;
import ru.cos.trace.TraceHandler;
import ru.cos.trace.item.Car;
import ru.cos.trace.item.Car.CarType;
import ru.cos.trace.item.Lane;
import ru.cos.trace.item.TransitionRule;

public class CarsHandler implements IRenderable {
	
	/**
	 * Help map of the cars. It is used to copy there 
	 * cars that will be on the map after update finish;
	 * 
	 */
	private ArrayList<Car> objectsForRender;
	private HashMap<Integer, Car> mapForRender;
	
	private int currentMark = 0;
	
	public CarsHandler()
	{
		mapForRender = new HashMap<Integer, Car>();
		objectsForRender = new ArrayList<Car>();
	}
	
	public void moveCar(int uuid , float speed ,float distance , 
			int link_id , int seg_id , int lane_id , CarType type,VehicleDTO dto)
	{
		Car car = mapForRender.get(uuid);
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();

		Lane lane = handler.getLink(link_id).getSegment(seg_id).getLane(lane_id);
		if (car == null) {
			car = new Car(uuid,type);
			
			this.addCar(car);
		}
		
		car.mark(currentMark);
		LinkLocationData data = (LinkLocationData) dto.getLocation();
		car.setInformation(dto);
		car.move(lane.getPosition((float)distance,data.getShift(),  car.getLastPosition()));
		
		LaneLocation location = new LaneLocation(link_id, seg_id, lane_id, (float)distance);
		CarInformation info = new CarInformation(location, type, (float)speed, car.getLastPosition());
		
	}
	
	public void moveCar(int uuid ,float speed, float distance , int link_id , 
			int seg_id , int lane_id , float percents , int from_lane_id , 
			CarType type,VehicleDTO dto)
	{
		/*Car car = mapForRender.get(uuid);
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		Lane lane_to = handler.getLink(link_id).getSegment(seg_id).getLane(lane_id);
		Lane lane_from = handler.getLink(link_id).getSegment(seg_id).getLane(from_lane_id);
		
		LaneLocation location = new LaneLocation(link_id, seg_id, lane_id, (float)distance);
		CarInformation info = new CarInformation(location, type, (float)speed, null);

		
		if (car == null) {
			car = new Car(uuid,type);	
			this.addCar(car);
			car.move(lane_to.getPosition((float)distance, car.getLastPosition()));
			car.mark(currentMark);
			car.setInformation(dto);
			return;
		}
		car.setInformation(dto);
		
		car.move(lane_to.getPosition((float) distance,(float)percents, lane_from, car
				.getLastPosition()));
		car.mark(currentMark);*/
		
//		Uncomment to move car at line changing state according to it last position;
//		CarPosition lastPosition = car.getLastPosition();
//		CarPosition newPos = lane_to.getPosition((float) distance, null);
//		
//		if (lastPosition == null) {
//			car.move(lane_to.getPosition((float) distance, car
//					.getLastPosition()));
//			car.mark(currentMark);
//			
//			return;
//		}
//
//		
//		Vector2f ort = lane_to.getOrtVectortoLane(lane_from);
//		float length = ort.length();
//		length *= (1 - percents);
//		ort.normalizeLocal().multLocal(length);
//		
//		
//		newPos.position.addLocal(ort);
//		newPos.direction = new Vector2f(newPos.position.subtract(
//				lastPosition.position).normalizeLocal());
//		
//		if (isPositionsEqual(newPos, lastPosition,uuid)) {
//			car.mark(currentMark);
//			return;
//		}
//		
//		car.move(newPos);
//		car.mark(currentMark);
	}
	
	protected boolean isPositionsEqual(CarPosition p1 , CarPosition p2, int id)
	{
		boolean l = (Math.abs(p1.position.x - p2.position.x) < 0.05f) && (Math.abs(p1.position.y - p2.position.y) < 0.05f);
		if (l)
			return true;
		return false;
	}
	
	public void moveCar(int uuid , float speed, int nodeId,int transitionRuleID , 
			float length , CarType type  ,VehicleDTO dto)
	{
		Car car = mapForRender.get(uuid);
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		if (car == null) {
			car = new Car(uuid,type);	
			this.addCar(car);
		}
		car.mark(currentMark);
		TransitionRule rule = handler.getNode(nodeId).getRule(transitionRuleID);
		car.setInformation(dto);
		car.move(rule.getPosition((float) length, car.getLastPosition()));
		
		RuleLocation location = new RuleLocation(nodeId, transitionRuleID, false);
		location.setPosition((float)length);
		CarInformation info = new CarInformation(location, type, (float)speed, car.getLastPosition());
		
	}
	
	protected void addCar(Car car)
	{
		this.objectsForRender.add(car);
		this.mapForRender.put(car.id(), car);
	}
	
	protected void handleAfterUpdate()
	{
		ArrayList<Car> newContainer = new ArrayList<Car>();
		for (int i = 0 ; i < objectsForRender.size(); i++ ) {
			if (objectsForRender.get(i).getMark() != currentMark) {
				this.mapForRender.remove(objectsForRender.get(i).id());
			} else {
				newContainer.add(objectsForRender.get(i));
			}
		}
		
		objectsForRender = newContainer; 
	}
	
	public void prepareBeforeUpdate()
	{
		this.currentMark = 1 - currentMark;
	}

	@Override
	public void render(RenderType mode) {
		if (objectsForRender != null)
		{
			for (Car car : this.objectsForRender)
			{
				car.render(mode);
			}
		}
		
		handleAfterUpdate();
	}

	@Override
	public FrustrumState getLastFrustrumState() {
		return FrustrumState.InView;
	}
	
}
