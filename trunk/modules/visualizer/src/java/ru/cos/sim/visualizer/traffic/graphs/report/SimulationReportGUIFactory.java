package ru.cos.sim.visualizer.traffic.graphs.report;

import javax.swing.*;

public class SimulationReportGUIFactory {
    public static JFrame newInstance(TrafficSimulationReport report){
        return new SimulationReportGUI(report).getFrame();
    }
}
