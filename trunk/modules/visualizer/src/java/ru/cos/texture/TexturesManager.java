package ru.cos.texture;

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
	
}
