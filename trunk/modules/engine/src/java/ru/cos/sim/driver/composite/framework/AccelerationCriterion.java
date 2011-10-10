/**
 * 
 */
package ru.cos.sim.driver.composite.framework;

/**
 * 
 * @author zroslaw
 */
public class AccelerationCriterion implements Criterion {

	private float acceleration;
	
	public AccelerationCriterion(float acceleration) {
		this.acceleration = acceleration;
	}

	@Override
	public int compareTo(Criterion criterion) {
		if (criterion.getCriterionType()!=CriterionType.Acceleration)
			throw new CompositeDriverFrameworkException("Unable to compare " +
					"acceleration criterion to criterion of "+criterion.getCriterionType()+" type.");
		AccelerationCriterion accelerationCriterion = (AccelerationCriterion)criterion;
		return Float.compare(acceleration,accelerationCriterion.getAcceleration());
	}

	@Override
	public final CriterionType getCriterionType() {
		return CriterionType.Acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getAcceleration() {
		return acceleration;
	}

	@Override
	public AccelerationCriterion clone() {
		try {
			return (AccelerationCriterion)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CompositeDriverFrameworkException(e);
		}
	}

}
