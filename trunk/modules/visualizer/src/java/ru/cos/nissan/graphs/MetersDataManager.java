package ru.cos.nissan.graphs;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


import ru.cos.nissan.graphs.gui.GraphGUIFactory;
import ru.cos.nissan.graphs.impl.IDataRequestable;
import ru.cos.nissan.graphs.impl.IRequest;
import ru.cos.nissan.simulation.VisualController;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.trace.item.Meter;

/**
 * @author Dudinov Ivan
 *
 * Grants methods for interactions between Graph Engine and Simulation Engine.
 * SingleTon. Use method <code>getInstance()</code> to operate 
 * with this class.
 */
public class MetersDataManager implements IDataRequestable {
	
	
	protected ArrayList<Meter> meters;
	protected ArrayList<IRequest> requests;
	protected Timer timer;
	
	public MetersDataManager()
	{
		this.requests = new ArrayList<IRequest>();
		this.meters = new ArrayList<Meter>();
	}
	
	
	public void addMeter(Meter m)
	{
		this.meters.add(m);
	}
	
	public ArrayList<Meter> getMeters()
	{
		return meters;
	}
	
	public ArrayList<Meter> getMetersList()
	{
		return meters;
	}

	public void addGraph(Meter m)
	{
//		JFrame frame = new GraphGUI(TrafficVolumeMeterGraph.getInstance(m.id), this);
        JFrame frame = GraphGUIFactory.newInstance(m, this);
//		frame.setTitle(m.name);
	}
	
	@Override
	public void requestData(IRequest r) {
		this.requests.add(r);
		VisualController.getInstance().requestMeterData((int)r.getId());
	}

	@Override
	public void checkRequests() {		
		for (int i=0 ; i < this.requests.size();){
			IRequest r = this.requests.get(i);
			MeterDTO result = VisualController.getInstance().getMeterData((int)r.getId());
			if (result != null) {
				r.receiveData(result);
				this.requests.remove(i);
			} else i++;
		}
		
	}
	
	/*public void handlePick(Geometry mesh) {
		if (mesh.getName() == CumulativeTrafficVolumeMeter.meshName) {
			CumulativeTrafficVolumeMeterShape shape = (CumulativeTrafficVolumeMeterShape) mesh;
			this.addGraph(shape.getMeter());
		}
	}*/
	
	
}
