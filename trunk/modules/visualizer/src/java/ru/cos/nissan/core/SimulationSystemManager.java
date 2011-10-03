package ru.cos.nissan.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.jdesktop.swingx.JXTreeTable;

import ru.cos.agents.car.CarsHandler;
import ru.cos.frame.FrameDataHandler;
import ru.cos.nissan.graphs.export.XMLImportExport;
import ru.cos.nissan.graphs.report.SimulationReportGUIFactory;
import ru.cos.nissan.gui.CarInformationTempHandler;
import ru.cos.nissan.information.CarInformatioArea;
import ru.cos.nissan.information.StatisticHandler;
import ru.cos.nissan.information.UniverseSimpleModel;
import ru.cos.nissan.information.UniverseTabelModel;
import ru.cos.nissan.parser.VisParser;
import ru.cos.nissan.simulation.VisualController;
import ru.cos.nissan.utils.ViewFieldController;
import ru.cos.nissan.view.input.PickingHandler;
import ru.cos.renderer.Renderer;
import ru.cos.scene.impl.ITrafficLight;
import ru.cos.sim.agents.tlns.TrafficLightSignal;
import ru.cos.sim.communication.dto.AbstractDTO;
import ru.cos.sim.communication.dto.AbstractDTO.DTOType;
import ru.cos.sim.communication.dto.FrameData;
import ru.cos.sim.communication.dto.StatisticsData;
import ru.cos.sim.communication.dto.TrafficLightDTO;
import ru.cos.sim.communication.dto.VehicleDTO;
import ru.cos.sim.road.init.data.LinkLocationData;
import ru.cos.sim.road.init.data.LocationData.LocationType;
import ru.cos.sim.road.init.data.NodeLocationData;
import ru.cos.sim.vehicle.Vehicle.VehicleClass;
import ru.cos.trace.TraceHandler;
import ru.cos.trace.item.Car.CarType;
import ru.cos.trace.item.TransitionRule;
import ru.cos.trace.item.base.TrafficLight;
import ru.cos.trace.item.base.TrafficLight.Color;

public class SimulationSystemManager {
	
	private static SimulationSystemManager instance;
	private VisParser parser;
	private TraceHandler traceHandler;
	private CarsHandler carsHandler;
	private ViewFieldController viewController;
	private PickingHandler pickingHandler;
	private Renderer renderer;
	private double wrap = 1;
	private double currentTime = 0;
	private File lastFile = null;
	private StatisticHandler statisticHandler;
	private SystemProperties systemProperties;
	private long startTime;
	private long finishTime;
	
	//TEMP
	public CarInformatioArea informationHandler;
	
	private SimulationSystemManager()
	{
		ConditionManager.getInstance();
		initManagers();
		systemProperties =  new SystemProperties();
		Thread.setDefaultUncaughtExceptionHandler(ExceptionsHandler.getInstance());
	}
	

	public static SimulationSystemManager getInstance()
	{
		if (instance == null) instance = new SimulationSystemManager();
		return instance;
	}
	
	public void start()
	{
		if (ConditionManager.getInstance().getRunningState() == States.PAUSED)
		if (ConditionManager.getInstance().getInitState()==States.INITIALIZED)
		if (ConditionManager.getInstance().getMapState()==States.MAP_LOADED)
		{
			ConditionManager.getInstance().setRunningState(States.STARTED);
			VisualController.getInstance().run();
			Renderer.getRenderer().makeDirty();
			return;
		}
		
		if ((ConditionManager.getInstance().getInitState() == States.NOT_INITIALIZED) &&
				(ConditionManager.getInstance().getMapState() == States.MAP_NOT_LOADED) &&
				(ConditionManager.getInstance().getRunningState() == States.NOT_STARTED ||
				ConditionManager.getInstance().getRunningState() == States.FINISHED)) {
			
			if (lastFile != null)loadMap(lastFile);
		}
		
		
		if ((ConditionManager.getInstance().getInitState() != States.INITIALIZED) ||
				(ConditionManager.getInstance().getMapState() != States.MAP_LOADED) ||
				(ConditionManager.getInstance().getRunningState() != States.NOT_STARTED)) return;
		VisualController.getInstance().setTimeWrapFactor((float) wrap);
		VisualController.getInstance().run();
		startTime = System.currentTimeMillis();
		Renderer.getRenderer().makeDirty();
		ConditionManager.getInstance().setRunningState(States.STARTED);
		ConditionManager.getInstance().setViewState(States.SIMULATION);
		
	}
	
