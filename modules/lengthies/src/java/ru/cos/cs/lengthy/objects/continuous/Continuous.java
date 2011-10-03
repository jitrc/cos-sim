package ru.cos.cs.lengthy.objects.continuous;

import java.util.List;

import ru.cos.cs.lengthy.Lengthy;

/**
 * 
 * @param <L> type of lengthies on which this continuous can be placed on
 * @author zroslaw
 */
public interface Continuous{

	public float length();

	public ContinuousPoint getBackPoint();

	public ContinuousPoint getFrontPoint();
	
	/**
	 * Returns list of occupied lengthies starting from the back point's lengthy 
	 * and finishing with front point lengthy.
	 * @return list of occupied lengthies
	 */
	public List<Lengthy> getOccupiedLengthies();

}