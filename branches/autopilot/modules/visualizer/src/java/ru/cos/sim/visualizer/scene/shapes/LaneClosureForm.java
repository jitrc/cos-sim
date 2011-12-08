package ru.cos.sim.visualizer.scene.shapes;

import java.util.ArrayList;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import static org.lwjgl.opengl.GL11.*;

public class LaneClosureForm extends AbstractItem {

	protected ArrayList<Part> parts;
	
	public LaneClosureForm() {
		parts = new ArrayList<Part>();
	}

	@Override
	public void render(RenderType mode) {
		glBegin(GL_TRIANGLES);
			glColor4f(Color.gray.r,Color.gray.g, Color.gray.b,Color.gray.a);
			for (Part p : parts) {
				glVertex2f(p.p1.x,p.p1.y);
				glVertex2f(p.p2.x,p.p2.y);
				glVertex2f(p.p3.x,p.p3.y);
				
				glVertex2f(p.p3.x,p.p3.y);
				glVertex2f(p.p4.x,p.p4.y);
				glVertex2f(p.p1.x,p.p1.y);
			}
			
		glEnd();
	}
	
	public void addPart(Vector2f p1, Vector2f p2, 
			Vector2f p3, Vector2f p4){
		this.parts.add(new Part(p1, p2, p3, p4));
	}
	
	class Part {
		public Vector2f p1;
		public Vector2f p2;
		public Vector2f p3;
		public Vector2f p4;
		
		public Part(Vector2f p1, Vector2f p2, Vector2f p3, Vector2f p4) {
			super();
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
			this.p4 = p4;
		}
		
		
	}

}
