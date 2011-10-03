package ru.cos.scene.impl;

import ru.cos.renderer.impl.IRenderable;
import ru.cos.trace.item.TransitionRule;

public interface INode  extends IRenderable{
	
	public static enum Nodetype{
		CrossRoad,
		DestinationNode,
		SourceNode
	}
	
	public Nodetype getNodeType();
	public TransitionRule getRule(int id);
}
