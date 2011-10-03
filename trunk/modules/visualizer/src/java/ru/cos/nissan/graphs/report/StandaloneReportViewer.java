package ru.cos.nissan.graphs.report;

import ru.cos.nissan.graphs.export.XMLImportExport;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class StandaloneReportViewer {
    public static void main(String... args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        if(args.length > 0){
            for(String arg : args){
//                System.out.println(arg);
                File f = new File(arg);
                if(f.exists()){
                    TrafficSimulationReport report = XMLImportExport.importReport(f);
                    SimulationReportGUIFactory.newInstance(report);
                }
            }
        }
        else{
            TrafficSimulationReport report = new TrafficSimulationReport
                    .TrafficSimulationReportBuilder("")
                    .modelName("")
                    .build();

            SimulationReportGUIFactory.newInstance(report);
        }

        System.gc();
    }
}
