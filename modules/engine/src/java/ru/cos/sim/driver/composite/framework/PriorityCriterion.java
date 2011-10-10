/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

/**
 * 
 * @author zroslaw
 */
public class PriorityCriterion implements Criterion {
	
	private Priority priority;

	public PriorityCriterion(Priority priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Criterion criterion) {
		if (criterion.getCriterionType()!=CriterionType.Priority)
			throw new CompositeDriverFrameworkException("Unable to compare " +
					"priority criterion to criterion of "+criterion.getCriterionType()+" type.");
		PriorityCriterion priorityCriterion = (PriorityCriterion)criterion;
		return priority.compareTo(priorityCriterion.getPriority());
	}

	@Override
	public CriterionType getCriterionType() {
		return CriterionType.Priority;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	@Override
	public PriorityCriterion clone(){
		try {
			return (PriorityCriterion)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CompositeDriverFrameworkException(e);
		}
	}

}
