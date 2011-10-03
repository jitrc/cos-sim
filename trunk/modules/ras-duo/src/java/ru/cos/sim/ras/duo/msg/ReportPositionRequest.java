package ru.cos.sim.ras.duo.msg;

import ru.cos.sim.ras.duo.utils.Extendable;

public class ReportPositionRequest {

	public ReportPositionRequest(int vehicleId, int linkId, float position, float speed, Extendable vehicleData) {
		this.vehicleId = vehicleId;
		this.linkId = linkId;
		this.position = position;
		this.speed = speed;
		this.vehicleData = vehicleData;
	}
	
	private final int vehicleId;
	public int getVehicleId() {
		return vehicleId;
	}
	
	private final int linkId;
	public int getLinkId() {
		return linkId;
	}
	
	private final float position;
	public float getPosition() {
		return position;
	}
	
	private final float speed;
	public float getSpeed() {
		return speed;
	}
	
	private final Extendable vehicleData;
	public Extendable getVehicleData() {
		return vehicleData;
	}
}
