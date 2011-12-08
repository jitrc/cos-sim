package ru.cos.sim.visualizer.renderer.impl;

import ru.cos.sim.visualizer.renderer.Renderer.RenderType;

public interface IRenderable {

	public static enum FrustrumState {
		OutOfView,
		PartlyInView,
		InView
	}
	
	void render(RenderType mode);
	FrustrumState getLastFrustrumState();
}
