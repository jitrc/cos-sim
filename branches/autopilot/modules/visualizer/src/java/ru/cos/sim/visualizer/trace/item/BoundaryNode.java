package ru.cos.sim.visualizer.trace.item;

import ru.cos.sim.visualizer.scene.impl.INode;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;
import ru.cos.sim.visualizer.trace.item.base.StaticEntity;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node.Type;

public class BoundaryNode extends StaticEntity implements INode {

	protected Nodetype type;
	
	public BoundaryNode(int uid, Node node, IPlaceable form) {
		super(uid, form);
		this.type = (node.getType() == Type.DestinationNode) ? Nodetype.DestinationNode : Nodetype.SourceNode;
	}

	public BoundaryNode(int uid,Node node) {
		super(uid);
		this.type = (node.getType() == Type.DestinationNode) ? Nodetype.DestinationNode : Nodetype.SourceNode;
	}

	@Override
	public Nodetype getNodeType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public TransitionRule getRule(int id){
		return null;
	}

}
