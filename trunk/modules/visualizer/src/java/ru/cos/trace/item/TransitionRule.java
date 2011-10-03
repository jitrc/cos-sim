package ru.cos.trace.item;

import java.util.ArrayList;
import java.util.HashMap;

import ru.cos.agents.car.CarPosition;
import ru.cos.nissan.core.SimulationSystemManager;
import ru.cos.nissan.core.SystemProperties;
import ru.cos.nissan.core.SystemProperties.ShowState;
import ru.cos.nissan.core.SystemProperties.TrafficLightSignalsShow;
import ru.cos.renderer.Renderer.RenderType;
import ru.cos.scene.impl.ICurveForm;
import ru.cos.scene.impl.IPlaceable;
import ru.cos.scene.impl.ITrafficLight;
import ru.cos.trace.item.base.BaseCarKeeper;
import ru.cos.trace.item.base.TrafficLight;
import ru.cos.trace.item.base.TrafficLight.Color;

public class TransitionRule extends BaseCarKeeper {
	
	protected ITrafficLight mainLight;
	protected ITrafficLight waitLight;
	protected IPlaceable waitPosition;
	protected Lane destinationLane;
	protected Lane sourceLane;
	protected ArrayList<TrafficLight> trLights;
	protected ITrafficLight[] trlforms;
	protected HashMap<Float, ITrafficLight> trlsHash;
	protected ITrafficLight[]fakeLights;
	protected boolean fakeLightsInited = false;
	protected boolean lightsInited = false;
	
	protected boolean wpActivated = false;
	
	protected ICurveForm curve;
	
	public TransitionRule(int uid) {
		super(uid);
		
		lightsInited = false;
		trLights = new ArrayList<TrafficLight>();
	}
	
	public void setCurve(ICurveForm curve)
	{
		this.curve = curve;
	}

	@Override
	public CarPosition getPosition(float position, CarPosition pos) {
		return curve.getPosition(position, pos);
	}
	
	public ITrafficLight getLight(Float position) {
		return trlsHash.get(position);
	}

	public Lane getDestinationLane() {
		return destinationLane;
	}

	public Lane getSourceLane() {
		return sourceLane;
	}

	public void setDestinationLane(Lane destinationLane) {
		this.destinationLane = destinationLane;
	}

	public void setSourceLane(Lane sourceLane) {
		this.sourceLane = sourceLane;
	}

	public IPlaceable getWaitPosition() {
		return waitPosition;
	}

	public void setWaitPosition(IPlaceable waitPosition) {
		this.waitPosition = waitPosition;
	}
	
	public void activateWP()
	{
		this.wpActivated = true;
	}
	
	public void switchLight(Color color, float position) {
		if (position>0) {
			this.waitLight.switchLight(color);
		} else {
			this.mainLight.switchLight(color);
			if (!wpActivated && waitLight != null) {
				this.waitLight.switchLight(color);
			}
		}
	}
	
	public void drawMainLight(RenderType mode) {
		if (mainLight != null) {
			if (SimulationSystemManager.getInstance().getSystemProperties().
					checkProperties(mainLight) == ShowState.No) return;
			mainLight.setRotation(rotationAngle);
			mainLight.setTranslation(posx, posy, 0);
			mainLight.preRender();
			mainLight.render(mode);
			mainLight.postRender();
		}
	}
	
	public void drawWaitLight(RenderType mode) {
		if (waitLight != null) {
			if (SimulationSystemManager.getInstance().getSystemProperties().
					checkProperties(waitLight) == ShowState.No) return;
			waitLight.setRotation(rotationAngle);
			waitLight.setTranslation(posx, posy, 0);
			waitLight.preRender();
			waitLight.render(mode);
			waitLight.postRender();
		}
	}
	
	public void drawWaitPosition(RenderType mode) {
		if (waitPosition != null) {
			waitPosition.setRotation(rotationAngle);
			waitPosition.setTranslation(posx, posy, 0);
			waitPosition.preRender();
			waitPosition.render(mode);
			waitPosition.postRender();
		}
	}
	
	public void addtrafficLight(TrafficLight light) {
		trLights.add(light);
	}
	
	public void completeLights() {
		trlforms = curve.completeLights(trLights);
		trlsHash = new HashMap<Float, ITrafficLight>();
		
		for (ITrafficLight light : trlforms) {
			trlsHash.put(light.getPosition(),light);
		}
		
		lightsInited = true;
	}
	
	public void makeFakeLight() {
		fakeLights = curve.completeLights(trLights);
		fakeLightsInited = true;
	}

	public void draw(RenderType mode) {
		if (!lightsInited) 	return;

		for (ITrafficLight light : trlforms) {
			drawLight(light, mode);
		}
	}
	
	public void drawRule(RenderType mode) {
		if (SimulationSystemManager.getInstance()
				.getSystemProperties().
				getTrafficLightSignalState() != TrafficLightSignalsShow.All) return;
		if (lightsInited) return;
		if (!fakeLightsInited) makeFakeLight();
		
		for (ITrafficLight light : fakeLights) {
			drawLight(light, mode);
		}
	}
	
	private void drawLight(ITrafficLight light, RenderType mode) {
		if (SimulationSystemManager.getInstance().getSystemProperties().
				checkProperties(light) == ShowState.No) return;
		light.setRotation(rotationAngle);
		light.setTranslation(posx, posy, 0);
		light.preRender();
		light.render(mode);
		light.postRender();
	}

	public boolean isLightsInited() {
		return lightsInited;
	}
	
	
}
