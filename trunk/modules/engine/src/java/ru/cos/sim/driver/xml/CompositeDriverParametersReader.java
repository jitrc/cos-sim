/**
 * 
 */
package ru.cos.sim.driver.xml;


import org.jdom.Element;

import ru.cos.sim.driver.composite.CompositeDriverParameters;
import ru.cos.sim.mdf.MDFReader;


/**
 * @author zroslaw
 *
 */
public class CompositeDriverParametersReader {

	public static final String MAX_SPEED = "maxSpeed";
	public static final String MAX_ACCELERATION = "maxAcceleration";
	public static final String COMFORT_DECELERATION = "comfortDeceleration";
	public static final String MIN_DISTANCE = "minDistance";
	public static final String DESIRED_HEADWAY_TIME = "desiredHeadwayTime";
	public static final String ABRUPTNESS = "abruptness";
	public static final String POLITENESS = "politeness";
	public static final String ACC_THRESHOLD = "accThreshold";
	public static final String INTERSECTION_GAP_TIME = "intersectionGapTime";

	public static CompositeDriverParameters read(Element parametersElement) {
		CompositeDriverParameters parameters = new CompositeDriverParameters();
		
		Element maxSpeedElement = parametersElement.getChild(MAX_SPEED, MDFReader.MDF_NAMESPACE);
		parameters.setMaxSpeed(Float.parseFloat(maxSpeedElement.getText()));

		Element maxAccElement = parametersElement.getChild(MAX_ACCELERATION, MDFReader.MDF_NAMESPACE);
		parameters.setMaxAcceleration(Float.parseFloat(maxAccElement.getText()));
		
		Element comfortDecelerationElement = parametersElement.getChild(COMFORT_DECELERATION, MDFReader.MDF_NAMESPACE);
		parameters.setComfortDeceleration(Float.parseFloat(comfortDecelerationElement.getText()));

		Element minDistanceElement = parametersElement.getChild(MIN_DISTANCE, MDFReader.MDF_NAMESPACE);
		parameters.setMinDistance(Float.parseFloat(minDistanceElement.getText()));
		
		Element desiredHeadwayTimeElement = parametersElement.getChild(DESIRED_HEADWAY_TIME, MDFReader.MDF_NAMESPACE);
		parameters.setDesiredHeadwayTime(Float.parseFloat(desiredHeadwayTimeElement.getText()));
		
		Element abruptnessElement = parametersElement.getChild(ABRUPTNESS, MDFReader.MDF_NAMESPACE);
		parameters.setAbruptness(Float.parseFloat(abruptnessElement.getText()));
		
		Element politenessElement = parametersElement.getChild(POLITENESS, MDFReader.MDF_NAMESPACE);
		parameters.setPoliteness(Float.parseFloat(politenessElement.getText()));

		Element accThresholdElement = parametersElement.getChild(ACC_THRESHOLD, MDFReader.MDF_NAMESPACE);
		parameters.setAccThreshold(Float.parseFloat(accThresholdElement.getText()));
		
		Element intersectionGapTimeElement = parametersElement.getChild(INTERSECTION_GAP_TIME, MDFReader.MDF_NAMESPACE);
		parameters.setIntersectionGapTime(Float.parseFloat(intersectionGapTimeElement.getText()));
		
		return parameters;
	}

}
