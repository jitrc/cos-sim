package ru.cos.sim.services;

import java.util.ArrayList;
import java.util.List;

import ru.cos.sim.engine.Clock;
import ru.cos.sim.engine.TrafficModelDefinition;
import ru.cos.sim.parameters.ModelParameters;
import ru.cos.sim.road.RoadNetwork;

/**
 * Router service.
 * @author zroslaw
 */
public class ServiceLocator {
	private static ServiceLocator instance;
	
	private RouteService routeService;
	private RandomizationService randomizationService;
	private TimeService timeService;
	
	private ServiceLocator(){
	}
	
	public static void init(TrafficModelDefinition modelDefinition, RoadNetwork roadNetwork, ModelParameters modelParameters, Clock timeProvider) {
		instance = new ServiceLocator();
		
		instance.routeService = new DijkstraRouteService(roadNetwork); 
		instance.randomizationService = new RandomizationService.Default(modelParameters.getRandomSeed());
		instance.timeService = new TimeService.Default(timeProvider);
		
		//TODO inappropriate solution
//		try {
//			Class.forName("ru.cos.sim.services.RasDuoServicesBootstrapper")
//				 .getMethod("bootstrap", ServiceLocator.class, TrafficModelDefinition.class)
//				 .invoke(null, this, modelDefinition);
//		} catch (Exception e) {
//			System.out.println("WARNING: Could not bootstrap DUO Route Assignment Services: " + e.getMessage());
//		}
	}
	
	public static ServiceLocator getInstance(){
		if (instance == null)
			throw new RuntimeException("Service locator instance has not been set");
		return instance;
	}
	
	
	private List<ClientFactory<ReportingServiceClient>> reportingServices = 
		new ArrayList<ClientFactory<ReportingServiceClient>>();
	public ReportingServiceClient createReportingServiceClient() {
		List<ReportingServiceClient> clients = new ArrayList<ReportingServiceClient>();
		for (ClientFactory<ReportingServiceClient> clientFactory : reportingServices)
			clients.add(clientFactory.createClient());
		
		return new MultiReportingServiceClient(clients);
	}
	protected void addReportingServiceClientFactory(ClientFactory<ReportingServiceClient> clientFactory) {
		reportingServices.add(clientFactory);
	}
	
	public RandomizationService getRandomizationService() {
		return randomizationService;
	}
	
	public TimeService getTimeSerivce() {
		return timeService;
	}
	
	public RouteService getRouteService(){
		return routeService;
	}
}
