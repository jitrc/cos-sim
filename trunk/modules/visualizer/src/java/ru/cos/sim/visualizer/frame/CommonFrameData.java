package ru.cos.sim.visualizer.frame;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for containing data about visible objects on the screen.
 * Use double-linked list for data storage. Use  <code>changeToInVisible</code>
 * and <code>changeToVisible</code> to add or remove object from list; 
 * List has Head element which id is -1. Its previous field is null.
 * 
 * @author Dudinov Ivan
 */
public class CommonFrameData {

	/**
	 * Head element;
	 */
	protected ViewableObjectInformation list;
	/**
	 * Last element
	 */
	protected ViewableObjectInformation last;
	
	/**
	 * @deprecated temporary data
	 */
	protected Set<Integer> segarray;

	
	/**
	 * Simple constructor. Initialize list.
	 * Just creates head element.
	 */
	public CommonFrameData() {
		super();
		list = new ViewableObjectInformation(-1);
		list.isHead = true;
		last = list;
	}

	/**
	 * Adds element to the list, where are visible elements.
	 * @param info - element to be added
	 */
	public void changeToVisible(ViewableObjectInformation info)
	{
		if (info.visible == true) return;
		this.last.next = info;
		info.previous = this.last;
		info.next = null;
		this.last = info;
		info.visible = true;
		//System.out.println("\n+Segment "+ info.uid.toString()+ " appeared");
		//System.out.println("Segment added : "+info.uid);
	}
	
	/**
	 * Remove element to the list, where are visible elements.
	 * @param info - element to be deleted
	 */
	public void changeToInVisible(ViewableObjectInformation info)
	{
		if (info.visible == false) return;
		if (last == info) last = info.previous;
		info.previous.next = info.next;
		if (info.next != null) info.next.previous = info.previous;
		info.previous = null;
		info.next = null;
		info.visible = false;
		//System.out.println("\n+Segment "+ info.uid.toString()+ " disappeared");
		//System.out.println("Segment removed : "+info.uid);
	}
	
	/**
	 * Head element is not a real element, it is only start element.
	 * It is always in the list.
	 * 
	 * @return Head element of the list
	 */
	public ViewableObjectInformation getHead() {
		return list;
	}

	/**
	 * 
	 * @return last element of the list
	 */
	public ViewableObjectInformation getLast() {
		return last;
	}
	
	/**
	 * Temporary function due to protocol differences. 
	 * Now it is deprecated and will be removed in next version.
	 * @deprecated
	 */
	public void update()
	{
		ViewableObjectInformation current = list.next;
		segarray = new HashSet<Integer>();
		while(current != null)
		{
			segarray.add(current.uid);
			current = current.next;
		}
	}	
}
