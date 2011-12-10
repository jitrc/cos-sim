/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.cos.sim.driver.RoadRoute;

/**
 * Route in the road network.<br>
 * Route is presented as list of ids of sequential links of the road.
 * @author zroslaw
 */
public class RoadRouteImpl implements RoadRoute{
	
	private ListIterator<Integer> linkIterator;
	private Integer currentLinkId;
	private List<Integer> linkIdList;

	public RoadRouteImpl(List<Integer> linkIdList) {
		this.linkIdList = linkIdList;
		linkIterator = linkIdList.listIterator();
		if(linkIterator.hasNext()){
			currentLinkId = linkIterator.next();
		}
	}

	public void setCurrentLinkId(Integer linkId) {
		if( currentLinkId == linkId){
			return;
		}
		if( !linkIterator.hasNext() ){
			return;
			//throw new Exception();
		}
		currentLinkId = linkIterator.next();
		if( currentLinkId == linkId){
			return;
		}
	}
	
	public int getNextLinkId() {
		return linkIterator.hasNext()? linkIterator.: -1;
	}
	
	@Override
	public String toString() {
		return linkIdList.toString();
	}
}
