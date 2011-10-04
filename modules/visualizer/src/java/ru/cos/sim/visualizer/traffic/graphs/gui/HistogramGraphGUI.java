package ru.cos.sim.visualizer.traffic.graphs.gui;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.impl.data.VehicleAppearanceHeadwayHistogram;
import ru.cos.sim.visualizer.traffic.graphs.AbstractGraph;
import ru.cos.sim.visualizer.traffic.graphs.IGraph;
import ru.cos.sim.visualizer.traffic.graphs.classes.HistogramData;
import ru.cos.sim.visualizer.traffic.graphs.converter.DataConverter;
import ru.cos.sim.visualizer.traffic.graphs.export.CSVImportExport;
import ru.cos.sim.visualizer.traffic.graphs.export.XMLImportExport;
import ru.cos.sim.visualizer.traffic.graphs.handler.DataHandler;
import ru.cos.sim.visualizer.traffic.graphs.impl.IDataRequestable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class HistogramGraphGUI implements IGraphGUI<VehicleAppearanceHeadwayHistogram>{
    private JFrame frame;
    private DataHandler dataHandler;
    private IGraph<HistogramData, VehicleAppearanceHeadwayHistogram> graph;
    private DataConverter<HistogramData, VehicleAppearanceHeadwayHistogram> dataConverter;
    private MeterDTO<VehicleAppearanceHeadwayHistogram> meterDTO;

    private JButton exportToCSVButton;
    private JButton exportToXMLButton;
    private JButton refreshButton;
    private JPanel graphPanel;
    private JLabel meterTypeLabel;
    private JLabel meterNameLabel;
    private JLabel meterLocationLabel;
    private JTextField meterTypeValue;
    private JTextField meterNameValue;
    private JTextField meterLocationValue;
    private JPanel mainPanel;
    private JPanel meterInformationPanel;
    private JPanel exportPanel;
    private JPanel leftPanel;
    private JPanel refreshPanel;

    private Preferences preferences;

    public JFrame getFrame(){
        return frame;
    }

    public HistogramGraphGUI(IGraph<HistogramData, VehicleAppearanceHeadwayHistogram> graph,
                             IDataRequestable<VehicleAppearanceHeadwayHistogram> iDataRequestable) {
        dataHandler = new DataHandler<VehicleAppearanceHeadwayHistogram>(iDataRequestable, this);
        this.graph = graph;
        this.dataConverter = graph.getConverter();

        preferences = Preferences.userNodeForPackage(this.getClass());

        frame = new JFrame();
        frame.setTitle(graph.getGraphName());
        initComponents();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dataHandler.sendRequest(DataHandler.RequestAction.InstantData);
    }

    private void refreshInfo(MeterDTO<VehicleAppearanceHeadwayHistogram> meterDTO) {
        meterTypeValue.setText(meterDTO.getType().toString());
        meterTypeValue.setCaretPosition(0);
        meterNameValue.setText(meterDTO.getName());
        meterNameValue.setCaretPosition(0);
    }

    private void initComponents(){
        meterTypeLabel = new JLabel();
        meterNameLabel = new JLabel();
        meterLocationLabel = new JLabel();
        meterTypeValue = new JTextField();
        meterNameValue = new JTextField();
        meterLocationValue = new JTextField();
        mainPanel = new JPanel();
        graphPanel = new JPanel();
        leftPanel = new JPanel();
        meterInformationPanel = new JPanel();
        exportPanel = new JPanel();
        exportToCSVButton = new JButton();
        exportToXMLButton = new JButton();
        refreshPanel = new JPanel();
        refreshButton = new JButton();

        initGraphPanel();
        initMainPanel();
        initMeterInformationPanel();
        initExportPanel();
        initRefreshPanel();
        initLeftPanel();

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        frame.pack();
    }

    @Override
    public long getId() {
        return graph.getId();
    }

    @Override
    public void refreshHistory(MeterDTO<VehicleAppearanceHeadwayHistogram> dto) {
    }

    @Override
    public void refreshScheduledHistory(MeterDTO<VehicleAppearanceHeadwayHistogram> dto) {
    }

    @Override
    public void refreshInstant(MeterDTO<VehicleAppearanceHeadwayHistogram> dto) {
        this.meterDTO = dto;
        graph.refreshInstant(dto);
        refreshInfo(dto);
    }

    private void initMainPanel() {
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(graphPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        ));
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGap(18, 18, 18))
        );
    }

    private void initGraphPanel() {
        graphPanel.setBorder(BorderFactory.createTitledBorder("History Graph"));

        GroupLayout graphPanelLayout = new GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);

        JPanel chartPanel = ((AbstractGraph)graph).getChartPanel();
        graphPanel.add(chartPanel);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(graphPanelLayout.createSequentialGroup()
                        .addComponent(chartPanel, GroupLayout.DEFAULT_SIZE, graphPanel.getWidth(), Short.MAX_VALUE)
                    )
                .addGap(0, 384, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(graphPanelLayout.createSequentialGroup()
                            .addComponent(chartPanel, GroupLayout.DEFAULT_SIZE, graphPanel.getHeight(), Short.MAX_VALUE)
                    )
            .addGap(0, 213, Short.MAX_VALUE)
        );
    }

    private void initLeftPanel() {
        GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(refreshPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(meterInformationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            )
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(meterInformationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(exportPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(refreshPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            )
        );
    }

    private void initMeterInformationPanel() {
        meterInformationPanel.setBorder(BorderFactory.createTitledBorder("Meter Information"));

        meterTypeLabel.setText("Meter type:");
        meterNameLabel.setText("Meter name:");
        meterLocationLabel.setText("Meter location:");

        meterTypeValue.setEditable(false);
        meterNameValue.setEditable(false);
        meterLocationValue.setEditable(false);

        GroupLayout meterInformationPanelLayout = new GroupLayout(meterInformationPanel);
        meterInformationPanel.setLayout(meterInformationPanelLayout);
        meterInformationPanelLayout.setHorizontalGroup(
            meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(meterInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(meterNameLabel)
                    .addComponent(meterTypeLabel)
                    .addComponent(meterLocationLabel))
                .addGap(18, 18, 18)
                .addGroup(meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(meterLocationValue, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(meterNameValue, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(meterTypeValue, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addContainerGap())
        );
        meterInformationPanelLayout.setVerticalGroup(
            meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(meterInformationPanelLayout.createSequentialGroup()
                .addGroup(meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(meterTypeLabel)
                    .addComponent(meterTypeValue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(meterNameLabel)
                    .addComponent(meterNameValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(meterInformationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(meterLocationLabel)
                    .addComponent(meterLocationValue, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void initExportPanel() {
        exportPanel.setBorder(BorderFactory.createTitledBorder("Export"));

        exportToCSVButton.setText("Export To CSV");
        exportToCSVButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCSVButtonActionPerformed();
            }
        });

        exportToXMLButton.setText("Export To XML");
        exportToXMLButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportToXMLButtonActionPerformed();
            }
        });

        GroupLayout exportPanelLayout = new GroupLayout(exportPanel);
        exportPanel.setLayout(exportPanelLayout);
        exportPanelLayout.setHorizontalGroup(
            exportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, exportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exportToCSVButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(exportToXMLButton)
                .addContainerGap())
        );
        exportPanelLayout.setVerticalGroup(
            exportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(exportPanelLayout.createSequentialGroup()
                .addGroup(exportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(exportToXMLButton)
                    .addComponent(exportToCSVButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void exportToXMLButtonActionPerformed() {
        JFileChooser fileChooser = new JFileChooser(preferences.get("simulationResults", System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.xml", "xml");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int value = fileChooser.showDialog(frame, "Save");
        if(value == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            String filePath = file.getAbsolutePath();
            if(!filePath.endsWith(".xml"))
                filePath = filePath + ".xml";

            File newFile = new File(filePath);
            try {
                if(!newFile.exists()){
                    preferences.put("simulationResults", newFile.getParentFile().getAbsolutePath());
                    if(!newFile.createNewFile())
                        throw new IOException("Unable to create file: " + newFile.getAbsolutePath());
                }
                XMLImportExport.exportMeterDTO(meterDTO, newFile);
                JOptionPane.showMessageDialog(frame, "File successfully saved");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Unable to save a file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportToCSVButtonActionPerformed() {
        JFileChooser fileChooser = new JFileChooser(preferences.get("simulationResults", System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int value = fileChooser.showDialog(frame, "Save");
        if(value == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            String filePath = file.getAbsolutePath();
            if(!filePath.endsWith(".csv"))
                filePath = filePath + ".csv";

            File newFile = new File(filePath);
            try{
                if(!newFile.exists()){
                    preferences.put("simulationResults", newFile.getParentFile().getAbsolutePath());
                    if(!newFile.createNewFile())
                        throw new IOException("Unable to create file: " + newFile.getAbsolutePath());
                }
                new CSVImportExport().exportToFileRow(dataConverter.getInstantData(meterDTO), newFile);
                JOptionPane.showMessageDialog(frame, "File successfully saved");
            } catch (IOException e1){
                JOptionPane.showMessageDialog(frame, "Unable to save a file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void initRefreshPanel() {
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshButtonActionPerformed();
            }
        });

        GroupLayout refreshPanelLayout = new GroupLayout(refreshPanel);
        refreshPanel.setLayout(refreshPanelLayout);
        refreshPanelLayout.setHorizontalGroup(
            refreshPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, refreshPanelLayout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(refreshButton)
                .addGap(101, 101, 101))
        );
        refreshPanelLayout.setVerticalGroup(
            refreshPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(refreshButton)
        );
    }

    private void refreshButtonActionPerformed() {
        dataHandler.sendRequest(DataHandler.RequestAction.InstantData);
    }
}
