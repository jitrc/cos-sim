package ru.cos.display;

import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBMultisample;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import ru.cos.camera.Camera;
import ru.cos.canvas.LWJGLCanvas;
import ru.cos.exception.OglException;
import ru.cos.renderer.Renderer;



public class DisplaySystem {
	private static final Logger logger = Logger.getLogger(DisplaySystem.class.getName());
	
	/**
     * Width selected for the renderer.
     */
    protected int width;

    /**
     * height selected for the renderer.
     */
    protected int height;

    /**
     * Bit depth selected for renderer.
     */
    protected int bpp;

    /**
     * Frequency selected for renderer.
     */
    protected int frq;

    /**
     * Is the display full screen?
     */
    protected boolean fs;

    /**
     * Is the display created already?
     */
    protected boolean created;

    /**
     * Alpha bits to use for the renderer.
     */
    protected int alphaBits = 0;

    /**
     * Depth bits to use for the renderer.
     */
    protected int depthBits = 8;

    /**
     * Stencil bits to use for the renderer.
     */
    protected int stencilBits = 0;

    /**
     * Number of samples to use for the multisample buffer.
     */
    protected int samples = 0;

    /**
     * Gamma value of display - default is 1.0f. 0->infinity
     */
    protected float gamma = 1.0f;

    /**
     * Brightness value of display - default is 0f. -1.0 -> 1.0
     */
    protected float brightness = 0;

    /**
     * Contrast value of display - default is 1.0f. 0->infinity
     */
    protected float contrast = 1;
	
	 /**
     * <code>initDisplay</code> creates the LWJGL window with the desired
     * specifications.
     */
    protected static DisplaySystem instance;
    protected Renderer currentRenderer;
    protected Camera camera;
    
    public static DisplaySystem getDisplaySystem()
    {
    	if (instance == null) instance = new DisplaySystem();
    	return instance;
    }
    
    private void initDisplay() {
        // create the Display.
        DisplayMode mode = selectMode();
        PixelFormat format = getFormat();
        if ( null == mode ) {
            throw new OglException( "Bad display mode" );
        }

        try {
            Display.setDisplayMode( mode );
            Display.setFullscreen( fs );
            if ( !fs ) {
                int x, y;
                x = ( Toolkit.getDefaultToolkit().getScreenSize().width - width ) >> 1;
                y = ( Toolkit.getDefaultToolkit().getScreenSize().height - height ) >> 1;
                Display.setLocation( x, y );
            }
            Display.create( format );
            
            if (samples != 0 && GLContext.getCapabilities().GL_ARB_multisample) {
                GL11.glEnable(ARBMultisample.GL_MULTISAMPLE_ARB);
            }
            
            // kludge added here... LWJGL does not properly clear their
            // keyboard and mouse buffers when you call the destroy method,
            // so if you run two jme programs in the same jvm back to back
            // the second one will pick up the esc key used to exit out of
            // the first.
            Keyboard.poll();
            Mouse.poll();

        } catch ( Exception e ) {
            // System.exit(1);
            logger.severe("Cannot create window");
            logger.logp(Level.SEVERE, this.getClass().toString(), "initDisplay()", "Exception", e);
            throw new OglException( "Cannot create window: " + e.getMessage() );
        }
    }
    
//    public OglCanvas createCanvas()
//    {
//    	LWJGLCanvas canvas = null;
//    	try {
//    		canvas = new LWJGLCanvas();
//    	} catch (Exception e){
//    		logger.logp(Level.SEVERE, this.getClass().toString(),"createCanvas()","Exception", e);
//    	}
//    	return canvas; 
//    }
    
    public void initforCanvas()
    {
    }
    
    private DisplayMode selectMode() {
        DisplayMode mode;
        if ( fs ) {
            mode = getValidDisplayMode( width, height, bpp, frq );
            if ( null == mode ) {
                throw new OglException(
                        "Bad display mode (w/h/bpp/freq): "
                        + width + " / " + height + " / " + bpp + " / " + frq);
            }
        }
        else {
            mode = new DisplayMode( width, height );
        }
        return mode;
    }
    