	public void stop()
	{	
		if (ConditionManager.getInstance().getRunningState() == States.STARTED) {
			finish();
		}
		VisualController.getInstance().stop();
		ConditionManager.getInstance().setRunningState(States.NOT_STARTED);
		ConditionManager.getInstance().setViewState(States.NON_SIMULATION) ;
		ConditionManager.getInstance().setInitState(States.NOT_INITIALIZED);
		ConditionManager.getInstance().setMapState(States.MAP_NOT_LOADED);
	}
	
	public void pause()
	{
		if (ConditionManager.getInstance().getRunningState() != States.STARTED) return;
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
		VisualController.getInstance().pause();
		ConditionManager.getInstance().setRunningState(States.PAUSED);
		
	}
	
	public void unPause()
	{
		if (ConditionManager.getInstance().getRunningState() != States.PAUSED) return;
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
		ConditionManager.getInstance().setRunningState(States.STARTED);
		VisualController.getInstance().run();
		
	}
	
	public void dispose()
	{
		VisualController.dispose();
		ConditionManager.getInstance().setRunningState(States.NOT_STARTED);
		ConditionManager.getInstance().setViewState(States.NON_SIMULATION) ;
		ConditionManager.getInstance().setInitState(States.NOT_INITIALIZED);
		ConditionManager.getInstance().setMapState(States.MAP_NOT_LOADED);
		currentTime = 0;
	}
	
	protected void initManagers()
	{
		renderer = Renderer.getRenderer();
		viewController = new ViewFieldController();
		traceHandler = new TraceHandler();
		carsHandler = new CarsHandler();
		pickingHandler = new PickingHandler();
		//VisualController.dispose();
	}
	
	public void init()
	{
		ConditionManager.getInstance().setInitState(States.INITIALIZED);
		//initManagers();
		renderer.init();
		renderer.segments = traceHandler.segments;
		renderer.rules = traceHandler.rules;
		renderer.css = traceHandler.lnodes;
		renderer.backgrounds = traceHandler.backgrounds;
	}
	
	public void loadMap(File file)
	{	
		initManagers();
		
		this.parser = new VisParser(file);
		VisualController.getInstance().init(file);
		
		ConditionManager.getInstance().setMapState(States.MAP_LOADED);
		this.lastFile = file;	

		init();
	}
	
	public void setTimeScale(int scale)
	{
		wrap = scale;
		if (ConditionManager.getInstance().getInitState() != States.INITIALIZED) return;
		VisualController.getInstance().setTimeWrapFactor(scale);
	}
	
	/*
	public void setCanvasPanel(Composite panel)
	{
		canvasPanel = panel;
	}*/
	
	public void resizeScene()
	{
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
	}
	
	public void changeScale(double scale)
	{
		if (Math.abs(scale - wrap) == 0) return;
		wrap = scale;
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
		VisualController.getInstance().setTimeWrapFactor((float) scale);
	}
	
	public void finish()
	{
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
		if (ConditionManager.getInstance().getRunningState()!=States.STARTED) return;
		finishTime = System.currentTimeMillis();
		ConditionManager.getInstance().setRunningState(States.FINISHED);
		ConditionManager.getInstance().setMapState(States.MAP_NOT_LOADED);
		ConditionManager.getInstance().setInitState(States.NOT_INITIALIZED);
	}
	
	/*public void exportData()
	{
		if (ConditionManager.getInstance().getRunningState()!= States.FINISHED) return;
		ResultsExporter.getInstance().showFinishMessage();
	}*/
	/*
	public boolean updateInformation(StatisticsData data)
	{
		SceneObjectData sdata = new SceneObjectData(data);
		this.currentTime = data.getUniverseTime();
		NewSWTApp.getInstance().setInformation(sdata);
		if (!VisualController.getInstance().isSimulationRunning()) {

			resultStatistic = sdata;
			NewSWTApp.getInstance().callAskDialog("Would you like to save Statistic to file?");
			stop();
			return true;
		}
		return false;
	}*/
	
	public double getCurrentTime()
	{
		return this.currentTime;
	}
	
