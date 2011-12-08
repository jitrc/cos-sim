package ru.cos.sim.ras.duo;

import ru.cos.sim.ras.duo.msg.FindRouteRequest;
import ru.cos.sim.ras.duo.msg.FindRouteResponse;
import ru.cos.sim.ras.duo.msg.ReportPositionRequest;
import ru.cos.sim.ras.duo.msg.ReportPositionResponse;

public interface MessageProcessor {

	public ReportPositionResponse reportPosition(float timestamp, ReportPositionRequest request);
	
	public FindRouteResponse findRoute(float timestamp, FindRouteRequest request);
	
}
