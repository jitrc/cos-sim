package ru.cos.sim.visualizer.scene.shapes;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineStipple;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import ru.cos.sim.visualizer.color.Color;
import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Frustum;
import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.renderer.impl.IRenderable;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;
import ru.cos.sim.visualizer.trace.item.Segment;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.parser.geometry.Arc;
import ru.cos.sim.visualizer.traffic.parser.geometry.GeometryPrimitive;
import ru.cos.sim.visualizer.traffic.parser.geometry.Line;
import ru.cos.sim.visualizer.traffic.parser.geometry.Side;
import ru.cos.sim.visualizer.traffic.utils.ViewFieldController;

public class BaseSegmentForm extends AbstractItem implements IRenderable {

	protected Segment segment ;
	protected Side firstside;
	protected Side secondside;
	protected ViewFieldController vfcontroller;
	
	public BaseSegmentForm(Segment segment) {
		super();
		this.segment = segment;
		this.firstside = segment.getGeometry().getSides().get(0);
		this.secondside = segment.getGeometry().getSides().get(1);
		this.setColor(Color.asphalt);
		vfcontroller = SimulationSystemManager.getInstance().getViewController();
		vfcontroller.addSide(firstside);
		vfcontroller.addSide(secondside);
		
	}

	private void renderOverride(Side side, Vector2f basePoint)
	{
		
		glBegin(GL_TRIANGLES);
			glColor4f(color.r, color.g, color.b, color.a);
			Arc arc;
			Line line;
			for (GeometryPrimitive g : side.override)
			{
				switch (g.getGeometrytype()) {
				case Arc : arc = (Arc) g;
					Vector2f old = null;
					Vector2f v = null;
					for (int i = 0 ; i < arc.arcPoints.size(); i++ ) {
						v = arc.arcPoints.get(i);
						if ( i > 0 ) {
							glVertex2f(basePoint.x, basePoint.y);
							glVertex2f(v.x, v.y);
							glVertex2f(old.x, old.y);
						}
						old = v;
					}
					glVertex2f(basePoint.x, basePoint.y);
					glVertex2f(old.x, old.y);
					glVertex2f(arc.endPoint.x, arc.endPoint.y);
					glVertex2f(basePoint.x, basePoint.y);
					glVertex2f(arc.beginPoint.x, arc.beginPoint.y);
					glVertex2f(arc.endPoint.x, arc.endPoint.y);
					break;
				case Line : line = (Line) g;
					glVertex2f(basePoint.x,basePoint.y);
					glVertex2f(line.beginPoint.x, line.beginPoint.y);
					glVertex2f(line.endPoint.x, line.endPoint.y);
					break;
				}
			}
		glEnd();
	}
	
	@Override
	public void render(RenderType mode) {
		Vector2f basePointf = new Vector2f(firstside.startX,firstside.startY);
		Vector2f lastPointf = new Vector2f(firstside.endX,firstside.endY);
		if (firstside.overrided) {
			renderOverride(firstside,basePointf);
		} 
		{
			glBegin(GL_TRIANGLES);
				glColor4f(color.r, color.g, color.b, color.a);
				glVertex2f(basePointf.x, basePointf.y);
				glVertex2f(lastPointf.x, lastPointf.y);
				glVertex2f(secondside.endX, secondside.endY);
			glEnd();
			
			glBegin(GL_LINE_LOOP);
				glColor4f(color.r, color.g, color.b, color.a);
				glVertex2f(basePointf.x, basePointf.y);
				glVertex2f(lastPointf.x, lastPointf.y);
			    glVertex2f(secondside.endX, secondside.endY);
			glEnd();
		}
		
		Vector2f basePoints = new Vector2f(secondside.startX,secondside.startY);
		Vector2f lastPoints = new Vector2f(secondside.endX,secondside.endY);
		
		if (secondside.overrided) {
			renderOverride(secondside,basePoints);
		} 
		{
			glBegin(GL_TRIANGLES);
				glColor4f(color.r, color.g, color.b, color.a);
				glVertex2f(basePointf.x, basePointf.y);
				glVertex2f(basePoints.x, basePoints.y);
				glVertex2f(lastPoints.x, lastPoints.y);
				
			glEnd();
			
			glBegin(GL_LINE_LOOP);
				glColor4f(color.r, color.g, color.b, color.a);
				glVertex2f(basePointf.x, basePointf.y);
				glVertex2f(basePoints.x, basePoints.y);
				glVertex2f(lastPoints.x, lastPoints.y);
			
			glEnd();
		}
		
//		glLineWidth(3.5f);
//		glBegin(GL_LINES);{
//			glColor4f(color.r, color.g, color.b, color.a);
//			glVertex2f(firstside.startX, firstside.startY);
//			glVertex2f(firstside.endX, firstside.endY);
//			
//			glVertex2f(secondside.endX, secondside.endY);
//			glVertex2f(secondside.startX, secondside.startY);
//		}glEnd();
//		glLineWidth(1.0f);
		
		glColor3f(Color.white.r, Color.white.g, Color.white.b);
		ArrayList<Integer> lanes = segment.getLanes();
		
		/*glEnable(GL_LINE_STIPPLE);
		glLineWidth(1.5f);
		glLineStipple(3, (short) 0x00FF);
		for (int i = 0 ; i < lanes.size() - 1; i++ ) {
			Side s = segment.getLane(lanes.get(i)).getRightSide();
			glBegin(GL_LINE); 
				glVertex2f(s.startX, s.startY);
				glVertex2f(s.endX, s.endY);
			glEnd();
		}
		glDisable(GL_LINE_STIPPLE);
		glLineWidth(1.0f);*/
		
		
		//glLineWidth(1.5f);
		glLineStipple(3, (short) 0x00FF);
		for (int i = 0 ; i < lanes.size() - 1; i++ ) {
			Side s = segment.getLane(lanes.get(i)).getRightSide();
			drawSplittedLane(s);
		}
		
		glLineWidth(1.0f);
		
		for (IPlaceable staff : segment.getStaff() ){
			staff.preRender();
			staff.render(mode);
			staff.postRender();
		}
	}
	
