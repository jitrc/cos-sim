package ru.cos.sim.services;

import ru.cos.sim.engine.TrafficModelDefinition;
import ru.cos.sim.ras.duo.services.DuoReportingServiceClient;
import ru.cos.sim.ras.duo.services.DuoRouteServiceClient;
import ru.cos.sim.ras.duo.services.DuoSolutionService;
import ru.cos.sim.services.ServiceLocator;

public class RasDuoServicesBootstrapper {
	
	public static void bootstrap(ServiceLocator serviceLocator, TrafficModelDefinition modelDefinition) {
		final DuoSolutionService solutionService = new DuoSolutionService(modelDefinition); 

		serviceLocator.setUpdatingRouteServiceClientFactory(new ClientFactory<UpdatingRouteServiceClient>() {
			@Override
			public UpdatingRouteServiceClient createClient() {
				return new DuoRouteServiceClient(solutionService);
			}
		});
		
		serviceLocator.addReportingServiceClientFactory(new ClientFactory<ReportingServiceClient>() {
			@Override
			public ReportingServiceClient createClient() {
				return new DuoReportingServiceClient(solutionService);
			}
		});
	}
}
