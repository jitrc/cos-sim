/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

import java.util.HashMap;
import java.util.Map;

import ru.cos.sim.driver.composite.framework.Criterion.CriterionType;

/**
 * Range of control command values
 * @author zroslaw
 */
public abstract class CCRange implements Cloneable{

	protected Map<CriterionType, Criterion> criteria = new HashMap<Criterion.CriterionType, Criterion>();

	public CCRange() {
		criteria.put(CriterionType.Priority, new PriorityCriterion(Priority.Lowest));
		criteria.put(CriterionType.Acceleration, new AccelerationCriterion(-Float.MAX_VALUE));
	}

	/**
	 * Intersect this control command range with another one
	 * @param ccRange ccRange to intersect with.
	 * @return intersection
	 */
	public abstract CCRange intersect(CCRange ccRange);

	/**
	 * Calculate resultant control command range for two source ranges
	 * @param ccRange1
	 * @param ccRange2
	 * @return resultant control command range
	 */
	public static CCRange calculateResultantRange(CCRange ccRange1, CCRange ccRange2){

		if (ccRange1==null) 
			if (ccRange2==null)
				throw new CompositeDriverFrameworkException("Unable to calculate resultant range for both null source ranges");
			else
				return ccRange2.clone();
		else if (ccRange2==null)
			return ccRange1.clone();
		
		CCRange resultantCCRange = ccRange1.intersect(ccRange2);
		if (resultantCCRange.isEmpty()){
			for (CriterionType criterionType:CriterionType.values()){
				Criterion criterion1 = ccRange1.getCriterion(criterionType);
				Criterion criterion2 = ccRange2.getCriterion(criterionType);
				int comparision = criterion1.compareTo(criterion2);
				if (comparision>0)
					return ccRange1.clone();
				else
					return ccRange2.clone();
			}
		}

		// for resultant ccRange set highest criteria
		for (CriterionType criterionType:CriterionType.values()){
			Criterion criterion1 = ccRange1.getCriterion(criterionType);
			Criterion criterion2 = ccRange2.getCriterion(criterionType);
			int comparision = criterion1.compareTo(criterion2);
			if (comparision>0)
				resultantCCRange.setCriterion(criterion1);
			else
				resultantCCRange.setCriterion(criterion2);
		}

		return resultantCCRange;
	}

	public void setCriterion(Criterion criterion) {
		criteria.put(criterion.getCriterionType(), criterion);
	}

	/**
	 * Check if control command range is empty
	 * @return true if range is empty, false otherwise
	 */
	public abstract boolean isEmpty();

	/**
	 * Get criterion of the control command range by type of the criterion.
	 * @param criterionType type of the criterion to be retrieved
	 * @return criterion of the ccRange of specified criterion type
	 */
	public Criterion getCriterion(CriterionType criterionType){
		return criteria.get(criterionType);
	}
	
	public void setPriority(Priority priority){
		((PriorityCriterion)criteria.get(CriterionType.Priority)).setPriority(priority);
	}

	@Override
	public CCRange clone(){
		try {
			CCRange result = (CCRange) super.clone();
			result.criteria = new HashMap<Criterion.CriterionType, Criterion>();
			for (Criterion criterion:criteria.values()){
				result.setCriterion(criterion.clone());
			}
			return result;
		} catch (CloneNotSupportedException e) {
			throw new CompositeDriverFrameworkException(e);
		}
	}

	/**
	 * Evaluate final control command on the base of the range.
	 * Method will choose one command among the range.
	 * @return
	 */
	public abstract ControlCommand controlCommand();

	public Priority getPriority() {
		return ((PriorityCriterion)criteria.get(CriterionType.Priority)).getPriority();
	}
}
