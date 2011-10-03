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
	
	public ServiceLocator(TrafficModelDefinition modelDefinition, RoadNetwork roadNetwork, ModelParameters modelParameters, Clock timeProvider) {
		DijkstraRouteService routeService = new DijkstraRouteService(roadNetwork); 
		this.initialRouteService = routeService;
		this.updatingRouteService = new ClientFactory.Static<UpdatingRouteServiceClient>(routeService);
		this.randomizationService = new RandomizationService.Default(modelParameters.getRandomSeed());
		this.timeService = new TimeService.Default(timeProvider);
		
		try {
			Class.forName("ru.cos.sim.services.RasDuoServicesBootstrapper")
				 .getMethod("bootstrap", ServiceLocator.class, TrafficModelDefinition.class)
				 .invoke(null, this, modelDefinition);
		} catch (Exception e) {
			System.out.println("WARNING: Could not bootstrap DUO Route Assignment Services: " + e.getMessage());
		}
	}
	
	private static ServiceLocator instance;
	public static ServiceLocator getInstance(){
		if (instance == null)
			throw new RuntimeException("Service locator instance has not been set");
		return instance;
	}
	public static void setInstance(ServiceLocator serviceLocator) {
		instance = serviceLocator;
	}
	
	private InitialRouteService initialRouteService;
	public InitialRouteService getInitialRouteService() {
		return initialRouteService;
	}
	protected void setInitialRouteService(InitialRouteService service) {
		this.initialRouteService = service;
	}
	
	private ClientFactory<UpdatingRouteServiceClient> updatingRouteService;
	public UpdatingRouteServiceClient createUpdatingRouteServiceClient() {
		return updatingRouteService.createClient();
	}
	protected void setUpdatingRouteServiceClientFactory(ClientFactory<UpdatingRouteServiceClient> clientFactory) {
		this.updatingRouteService = clientFactory;
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
	
	private final RandomizationService randomizationService;
	public RandomizationService getRandomizationService() {
		return randomizationService;
	}
	
	private final TimeService timeService;
	public TimeService getTimeSerivce() {
		return timeService;
	}
}
