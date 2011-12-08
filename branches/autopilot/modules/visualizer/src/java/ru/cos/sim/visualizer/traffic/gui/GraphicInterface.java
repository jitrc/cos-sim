package ru.cos.sim.visualizer.traffic.gui;


import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.painter.effects.NeonBorderEffect.BorderPosition;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import ru.cos.sim.visualizer.canvas.LWJGLCanvas;
import ru.cos.sim.visualizer.renderer.Renderer;
import ru.cos.sim.visualizer.traffic.core.ConditionController;
import ru.cos.sim.visualizer.traffic.core.ConditionManager;
import ru.cos.sim.visualizer.traffic.core.SimulationSystemManager;
import ru.cos.sim.visualizer.traffic.core.States;
import ru.cos.sim.visualizer.traffic.core.SystemProperties;
import ru.cos.sim.visualizer.traffic.core.ConditionManager.Action;
import ru.cos.sim.visualizer.traffic.core.SystemProperties.Properties;
import ru.cos.sim.visualizer.traffic.core.SystemProperties.TrafficLightSignalsShow;
import ru.cos.sim.visualizer.traffic.gui.models.TableRenderer;
import ru.cos.sim.visualizer.traffic.information.CarInformatioArea;
import ru.cos.sim.visualizer.traffic.information.MetersList;
import ru.cos.sim.visualizer.traffic.information.UniverseSimpleModel;
import ru.cos.sim.visualizer.traffic.information.UniverseTabelModel;

public class GraphicInterface extends JFrame {
        private static final long serialVersionUID = 1L;

        private static final String defaultTitle = "COS.SIM";
        
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton openReportButton;
        private javax.swing.JMenu jMenu1;
        private javax.swing.JMenu jMenu2;
        private javax.swing.JMenuBar jMenuBar1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JToolBar.Separator jSeparator1;
        private javax.swing.JSlider jSlider1;
        private javax.swing.JSpinner jSpinner1;
        private javax.swing.JButton jToggleButton1;
        private javax.swing.JToolBar jToolBar1;
        private javax.swing.JToolBar jToolBar2;
        private javax.swing.JToolBar jToolBar3;
        private javax.swing.JToolBar jToolBar4;
        private CarInformatioArea carinformationHandler;
        
        private JTable jXTreeTable1;
        private UniverseSimpleModel model;
        private javax.swing.JSplitPane mainPanel;
        private javax.swing.JPanel toolPanel;
        private javax.swing.JToggleButton toolPanelButton;
        private ru.cos.sim.visualizer.traffic.gui.models.SpinnerModel spinnermodel;
        private MetersList metersList; 
        private javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        private javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        
        LWJGLCanvas canvas = null;
        private Preferences pref;      
        private double dividerLocation = -1;
        private javax.swing.ImageIcon playIcon = new javax.swing.ImageIcon(getClass().getResource("/icons/start_task.gif"));
        private javax.swing.ImageIcon pauseIcon = new javax.swing.ImageIcon(getClass().getResource("/icons/suspend_co.gif"));
        
        
        private int width = 200;
        private int height = 200;
        
//        protected UIStatisticHandler statsHandler;
//        protected Mode[] modes = {
//        	Mode.All, Mode.None,Mode.NoRed	
//        };
        
        public static JFrame currentFrame;
        // Construct the frame
        public GraphicInterface(int width , int height) {
        	this.width = width;
        	this.height = height;
        	this.pref = Preferences.userNodeForPackage(this.getClass());
        	URL app_icon_url = getClass().getResource("/icons/app_icon.png");
        	Image appim = Toolkit.getDefaultToolkit().getImage(app_icon_url);
        	this.setIconImage(appim);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            init();
            setExtendedState(this.MAXIMIZED_BOTH);
            pack();
            currentFrame = this;
        }

