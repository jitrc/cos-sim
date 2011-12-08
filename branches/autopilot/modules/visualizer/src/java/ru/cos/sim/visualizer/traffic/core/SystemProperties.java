package ru.cos.sim.visualizer.traffic.core;

import java.util.HashMap;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import ru.cos.sim.visualizer.scene.impl.ITrafficLight;
import ru.cos.sim.visualizer.trace.item.base.TrafficLight.Color;

public class SystemProperties {

	public static enum TrafficLightSignalsShow {
		None,
		All,
		TrafficLightsOnly,
		Green,
		Red
	}
	
	public static enum Properties {
		supportMultisamples,
		workingDirectory
	}
	
	protected static HashMap<Properties, Object> defaultPropertiesValues;
	protected static Preferences prefs;
	protected TrafficLightSignalsShow trafficLightSignalState = TrafficLightSignalsShow.All;
	
	public static enum ShowState {
		Yes,
		No
	}
	
	protected ShowState segmentsShowState = ShowState.Yes;
	protected ShowState crossRoadShowState = ShowState.Yes;
	protected ShowState boundNodeShowState = ShowState.Yes;
	
	protected JFrame currentFrame;
	
	static {
		defaultPropertiesValues = new HashMap<Properties, Object>();
		
		defaultPropertiesValues.put(Properties.supportMultisamples, "NotTested");
		defaultPropertiesValues.put(Properties.workingDirectory, System.getProperty("user.home"));
		
	}
	
	public SystemProperties() {
		prefs = Preferences.systemNodeForPackage(getClass());
	}

	public TrafficLightSignalsShow getTrafficLightSignalState() {
		return trafficLightSignalState;
	}
	
	public ShowState getSegmentsShowState() {
		return segmentsShowState;
	}
	
	public ShowState getCrossRoadShowState() {
		return crossRoadShowState;
	}
	
	public ShowState getBoundNodeShowState() {
		return boundNodeShowState;
	}
	
	public void setTrafficLightSignalState(
			TrafficLightSignalsShow trafficLightSignalState) {
		if (trafficLightSignalState == null) return;
		this.trafficLightSignalState = trafficLightSignalState;
	}
	
	public void setSegmentsShowState(ShowState segmentsShowState) {
		
		if (segmentsShowState == null) return;
		
		this.segmentsShowState = segmentsShowState;
	}
	
	public void setCrossRoadShowState(ShowState crossRoadShowState) {
		if (crossRoadShowState == null) return;
		this.crossRoadShowState = crossRoadShowState;
	}
	
	public void setBoundNodeShowState(ShowState boundNodeShowState) {
		if (boundNodeShowState == null) return;
		this.boundNodeShowState = boundNodeShowState;
	}
	
	public ShowState checkProperties(ITrafficLight trafficLight)
	{
		switch (this.trafficLightSignalState) {
		case All:
			return ShowState.Yes;
		case TrafficLightsOnly :
			return  (trafficLight.getLightColor() == Color.None) ? ShowState.No
					: ShowState.Yes;
		case None:
			return ShowState.No;
		case Green:
			return (trafficLight.getLightColor() == Color.Green) ? ShowState.Yes
					: ShowState.No;
		case Red:
			return (trafficLight.getLightColor() == Color.Red) ? ShowState.Yes
					: ShowState.No;
		default:
			return ShowState.No;
		}
	}
	
	public String getProperty(Properties p)
	{
		return prefs.get(p.toString(), defaultPropertiesValues.get(p).toString());
	}
	
	public boolean getBooleanProperty(Properties p) {
		return prefs.getBoolean(p.toString(), (Boolean)defaultPropertiesValues.get(p));
	}
	
	public void setProperty(Properties p,String value)
	{
		prefs.put(p.toString(), value);
		//return prefs.get(p.toString(), defaultPropertiesValues.get(p).toString());
	}
	
	public void setCurrentFrame(JFrame frame) {
		this.currentFrame = frame;
	}
	
	public JFrame getCurrentFrame() {
		return this.currentFrame;
	}
}
