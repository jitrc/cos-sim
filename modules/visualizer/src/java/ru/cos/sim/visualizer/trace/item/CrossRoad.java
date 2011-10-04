package ru.cos.sim.visualizer.trace.item;

import java.util.HashMap;

import ru.cos.sim.visualizer.frame.FrameDataHandler;
import ru.cos.sim.visualizer.frame.ViewableObjectInformation;
import ru.cos.sim.visualizer.scene.impl.INode;
import ru.cos.sim.visualizer.trace.item.base.StaticEntity;

public class CrossRoad extends StaticEntity implements INode {

	protected HashMap<Integer, TransitionRule> rules;
	protected ViewableObjectInformation frameData;
	public CrossRoad(int uid) {
		super(uid);
		this.rules = new HashMap<Integer, TransitionRule>();
		this.renderMode = RenderMode.Simple;
		
		this.frameData = new ViewableObjectInformation(this.id());
		FrameDataHandler.getInstance().getNodeListLink().changeToVisible(frameData);
	}
	
	public void addRule(TransitionRule rule)
	{
		this.rules.put(rule.id(), rule);
	}
	
	public TransitionRule getRule(int id)
	{
		return this.rules.get(id);
	}

	public HashMap<Integer, TransitionRule> getRules() {
		return rules;
	}
	
	@Override
	public Nodetype getNodeType() {
		return Nodetype.CrossRoad;
	}
	
	public void checkFrustum()
	{
		this.form.checkFrustum();
	}
	
}
