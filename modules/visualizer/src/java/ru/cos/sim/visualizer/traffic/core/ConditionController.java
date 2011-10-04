package ru.cos.sim.visualizer.traffic.core;

import ru.cos.sim.visualizer.traffic.core.ConditionManager.Action;

public class ConditionController implements Controller {

	public Action actionType = Action.All;
	
	public ConditionController(Action actionType) {
		super();
		this.actionType = actionType;
	}
	
	public ConditionController() {
		super();
	}

	@Override
	public void actionPerformed(String action) {
		
	}

	@Override
	public Action getActionType() {
		return actionType;
	}

	public void setActionType(Action actionType) {
		this.actionType = actionType;
	}

	
}
