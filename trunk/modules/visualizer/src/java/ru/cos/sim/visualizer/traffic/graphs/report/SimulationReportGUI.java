package ru.cos.sim.visualizer.traffic.graphs.report;

import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.visualizer.trace.item.Meter;
import ru.cos.sim.visualizer.traffic.graphs.converter.ConverterFactory;
import ru.cos.sim.visualizer.traffic.graphs.converter.DataConverter;
import ru.cos.sim.visualizer.traffic.graphs.export.XMLImportExport;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class SimulationReportGUI{
    private final ReportDataManager manager;
    private final TrafficSimulationReport report;
    private JFrame frame;

    private JLabel nameLabel;
    private JLabel modelNameLabel;
    private JLabel reportDateLabel;
    private JLabel realTimeLabel;
    private JLabel virtualTimeLabel;
    private JLabel timeCoefficientLabel;
    private JLabel numberOfVehiclesLabel;
    private JLabel totalTravelTimeLabel;
    private JLabel averageTravelTimeLabel;
    private JPanel reportPanel;
    private JPanel metersPanel;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JTextField nameValue;
    private JTextField modelNameValue;
    private JTextField reportDateValue;
    private JTextField realTimeValue;
    private JTextField virtualTimeValue;
    private JTextField timeCoefficient;
    private JTextField numberOfVehiclesValue;
    private JTextField totalTravelTimeValue;
    private JTextField averageTravelTimeValue;

    private Preferences preferences;

    public JFrame getFrame(){
        return frame;
    }

    public SimulationReportGUI(TrafficSimulationReport report) {
        manager = new ReportDataManager(report);
        this.report = report;
        preferences = Preferences.userNodeForPackage(this.getClass());

        frame = new JFrame();
        initComponents();
        initTextFields(report);
        frame.setTitle("Traffic Simulation Report Viewer");
        frame.setSize(new Dimension(1080, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        reportPanel = new JPanel();
        nameLabel = new JLabel();
        modelNameLabel = new JLabel();
        reportDateLabel = new JLabel();
        realTimeLabel = new JLabel();
        virtualTimeLabel = new JLabel();
        timeCoefficientLabel = new JLabel();
        numberOfVehiclesLabel = new JLabel();
        totalTravelTimeLabel = new JLabel();
        averageTravelTimeLabel = new JLabel();
        nameValue = new JTextField();
        modelNameValue = new JTextField();
        reportDateValue = new JTextField();
        realTimeValue = new JTextField();
        virtualTimeValue = new JTextField();
        timeCoefficient = new JTextField();
        numberOfVehiclesValue = new JTextField();
        totalTravelTimeValue = new JTextField();
        averageTravelTimeValue = new JTextField();
        metersPanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        jPanel3 = new JPanel();

        initReportPanel();
        initMetersPanel();
        initMenuBar();

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reportPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(metersPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(reportPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(metersPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        frame.pack();
    }

    private void initMetersPanel() {
        metersPanel.setBorder(BorderFactory.createTitledBorder("Meters"));

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);

        GroupLayout.ParallelGroup hGroup = jPanel3Layout.createParallelGroup();
        GroupLayout.SequentialGroup vGroup = jPanel3Layout.createSequentialGroup();


        for(MeterDTO dto : report.getMeterList()){
            JPanel p = getMeterEntry(dto);
            hGroup.addComponent(p);
            vGroup.addComponent(p);
        }

        jPanel3Layout.setHorizontalGroup(hGroup);
        jPanel3Layout.setVerticalGroup(vGroup);

        jScrollPane1.setViewportView(jPanel3);
        jScrollPane1.setPreferredSize(new Dimension(231, 345));
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        GroupLayout metersPanelLayout = new GroupLayout(metersPanel);
        metersPanel.setLayout(metersPanelLayout);
        metersPanelLayout.setHorizontalGroup(
            metersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(metersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap()
            )
        );
        metersPanelLayout.setVerticalGroup(
            metersPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(metersPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addContainerGap()
            )
        );

    }

    private void initReportPanel() {
        reportPanel.setBorder(BorderFactory.createTitledBorder("Report"));

        nameLabel.setText("Name:");
        modelNameLabel.setText("Model name:");
        reportDateLabel.setText("Report date:");
        realTimeLabel.setText("Simulation time:");
        virtualTimeLabel.setText("Simulated time:");
        timeCoefficientLabel.setText("Time scale:");
        numberOfVehiclesLabel.setText("Number of vehicles:");
        totalTravelTimeLabel.setText("Total travel time:");
        averageTravelTimeLabel.setText("Average travel time:");

        nameValue.setEditable(false);
        nameValue.setCaretPosition(0);
        modelNameValue.setEditable(false);
        modelNameValue.setCaretPosition(0);
        reportDateValue.setEditable(false);
        reportDateValue.setCaretPosition(0);
        realTimeValue.setEditable(false);
        realTimeValue.setCaretPosition(0);
        virtualTimeValue.setEditable(false);
        virtualTimeValue.setCaretPosition(0);
        timeCoefficient.setEditable(false);
        timeCoefficient.setCaretPosition(0);
        numberOfVehiclesValue.setEditable(false);
        numberOfVehiclesValue.setCaretPosition(0);
        totalTravelTimeValue.setEditable(false);
        totalTravelTimeValue.setCaretPosition(0);
        averageTravelTimeValue.setEditable(false);
        averageTravelTimeValue.setCaretPosition(0);

        GroupLayout reportPanelLayout = new GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setHorizontalGroup(
            reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, reportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(modelNameLabel)
                    .addComponent(reportDateLabel)
                    .addComponent(realTimeLabel)
                    .addComponent(virtualTimeLabel)
                    .addComponent(timeCoefficientLabel)
                    .addComponent(numberOfVehiclesLabel)
                    .addComponent(totalTravelTimeLabel)
                    .addComponent(averageTravelTimeLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(averageTravelTimeValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalTravelTimeValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberOfVehiclesValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeCoefficient, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(virtualTimeValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(realTimeValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(reportDateValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(modelNameValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        reportPanelLayout.setVerticalGroup(
            reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(modelNameLabel)
                    .addComponent(modelNameValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(reportDateLabel)
                    .addComponent(reportDateValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(realTimeLabel)
                    .addComponent(realTimeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(virtualTimeLabel)
                    .addComponent(virtualTimeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(timeCoefficientLabel)
                    .addComponent(timeCoefficient, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfVehiclesLabel)
                    .addComponent(numberOfVehiclesValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(totalTravelTimeLabel)
                    .addComponent(totalTravelTimeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(averageTravelTimeLabel)
                    .addComponent(averageTravelTimeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }

    private void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");

        JMenuItem openItem = new JMenuItem();
        openItem.setText("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.VK_CONTROL));
        openItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openReportFile();
            }
        });
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem();
        saveItem.setText("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL));
        saveItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReportFile();
            }
        });
        fileMenu.add(saveItem);

        menuBar.add(fileMenu);

    }

    private void saveReportFile(){
        JFileChooser fileChooser = new JFileChooser(preferences.get("simulationResults", System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Simulation Report","tsr");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        String modelName = report.getModelName();
        if(modelName.endsWith(".mdf") || modelName.endsWith(".MDF"))
            modelName = modelName.substring(0, modelName.length() - 4);

        modelName = modelName + ".tsr";

        fileChooser.setSelectedFile(new File(modelName));
        int value = fileChooser.showDialog(frame, "Save");
        if(value == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();

            String filePath = file.getAbsolutePath();
            if(!filePath.endsWith(".tsr") && !filePath.endsWith(".TSR"))
                filePath = filePath + ".tsr"; 

            File newFile = new File(filePath);
            try {
                if(!newFile.exists()){
                    preferences.put("simulationResults", newFile.getParentFile().getAbsolutePath());
                    if(!newFile.createNewFile())
                        throw new IOException("Unable to create file: " + newFile.getAbsolutePath());
                }
                XMLImportExport.exportReport(report, newFile);
                JOptionPane.showMessageDialog(frame, "File successfully saved");
            } catch (IOException e){
                JOptionPane.showMessageDialog(frame, "Unable to save a file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openReportFile(){
        JFileChooser fileChooser = new JFileChooser(preferences.get("simulationResults", System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Simulation Report","tsr");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

        int value = fileChooser.showDialog(frame, "Open");
        if(value == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            preferences.put("simulationResults", file.getParentFile().getAbsolutePath());
            try {
                TrafficSimulationReport report = XMLImportExport.importReport(file);
                new SimulationReportGUI(report).getFrame();
                this.frame.dispose();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Unable to open a file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addGraph(Meter meter){
        manager.addGraph(meter);
    }

    private JPanel getMeterEntry(final MeterDTO dto){
        JPanel meterPanel = new JPanel();
        meterPanel.setBorder(new TitledBorder("Meter"));

        final JLabel typeLabel = new JLabel("Meter type:");
        final JLabel nameLabel = new JLabel("Meter name:");
        final JLabel locationLabel = new JLabel("Meter location:");
        final JLabel instantValueLabel = new JLabel("Instant value:");
        final JLabel averageValueLabel = new JLabel("Average value:");

        final JTextField typeValue = new JTextField("");
        typeValue.setEditable(false);
        final JTextField nameValue = new JTextField("");
        nameValue.setEditable(false);
        final JTextField locationValue = new JTextField("");
        locationValue.setEditable(false);
        final JTextField instantValue = new JTextField("");
        instantValue.setEditable(false);
        final JTextField averageValue = new JTextField("");
        averageValue.setEditable(false);

        if(dto.getName() != null) nameValue.setText(dto.getName());
        if(dto.getType() != null) typeValue.setText(dto.getType().toString());
        DataConverter converter = ConverterFactory.getConverter(dto);
        if(dto.getInstantMeasuredData() != null) instantValue.setText(converter.buildValueString(dto.getInstantMeasuredData()));
        if(dto.getAverageData() != null) averageValue.setText(converter.buildValueString(dto.getAverageData()));

        typeValue.setCaretPosition(0);
        nameValue.setCaretPosition(0);
        locationValue.setCaretPosition(0);
        instantValue.setCaretPosition(0);
        averageValue.setCaretPosition(0);

        meterPanel.setMaximumSize(new Dimension(650, 220));
//        meterPanel.setLayout(new BoxLayout(meterPanel, BoxLayout.X_AXIS));

        JPanel meterInfoPanel = new JPanel();
        meterInfoPanel.setMaximumSize(new Dimension(250, 220));

        GroupLayout meterInfoLayout = new GroupLayout(meterInfoPanel);
        meterInfoPanel.setLayout(meterInfoLayout);
        meterInfoLayout.setHorizontalGroup(
               meterInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, meterInfoLayout.createSequentialGroup()
                       .addContainerGap()
                       .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(typeLabel)
                            .addComponent(nameLabel)
                            .addComponent(locationLabel)
                            .addComponent(instantValueLabel)
                            .addComponent(averageValueLabel))
                       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                       .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(averageValue, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                            .addComponent(instantValue, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                           .addComponent(locationValue, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                           .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                           .addComponent(typeValue, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                       .addContainerGap())
        );
        meterInfoLayout.setVerticalGroup(
                meterInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(meterInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(typeLabel)
                            .addComponent(typeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel)
                            .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(locationLabel)
                            .addComponent(locationValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(instantValueLabel)
                            .addComponent(instantValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(meterInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(averageValueLabel)
                            .addComponent(averageValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(19, Short.MAX_VALUE))
        );

        JPanel picturePanel = new JPanel();
        picturePanel.setMaximumSize(new Dimension(400, 220));
        JLabel picLabel = new JLabel();
        BufferedImage image = ThumbnailFactory.getThumbnail(dto);
        ImageIcon icon = new ImageIcon(image);
        picLabel.setIcon(icon);
        picturePanel.add(picLabel);
        picLabel.setToolTipText("click to get meter information");
        picLabel.setBorder(new EtchedBorder(1));
        picLabel.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                Meter m = new Meter(dto.getName(), dto.getId(), dto.getType());
                addGraph(m);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        GroupLayout meterPanelLayout = new GroupLayout(meterPanel);
        meterPanel.setLayout(meterPanelLayout);
        meterPanelLayout.setHorizontalGroup(
                meterPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(meterPanelLayout.createSequentialGroup()
                        .addComponent(meterInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(picturePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        )
        );
        meterPanelLayout.setVerticalGroup(
                meterPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(meterPanelLayout.createSequentialGroup()
                        .addGroup(meterPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(meterInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(picturePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        )
        );
        return meterPanel;
    }

    private void initTextFields(TrafficSimulationReport report){
        if (report == null) return;

        if(report.getName() != null)
            nameValue.setText(report.getName());

        if(report.getModelName() != null)
            modelNameValue.setText(report.getModelName());

        if(report.getDate() != null)
            reportDateValue.setText(report.getDate().toString());

        realTimeValue.setText(Float.toString(report.getRealTime()));
        virtualTimeValue.setText(Float.toString(report.getVirtualTime()));
        timeCoefficient.setText(Float.toString(report.getTimeCoefficient()));
        numberOfVehiclesValue.setText(Float.toString(report.getTotalVehiclesNumber()));
        totalTravelTimeValue.setText(Float.toString(report.getTotalTravelTime()));
        averageTravelTimeValue.setText(Float.toString(report.getAverageTravelTime()));
    }

}
