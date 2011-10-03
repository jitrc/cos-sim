package ru.cos.sim.ras.duo.services;

import ru.cos.sim.driver.route.RouteProviderFactory;
import ru.cos.sim.ras.duo.msg.ReportPositionRequest;
import ru.cos.sim.ras.duo.utils.Extendable;
import ru.cos.sim.ras.duo.utils.ExtensionCollection;
import ru.cos.sim.road.link.Lane;
import ru.cos.sim.services.ReportingServiceClient;
import ru.cos.sim.services.ServiceLocator;
import ru.cos.sim.vehicle.Vehicle;

public class DuoReportingServiceClient implements ReportingServiceClient {
	public DuoReportingServiceClient(DuoSolutionService solutionService) {
		this.solutionService = solutionService;
		this.updatePeriod = RouteProviderFactory.UPDATE_PERIOD;
	}
	
	private final float updatePeriod;
	private final DuoSolutionService solutionService;
	
	private Vehicle vehicle;
	private Extendable vehicleData = new Extendable() {
		private final ExtensionCollection extensions = new ExtensionCollection();
		
		@Override
		public ExtensionCollection getExtensions() {
			return extensions;
		}
	};
	
	protected void validateVehicle(Vehicle vehicle) {
		if (this.vehicle != null && this.vehicle != vehicle)
			throw new RuntimeException(getClass() + " can be used with one vehicle only");
		this.vehicle = vehicle;
	}
	
	private float lastUpdateTime = 0;
	
	protected float getCurrentTime() {
		return ServiceLocator.getInstance().getTimeSerivce().getAbsoluteTime();
	}
	
	protected boolean isUpdateTime() {
		return getCurrentTime() - lastUpdateTime > updatePeriod;
	}
	
	@Override
	public void reportParameters(Vehicle vehicle) {
		validateVehicle(vehicle);
	
		if (isUpdateTime() && vehicle.getLengthy() instanceof Lane) {
			int vehicleId = vehicle.getAgentId();
			int linkId = ((Lane)vehicle.getLengthy()).getLink().getId();
			float position = vehicle.getPosition();
			float speed = vehicle.getSpeed();
			
			solutionService.getMessageBus().reportPosition(new ReportPositionRequest(vehicleId, linkId, position, speed, vehicleData));
			
			lastUpdateTime = getCurrentTime();
		}
	}
	
	
}
