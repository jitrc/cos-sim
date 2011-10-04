package ru.cos.sim.visualizer.scene.impl;

import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.trace.item.TransitionRule;

public interface INode  extends IRenderable{
	
	public static enum Nodetype{
		CrossRoad,
		DestinationNode,
		SourceNode
	}
	
	public Nodetype getNodeType();
	public TransitionRule getRule(int id);
}
