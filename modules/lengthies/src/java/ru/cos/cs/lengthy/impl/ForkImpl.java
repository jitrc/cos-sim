/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ru.cos.cs.lengthy.Fork;
import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.cs.lengthy.Router;
import ru.cos.cs.lengthy.exceptions.IllegalLengthiesNavigationException;
import ru.cos.cs.lengthy.objects.MultiPoint;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.Point.PointType;
import ru.cos.cs.lengthy.objects.continuous.Continuous;

/**
 * Class implements basic functionality of the Fork.
 * @see Fork
 * @author zroslaw
 */
public class ForkImpl extends LengthyImpl implements Fork {
	
	protected Set<RegularLengthy> forkedLengthies = new HashSet<RegularLengthy>();
	
	public ForkImpl() {
		
	}

	/**
	 * @see ru.cos.cs.lengthy.Fork#getForkedLengthies()
	 */
	@Override
	public Set<RegularLengthy> getForkedLengthies() {
		return forkedLengthies;
	}

	@Override
	public void forkTo(RegularLengthy regularLengthy) {
		forkedLengthies.add(regularLengthy);
		regularLengthy.setPrev(this);
	}

	@Override
	public void unforkTo(RegularLengthy regularLengthy) {
		if(!forkedLengthies.remove(regularLengthy))
			throw new InternalError("Lengthy was not yet forked from this fork");	
		regularLengthy.setPrev(null);
	}

	@Override
	public List<Observation> observeForward(float position,	float distance, Router router) {
		List<Observation> result;
		if (router!=null){
			next = router.getNextLengthy(this);
			if (next!=null && !forkedLengthies.contains(next))
				throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not forked from this fork");
			if (next!=null)
				result = super.observeForward(position, distance, router);
			else{
				result = new ArrayList<Observation>(0);
				for (Point point:points){
					if (point.getPointType()==PointType.MultiPoint){
						for (Point point2:((MultiPoint)point).getPoints()){
							result.add(new Observation(-position, point2));
						}
					}else
						result.add(new Observation(-position, point));
				}
			}
			next=null;
		}else{
			result = new Vector<Observation>();
			for (Point  point:points){
				if (point.getPointType()==PointType.MultiPoint){
					for (Point point2:((MultiPoint)point).getPoints()){
						result.add(new Observation(-position, point2));
					}
				}else
					result.add(new Observation(-position, point));
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.impl.RegularLengthyImpl#putContinuous(ru.cos.cs.lengthy.objects.continuous.Continuous, float, ru.cos.cs.lengthy.Router)
	 */
	@Override
	public void putContinuous(Continuous continuous, float position, Router router) {
		next = router.getNextLengthy(this);
		if (!forkedLengthies.contains(next))
			throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not forked from this fork");
		super.putContinuous(continuous, position, router);
		next = null;
	}

	@Override
	public void setPrev(RegularLengthy prevLengthy) {
		this.prev = prevLengthy;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.impl.RegularLengthyImpl#move(ru.cos.cs.lengthy.objects.Moveable, float, ru.cos.cs.lengthy.Router)
	 */
	@Override
	public void move(Point point, float distance, Router router) {
		next = router.getNextLengthy(this);
		if (next==null)
			throw new IllegalLengthiesNavigationException("Router has not choose any of forked lengthies to move on");
		if (!forkedLengthies.contains(next))
			throw new IllegalLengthiesNavigationException("Chosen by router lengthy does not forked from this fork");
		super.move(point, distance, router);
		next = null;
	}

	@Override
	public void move(Continuous continuous, float distance, Router router) {
		next = router.getNextLengthy(this);
		if (!forkedLengthies.contains(next))
			throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not forked from this fork");
		super.move(continuous, distance, router);
		next = null;
	}

	@Override
	public final float getLength() {
		return 0;
	}

	@Override
	public RegularLengthy getPrev() {
		return (RegularLengthy) prev;
	}
	
}
