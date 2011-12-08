/**
 * 
 */
package ru.cos.cs.lengthy.impl.objects;

import java.util.HashSet;
import java.util.Set;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.cs.lengthy.exceptions.IllegalOperationExceprion;
import ru.cos.cs.lengthy.objects.MultiPoint;
import ru.cos.cs.lengthy.objects.Point;

/**
 * 
 * @author zroslaw
 */
public class MultiPointImpl extends PointImpl implements MultiPoint {

	/**
	 * Points in this multipoint instance.
	 */
	protected Set<Point> points = new HashSet<Point>();

	/**
	 * MultiPoint is constructed on the base of regular point already placed on some lengthy
	 * @param point
	 */
	public MultiPointImpl(Point point){
		Lengthy lengthy = point.getLengthy();
		float position = point.getPosition();
		
		if (lengthy==null)
			throw new IllegalOperationExceprion("Unable to create " +
					"multiPoint on the base of regular point that is not yet placed on any lengthy");
		
		lengthy.removePoint(point);
		point.setLengthy(lengthy);
		point.setPosition(position);
		
		lengthy.putPoint(this, position);

		points.add(point);
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.objects.MultiPoint#getPoints()
	 */
	@Override
	public Set<Point> getPoints() {
		return points;
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.objects.MultiPoint#addPoint(ru.cos.cs.lengthy.objects.Point)
	 */
	@Override
	public void addPoint(Point point) {
		points.add(point);
		point.setLengthy(lengthy);
		point.setPosition(position);
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.objects.MultiPoint#removePoint(ru.cos.cs.lengthy.objects.Point)
	 */
	@Override
	public void removePoint(Point point) {
		points.remove(point);
	}

	/* (non-Javadoc)
	 * @see ru.cos.cs.lengthy.impl.objects.PointImpl#getType()
	 */
	@Override
	public final PointType getPointType() {
		return PointType.MultiPoint;
	}

}