        // Component initialization
        private void init() {
        	Color defaultColor = this.getBackground(); //SystemColor.control;//UIManager.getColor("Button.background");
            //contentPane = (JPanel) this.getContentPane();
            //contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

            //mainPanel.setLayout(new GridBagLayout());
            setTitle(defaultTitle);

            // -------------GL STUFF------------------

            // make the canvas:
            
            //System.out.println(GLContext.getCapabilities().GL_ARB_multisample);

            //this.setE
            boolean multisamplingEnabled = testMultisampleSupport();
            
            try {
                if (multisamplingEnabled) canvas = new LWJGLCanvas(new PixelFormat(8, 0, 0, 4) ); else
                	canvas = new LWJGLCanvas();
                Renderer.getRenderer().setCanvas(canvas);
            } catch (Exception e) {
            	System.out.println("Antialiasing is not supported , switching to weak-antialiasing");
            	
            }
           // canvas.setDrawWhenDirty(true);
            this.setAlwaysOnTop(false);
            // add a listener... if window is resized, we can do something about
            // it.
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
            canvas.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent ce) {
                    doResize();
                }
            });
           
            canvas.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					//if (impl != null) impl.mclick(arg0.getX(), canvas.getHeight()-arg0.getY());
					
				}
			});
            
            
            // Setup key and mouse input
//            KeyInput.setProvider(KeyInput.INPUT_AWT);
//            KeyListener kl = (KeyListener) KeyInput.get();
//            canvas.addKeyListener(kl);
//            AWTMouseInput.setup(canvas, false);
//            ConditionManager.getInstance().addController(new SimulationController(Action.RunningStateChanged));
//            // Important! Here is where we add the guts to the panel:
//            ColorRGBA color = new ColorRGBA(defaultColor.getRed()/255.0f,
//            		defaultColor.getGreen()/255.0f, defaultColor.getBlue()/255.0f, defaultColor.getAlpha()/255.0f);
//            impl = new Canvas(width, height,color);
//            canvas.setImplementor(impl);
//            GUIExportHandler handler = new GUIExportHandler(this,canvas);
//            ResultsExporter.getInstance().setHandler(handler);

            // -----------END OF GL STUFF-------------
            ConditionManager.getInstance().addController(new SimulationController(Action.RunningStateChanged));
           this.initComponents();
            
           // NissanConsole nc = new NissanConsole(jEditorPane1);
           // NissanConsole.appendMessage("Console running");
           
        }
        
        protected void initComponents()
        {
        	SimulationSystemManager.getInstance().getSystemProperties().setCurrentFrame(this);
        	JMenuBar bar = new JMenuBar();
            this.initMenu(bar);
            this.setJMenuBar(bar);
            model = new UniverseSimpleModel();
            
            jToolBar1 = new javax.swing.JToolBar();
            jButton1 = new javax.swing.JButton();
            jToolBar2 = new javax.swing.JToolBar();
            jButton2 = new javax.swing.JButton();
            jButton3 = new javax.swing.JButton();
            jToolBar3 = new javax.swing.JToolBar();
            jButton4 = new javax.swing.JButton();
            jSeparator1 = new javax.swing.JToolBar.Separator();
            toolPanelButton = new javax.swing.JToggleButton();
            mainPanel = new javax.swing.JSplitPane();
            jPanel1 = new javax.swing.JPanel();
            toolPanel = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jSlider1 = new javax.swing.JSlider();
            openReportButton = new javax.swing.JButton();
            jScrollPane1 = new javax.swing.JScrollPane();
            jXTreeTable1 = new JTable(model);
            jToolBar4 = new javax.swing.JToolBar();
            jToggleButton1 = new javax.swing.JButton();
            jMenuBar1 = new javax.swing.JMenuBar();
            jMenu1 = new javax.swing.JMenu();
            jMenu2 = new javax.swing.JMenu();

            carinformationHandler = new CarInformatioArea();
            SimulationSystemManager.getInstance().informationHandler = carinformationHandler;
            
            for (int i = 0 ; i < jXTreeTable1.getColumnCount(); i++) {
            	jXTreeTable1.getColumnModel().getColumn(i).setCellRenderer( new DefaultTableCellRenderer());
            	((DefaultTableCellRenderer)jXTreeTable1.getColumnModel().getColumn(i).getCellRenderer()).
            	setHorizontalAlignment(SwingConstants.CENTER);
            }
             
            jXTreeTable1.getColumnModel().getColumn(0).setCellRenderer( new DefaultTableCellRenderer());
            //this.statsHandler = new UIStatisticHandler(model, jXTreeTable1);
            //PickingHandler.getInstance().setTreeTable(jXTreeTable1);
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setMinimumSize(new java.awt.Dimension(640, 480));
            addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    formComponentResized(evt);
                }
            });

            jToolBar1.setRollover(false);
            jToolBar1.setFloatable(false);
            jToolBar2.setFloatable(false);
            jToolBar3.setFloatable(false);
            jToolBar4.setFloatable(false);
            jToolBar1.setBorder(new MetalBorders.ToolBarBorder());
            jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fldr_obj.gif"))); // NOI18N
            jButton1.setBorderPainted(false);
            jButton1.setToolTipText("Open map");
            jButton1.setFocusable(false);
            jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton1.setOpaque(false);
            jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(jButton1);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    openMap();
                }
            });
            
            openReportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fldr_rep.gif"))); // NOI18N
            openReportButton.setToolTipText("Open report");
            openReportButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
            openReportButton.setFocusable(false);
            openReportButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            openReportButton.setMaximumSize(new java.awt.Dimension(28, 28));
            openReportButton.setMinimumSize(new java.awt.Dimension(28, 28));
            openReportButton.setOpaque(false);
            openReportButton.setPreferredSize(new java.awt.Dimension(28, 28));
            openReportButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(openReportButton);
            openReportButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openReport();
				}
			});
            

            jToolBar2.setBorder(new MetalBorders.ToolBarBorder());
            jToolBar2.setRollover(true);
            jToolBar2.setMinimumSize(new java.awt.Dimension(50, 50));

            jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start_task.gif"))); // NOI18N
            jButton2.setBorderPainted(false);
            jButton2.setFocusable(false);
            jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton2.setOpaque(false);
            jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar2.add(jButton2);
            
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (ConditionManager.getInstance().getRunningState()!=States.STARTED) {
                    	simulationStart();
                    } else {
                    	simulationPause();
                    }
                }
            });

            jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/nav_stop.gif"))); // NOI18N
            jButton3.setBorderPainted(false);
            jButton3.setFocusable(false);
            jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton3.setOpaque(false);
            jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar2.add(jButton3);
            
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    simulationStop();
                }
            });
            jToolBar2.add(jButton3);

            jToolBar3.setRollover(true);
            jToolBar3.setBorder(new MetalBorders.ToolBarBorder());
            jToolBar4.setBorder(new MetalBorders.ToolBarBorder());
            /*jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/settings_16.png"))); // NOI18N
            jButton4.setBorder(null);
            jButton4.setFocusable(false);
            jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            jButton4.setOpaque(false);
            jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar3.add(jButton4);
            jToolBar3.add(jSeparator1);*/
            /*toolPanelButton.setIcon(new javax.swing.ImageIcon("/icons/task_choice.gif")); // NOI18N
            toolPanelButton.setSelected(true);
            toolPanelButton.setBorder(null);
            toolPanelButton.setFocusable(false);
            toolPanelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            toolPanelButton.setOpaque(false);
            toolPanelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            toolPanelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    toolPanelButtonActionPerformed(evt);
                }
            });
            jToolBar3.add(toolPanelButton);*/
            
            mainPanel.setBorder(null);
            mainPanel.setDividerLocation(400);


