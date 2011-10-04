package ru.cos.sim.visualizer.scene.shapes;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.traffic.parser.trace.DestinationNode;
import ru.cos.sim.visualizer.traffic.parser.trace.SourceNode;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Node.Type;

public class BoundNodeShape extends TexturedRectangleShape {
	private static String destinationNodeTextureLocation = "/textures/roadNetwork/basicDestNode.png";
	private static String sourceNodeTextureLocation = "/textures/roadNetwork/basicSourceNode.png";
	
	private Type type;
	private float width = 0;
	private Vector2f position;
	
	public BoundNodeShape(Node node)
	{
		super();
		
		this.type = node.getType();
		
		if (type == Type.DestinationNode) {
			DestinationNode n = (DestinationNode) node;
			this.position = new Vector2f(n.geometry.center.x,n.geometry.center.y);
			this.width = n.geometry.radius;
			this.setTexture(destinationNodeTextureLocation);
		} else {
			if (type == Type.SourceNode) {
				SourceNode s = (SourceNode) node;
				this.position = new Vector2f(s.geometry.center.x,s.geometry.center.y);
				this.width = s.geometry.radius;
				this.setTexture(sourceNodeTextureLocation);
			}
		}
		
		this.set(this.position.x, this.position.y, width, 0, 0, width);
		
	}
	
	public Type getType()
	{
		return type;
	}
}
