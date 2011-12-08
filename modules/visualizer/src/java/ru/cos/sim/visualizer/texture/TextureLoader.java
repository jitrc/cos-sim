package ru.cos.sim.visualizer.texture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * A utility class to load textures for LWJGL. This source is based
 * on a texture that can be found in the Java Gaming (www.javagaming.org)
 * Wiki. 
 * 
 * OpenGL uses a particular image format. Since the images that are 
 * loaded from disk may not match this format this loader introduces
 * a intermediate image which the source image is copied into. In turn,
 * this image is used as source for the OpenGL texture.
 *
 * @author Kevin Glass
 * @author Brian Matzon
 */
public class TextureLoader {
	private static Logger logger = Logger.getLogger(TextureLoader.class.getName()); 
    /** The table of textures that have been loaded in this loader */
    private HashMap table = new HashMap();

    /** The colour model including alpha for the GL image */
    private ColorModel glAlphaColorModel;
    
    /** The colour model for the GL image */
    private ColorModel glColorModel;
    
    /** 
     * Create a new texture loader based on the game panel
     *
     * @param gl The GL content in which the textures should be loaded
     */
    public TextureLoader() {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                                            new int[] {8,8,8,8},
                                            true,
                                            false,
                                            ComponentColorModel.TRANSLUCENT,
                                            DataBuffer.TYPE_BYTE);
                                            
        glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                                            new int[] {8,8,8,0},
                                            false,
                                            false,
                                            ComponentColorModel.OPAQUE,
                                            DataBuffer.TYPE_BYTE);
    }
    
    /**
     * Create a new texture ID 
     *
     * @return A new texture ID
     */
    private int createTextureID() 
    { 
       IntBuffer tmp = createIntBuffer(1); 
       GL11.glGenTextures(tmp); 
       return tmp.get(0);
    } 
    
    /**
     * Load a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(String resourceName) throws IOException {
        Texture tex = (Texture) table.get(resourceName);
        
        if (tex != null) {
            return tex;
        }
        
        logger.log(Level.INFO,"Loading texture " + resourceName);
        
        tex = getTexture(resourceName,
        				 null,
                         GL11.GL_TEXTURE_2D, // target
                         GL11.GL_RGBA,     // dst pixel format
                         GL11.GL_NEAREST, // min filter (unused)
                         GL11.GL_NEAREST_MIPMAP_NEAREST);
        
        table.put(resourceName,tex);
        
        return tex;
    }
    
    
    /**
     * Load a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTextureFromStream(String resourceName ,InputStream stream, boolean downloadForce) throws IOException {
       if (!downloadForce) {
    	   Texture tex = (Texture) table.get(resourceName);
           
           if (tex != null) {
               return tex;
           }
       }
    	
        
        logger.log(Level.INFO,"Loading texture " + resourceName);
        
        Texture tex = getTexture(resourceName,stream,
                         GL11.GL_TEXTURE_2D, // target
                         GL11.GL_RGBA,     // dst pixel format
                         GL11.GL_LINEAR, // min filter (unused)
                         GL11.GL_NEAREST);
        
        table.put(resourceName,tex);
        
        return tex;
    }
    
    /**
     * Load a texture into OpenGL from a image reference on
     * disk.
     *
     * @param resourceName The location of the resource to load
     * @param target The GL target to load the texture against
     * @param dstPixelFormat The pixel format of the screen
     * @param minFilter The minimising filter
     * @param magFilter The magnification filter
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(String resourceName,
    						  InputStream stream,
                              int target, 
                              int dstPixelFormat, 
                              int minFilter, 
                              int magFilter) throws IOException 
    { 
        int srcPixelFormat = 0;
        
        // create the texture ID for this texture 
        int textureID = createTextureID(); 
        Texture texture = new Texture(target,textureID); 
        
        // bind this texture 
        GL11.glBindTexture(target, textureID); 
 
        BufferedImage bufferedImage = (stream == null) ? loadImage(resourceName) : loadImage(stream); 
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());
        
        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL11.GL_RGBA;
        } else {
            srcPixelFormat = GL11.GL_RGB;
        }

        // convert that image into a byte buffer of texture data 
        ByteBuffer textureBuffer = convertImageData(bufferedImage,texture); 
        
        if (target == GL11.GL_TEXTURE_2D) 
        { 
            //GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter); 
            //GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter); 
        } 
 
        // produce a texture from the byte buffer
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
      /*  GL11.glTexEnvi ( GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_BLEND );
        */GLU.gluBuild2DMipmaps(target, 
                srcPixelFormat,
                //bufferedImage.getWidth(), 
                //bufferedImage.getHeight(),
                get2Fold(bufferedImage.getWidth()), 
                get2Fold(bufferedImage.getHeight()), 
                
                srcPixelFormat, 
                GL11.GL_UNSIGNED_BYTE, 
                textureBuffer);
       /* GL11.glTexImage2D(target, 
                      0, 
                      dstPixelFormat, 
                      get2Fold(bufferedImage.getWidth()), 
                      get2Fold(bufferedImage.getHeight()), 
                      0, 
                      srcPixelFormat, 
                      GL11.GL_UNSIGNED_BYTE, 
                      textureBuffer ); */
        
        return texture; 
    } 
    
    /**
     * Get the closest greater power of 2 to the fold number
     * 
     * @param fold The target number
     * @return The power of 2
     */
    private int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    } 
    
    /**
     * Convert the buffered image to a texture
     *
     * @param bufferedImage The image to convert to a texture
     * @param texture The texture to store the data into
     * @return A buffer containing the data
     */
    private ByteBuffer convertImageData(BufferedImage bufferedImage,Texture texture) { 
        ByteBuffer imageBuffer = null; 
        WritableRaster raster;
        BufferedImage texImage;
        
        int texWidth = 2;
        int texHeight = 2;
        
        // find the closest power of 2 for the width and height
        // of the produced texture
        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }
        
        texture.setTextureHeight(texHeight);
        texture.setTextureWidth(texWidth);
        
        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,4,null);
            texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
            texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
        }
            
        // copy the source image into the produced image
        Graphics2D g = (Graphics2D) texImage.getGraphics();
        g.setColor(new Color(0f,0f,0f,0f));
        g.fillRect(0,0,texWidth,texHeight);
        g.translate(0,texHeight);
        AffineTransform t = AffineTransform.getScaleInstance(1,-1);
        g.drawImage(bufferedImage,t,null);
        
        // build a byte buffer from the temporary image 
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData(); 

        imageBuffer = ByteBuffer.allocateDirect(data.length); 
        imageBuffer.order(ByteOrder.nativeOrder()); 
        imageBuffer.put(data, 0, data.length); 
        imageBuffer.flip();
        
        return imageBuffer; 
    } 
    
    /** 
     * Load a given resource as a buffered image
     * 
     * @param ref The location of the resource to load
     * @return The loaded buffered image
     * @throws IOException Indicates a failure to find a resource
     */
    private BufferedImage loadImage(String ref) throws IOException 
    { 
        URL url = TextureLoader.class.getClassLoader().getResource(ref);
       File file  = new File(ref);
        //file.get
        //URL a = new URL
       
        
//        if (!file.exists()) {
//            throw new IOException("Cannot find: "+ref);
//        }
        
       //System.out.println("Try to find: "+ref);
        //BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(ref)));
        BufferedImage bufferedImage = ImageIO.read(Class.class.getResourceAsStream(ref));
        
        return bufferedImage;
    }
    
    private BufferedImage loadImage(InputStream stream) throws IOException
    { 
        BufferedImage bufferedImage = ImageIO.read(stream);
        
        return bufferedImage;
    }
    
    /**
     * Creates an integer buffer to hold specified ints
     * - strictly a utility method
     *
     * @param size how many int to contain
     * @return created IntBuffer
     */
    protected IntBuffer createIntBuffer(int size) {
      ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
      temp.order(ByteOrder.nativeOrder());

      return temp.asIntBuffer();
    }    
}
