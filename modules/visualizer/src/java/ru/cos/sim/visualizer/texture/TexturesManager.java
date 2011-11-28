package ru.cos.sim.visualizer.texture;

import ru.cos.sim.visualizer.renderer.impl.IRenderable;

public class TexturesManager {
	private static TexturesManager instance; 
	
	private TextureLoader loader;
	
	private TexturesManager(){
		loader = new TextureLoader();
	}
	
	public static TexturesManager getInstance()
	{
		if (instance == null) instance = new TexturesManager();
		return instance;
	}
	
	public TextureLoader getLoader()
	{
		return loader;
	}
	
//	public Texture renderTotexture(IRenderable r){
//		loader.createTexture(name, width, height);
//	}
	
}
