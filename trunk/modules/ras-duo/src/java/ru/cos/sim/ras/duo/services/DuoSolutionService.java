package ru.cos.sim.ras.duo.services;

import ru.cos.sim.engine.TrafficModelDefinition;
import ru.cos.sim.ras.duo.Configuration;
import ru.cos.sim.ras.duo.MessageBus;
import ru.cos.sim.ras.duo.MessageProcessor;
import ru.cos.sim.ras.duo.digraph.roadnet.RoadNetDigraph;

public class DuoSolutionService {

	public DuoSolutionService(TrafficModelDefinition modelDefinition) {
		Configuration config = Configuration.getInstance();
		RoadNetDigraph graph = new RoadNetDigraph(modelDefinition);
		MessageProcessor solution = config.getSolutionFactory().createSolution(graph, config.getDefaultParameters());
		messageBus = new MessageBus(solution);
		messageBus.start();
	}
	
	public void dispose() {
		messageBus.stop();
	}
	
	private MessageBus messageBus;
	public MessageBus getMessageBus() {
		return messageBus;
	}
}
