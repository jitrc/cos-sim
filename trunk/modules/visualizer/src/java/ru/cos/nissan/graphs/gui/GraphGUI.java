package ru.cos.nissan.graphs.gui;

import ru.cos.nissan.graphs.classes.GraphData;
import ru.cos.nissan.graphs.classes.IntervalData;
import ru.cos.nissan.graphs.handler.DataHandler;
import ru.cos.nissan.graphs.IGraph;
import ru.cos.nissan.graphs.AbstractGraph;
import ru.cos.nissan.graphs.listener.ChartEventNotifier;
import ru.cos.nissan.graphs.listener.CrosshairChangedEvent;
import ru.cos.nissan.graphs.export.CSVImportExport;
import ru.cos.nissan.graphs.export.XMLImportExport;
import ru.cos.nissan.graphs.impl.IDataRequestable;
import ru.cos.nissan.graphs.converter.DataConverter;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.TimePeriod;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import java.util.prefs.Preferences;
import java.io.File;
import java.io.IOException;

public class GraphGUI<GD extends GraphData, MD extends MeasuredData<MD>>
        implements IGraphGUI<MD>, MouseListener{
    private JFrame frame;
    private DataHandler dataHandler;
    private IGraph<GD, MD> graph;
    private DataConverter<GD, MD> dataConverter;
    private MeterDTO<MD> meterDTO;

    private ButtonGroup graphTypeGroup;
    private JButton exportToCSVButton;
    private JButton exportToXMLButton;
    private JButton refreshButton;
    private JLabel meterTypeLabel;
    private JLabel meterNameLabel;
    private JLabel meterLocationLabel;
    private JLabel instantValueLabel;
    private JLabel averageValueLabel;
    private JPanel mainPanel;
    private JPanel scheduledHistoryDataPanel;
    private JPanel graphPanel;
    private JPanel meterInformationPanel;
    private JPanel meterDataPanel;
    private JPanel graphTypePanel;
    private JPanel exportPanel;
    private JPanel leftPanel;
    private JPanel refreshPanel;
    private JPanel scheduledDataPanel;
    private JPanel scheduledAverageDataPanel;
    private JRadioButton historyRadioButton;
    private JRadioButton scheduledHistoryRadioButton;
    private JScrollPane scheduledAverageTableScroll;
    private JScrollPane scheduledHistoryScroll;
    private JTable scheduledAverageTable;
    private JTable scheduledHistoryTable;
    private JTextField meterTypeValue;
    private JTextField meterNameValue;
    private JTextField meterLocationValue;
    private JTextField instantValue;
    private JTextField averageValue;

    private JPanel averagePanel;
    private JLabel startValueLabel;
    private JLabel endValueLabel;
    private JLabel integratedValueLabel;
    private JTextField startValue;
    private JTextField endValue;
    private JTextField integratedValue;
    private JButton integrateButton;

    private Preferences preferences;

    private class CrosshairEventListener implements ChartEventNotifier<CrosshairChangedEvent> {
        @Override
        public void update(CrosshairChangedEvent event) {
            GD g = dataConverter.convertHistory(meterDTO);
            float average = graph.getAverageValue(g, event.getInterval());

            startValue.setText(Float.toString(event.getInterval().getPeriodStart()));
            endValue.setText(Float.toString(event.getInterval().getPeriodEnd()));
            integratedValue.setText(Float.toString(average));
        }
    }

    private final String[] scheduledAverageColumnNames = {"Start Time", "End Time", "Value"};
    private final String[] scheduledHistoryColumnNames = {"Start Time", "End Time"};

    public JFrame getFrame(){
        return frame;
    }

    public GraphGUI(IGraph<GD, MD> graph, IDataRequestable<MD> iDataRequestable) {
        dataHandler = new DataHandler<MD>(iDataRequestable, this);
        this.graph = graph;
        this.graph.setCrosshairEventNotifier(new CrosshairEventListener());
        this.dataConverter = graph.getConverter();

        preferences = Preferences.userNodeForPackage(this.getClass());

        frame = new JFrame();
        frame.setTitle(graph.getGraphName());
        initComponents();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        dataHandler.sendRequest(DataHandler.RequestAction.History);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        graphTypeGroup = new ButtonGroup();
        mainPanel = new JPanel();
        scheduledDataPanel = new JPanel();
        scheduledAverageDataPanel = new JPanel();
        scheduledAverageTableScroll = new JScrollPane();
        scheduledAverageTable = new JTable();
        scheduledHistoryDataPanel = new JPanel();
        scheduledHistoryScroll = new JScrollPane();
        scheduledHistoryTable = new JTable();
        graphPanel = new JPanel();
        leftPanel = new JPanel();
        meterInformationPanel = new JPanel();
        meterTypeLabel = new JLabel();
        meterNameLabel = new JLabel();
        meterLocationLabel = new JLabel();
        meterTypeValue = new JTextField();
        meterNameValue = new JTextField();
        meterLocationValue = new JTextField();
        meterDataPanel = new JPanel();
        instantValueLabel = new JLabel();
        averageValueLabel = new JLabel();
        instantValue = new JTextField();
        averageValue = new JTextField();
        graphTypePanel = new JPanel();
        historyRadioButton = new JRadioButton();
        scheduledHistoryRadioButton = new JRadioButton();
        exportPanel = new JPanel();
        exportToCSVButton = new JButton();
        exportToXMLButton = new JButton();
        refreshPanel = new JPanel();
        refreshButton = new JButton();

        averagePanel = new JPanel();
        startValueLabel = new JLabel();
        startValue = new JTextField();
        endValueLabel = new JLabel();
        endValue = new JTextField();
        integratedValueLabel = new JLabel();
        integratedValue = new JTextField();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initScheduledDataPanel();
        initGraphPanel();
        initMainPanel();
        initMeterInformationPanel();
        initMeterDataPanel();
        initGraphTypePanel();
        initExportPanel();
        initRefreshPanel();
        initLeftPanel();
        initAveragePanel();

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

    private void initLeftPanel() {
        GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(refreshPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(meterDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(averagePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(graphTypePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(meterInformationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            )
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(meterInformationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(meterDataPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(averagePanel,  GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(graphTypePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(exportPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(refreshPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            )
        );
    }

    private void initMainPanel() {
        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(graphPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scheduledDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            )
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scheduledDataPanel, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
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

    private void initGraphTypePanel() {
        graphTypePanel.setBorder(BorderFactory.createTitledBorder("Graph Type"));

        graphTypeGroup.add(historyRadioButton);
        historyRadioButton.setText("history graph");
        historyRadioButton.setSelected(true);
        historyRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                historyRadioButtonActionPerformed();
            }
        });

        graphTypeGroup.add(scheduledHistoryRadioButton);
        scheduledHistoryRadioButton.setText("scheduled history graph");
        scheduledHistoryRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                scheduledHistoryRadioButtonActionPerformed();
            }
        });

        GroupLayout graphTypePanelLayout = new GroupLayout(graphTypePanel);
        graphTypePanel.setLayout(graphTypePanelLayout);
        graphTypePanelLayout.setHorizontalGroup(
            graphTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(graphTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(historyRadioButton)
                .addGap(20, 20, 20)
                .addComponent(scheduledHistoryRadioButton)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        graphTypePanelLayout.setVerticalGroup(
            graphTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(graphTypePanelLayout.createSequentialGroup()
                .addGroup(graphTypePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(scheduledHistoryRadioButton)
                    .addComponent(historyRadioButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void initMeterDataPanel() {
        meterDataPanel.setBorder(BorderFactory.createTitledBorder("Meter Data"));

        instantValueLabel.setText("Instant value:");
        averageValueLabel.setText("Average value:");

        instantValue.setEditable(false);
        averageValue.setEditable(false);

        GroupLayout meterDataPanelLayout = new GroupLayout(meterDataPanel);
        meterDataPanel.setLayout(meterDataPanelLayout);
        meterDataPanelLayout.setHorizontalGroup(
            meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(meterDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(instantValueLabel)
                    .addComponent(averageValueLabel))
                .addGap(18, 18, 18)
                .addGroup(meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(instantValue, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(averageValue, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        meterDataPanelLayout.setVerticalGroup(
            meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(meterDataPanelLayout.createSequentialGroup()
                .addGroup(meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(instantValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(instantValueLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(meterDataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(averageValueLabel)
                    .addComponent(averageValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void initScheduledDataPanel() {
        scheduledDataPanel.setBorder(BorderFactory.createTitledBorder("Scheduled Data"));

        initScheduledAveragePanel();
        initScheduledHistoryPanel();

        GroupLayout scheduledDataPanelLayout = new GroupLayout(scheduledDataPanel);
        scheduledDataPanel.setLayout(scheduledDataPanelLayout);
        scheduledDataPanelLayout.setHorizontalGroup(
            scheduledDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledDataPanelLayout.createSequentialGroup()
                .addComponent(scheduledAverageDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scheduledHistoryDataPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );
        scheduledDataPanelLayout.setVerticalGroup(
            scheduledDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledDataPanelLayout.createSequentialGroup()
                .addGroup(scheduledDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scheduledAverageDataPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(scheduledHistoryDataPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            )
        );
    }

    private void initScheduledHistoryPanel() {
        scheduledHistoryDataPanel.setBorder(BorderFactory.createTitledBorder("Scheduled History Data"));

        scheduledHistoryTable.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            scheduledHistoryColumnNames
        ));
        scheduledHistoryScroll.setViewportView(scheduledHistoryTable);
        scheduledHistoryScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scheduledHistoryTable.setVisible(false);
        scheduledHistoryTable.addMouseListener(this);

        GroupLayout scheduledHistoryDataPanelLayout = new GroupLayout(scheduledHistoryDataPanel);
        scheduledHistoryDataPanel.setLayout(scheduledHistoryDataPanelLayout);
        scheduledHistoryDataPanelLayout.setHorizontalGroup(
            scheduledHistoryDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledHistoryDataPanelLayout.createSequentialGroup()
                .addComponent(scheduledHistoryScroll, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
            )
        );
        scheduledHistoryDataPanelLayout.setVerticalGroup(
            scheduledHistoryDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledHistoryDataPanelLayout.createSequentialGroup()
                .addComponent(scheduledHistoryScroll, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void initScheduledAveragePanel() {
        scheduledAverageDataPanel.setBorder(BorderFactory.createTitledBorder("Scheduled Average Data"));

        scheduledAverageTable.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            scheduledAverageColumnNames
        ));
        scheduledAverageTableScroll.setViewportView(scheduledAverageTable);

        GroupLayout scheduledAverageDataPanelLayout = new GroupLayout(scheduledAverageDataPanel);
        scheduledAverageDataPanel.setLayout(scheduledAverageDataPanelLayout);
        scheduledAverageDataPanelLayout.setHorizontalGroup(
            scheduledAverageDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledAverageDataPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scheduledAverageTableScroll, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        scheduledAverageDataPanelLayout.setVerticalGroup(
            scheduledAverageDataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(scheduledAverageDataPanelLayout.createSequentialGroup()
                .addComponent(scheduledAverageTableScroll, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void initAveragePanel() {
        averagePanel.setBorder(BorderFactory.createTitledBorder("Average Data"));

        startValueLabel.setText("Start value:");
        endValueLabel.setText("End value:");
        integratedValueLabel.setText("Average value:");

//        startValue.setEditable(false);
//        endValue.setEditable(false);
        integratedValue.setEditable(false);

        integrateButton = new JButton("Get Average");
        integrateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Float start;
                Float end;
                try{
                     start = Float.parseFloat(startValue.getText());
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "Wrong number format: couldn't parse entered value: \"" + startValue.getText() + "\"",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    startValue.setText("");
                    return;
                }
                try{
                     end = Float.parseFloat(endValue.getText());
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "Wrong number format: couldn't parse entered value: \"" + endValue.getText() + "\"",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    endValue.setText("");
                    return;
                }

                IntervalData d = new IntervalData();
                d.setPeriodStart(start);
                d.setPeriodEnd(end);
                
                GD g = dataConverter.convertHistory(meterDTO);
                float average = graph.getAverageValue(g, d);

//                startValue.setText(Float.toString(event.getInterval().getPeriodStart()));
//                endValue.setText(Float.toString(event.getInterval().getPeriodEnd()));
                integratedValue.setText(Float.toString(average));
            }
        });

        GroupLayout averagePanelLayout = new GroupLayout(averagePanel);
        averagePanel.setLayout(averagePanelLayout);
        averagePanelLayout.setHorizontalGroup(
            averagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(averagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(startValueLabel)
                    .addComponent(endValueLabel)
                    .addComponent(integratedValueLabel)
                    )
                .addGap(18, 18, 18)
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(startValue, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(endValue, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(integratedValue, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(integrateButton, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                .addContainerGap())
        );
        averagePanelLayout.setVerticalGroup(
            averagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(averagePanelLayout.createSequentialGroup()
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startValueLabel)
                    .addComponent(startValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(endValueLabel)
                    .addComponent(endValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(integratedValueLabel)
                    .addComponent(integratedValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(averagePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(integrateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }


    private void historyRadioButtonActionPerformed() {
//            JOptionPane.showMessageDialog(this, "radio changed to history");
        refreshHistory(meterDTO);
        clearScheduledHistoryTable();
        scheduledHistoryTable.setVisible(false);
    }

    private void scheduledHistoryRadioButtonActionPerformed(){
//            JOptionPane.showMessageDialog(this, "radio changed to scheduled history");
        refreshScheduledHistory(meterDTO);
        scheduledHistoryTable.setVisible(true);
    }

    private void refreshButtonActionPerformed(){
        if(historyRadioButton.isSelected()){
//                JOptionPane.showMessageDialog(this, "refreshing history");
            dataHandler.sendRequest(DataHandler.RequestAction.History);
        }
        else if(scheduledHistoryRadioButton.isSelected()){
//                JOptionPane.showMessageDialog(this, "refreshing scheduled history");
            dataHandler.sendRequest(DataHandler.RequestAction.ScheduledHistory);
        }
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

    private void exportToCSVButtonActionPerformed(){
//        System.out.println(preferences.get("simulationResults", System.getProperty("user.home")));
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
                if(historyRadioButton.isSelected()){
                    new CSVImportExport().exportToFile(dataConverter.getHistoryData(meterDTO), newFile);
                }
                else if(scheduledHistoryRadioButton.isSelected()){
                    int row = scheduledHistoryTable.getSelectedRow();
                    if(row != -1){
                        TimePeriod period = getScheduledTimePeriod(row);
                        float[][] data = dataConverter.getScheduledHistoryDataByPeriod(meterDTO, period);  
                        new CSVImportExport().exportToFileColumns(data, period, newFile);
                    }
                    else{
                        List<TimePeriod> periods = dataConverter.getScheduledHistoryPeriods(meterDTO);
                        List<float[][]> data = dataConverter.getScheduledHistoryData(meterDTO);
                        new CSVImportExport().exportToFileColumns(data, periods, newFile);
                    }
                }
                JOptionPane.showMessageDialog(frame, "File successfully saved");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Unable to save a file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    @Override
    public long getId() {
        return graph.getId();
    }

    @Override
    public void refreshHistory(MeterDTO<MD> meterDTO) {
        this.meterDTO = meterDTO;
        GD graphData = graph.refreshHistory(meterDTO);
        refreshInfo(meterDTO);

        refreshScheduledTable(graphData);
//        refreshScheduledHistoryTable(meterDTO);
    }

    @Override
    public void refreshScheduledHistory(MeterDTO<MD> meterDTO) {
        this.meterDTO = meterDTO;
        GD graphData = graph.refreshScheduledHistory(meterDTO);
        refreshInfo(meterDTO);
        refreshScheduledTable(graphData);
        refreshScheduledHistoryTable(meterDTO);
    }

    public void refreshScheduledHistoryByPeriod(TimePeriod timePeriod) {
        graph.refreshScheduledHistoryByPeriod(meterDTO, timePeriod);
    }

    @Override
    public void refreshInstant(MeterDTO<MD> meterDTO) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == scheduledHistoryTable){
            int row = scheduledHistoryTable.rowAtPoint(e.getPoint());
            refreshScheduledHistoryByPeriod(getScheduledTimePeriod(row));
        }
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


    private void refreshInfo(MeterDTO<MD> meterDTO){
        this.meterDTO = meterDTO;

        meterTypeValue.setText(meterDTO.getType().toString());
        meterTypeValue.setCaretPosition(0);
        meterNameValue.setText(meterDTO.getName());
        meterNameValue.setCaretPosition(0);

        if(meterDTO.getInstantMeasuredData() != null)
            instantValue.setText(dataConverter.buildValueString(meterDTO.getInstantMeasuredData()));
        if(meterDTO.getAverageData() != null)
            averageValue.setText(dataConverter.buildValueString(meterDTO.getAverageData()));
    }

    private void refreshScheduledTable(GD graphData){
        int scheduledSize = graphData.getIntervalCollection().size();
        Object[][] data = new Object[scheduledSize][3];
        for(int i = 0; i < scheduledSize; i++){
            IntervalData intervalData = graphData.getIntervalCollection().get(i);
            data[i][0] = intervalData.getPeriodStart();
            data[i][1] = intervalData.getPeriodEnd();
            data[i][2] = intervalData.getValue();
        }
        DefaultTableModel model = new DefaultTableModel(data, scheduledAverageColumnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        scheduledAverageTable.setModel(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        sorter.setComparator(0, new Comparator<Float>(){
            @Override
            public int compare(Float o1, Float o2){
                return Float.compare(o1, o2);
            }
        });
        scheduledAverageTable.setRowSorter(sorter);
        scheduledAverageTable.getRowSorter().toggleSortOrder(0);
    }

    private void refreshScheduledHistoryTable(MeterDTO<MD> dto){
        java.util.List<TimePeriod> periods = dataConverter.getScheduledHistoryPeriods(dto);
        int scheduledSize = periods.size();
        Object[][] data = new Object[scheduledSize][2];
        for(int i = 0; i< scheduledSize; i++){
            data[i][0] = periods.get(i).getTimeFrom();
            data[i][1] = periods.get(i).getTimeTo();
        }
        DefaultTableModel model = new DefaultTableModel(data, scheduledHistoryColumnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        scheduledHistoryTable.setModel(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        sorter.setComparator(0, new Comparator<Float>(){
            @Override
            public int compare(Float o1, Float o2) {
                return Float.compare(o1, o2);
            }
        });
        scheduledHistoryTable.setRowSorter(sorter);
        scheduledHistoryTable.getRowSorter().toggleSortOrder(0);
    }

    private TimePeriod getScheduledTimePeriod(int row){
        float periodStart = (Float)scheduledHistoryTable.getValueAt(row, 0);
        float periodEnd = (Float)scheduledHistoryTable.getValueAt(row, 1);
        return new TimePeriod(periodStart, periodEnd);
    }

    private void clearScheduledHistoryTable(){
        DefaultTableModel dm = (DefaultTableModel)scheduledHistoryTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
    }
}
