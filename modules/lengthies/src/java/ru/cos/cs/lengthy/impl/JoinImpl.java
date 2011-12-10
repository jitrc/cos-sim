/**
 * 
 */
package ru.cos.cs.lengthy.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ru.cos.cs.lengthy.Join;
import ru.cos.cs.lengthy.Observation;
import ru.cos.cs.lengthy.RegularLengthy;
import ru.cos.cs.lengthy.Router;
import ru.cos.cs.lengthy.exceptions.IllegalLengthiesNavigationException;
import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.continuous.Continuous;

/**
 * General join implementation.
 * @author zroslaw
 */
public class JoinImpl extends LengthyImpl implements Join {

	protected Set<RegularLengthy> joinedLengthies = new HashSet<RegularLengthy>();
	
	public JoinImpl() {
		
	}

	@Override
	public Set<RegularLengthy> getJoinedLengthies() {
		return joinedLengthies;
	}


	@Override
	public void join(RegularLengthy regularLengthy) {
		joinedLengthies.add(regularLengthy);
		regularLengthy.setNext(this);
	}

	@Override
	public void unjoin(RegularLengthy regularLengthy) {
		if (!joinedLengthies.remove(regularLengthy))
			throw new InternalError("Lengthy was not yet joined in this join");
		regularLengthy.setNext(null);
	}


	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.impl.RegularLengthyImpl#observeBackward(float, float, ru.cos.cs.lengthy.Router)
	 */
	@Override
	public List<Observation> observeBackward(float position, float distance, Router router) {
		List<Observation> result;
		if (router!=null){
			prev = router.getPrevLengthy(this);
			if (!joinedLengthies.contains(prev))
				throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not joined to this join");
			result = super.observeBackward(position, distance, router);
			prev = null;
		}else{
			result = new Vector<Observation>();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.impl.RegularLengthyImpl#propagateContinuous(ru.cos.cs.lengthy.objects.continuous.Continuous, float, float, ru.cos.cs.lengthy.Router)
	 */
	@Override
	public void putContinuousFromEndPoint(Continuous continuous, float position, Router router) {
		prev = router.getPrevLengthy(this);
		if (!joinedLengthies.contains(prev))
			throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not joined to this join");
		super.putContinuousFromEndPoint(continuous, position, router);
		prev = null;
	}

	@Override
	public void setNext(RegularLengthy nextLengthy) {
		this.next = nextLengthy;
	}

	@Override
	public void move(Point point, float distance, Router router) {
		if (distance<0){
			prev = router.getPrevLengthy(this);
			if (!joinedLengthies.contains(prev))
				throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not joined to this join");
		}
		super.move(point, distance, router);
		prev = null;
	}
	
	@Override
	public void move(Continuous continuous, float distance, Router router) {
		prev = router.getPrevLengthy(this);
		if (!joinedLengthies.contains(prev))
			throw new IllegalLengthiesNavigationException("Chosed by router lengthy does not joined to this join");
		super.move(continuous, distance, router);
		prev = null;
	}

	@Override
	public final float getLength() {
		return 0;
	}

	@Override
	public RegularLengthy getNext() {
		return (RegularLengthy) next;
	}
	
}
