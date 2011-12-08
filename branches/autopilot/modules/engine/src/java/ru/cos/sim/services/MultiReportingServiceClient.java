package ru.cos.sim.services;

import ru.cos.sim.vehicle.Vehicle;

public class MultiReportingServiceClient implements ReportingServiceClient {
	public MultiReportingServiceClient(Iterable<ReportingServiceClient> clients) {
		this.clients = clients;
	}
	
	private final Iterable<ReportingServiceClient> clients;
	
	@Override
	public void reportParameters(Vehicle vehicle) {
		for (ReportingServiceClient client : clients)
			client.reportParameters(vehicle);
	}
	
}
