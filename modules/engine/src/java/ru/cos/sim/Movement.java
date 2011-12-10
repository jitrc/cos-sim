package ru.cos.sim;

public interface Movement {

	public abstract float getSpeed();

	public abstract float getShiftSpeed();

	public abstract float getAcceleration();

	public abstract ShiftDirection getDirection();

}