package ru.cos.nissan.graphs.report;

import javax.swing.*;

public class SimulationReportGUIFactory {
    public static JFrame newInstance(TrafficSimulationReport report){
        return new SimulationReportGUI(report).getFrame();
    }
}
