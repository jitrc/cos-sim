/**
 * 
 */
package ru.cos.sim.communication.dto;

/**
 * Abstract data transfer object.
 * Data transfer objects used to transfer data about objects of the model from simulation thread to client thread.
 * @author zroslaw
 */
public abstract class AbstractDTO {

	/**
	 * Data transfer object types
	 * @see AbstractDTO
	 * @author zroslaw
	 */
	public enum DTOType {
			VehicleDTO,
			TrafficLightDTO, 
			MeterDTO,
			MeterShortDTO
	}

	/**
	 * @return the dTOType
	 */
	public abstract DTOType getDynamicObjectType();
	
}
