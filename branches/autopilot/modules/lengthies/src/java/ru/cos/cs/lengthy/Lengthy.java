package ru.cos.cs.lengthy;

import java.util.List;
import java.util.Set;

import ru.cos.cs.lengthy.objects.Point;
import ru.cos.cs.lengthy.objects.continuous.Continuous;
import ru.cos.cs.lengthy.objects.continuous.ContinuousPoint;

/**
 * Lengthy object.
 * <p>
 * During observation process position may be negative or exceed length of the lengthy, this fact 
 * is treated as observation performing out of current lengthy. If position is negative 
 * it is treated as position of observation outlying from the beginning of current 
 * lengthy on the distance (-position) in the opposite direction. 
 * If position is greater than lengthy length L it is treated as position of observation outlying
 * from the end of current lengthy on the distance (position-L) in the lengthy direction.
 * That fact must be presented in resulted observation distances.
 * 
 * 
 * @author zroslaw
 */
public interface Lengthy{

	/**
	 * Returns lengthy's length.
	 * @return length of this lengthy.
	 */
	public float getLength();

	/**
	 * Observing lengthy forward on the specified path.<br>
	 * Behavior of this method is the same as simple observeForward(float,float) method, except of 
	 * it doesn't end when it encounters Fork lengthy. It tries to observe observe forward using specified 
	 * path to choose next forked lengthy. In case of specified lengthy is not found in the set of forked lengthies, 
	 * then observation process will be stopped and ForkPoint observation will be provided.
	 * <p>
	 * @param position position on the lengthy from which to observe
	 * @param distance distance to observe on
	 * @param router router instance to find path in lengthies net in case of forks and joins
	 * @return list of forward observations. Distances to observations in the resulted list are in ascent order and more then zero. 
	 */
	public List<Observation> observeForward(float position, float distance, Router router);

	/**
	 * Observing lengthy backward on the specified path.
	 * Behavior of this method is the same as simple observeBackward(float,float) method, except of 
	 * it doesn't end when it encounters Join lengthy. It tries to observe backward using specified 
	 * path to choose next joined lengthy. In case of specified lengthy is not found in the set of joined lengthies, 
	 * then observation process will be stopped and JoinPoint observation will be provided.
	 * @param position position on the lengthy from which to observe
	 * @return list of backward observations. Distances to observations in the resulted list are in descent order and less then zero. 
	 */
	public List<Observation> observeBackward(float position, float distance, Router router);
	
	/**
	 * Observe continuous objects in the position.
	 * @param position position on the lengthy from which to observe.
	 * @return set of continuous observations in this position.
	 */
	public Set<ContinuousPoint> observeContinuous(float position);

	/**
	 * Put observable in the specified position.
	 * Point properties (such as position and lengthy) will be changed accordingly.
	 * <p>
	 * <b>Important:</b> position on the lengthy may be specified as negative or exceed lengthy's length,
	 * in this case observable will be placed on this lengthy anyway and will not be moved on next or previous lengthies.
	 * @param observalbe observable to put on this lengthy
	 * @param position position to put on
	 */
	public void putPoint(Point observalbe, float position);

	/**
	 * Remove point from this lengthy.<p>
	 * Properties of the observable such as position and lengthy will be changed. 
	 * Position will be set to Float.NaN, lengthy will be set to NULL.<p>
	 * In case observable is not on the specified lengthy then exception will be thrown.
	 * @param obsevable observable to remove
	 */
	public void removePoint(Point obsevable);

	/**
	 * Put continuous object on the lengthies starting from this lengthy and from specified position.
	 * In case of there are forks exists on the lengthies then path to propagate continuous object must be provided.
	 * Path is provided as list of forked lengthies that must be sequentially chosen on each fork.
	 * @param continuous continuous object to put on
	 * @param position starting position to put from
	 * @param path list of sequential lengthies that must be chosen in fork points
	 */
	public void putContinuous(Continuous continuous, float position, Router router);

	/**
	 * Put on the lengthy continuous object starting from its end end point and propagating backward.
	 * In case of there are joins exists on the lengthies then path to propagate continuous object must be provided.
	 * Path is provided as list of joined lengthies that must be sequentially chosen on each join.
	 * @param continuous object to put on
	 * @param position position to put from, this position must be measured from this lengthy
	 * @param router router instance to choose path in case of forks and joins
	 * @throws IllegalOperationExceprion in case continuous is not yet placed on lengthies network
	 */
	public void putContinuousFromEndPoint(Continuous continuous, float position, Router router);

	/**
	 * Move point on the specified distance.
	 * @param point
	 * @param distance
	 * @throws IllegalOperationExceprion in case point is not yet placed on any lengthy instance
	 */
	public void move(Point point, float distance, Router router);

	/**
	 * Move continuous on the specified distance.
	 * @param point
	 * @param distance
	 */
	public void move(Continuous continuous, float distance, Router router);

	/**
	 * Remove continuous from lengthy.
	 * @param c1
	 */
	public void removeContinuous(Continuous continuous);

	/**
	 * Retrieve ordered list of points placed on this lengthy.
	 * @return
	 */
	public List<Point> getPoints();
	
	/**
	 * Retrieve ordered list of regular points placed on this lengthy.
	 * @return
	 */
	public List<Point> getRegularPoints();

}