package ru.cos.sim.visualizer.trace.item.base;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.renderer.Renderer.RenderType;
import ru.cos.sim.visualizer.scene.impl.IPlaceable;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.utils.ViewFieldController;

public class StaticEntity extends BasicEntity {

	protected ViewFieldController vfcontroller;
	public StaticEntity(int uid, IPlaceable form, float x, float y) {
		super(uid, form, x, y);
		vfcontroller = SimulationSystemManager.getInstance().getViewController();
	}

	public StaticEntity(int uid, IPlaceable form, Vector2f pos) {
		super(uid, form, pos);
		vfcontroller = SimulationSystemManager.getInstance().getViewController();
	}

	public StaticEntity(int uid, IPlaceable form) {
		super(uid, form);
		vfcontroller = SimulationSystemManager.getInstance().getViewController();
	}

	public StaticEntity(int uid) {
		super(uid);
		vfcontroller = SimulationSystemManager.getInstance().getViewController();
	}

	@Override
	public void draw(RenderType mode) {
		form.setRotation(rotationAngle);
		form.setTranslation(posx, posy, 0);
		form.preRender();
		form.render(mode);
		form.postRender();
	}
	
}
