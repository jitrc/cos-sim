package ru.cos.sim.visualizer.traffic.information;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.JXTreeTable;

import ru.cos.sim.communication.dto.StatisticsData;
import ru.cos.sim.visualizer.traffic.core.ConditionController;
import ru.cos.sim.visualizer.traffic.core.ConditionManager;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.core.States;
import ru.cos.sim.visualizer.traffic.core.ConditionManager.Action;

public class StatisticHandler {

	protected Timer timer;
	protected Repeater repeater;
	protected SimulationController controller;
	protected UniverseSimpleModel model;
	
	public StatisticHandler(UniverseSimpleModel model){
		this.model = model;

		this.repeater = new Repeater();
		timer = new Timer(500, this.repeater);
		timer.setRepeats(true);
		this.controller = new SimulationController(Action.RunningStateChanged);
		ConditionManager.getInstance().addController(controller);
	}
	
	class Repeater implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			StatisticsData data = SimulationSystemManager.getInstance().getStatistics();
			if (data != null) model.update(data);
			
		}
		
	}
	
	class SimulationController extends ConditionController
	{

		
		public SimulationController() {
			super();
			// TODO Auto-generated constructor stub
		}

		public SimulationController(Action actionType) {
			super(actionType);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(String action) {
			if (action == States.STARTED) {
				timer.start();
				return;
			}
			if (action == States.FINISHED){
				timer.stop();
				return;
			}
			if (action == States.PAUSED) {
				timer.stop();
				return;
			}
			
		}
		
	}
	
}
