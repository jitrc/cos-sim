package ru.cos.trace.item;

import ru.cos.agents.car.CarPosition;
import ru.cos.math.Vector2f;
import ru.cos.nissan.parser.geometry.Side;
import ru.cos.scene.impl.ICarKeeper;
import ru.cos.trace.item.base.StaticEntity;

public class Lane extends StaticEntity implements ICarKeeper{

	protected Lane next;
	protected Lane previous;
	protected float width;
	protected float desiredLength;
	protected float length;
	protected Side rightSide;
	protected Side leftSide;
	protected Vector2f bw;
	protected Vector2f ew;
	
	protected Vector2f normalVector;
	
	public Lane(int uid) {
		super(uid);
	}

	public Lane getNext() {
		return next;
	}

	public Lane getPrevious() {
		return previous;
	}

	public void setNext(Lane next) {
		this.next = next;
	}

	public void setPrevious(Lane previous) {
		this.previous = previous;
	}

	public float getWidth() {
		return width;
	}

	public float getLength() {
		return length;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setDesiredLength(float length) {
		this.desiredLength = length;
	}

	public float getDesiredLength() {
		return desiredLength;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Side getRightSide() {
		return rightSide;
	}

	public void setRightSide(Side rightSide) {
		this.rightSide = rightSide;
	}

	public Vector2f getBegin() {
		return bw;
	}

	public Vector2f getEnd() {
		return ew;
	}

	private void recalc()
	{
		if (bw == null || ew == null) return;
		this.normalVector = ew.subtract(bw).normalizeLocal();
	}
	
	public void setBegin(Vector2f bw) {
		this.bw = bw;
	}

	public void setEnd(Vector2f ew) {
		this.ew = ew;
	}

	@Override
	public CarPosition getPosition(float position, CarPosition pos) {
		if (pos == null) pos = new CarPosition(new Vector2f(), new Vector2f());
		pos.direction.x = this.normalVector.x; 
		pos.direction.y	= this.normalVector.y;
		
		pos.position.x = this.normalVector.x;
		pos.position.y = this.normalVector.y;
		
		pos.position.multLocal(position).addLocal(this.getBegin());
		
		return pos;
	}
	
	public CarPosition getPosition(float position,float shift, CarPosition pos) {
		if (pos == null) pos = new CarPosition(new Vector2f(), new Vector2f());
		pos = getPosition(position, pos);
		
		Vector2f ort = this.normalVector.clone();
		ort.rotate90right();
		ort.normalizeLocal().multLocal(shift);
		
		pos.position.addLocal(ort);
		return pos;
	}
	
	@Override
	public CarPosition getPosition(float position) {
		return getPosition(position, null);
	}
	
	public Vector2f getOrtVectortoLane(Lane lane)
	{
		Vector2f vector = this.getEnd().subtract(this.getBegin());
		Vector2f b = lane.getBegin().subtract(this.getBegin());
		vector.rotate90();
		if (vector.mult(b) < 0) vector.negateLocal();
		vector.normalizeLocal();
		
		float height = 0;
		
		Vector2f a = this.getEnd().subtract(this.getBegin());
		float square = Math.abs(b.determinant(a)); 
		vector.multLocal(square / a.length());
		return vector;
	}
	
}