	public long getTotalSimulationTime()
	{
		return this.finishTime - this.startTime;
	}
	
	
	public void update()
	{
		if (ConditionManager.getInstance().getRunningState() != States.PAUSED && ConditionManager.getInstance().getRunningState() != States.STARTED) {
			//stop();
			return;
		}
		if (ConditionManager.getInstance().getViewState() == States.NON_SIMULATION) {
			//stop();
			return;
		}
		if (!VisualController.getInstance().isSimulationAlive()){
			stop();
			//SimulationSystemManager.getInstance().finish();
			//return;
		}
		
		if (traceHandler != null) traceHandler.doUpdate();
		
		//FrameDataHandler.getInstance().setDataInvalid();
		VisualController visualController = VisualController.getInstance();
		
		// set frame properties and request frame data
		FrameDataHandler.getInstance().setFrameProperties();
		visualController.requestFrameData();
		
		// retrieve frame data
		FrameData frameData = visualController.getFrameData();
		if (ConditionManager.getInstance().getRunningState() != States.PAUSED) Renderer.getRenderer().makeDirty();
		if (frameData==null) return;  // do nothing if no data is available
		StatisticsData statistic = frameData.getStatisticsData();
		this.currentTime = statistic.getUniverseTime();
		Set<AbstractDTO> data = frameData.getDataTransferObjects();
		this.carsHandler.prepareBeforeUpdate();
		
		ArrayList<TransitionRule> rulesToInit = new ArrayList<TransitionRule>();
		
		for (AbstractDTO object : data){
			if (object.getDynamicObjectType() == DTOType.VehicleDTO){
				VehicleDTO vehicleDTO = (VehicleDTO)object;
				CarType type = CarType.LightCar;
				if (vehicleDTO.getVehicleClass()  == VehicleClass.Truck) type  = CarType.Truck;
				
				if (vehicleDTO.getLocation().getLocationType()==LocationType.LinkLocation){
					LinkLocationData linkLocationDTO = (LinkLocationData)vehicleDTO.getLocation();
					
					if (vehicleDTO.getAgentId()==pickingHandler.getSelectedCar()) {
						informationHandler.update(vehicleDTO);
					}
					
//					if (vehicleDTO.isInLaneChanging()) {
//						
//						this.carsHandler.moveCar(vehicleDTO.getId(), vehicleDTO.getSpeed() ,
//								linkLocationDTO.getPosition(), new Long(linkLocationDTO.getLinkId()), 
//								new Long(linkLocationDTO.getSegmentId()), new Long(linkLocationDTO.getLaneId()),vehicleDTO.getProgressOfLaneChanging(),
//								new Long(vehicleDTO.getFromLaneId()), type);
//					}
//					else {
						this.carsHandler.moveCar(vehicleDTO.getAgentId(), vehicleDTO.getSpeed() ,
								linkLocationDTO.getPosition(), linkLocationDTO.getLinkId(), 
								linkLocationDTO.getSegmentId(), linkLocationDTO.getLaneIndex(),
								type,vehicleDTO);
//					}
					
				}
				if (vehicleDTO.getLocation().getLocationType()==LocationType.NodeLocation)
				{
					NodeLocationData nodeLocationDTO = (NodeLocationData)vehicleDTO.getLocation();
					
					if (vehicleDTO.getAgentId()==pickingHandler.getSelectedCar()) {
						informationHandler.update(vehicleDTO);
					}
					
					this.carsHandler.moveCar(vehicleDTO.getAgentId(), vehicleDTO.getSpeed() , 
							nodeLocationDTO.getNodeId(), nodeLocationDTO.getTransitionRuleId(), 
							nodeLocationDTO.getPosition(),type,vehicleDTO);
				}
			}
			if (object.getDynamicObjectType() == DTOType.TrafficLightDTO)
			{
				TrafficLightDTO light = (TrafficLightDTO) object;
				//light.ge
				
				TransitionRule rule =  this.traceHandler.getNode((int)light.getNodeId()).getRule(light.getTransitionRuleId());
				if (rule.isLightsInited()) {
					ITrafficLight trlight = rule.getLight(light.getPosition());
					trlight.switchLight(convertColor(light.getSignal()));
				} else {
					TrafficLight vislight = new TrafficLight(-1, rule,light.getPosition(),
							convertColor(light.getSignal()));
					rule.addtrafficLight(vislight);
					rulesToInit.add(rule);
				}
				
				
				
				/*BaseTransitionRule.Color color = BaseTransitionRule.Color.Green;
				if (light.getSignal() == TrafficLightSignal.RED)
						color = BaseTransitionRule.Color.Red;
				if (light.getSignal() == TrafficLightSignal.GREEN) color = BaseTransitionRule.Color.Green;
				if (light.getSignal() == TrafficLightSignal.YELLOW) color = BaseTransitionRule.Color.Yellow;*/
				//TraceManager.getInstance().updateTrafficLights(light.getNodeId(), light.getTransitionRuleId(),light.isOnWaitingPosition(),color);
			}
		}
		
		for (TransitionRule rule : rulesToInit) {
			rule.completeLights();
		}
		
		
	}
	
