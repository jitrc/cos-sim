package ru.cos.sim;


public class MovementImpl implements Movement {
	private float speed;
	private float shiftSpeed;
	private float acceleration;
	private ShiftDirection direction;
	
	@Override
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	@Override
	public float getShiftSpeed() {
		return shiftSpeed;
	}
	public void setShiftSpeed(float shiftSpeed) {
		this.shiftSpeed = shiftSpeed;
	}
	@Override
	public float getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	@Override
	public ShiftDirection getDirection() {
		return direction;
	}
	public void setDirection(ShiftDirection direction) {
		this.direction = direction;
	}
}