	private DisplayMode getValidDisplayMode(int width, int height, int bpp,
			int freq) {
		// get all the modes, and find one that matches our width, height, bpp.
		DisplayMode[] modes;
		try {
			modes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			logger.logp(Level.SEVERE, this.getClass().toString(),
					"getValidDisplayMode(width, height, bpp, freq)",
					"Exception", e);
			return null;
		}

		// Try to find a best match.
		int best_match = -1; // looking for request size/bpp followed by exact
								// or highest freq
		int match_freq = -1;
		for (int i = 0; i < modes.length; i++) {
			if (modes[i].getWidth() != width) {
				logger.log(Level.FINE, "DisplayMode {0}: Width != {1}",
						new Object[] { modes[i], width });
				continue;
			}
			if (modes[i].getHeight() != height) {
				logger.log(Level.FINE, "DisplayMode {0}: Height != {1}",
						new Object[] { modes[i], height });
				continue;
			}
			if (bpp != 0 && modes[i].getBitsPerPixel() != bpp) { // should pick
																	// based on
																	// best
																	// match
																	// here too
				logger.log(Level.FINE,
						"DisplayMode {0}: Bits per pixel != {1}", new Object[] {
								modes[i], bpp });
				continue;
			}
			if (best_match == -1) {
				logger.log(Level.FINE, "DisplayMode {0}: Match! ", modes[i]);
				best_match = i;
				match_freq = modes[i].getFrequency();
			} else {
				int cur_freq = modes[i].getFrequency();
				if (match_freq != freq && // Previous is not a perfect match
						(cur_freq == freq || // Current is perfect match
						match_freq < cur_freq)) // or is higher freq
				{
					logger.log(Level.FINE, "DisplayMode {0}: Better match!",
							modes[i]);
					best_match = i;
					match_freq = cur_freq;
				}
			}
		}

		if (best_match == -1)
			return null; // none found;
		else {
			logger.log(Level.INFO, "Selected DisplayMode: {0}",
					modes[best_match]);
			return modes[best_match];
		}
	}
	
	public Renderer getRenderer()
	{
		if (currentRenderer == null) currentRenderer = Renderer.createRenderer();
		return currentRenderer;
	}
    
    /**
     * Returns a new PixelFormat with the current settings.
     *
     * @return a new PixelFormat with the current settings
     */
    public PixelFormat getFormat() {
        return new PixelFormat( bpp, alphaBits, depthBits,
                stencilBits, samples );
    }

    
    public int getMinAlphaBits() {
        return alphaBits;
    }

    /**
     * Sets the minimum bits per pixel in the alpha buffer.
     * 
     * @param alphaBits -
     *            the new value for alphaBits
     */
    public void setMinAlphaBits(int alphaBits) {
        this.alphaBits = alphaBits;
    }

    /**
     * Returns the minimum bits per pixel in the depth buffer.
     * 
     * @return the int value of depthBits.
     */
    public int getMinDepthBits() {
        return depthBits;
    }

    /**
     * Sets the minimum bits per pixel in the depth buffer.
     * 
     * @param depthBits -
     *            the new value for depthBits
     */
    public void setMinDepthBits(int depthBits) {
        this.depthBits = depthBits;
    }

    /**
     * Returns the minimum bits per pixel in the stencil buffer.
     * 
     * @return the int value of stencilBits.
     */
    public int getMinStencilBits() {
        return stencilBits;
    }

    /**
     * Sets the minimum bits per pixel in the stencil buffer.
     * 
     * @param stencilBits -
     *            the new value for stencilBits
     */
    public void setMinStencilBits(int stencilBits) {
        this.stencilBits = stencilBits;
    }

    /**
     * Returns the minimum samples in multisample buffer.
     * 
     * @return the int value of samples.
     */
    public int getMinSamples() {
        return samples;
    }

    /**
     * Sets the minimum samples in the multisample buffer.
     * 
     * @param samples -
     *            the new value for samples
     */
    public void setMinSamples(int samples) {
        this.samples = samples;
    }

    /**
     * Returns the brightness last requested by this display.
     * 
     * @return brightness - should be between -1 and 1.
     */
    public float getBrightness() {
        return brightness;
    }

    /**
     * Note: This affects the whole screen, not just the game window.
     * 
     * @param brightness
     *            The brightness to set (set -1 to 1) default is 0
     */
    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    /**
     * @return Returns the contrast.
     */
    public float getContrast() {
        return contrast;
    }

    /**
     * Note: This affects the whole screen, not just the game window.
     * 
     * @param contrast
     *            The contrast to set (set greater than 0) default is 1
     */
    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    /**
     * @return Returns the gamma.
     */
    public float getGamma() {
        return gamma;
    }

	public Camera getCamera() {
		if (camera == null) camera = new Camera();
		return camera;
	}
    
    
    
}