/*!!!!*/    canvas.setMinimumSize(new java.awt.Dimension(400, 100));
/*!!!!*/    canvas.setPreferredSize(new java.awt.Dimension(400, 440));

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            
            //canvas.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 400, Short.MAX_VALUE)
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 582, Short.MAX_VALUE)
                );

        /*!!!!*/ 
        mainPanel.setLeftComponent(canvas);

		toolPanel.setPreferredSize(new java.awt.Dimension(101, 440));

		jPanel2.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Time Scale"));
		jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2,
				javax.swing.BoxLayout.LINE_AXIS));

		jSlider1.setMinimum(1);
		jSlider1.setMaximum(200);
		jSlider1.setValue(1);
		jPanel2.add(jSlider1);
		
		jSlider1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Float spinnerValue = (Float)jSpinner1.getValue();
				if ( spinnerValue.intValue()!= jSlider1.getValue()){
					jSpinner1.setValue(jSlider1.getValue());
				}
			}
		});
//		 Float value = 1f; 
//		 Float min = 0.5f;
//		 Float max  = 100f;
//		 Float step = 0.5f; 
//		spinnermodel = new SpinnerNumberModel(value, min, max, step);
		
		// int fifty = spinnermodel.getNumber().intValue(); 
		spinnermodel = new  ru.cos.sim.visualizer.traffic.gui.models.SpinnerModel();
		jSpinner1 = new javax.swing.JSpinner(spinnermodel);
		jSpinner1.setMaximumSize(new java.awt.Dimension(48, 20));
		jSpinner1.setMinimumSize(new java.awt.Dimension(48, 20));
		jSpinner1.setPreferredSize(new java.awt.Dimension(48, 20));
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) jSpinner1.getEditor();
		if (editor != null) {
			editor.getTextField().setEditable(true);
		}
		jSpinner1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				SimulationSystemManager.getInstance().changeScale(spinnermodel.getFloatValue());
				jSlider1.setValue(spinnermodel.getIntValue());
				
			}
		});
		jPanel2.add(jSpinner1);
		//jXTreeTable1.getColumn(1).setMaxWidth(160);
		//jXTreeTable1.getColumn(0).setMaxWidth(240);
		//jXTreeTable1.getColumn(1).setMinWidth(80);
		SimulationSystemManager.getInstance().setInformationTable(model);
		jScrollPane1.setViewportView(jXTreeTable1);
		metersList = new MetersList();
		
		JTable list = metersList.list;
		 jScrollPane2.setViewportView(list);
		 jScrollPane3.setViewportView(carinformationHandler.getTable());
		 
	    javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
        toolPanel.setLayout(toolPanelLayout);
        toolPanelLayout.setHorizontalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                .addContainerGap())
        );
        toolPanelLayout.setVerticalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
        );

		mainPanel.setRightComponent(toolPanel);

		//jToolBar4.setRollover(true);

		jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/icons/redLight.gif"))); // NOI18N
		jToggleButton1.setToolTipText("Show all lights");
		jToggleButton1.setBorderPainted(false);
		jToggleButton1.setFocusable(false);
		jToggleButton1
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jToggleButton1.setOpaque(false);
		jToggleButton1
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToggleButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SystemProperties prop = SimulationSystemManager.getInstance()
						.getSystemProperties();
				switch (prop.getTrafficLightSignalState()) {
				case All:
					prop
							.setTrafficLightSignalState(TrafficLightSignalsShow.TrafficLightsOnly);
					break;
				case TrafficLightsOnly:
					prop
							.setTrafficLightSignalState(TrafficLightSignalsShow.Green);
					break;	
				case Green:
					prop
							.setTrafficLightSignalState(TrafficLightSignalsShow.None);
					break;
				case None:
					prop
							.setTrafficLightSignalState(TrafficLightSignalsShow.All);
					break;
				}
