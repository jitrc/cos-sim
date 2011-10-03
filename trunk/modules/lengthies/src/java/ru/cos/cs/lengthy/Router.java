/**
 * 
 */
package ru.cos.cs.lengthy;

/**
 * Object that choose route on lengthies network.
 * @author zroslaw
 */
public interface Router {

	/**
	 * Choose route's forward lengthy on the join
	 * @param join join instance to choose prev lengthy
	 * @return prev lengthy in the route
	 */
	public Lengthy chooseNextLengthy(Join join);
	
	/**
	 * Choose route's backward lengthy on the fork
	 * @param fork fork instance to choose next lengthy 
	 * @return next lengthy in the route
	 */
	public Lengthy chooseNextLengthy(Fork fork);
	
}
