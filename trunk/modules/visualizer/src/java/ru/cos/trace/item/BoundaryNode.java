package ru.cos.trace.item;

import ru.cos.nissan.parser.trace.base.Node;
import ru.cos.nissan.parser.trace.base.Node.Type;
import ru.cos.scene.impl.INode;
import ru.cos.scene.impl.IPlaceable;
import ru.cos.trace.item.base.StaticEntity;

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
