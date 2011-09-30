/**
 * 
 */
package ru.cos.sim.meters.impl;

import java.util.ArrayList;

import ru.cos.cs.lengthy.Lengthy;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.road.link.Segment;
import ru.cos.sim.utils.Hand;
import ru.cos.sim.utils.Pair;

/**
 * Observe vehicles on certain area of road link.
 * Provide information about them.
 * @author zroslaw
 */
public class LinkDetector extends VehiclesDetector{

	
	public LinkDetector(Segment segment, float position){
		/**
		 * Main purpose is to initialize list lengthiesAndPositions
		 */
		lengthiesAndPositions = new ArrayList<Pair<Lengthy,Float>>(segment.getLanes().length);
		for(Lane lane:segment.getLanes()){
			lengthiesAndPositions.add(new Pair<Lengthy, Float>(lane, position));
			if (lane.getIndex()==segment.getLanes().length-1) break;
			position = segment.calculateAdjacentPosition(lane.getIndex(), position, Hand.Right);
		}
	}
	
	
}
