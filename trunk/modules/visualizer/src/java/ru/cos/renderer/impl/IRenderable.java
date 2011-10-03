package ru.cos.renderer.impl;

import ru.cos.renderer.Renderer.RenderType;

public interface IRenderable {

	public static enum FrustrumState {
		OutOfView,
		PartlyInView,
		InView
	}
	
	void render(RenderType mode);
	FrustrumState getLastFrustrumState();
}