	private void drawSplittedLane(Side s)
	{
		glColor4f(1, 1, 1, 1);
		Vector2f v = s.getVector();
		Vector2f ort = new Vector2f(-v.y, v.x);
		ort.normalizeLocal().multLocal(0.1f);
		float stepLength = 6f;
		float spaceLength = 3f;
		float length = 0;
		float maxLength = v.length();
		
		Vector2f space = v.normalize().multLocal(spaceLength);
		v.normalizeLocal().multLocal(stepLength);
		Vector2f point = new Vector2f(s.startX,s.startY);
		while (length < maxLength) {
			
			if (length + stepLength > maxLength) v.normalizeLocal().multLocal(maxLength - length);
			glBegin(GL_TRIANGLES);
				glVertex2f(point.x + ort.x, point.y+ort.y);
				glVertex2f(point.x+v.x + ort.x, point.y+v.y+ort.y);
				glVertex2f(point.x+v.x - ort.x, point.y+v.y-ort.y);
			
				glVertex2f(point.x+v.x - ort.x, point.y+v.y-ort.y);
				glVertex2f(point.x - ort.x, point.y-ort.y);
				glVertex2f(point.x + ort.x, point.y+ort.y);
			glEnd();
			
			glBegin(GL_LINES); 
				glVertex2f(point.x + ort.x, point.y+ort.y);
				glVertex2f(point.x+v.x + ort.x, point.y+v.y+ort.y);
			
				glVertex2f(point.x+v.x + ort.x, point.y+v.y+ort.y);
				glVertex2f(point.x+v.x - ort.x, point.y+v.y-ort.y);
			
				glVertex2f(point.x+v.x - ort.x, point.y+v.y-ort.y);
				glVertex2f(point.x - ort.x, point.y-ort.y);
			
				glVertex2f(point.x - ort.x, point.y-ort.y);
				glVertex2f(point.x + ort.x, point.y+ort.y);
			glEnd();
			length += stepLength;
			if (length + spaceLength > maxLength) break; else {
				point.x += space.x + v.x;
				point.y+= space.y + v.y;
				length+=spaceLength;
			}
			
		}
		
		
	}

	@Override
	public void checkFrustum() {
		super.checkFrustum();
		
		Frustum fr = Renderer.getRenderer().getFrustum();
//		
		if (fr.PointInFrustum(firstside.startX,firstside.startY,0)) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
//		
		if (fr.PointInFrustum(firstside.endX,firstside.endY,0)) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
		
		if (fr.PointInFrustum(secondside.startX,secondside.startY,0)) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
		
		if (fr.PointInFrustum(secondside.endX,secondside.endY,0)) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
		
		if (fr.checkSegment(new Vector2f(firstside.startX,firstside.startY),
				new Vector2f(firstside.endX,firstside.endY))) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
		
		if (fr.checkSegment(new Vector2f(secondside.startX,secondside.startY),
				new Vector2f(secondside.endX,secondside.endY))) {
			this.frustrumState = FrustrumState.InView;
			return;
		}
		
		this.frustrumState = FrustrumState.OutOfView;
	}
	
}