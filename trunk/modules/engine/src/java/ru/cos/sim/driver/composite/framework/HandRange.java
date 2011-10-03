/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import java.util.HashSet;
import java.util.Set;

import ru.cos.sim.utils.Hand;

import static ru.cos.sim.utils.Hand.*;

/**
 * Range of Hand values.
 * @see Hand
 * @author zroslaw
 */
public class HandRange implements Cloneable{

	// range of turn command
	protected Set<Hand> turnRange = new HashSet<Hand>();
	
	public HandRange(){
		turnRange.add(Right);
		turnRange.add(null);
		turnRange.add(Left);
	}
	
	public HandRange intersection(HandRange handRange){
		HandRange result = new HandRange();
		
		Set<Hand> set = new HashSet<Hand>();
		if (this.contains(Hand.Left) && handRange.contains(Left))
			set.add(Left);
		if (this.contains(Hand.Right) && handRange.contains(Right))
			set.add(Right);
		if (this.contains(null) && handRange.contains(null))
			set.add(null);
		
		result.setTurnRange(set);
		
		return result;
	}
	
	public boolean isEmpty(){
		return turnRange.size()==0;
	}

	public Set<Hand> getTurnRange() {
		return turnRange;
	}

	public void setTurnRange(Set<Hand> turnRange) {
		this.turnRange = turnRange;
	}

	@Override
	public HandRange clone() {
		HandRange result;
		try {
			result = (HandRange) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Exception in HandRange..");
		}
		
		Set<Hand> hands = new HashSet<Hand>();
		hands.addAll(this.turnRange);
		result.setTurnRange(hands);
		
		return result;
	}
	
	public boolean contains(Hand turn){
		return turnRange.contains(turn);
	}

	@Override
	public boolean equals(Object handRangeObj) {
		if (!(handRangeObj instanceof HandRange)) return false;
		HandRange handRange = (HandRange)handRangeObj;
		for (Hand hand:turnRange){
			if (!handRange.contains(hand)) return false;
		}
		for (Hand hand:handRange.turnRange){
			if (!contains(hand)) return false;
		}
		return true;
	}

	public void remove(Hand hand) {
		turnRange.remove(hand);
	}

	/**
	 * Setting the only one hand in the range,
	 * all other will be not in the range.
	 * @param hand hand to set theonly one in the range
	 */
	public void setOneHand(Hand turnHand) {
		HashSet<Hand> handRange = new HashSet<Hand>();
		handRange.add(turnHand);
		this.turnRange = handRange;
	}
}
