package ru.cos.sim.visualizer.renderer;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import ru.cos.sim.visualizer.math.Vector2f;

public class Frustum {

	float[][] frustum = new float[6][4];
	Vector2f[] square = new Vector2f[4];
	
	public void extractFrustum()
	{
	   float[] proj = new float[16];
	   float[] modl = new float[16];
	   float[] clip = new float[16];
	   float   t;

	   /* Get the current PROJECTION matrix from OpenGL */
	   FloatBuffer a = BufferUtils.createFloatBuffer(16);
	   glGetFloat( GL_PROJECTION_MATRIX, a );
	   
	   for (int i = 0 ; i < a.capacity(); i++) {
		   proj[i] = a.get();
	   }

	   a = BufferUtils.createFloatBuffer(16);
	   /* Get the current MODELVIEW matrix from OpenGL */
	   glGetFloat( GL_MODELVIEW_MATRIX,a );

	   for (int i = 0 ; i < a.capacity(); i++) {
		   modl[i] = a.get();
	   }
	   /* Combine the two matrices (multiply projection by modelview) */
	   clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
	   clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
	   clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
	   clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];

	   clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
	   clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
	   clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
	   clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];

	   clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
	   clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
	   clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
	   clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];

	   clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
	   clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
	   clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
	   clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];

	   /* Extract the numbers for the RIGHT plane */
	   frustum[0][0] = clip[ 3] - clip[ 0];
	   frustum[0][1] = clip[ 7] - clip[ 4];
	   frustum[0][2] = clip[11] - clip[ 8];
	   frustum[0][3] = clip[15] - clip[12];

	   /* Normalize the result */
	   t = sqrt( frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2] );
	   frustum[0][0] /= t;
	   frustum[0][1] /= t;
	   frustum[0][2] /= t;
	   frustum[0][3] /= t;

	   /* Extract the numbers for the LEFT plane */
	   frustum[1][0] = clip[ 3] + clip[ 0];
	   frustum[1][1] = clip[ 7] + clip[ 4];
	   frustum[1][2] = clip[11] + clip[ 8];
	   frustum[1][3] = clip[15] + clip[12];

	   /* Normalize the result */
	   t = sqrt( frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2] );
	   frustum[1][0] /= t;
	   frustum[1][1] /= t;
	   frustum[1][2] /= t;
	   frustum[1][3] /= t;

	   /* Extract the BOTTOM plane */
	   frustum[2][0] = clip[ 3] + clip[ 1];
	   frustum[2][1] = clip[ 7] + clip[ 5];
	   frustum[2][2] = clip[11] + clip[ 9];
	   frustum[2][3] = clip[15] + clip[13];

	   /* Normalize the result */
	   t = sqrt( frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2] );
	   frustum[2][0] /= t;
	   frustum[2][1] /= t;
	   frustum[2][2] /= t;
	   frustum[2][3] /= t;

	   /* Extract the TOP plane */
	   frustum[3][0] = clip[ 3] - clip[ 1];
	   frustum[3][1] = clip[ 7] - clip[ 5];
	   frustum[3][2] = clip[11] - clip[ 9];
	   frustum[3][3] = clip[15] - clip[13];

	   /* Normalize the result */
	   t = sqrt( frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2] );
	   frustum[3][0] /= t;
	   frustum[3][1] /= t;
	   frustum[3][2] /= t;
	   frustum[3][3] /= t;

	   /* Extract the FAR plane */
	   frustum[4][0] = clip[ 3] - clip[ 2];
	   frustum[4][1] = clip[ 7] - clip[ 6];
	   frustum[4][2] = clip[11] - clip[10];
	   frustum[4][3] = clip[15] - clip[14];

	   /* Normalize the result */
	   t = sqrt( frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2] );
	   frustum[4][0] /= t;
	   frustum[4][1] /= t;
	   frustum[4][2] /= t;
	   frustum[4][3] /= t;

	   /* Extract the NEAR plane */
	   frustum[5][0] = clip[ 3] + clip[ 2];
	   frustum[5][1] = clip[ 7] + clip[ 6];
	   frustum[5][2] = clip[11] + clip[10];
	   frustum[5][3] = clip[15] + clip[14];

	   /* Normalize the result */
	   t = sqrt( frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2] );
	   frustum[5][0] /= t;
	   frustum[5][1] /= t;
	   frustum[5][2] /= t;
	   frustum[5][3] /= t;
	   
	   int p;

	 //  for( p = 0; p < 6; p++ )