	protected Color convertColor(TrafficLightSignal signal)
	{
		switch (signal) {
			case Green : return Color.Green;
			case Red : return Color.Red;
			case Yellow : return Color.Yellow;
		}
		return Color.Green;
	}
	
	public StatisticsData getStatistics()
	{
		if (ConditionManager.getInstance().getRunningState() != States.STARTED) return null;
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return null;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return null;
		VisualController visualController = VisualController.getInstance();
		FrameData frameData = visualController.getFrameData();
		visualController.requestFrameData();
		return frameData!=null?frameData.getStatisticsData():null;
	}
	
	/*public void doUpdate()
	{
		MetersDataManager.getInstance().checkRequests();
		//if (ConditionManager.getInstance().getRunningState() != States.STARTED) return;
		if (ConditionManager.getInstance().getInitState()!=States.INITIALIZED) return;
		if (ConditionManager.getInstance().getMapState()!=States.MAP_LOADED) return;
		if (scene == null) return;
		StatisticsData data = VisualController.getInstance().getStatistic();
		if (data != null) {
			this.currentTime = data.getUniverseTime();
		}

		scene.refreshCarsPosition();
	}*/
	/*
	public void acceptSettings()
	{
		Document doc = SettingDialog.loadSettingsFromFile();
		if (doc == null) return;
		Element root = doc.getRootElement();
		Element offset = root.getChild("OpenGL").getChild("Offset");
		Element abrupt = root.getChild("OpenGL").getChild("Abrupt");
		Element border = root.getChild("OpenGL").getChild("Border");
		Element other = root.getChild("OpenGL").getChild("Other");
		
		TraceConstants.xOffset = Integer.parseInt(offset.getChildText("X"));
		TraceConstants.yOffset = Integer.parseInt(offset.getChildText("Y"));
		TraceConstants.zOffset = Integer.parseInt(offset.getChildText("Z"));
		TraceConstants.wheelSens = Integer.parseInt(abrupt.getChildText("wheel"));
		TraceConstants.moveSens = Integer.parseInt(abrupt.getChildText("move"));
		TraceConstants.nearBorder = Integer.parseInt(border.getChildText("NearBorder"));
		TraceConstants.farBorder = Integer.parseInt(border.getChildText("FarBorder"));
		
		 Boolean smooth = Boolean.parseBoolean(other.getChildText("Smooth"));
		 if (smooth )SessionsProperties.setGraphicMode("Hard"); else SessionsProperties.setGraphicMode("Simple"); 
		//this.grip.setOffsets(xOffset, yOffset, zOffset);
	}*/
	/*
	public void focusMap()
	{
		if ((ConditionManager.getInstance().getInitState() != States.INITIALIZED) ||
				(ConditionManager.getInstance().getMapState() != States.MAP_LOADED)) return;
		 TracePoint focus = TraceManager.getInstance().autoFocusRandomNode();
		 if (scene ==null) return;
		 if (focus == null ) return;
		 scene.setOffsets((float)focus.getX(), (float)focus.getY(),(float)(-500));
		
	}
	
	public void updateObjectInformation(SceneObjectData data)
	{
		NewSWTApp.getInstance().setObjectInformation(data);
	}*/
	
	public File getCurrentFile() {
		return lastFile;
	}
	
	public void openReport(File file) {
		try {
			SimulationReportGUIFactory.newInstance(XMLImportExport.importReport(file));
		} catch (Exception e) {
			
		}
	}
	
	protected void setCurrentTime(double t)
	{
		this.currentTime = t;
	}


	public TraceHandler getTraceHandler() {
		return traceHandler;
	}


	public Renderer getRenderer() {
		if (renderer == null) renderer = Renderer.createRenderer();
		return renderer;
	}


	public ViewFieldController getViewController() {
		return viewController;
	}


	public CarsHandler getCarsHandler() {
		return carsHandler;
	}


	public PickingHandler getPickingHandler() {
		return pickingHandler;
	}
	
	public void setInformationTable(UniverseSimpleModel model)
	{
		statisticHandler = new StatisticHandler(model);
		//this.informationHandler = new CarInformationTempHandler(model);
	}


	public SystemProperties getSystemProperties() {
		return systemProperties;
	}
}
