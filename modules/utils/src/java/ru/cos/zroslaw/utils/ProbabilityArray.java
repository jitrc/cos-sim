/**
 * 
 */
package ru.cos.zroslaw.utils;

import java.util.List;
import java.util.Random;

/**
 * ProbabilityArray allows to have access to an arbitrary element with predefined probability.
 * Probability is defined by means of weight of the element.
 * Probability of getting element with weight Integer equals to Integer/Summ(Integeri), where Summ(Integeri) is 
 * the sum of weight all elements in the array 
 * @author zroslaw
 */
// TODO Implement this class using the binary search tree
public class ProbabilityArray<E> {
	
	protected Random random = new Random();
	
	protected int summ;
	
	protected List<Pair<E,Integer>> list;

	public ProbabilityArray(List<Pair<E,Integer>> list){
		this.list = list;
		for (Pair<E,Integer> pair:list){
			summ+=pair.getSecond().intValue();
		}
	}
	
	public E getArbitraryElement(){
		double distance = random.nextDouble()*summ;
		for(Pair<E,Integer> pair:list){
			int weight = pair.getSecond().intValue();
			if (distance<weight){
				return pair.getFirst();
			}
			distance-=weight;
		}
		throw new RuntimeException("Unexpexted error in probability array");
	}
}