//	   {
//		   for (int j = p; j <= 3; j++) {
//			 //  System.out.println("p = " + p + " j= " +j);
//			  // intersectPoint(frustum[p][0], frustum[p][1], frustum[p][2], frustum[p][3],
//			//		   frustum[j][0], frustum[p][1], frustum[j][2], frustum[j][3]);
//		   }
//		   //System.out.println("A: "+frustum[p][0] + " B: " + frustum[p][1] +
//			//	   " C: " + frustum[p][2] + " D: " +frustum[p][3]);
//		   
//	   }
//	   System.out.println("------------");
	   square[0] = intersectPoint(0, 2);
	   square[1] = intersectPoint(0, 3);
	   square[2] = intersectPoint(1, 3);
	   square[3] = intersectPoint(1, 2);
	}
	
	private float sqrt(float a) {
		return (float)Math.sqrt(a);
	}
	
	public boolean squareInFrustum( float x, float y, float z, float size )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	   {
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y - size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y - size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y + size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y + size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y - size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y - size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x - size) + frustum[p][1] * (y + size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      if( frustum[p][0] * (x + size) + frustum[p][1] * (y + size) + frustum[p][2] * (z) + frustum[p][3] > 0 )
	         continue;
	      return false;
	   }
	   return true;
	}
	
	public boolean PointInFrustum( float x, float y, float z )
	{
	   int p;

	   for( p = 0; p < 6; p++ )
	      if( frustum[p][0] * x + frustum[p][1] * y + frustum[p][2] * z + frustum[p][3] <= 0 )
	         return false;
	   return true;
	}
	
	public boolean lineInFrustrum(float x1, float y1 ,float x2 , float y2) {
		boolean result = true;
		
		for(int p = 0; p < 6; p++ )
		{
			boolean p1 =  frustum[p][0] * (x1) + frustum[p][1] * (y1) + frustum[p][2] * (0) + frustum[p][3] > 0 ;
			boolean p2 = frustum[p][0] * (x2) + frustum[p][1] * (y2) + frustum[p][2] * (0) + frustum[p][3] > 0 ;
			if (!((p1 && p2) || (p1 ^ p2))) return false;
		}
		
		return result;
	}
	
	protected Vector2f intersectPoint(int a , int b) {
		return this.intersectPoint(frustum[a][0], frustum[a][1], frustum[a][2], frustum[a][3],
				frustum[b][0], frustum[b][1], frustum[b][2], frustum[b][3]);
	}
	
	protected Vector2f intersectPoint(float a1, float b1, float c1 , float d1,
			float a2, float b2, float c2 , float d2) {
		Vector2f result = new Vector2f((b2*d1 + b1*d2)/(a2*b1 - b2*a1),
				(a2*d1+a1*d2)/(a2*b1 - b2*a1));
		return result;
	}
	
	protected boolean sectionsIntersecting(Vector2f b1, Vector2f e1,
			Vector2f b2, Vector2f e2) 
	{
		boolean check1 = check(b1.x,b1.y,e1.x,e1.y,b2.x,b2.y,e2.x,e2.y);
		boolean check2 = check(b2.x,b2.y,e2.x,e2.y,b1.x,b1.y,e1.x,e1.y);
		return check1 && check2;
	}
	
	protected boolean check(float bx, float by, float x, float y,
			float x1 ,float y1, float x2, float y2) {
		float mult1 = (x - bx)*(y1 - by) - (x1 - bx)*(y-by);
		float mult2 = (x - bx)*(y2 - by) - (x2 - bx)*(y-by);
		return mult1*mult2 < 0;
	}
	
	public boolean checkSegment(Vector2f begin, Vector2f end) {
		if (
				sectionsIntersecting(begin, end, square[0], square[1]) || 
				sectionsIntersecting(begin, end, square[1], square[2]) ||
				sectionsIntersecting(begin, end, square[2], square[3]) ||
				sectionsIntersecting(begin, end, square[1], square[3])
				) return true;
		return false;
	}
}
