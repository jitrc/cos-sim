/**
 * 
 */
package ru.cos.sim.driver;

import java.util.List;

/**
 * Route in the road network.<br>
 * Route is presented as list of ids of sequential links of the road.
 * @author zroslaw
 */
public class RoadRoute {
	
	private int currentLinkIndex = 0;
	private List<Integer> links;

	public List<Integer> getLinks() {
		return links;
	}

	public void setLinks(List<Integer> links) {
		this.links = links;
	}

	public int getCurrentLinkId() {
		return links.size() > currentLinkIndex ? links.get(currentLinkIndex) : -1;
	}
	
	public int getNextLinkId() {
		return links.size() > currentLinkIndex + 1 ? links.get(currentLinkIndex + 1) : -1;
	}
	
	public void next() {
		currentLinkIndex++;
	}
	
	@Deprecated
	public int getLink(int linkIndex) {
		return links.get(linkIndex);
	}
	
	@Override
	public String toString() {
		return getLinks().toString();
	}
}
