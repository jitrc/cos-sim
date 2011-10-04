package ru.cos.sim.visualizer.traffic.gui;

import java.awt.Component;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import ru.cos.sim.communication.dto.FrameData;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.communication.dto.StatisticsData;
import ru.cos.sim.visualizer.trace.TraceHandler;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.graphs.report.SimulationReportGUIFactory;
import ru.cos.sim.visualizer.traffic.graphs.report.TrafficSimulationReport;
import ru.cos.sim.visualizer.traffic.graphs.report.TrafficSimulationReport.TrafficSimulationReportBuilder;
import ru.cos.sim.visualizer.traffic.simulation.VisualController;

public class ExportHandler {

	private JFrame parent;
	private Component component;
	private Preferences pref;
	
	private String message = "";
	
	public ExportHandler(JFrame frame)
	{
		this.parent = frame;
		this.pref = Preferences.systemNodeForPackage(this.getClass());
	}
	
	public ExportHandler(JFrame frame,Component c)
	{
		this.parent = frame;
		this.component = c;
		this.pref = Preferences.systemNodeForPackage(this.getClass());
	}
	
	public void transmitManagement(ExportData data) {
		AskDialog dialog = new AskDialog(this.parent, true);
		dialog.pack();
		dialog.setVisible(true);
		if (dialog.getValue()) {
			JFileChooser chooser = new JFileChooser(pref.get("reportDirectory", System.getProperty("user.home")));
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			int value = chooser.showDialog(this.parent, "Save");
			if (value == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				pref.put("reportDirectory",file.getAbsolutePath());
			}
		}
	}
	
	protected void showReport()
	{
		VisualController visualController = VisualController.getInstance();
		visualController.requestFrameData();
		visualController.processResponseMessages();
		FrameData frameData = visualController.getFrameData();
		StatisticsData data =  frameData.getStatisticsData();
		long simtime = SimulationSystemManager.getInstance().getTotalSimulationTime();
		TrafficSimulationReport report;
		TrafficSimulationReportBuilder builder = new TrafficSimulationReport.
		TrafficSimulationReportBuilder("Traffic Simulation Report")
		.modelName(SimulationSystemManager.getInstance().getCurrentFile().getName())
		.date(new Date(System.currentTimeMillis()))
		.virtualTime(data.getUniverseTime())
		.realTime(simtime/1000.0f)
		.totalVehiclesNumber(0)
		.totalTravelTime(data.getTotalTime())
		.averageTravelSpeed(0);
		
		TraceHandler handler = SimulationSystemManager.getInstance().getTraceHandler();
		
		for (Meter meter : handler.getMetersManager().getMetersList()) {
			VisualController controller = VisualController.getInstance();
			controller.requestMeterData(meter.id());
			controller.processResponseMessages();
			MeterDTO mdto = controller.getMeterData(meter.id());
			builder.addMeter(mdto);
		}
		
		report = builder.build();
		SimulationReportGUIFactory.newInstance(report);
	}
	
	public void showMessage(String m){
		/*MessageDialog dialog;
		if (component == null ) {
			dialog = new MessageDialog(message,this.parent, true);
		} else {
			dialog = new MessageDialog(message,this.parent,component, true);
		}
		
		dialog.pack();
		dialog.setVisible(true);*/
		this.message = m;
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               final AskDialog dialog = new AskDialog(parent, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                       
                    }
                });
                dialog.setVisible(true);
                if (dialog.getValue()) showReport();
            }
        });
		
	}

}
