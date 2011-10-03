package ru.cos.scene.impl;

import ru.cos.renderer.impl.IRenderable;

public interface IDisplayList extends IRenderable {
	
	void load();
	
	void begin();
	
	void end();
	
}
