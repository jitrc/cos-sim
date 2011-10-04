package ru.cos.sim.visualizer.scene.impl;

import ru.cos.sim.visualizer.renderer.impl.IRenderable;

public interface IDisplayList extends IRenderable {
	
	void load();
	
	void begin();
	
	void end();
	
}
