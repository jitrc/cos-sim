package ru.cos.sim.visualizer.color;

public class Color {

	/**
     * the color black (0,0,0)
     */
	public static final Color black = new Color(0f, 0f, 0f, 1f);
    /**
     * the color white (1,1,1).
     */
    public static final Color white = new Color(1f, 1f, 1f, 1f);
    /**
     * the color gray (.2,.2,.2)
     */
    public static final Color darkGray = new Color(0.2f, 0.2f, 0.2f, 1f);
    /**
     * the color gray (.5,.5,.5)
     */
    public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1f);
    /**
     * the color gray (.8,.8,.8)
     */
    public static final Color lightGray = new Color(0.8f, 0.8f, 0.8f, 1f);
    /**
     * the color red (1,0,0)
     */
    public static final Color red = new Color(1f, 0f, 0f, 1f);
    /**
     * the color green (0,1,0)
     */
    public static final Color green = new Color(0f, 1f, 0f, 1f);
    /**
     * the color blue (0,0,1)
     */
    public static final Color blue = new Color(0f, 0f, 1f, 1f);
    /**
     * the color yellow (1,1,0)
     */
    public static final Color yellow = new Color(1f, 1f, 0f, 1f);
    /**
     * the color magenta (1,0,1)
     */
    public static final Color magenta = new Color(1f, 0f, 1f, 1f);
    /**
     * the color cyan (0,1,1)
     */
    public static final Color cyan = new Color(0f, 1f, 1f, 1f);
    /**
     * the color orange (251/255, 130/255,0)
     */
    public static final Color orange = new Color(251f/255f, 130f/255f, 0f, 1f);
    /**
     * the color brown (65/255, 40/255, 25/255)
     */
    public static final Color brown = new Color(65f/255f, 40f/255f, 25f/255f, 1f);
    /**
     * the color pink (1, 0.68, 0.68)
     */
    public static final Color pink = new Color(1f, 0.68f, 0.68f, 1f);
    
    public static final Color greenLight = new Color(0.196f, 0.780f, 0.196f, 1);
    
    public static final Color asphalt = new Color(0.1569f, 0.1569f, 0.1569f, 1f);
    
    public static final Color transitioRule = new Color(0.8275f, 0.8275f, 0.8275f, 1f);
	
	public float r = 0;
	public float g = 0;
	public float b = 0;
	public float a = 0;
	
	
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(int r , int g, int b , int a)
	{
		this(r/255.0f, g/255.0f, b/255.0f, a/255.0f);
	}
}