//				Mode mode = TrafficLightHandler.getMode();
//				for (int i = 0; i< modes.length; i++) {
//					if (modes[i] == mode) {
//						mode = (i+1 >= modes.length) ? modes[0] : modes[i+1];
//						TrafficLightHandler.setMode(mode);
//						switch (mode) {
//							case None : jToggleButton1.setToolTipText("Do not show any lights");
//										break;
//							case All: jToggleButton1.setToolTipText("Show all lights");
//										break;
//							case NoRed : jToggleButton1.setToolTipText("Do not show red lights");
//										break;
//						}
//						TraceManager.getInstance().updateAllLights();
//						return;
//					}
//				}
				
			}
		});
		jToolBar4.add(jToggleButton1);

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);
		jMenuBar1.add(jMenu2);

		//setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																mainPanel,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																699,
																Short.MAX_VALUE)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jToolBar1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jToolBar2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				69,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jToolBar4,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				456,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jToolBar3,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				104,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jToolBar3, 0,
																0,
																Short.MAX_VALUE)
														.addComponent(
																jToolBar4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jToolBar1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jToolBar2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												mainPanel,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												582, Short.MAX_VALUE)
										.addContainerGap()));

        }
        
        private void initMenu(JMenuBar bar)
        {
        	JMenu filemenu = new JMenu();
            filemenu.setText("File");
            {
            	//Open map item
            	JMenuItem openitem = new JMenuItem();
                openitem.setText("Open Map");
                openitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.VK_CONTROL));
                openitem.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent arg0) {
    					openMap();
    					
    				}
    			});
                filemenu.add(openitem);
            }
            
            {
            	JMenuItem reportitem = new JMenuItem();
                reportitem.setText("Open Report");
                //reportitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_,KeyEvent.VK_CONTROL));
                reportitem.addActionListener(new ActionListener() {
    				
    				@Override
    				public void actionPerformed(ActionEvent arg0) {
    					openReport();
    					
    				}
    			});
                filemenu.add(reportitem);
            }
            
            {
            	//Exit Menuitem
            	JMenuItem exititem = new JMenuItem();
            	exititem.setText("Exit");
            	exititem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.VK_CONTROL));
            	exititem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
            	filemenu.add(exititem);
            }
            
            //Simulation Menu
            JMenu simulationMenu = new JMenu("Simulation");
            {
            	//Start menu Item
            	JMenuItem start = new JMenuItem("Start");
            	start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.VK_CONTROL));
            	start.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						simulationStart();
					}
				});
            	simulationMenu.add(start);
            	
            }
            
            {
            	//Pause menu item
            	JMenuItem pause = new JMenuItem("Pause");
            	pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.VK_CONTROL));
            	pause.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						simulationPause();
					}
				});
            	simulationMenu.add(pause);
            	
            }
            
            {
            	//Stop Menu Item
            	JMenuItem stop = new JMenuItem("Stop");
            	stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,KeyEvent.VK_CONTROL));
            	stop.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						simulationStop();
					}
				});
            	simulationMenu.add(stop);
            	
            }
            JMenu graphics = new JMenu("Charts");
            {
            	JMenuItem launch = new JMenuItem("Show chart");
            	graphics.add(launch);
            	launch.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							//ShowCumulativeVolumeGraphDynamic.main(null);
						} catch (Exception e) {System.out.println(e.getStackTrace());}
					}
				});
            }
            
            
            bar.add(filemenu);
            bar.add(simulationMenu);
           // bar.add(graphics);
        }
        
        private void toolPanelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolPanelButtonActionPerformed
            if (toolPanelButton.isSelected()) {
                toolPanel.setVisible(true);
                mainPanel.setDividerSize(10);
                if (dividerLocation <0) {
                    dividerLocation = mainPanel.getWidth()-toolPanel.getWidth();
                }
                if (dividerLocation > mainPanel.getWidth()) dividerLocation = mainPanel.getWidth() - 250;
                mainPanel.setDividerLocation(dividerLocation/(mainPanel.getWidth()-mainPanel.getDividerSize()));
                //toolPanel.set
            } else {
                this.dividerLocation = mainPanel.getDividerLocation();///(mainPanel.getWidth()-mainPanel.getDividerSize());
                toolPanel.setVisible(false);
                mainPanel.setDividerSize(0);
            }
        }//GEN-LAST:event_toolPanelButtonActionPerformed

        private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
            dividerLocation = mainPanel.getWidth() - 250;
            if (dividerLocation < 0) return;
            if (toolPanel.isVisible()) {
                mainPanel.setDividerLocation(dividerLocation/(mainPanel.getWidth()-mainPanel.getDividerSize()));
            }

        }

        protected void doResize() {       
        	canvas.resize(canvas.getWidth(), canvas.getHeight());
//        	impl.resizeCanvas(canvas.getWidth(), canvas.getHeight());
//            ((JMECanvas)canvas).makeDirty();
           // SessionsProperties.rootNode.updateRenderState();
            //SessionsProperties.carNode.updateRenderState();
            //SessionsProperties.carNode.updateGeometricState(0.0f, true);
        }
        
        protected void openMap()
        {
        	JFileChooser chooser = new JFileChooser(pref.get("workDirectory",System.getProperty("user.home")));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("MDF map", "mdf");
			chooser.setFileFilter(filter);
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			int value = chooser.showDialog(this, "Open");
			if (value == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				if (file.exists()) {
					this.flush();
					SimulationSystemManager.getInstance().loadMap(file);
					pref.put("workDirectory", file.getAbsolutePath());	
					this.metersList.init();
					setTitle(defaultTitle + " - " + file.getName());
					this.canvas.resize( canvas.getWidth(),canvas.getHeight());
//					if (impl != null) impl.focusCamera();
				}
			}
			if (ConditionManager.getInstance().getMapState() == States.MAP_LOADED)jButton2.setEnabled(true);
        }
        
        protected void flush()
        {
        	Color defaultColor = this.getBackground();
//        	ColorRGBA color = new ColorRGBA(defaultColor.getRed()/255.0f,
//            		defaultColor.getGreen()/255.0f, defaultColor.getBlue()/255.0f, defaultColor.getAlpha()/255.0f);
//        	
        	//SimulationSystemManager.getInstance().stop();
        	if (ConditionManager.getInstance().getRunningState() != States.STARTED) {
        		jButton2.setIcon(playIcon);
        	}
        	
//        	impl.flush();
        }
        
        protected void simulationStart()
        {
        	SimulationSystemManager.getInstance().start();
        	if (ConditionManager.getInstance().getRunningState() == States.STARTED) {
        		jButton2.setIcon(pauseIcon);
        	}
        }
        
        protected void simulationStop()
        {
        	SimulationSystemManager.getInstance().stop();
        	if (ConditionManager.getInstance().getRunningState() != States.STARTED) {
        		jButton2.setIcon(playIcon);
        	}
        }
        
        protected void simulationFinished()
        {
        	if (ConditionManager.getInstance().getRunningState() != States.STARTED) {
        		jButton2.setIcon(playIcon);
        	}
        	
        	ExportHandler handler = new ExportHandler(this);
        	handler.showMessage("Simulation finished");
        }
        
        public void openReport()
        {
        	JFileChooser chooser = new JFileChooser(pref.get("workDirectory",System.getProperty("user.home")));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Simulation report", "tsr");
			chooser.setFileFilter(filter);
			chooser.setDialogType(JFileChooser.OPEN_DIALOG);
			int value = chooser.showDialog(this, "Open");
			if (value == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				if (file.exists()) {
					SimulationSystemManager.getInstance().openReport(file);
				}
			}
        }
        
        protected void simulationPause()
        {
        	SimulationSystemManager.getInstance().pause();
        	if (ConditionManager.getInstance().getRunningState() == States.PAUSED) {
        		jButton2.setIcon(playIcon);
        	} else {
        		jButton2.setIcon(pauseIcon);
        	}
        }
        // Overridden so we can exit when window is closed
        protected void processWindowEvent(WindowEvent e) {
            super.processWindowEvent(e);
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                System.exit(0);
            }
        }
        
        protected boolean testMultisampleSupport() {
        	SystemProperties props = SimulationSystemManager.getInstance().getSystemProperties();
        	String multisampleCapability = props.getProperty(Properties.supportMultisamples);
        	
            if (!multisampleCapability.equals(Boolean.TRUE.toString())  && !multisampleCapability.equals(Boolean.FALSE.toString())) {
            	// Multisample capability is not tested;
            	System.out.println("Testing multisample capability");
            	boolean multisamplingEnabled = false;
                try {
                	Display.create(new PixelFormat(8, 0, 0, 4));
                	multisamplingEnabled = true;
                	Display.destroy();
                }  catch (Exception e) {
                	//e.printStackTrace();
                	Logger.getLogger(GraphicInterface.class.getName()).
                	log(Level.WARNING,"ARB_MULTISAMPLING is not supported - " + e.toString() );
                }
                
                if (multisamplingEnabled) 
                	props.setProperty(Properties.supportMultisamples, Boolean.TRUE.toString()); 
                else  props.setProperty(Properties.supportMultisamples, Boolean.FALSE.toString());
                
                return multisamplingEnabled;
            } else {
            	if (multisampleCapability.equals(Boolean.TRUE.toString())) return true;
            	if (multisampleCapability.equals(Boolean.FALSE.toString())) return false;
            }
            
            return false;
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
    				return;
    			}
    			if (action == States.FINISHED){
    				simulationFinished();
    				return;
    			}
    			if (action == States.PAUSED) {
    				return;
    			}
    			
    		}
    		
    	}
    }

	
