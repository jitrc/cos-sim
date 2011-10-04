package ru.cos.sim.visualizer.traffic.core;

import java.util.ArrayList;

public class ConditionManager {
	private  static ConditionManager instance;
	private  String runningState = States.NOT_STARTED;
	private  String initState = States.NOT_INITIALIZED;
	private  String mapState = States.MAP_NOT_LOADED;
	private  String viewState = States.NON_SIMULATION;
	
	private ArrayList<Controller> controllers;
	
	public static enum Action {
		RunningStateChanged,
		InitStateChanged,
		MapStateChanged,
		ViewStateChanged,
		All
	};
	
	private ConditionManager()
	{
		controllers = new ArrayList<Controller>();
	}
	
	private void dispatchEvent(String state, Action action)
	{
		for (Controller c : controllers)
		{
			if (action == c.getActionType() ||
					c.getActionType() == Action.All)  
				{
					c.actionPerformed(state);
				}
		}
	}
	
	public void addController(Controller c)
	{
		this.controllers.add(c);
	}
	
	public static ConditionManager getInstance()
	{
		if (instance == null) instance = new ConditionManager();
		return instance;
	}

	public  String getRunningState() {
		return runningState;
	}

	public void setRunningState(String runningState) {
		this.runningState = runningState;
		dispatchEvent(this.runningState,Action.RunningStateChanged);
	}

	public String getInitState() {
		return initState;
	}

	public void setInitState(String initState) {
		this.initState = initState;
		dispatchEvent(this.initState,Action.InitStateChanged);
	}

	public String getMapState() {
		return mapState;
	}

	public void setMapState(String mapState) {
		this.mapState = mapState;
		dispatchEvent(this.mapState,Action.MapStateChanged);
	}

	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
		dispatchEvent(viewState,Action.ViewStateChanged);
	}
	
	public void dispose()
	{
		instance = null;
	}
	
	
}
