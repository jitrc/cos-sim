package ru.cos.nissan.graphs.export;

import ru.cos.sim.communication.dto.AbstractDTO;
import ru.cos.sim.communication.dto.MeterDTO;
import ru.cos.sim.meters.framework.MeasuredData;
import ru.cos.sim.meters.framework.Measurement;
import ru.cos.sim.meters.framework.MeasurementHistory;
import ru.cos.sim.meters.framework.PeriodData;
import ru.cos.sim.meters.impl.data.*;
import ru.cos.sim.road.init.data.LinkLocationData;
import ru.cos.sim.road.init.data.NodeLocationData;
import ru.cos.nissan.graphs.report.TrafficSimulationReport;

import java.io.*;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XMLImportExport{
    private static String METER_DTO_ALIAS = "meter";
    private static String DTO_TYPE = "dType";
    private static String MEASUREMENT_ALIAS = "measurement";
    private static String PERIOD_DATA_ALIAS = "periodData";
    private static String MEASURED_DATA_HISTORY_ALIAS = "measuredDataHistory";
    private static String TRAFFIC_VOLUME_ALIAS = "trafficVolume";
    private static String DENSITY_ALIAS = "density";
    private static String FLOW_ALIAS = "flow";
    private static String SPEED_ALIAS = "speed";
    private static String TIME_ALIAS = "time";
    private static String DENSITY_FLOW_ALIAS = "densityFlow";
    private static String VEHICLE_APPEARANCE_ALIAS = "vehicleAppearance";
    private static String LINK_LOCATION_DTO_ALIAS = "linkLocation";
    private static String NODE_LOCATION_DTO_ALIAS = "nodeLocation";
    private static String ROAD_NETWORK_DTO_ALIAS = "roadNetwork";
    private static String TRAFFIC_SIMULATION_REPORT_ALIAS = "TrafficSimulationReport";

    public static <MD extends MeasuredData<MD>> void exportMeterDTO(MeterDTO<MD> meterDTO, File toXMLFile) throws IOException {
        OutputStream output = new FileOutputStream(toXMLFile);

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        xStream.autodetectAnnotations(true);
        setAliases(xStream);

        xStream.toXML(meterDTO, new BufferedWriter(new OutputStreamWriter(output)));
        output.close();
    }

    public static <MD extends MeasuredData<MD>> MeterDTO<MD> importMeterDTO(File fromXMLFile) throws UnsupportedEncodingException, FileNotFoundException {
        InputStream inputStream = new FileInputStream(fromXMLFile);
        XStream xStream = new XStream(new StaxDriver());
        xStream.autodetectAnnotations(true);
        setAliases(xStream);

        //if wrong file given to parse, ClassCastException would be thrown.
        //todo think about handling this situation
        @SuppressWarnings("unchecked")
        MeterDTO<MD> dto = (MeterDTO<MD>)xStream.fromXML(new BufferedReader(
                new InputStreamReader(inputStream, "UTF-8"), 512 * 1024));

        return dto;
    }

    public static void exportReport(TrafficSimulationReport report, File toFile) throws IOException {
        OutputStream output = new FileOutputStream(toFile);

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        xStream.autodetectAnnotations(true);
        setAliases(xStream);
        xStream.alias(TRAFFIC_SIMULATION_REPORT_ALIAS, TrafficSimulationReport.class);
        xStream.registerConverter(new DateConverter());

        xStream.toXML(report, new BufferedWriter(new OutputStreamWriter(output)));
        output.close();
    }
    
    public static byte[] exportReport(TrafficSimulationReport report) throws IOException {
    	ByteArrayOutputStream output = new ByteArrayOutputStream();

        XStream xStream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
        xStream.autodetectAnnotations(true);
        setAliases(xStream);
        xStream.alias(TRAFFIC_SIMULATION_REPORT_ALIAS, TrafficSimulationReport.class);
        xStream.registerConverter(new DateConverter());

        xStream.toXML(report, new BufferedWriter(new OutputStreamWriter(output)));
        output.close();
        
        return output.toByteArray();
    }

    public static TrafficSimulationReport importReport(File fromFile) throws FileNotFoundException, UnsupportedEncodingException {
        InputStream inputStream = new FileInputStream(fromFile);
        XStream xStream = new XStream(new StaxDriver());
        xStream.autodetectAnnotations(true);
        setAliases(xStream);
        xStream.alias(TRAFFIC_SIMULATION_REPORT_ALIAS, TrafficSimulationReport.class);
        xStream.registerConverter(new DateConverter());

        TrafficSimulationReport report = (TrafficSimulationReport)xStream.fromXML(new BufferedReader(
                new InputStreamReader(inputStream, "UTF-8"), 512 * 1024));
        return report;
    }

    private static void setAliases(XStream xStream){
        xStream.alias(METER_DTO_ALIAS, MeterDTO.class);
        xStream.aliasField(DTO_TYPE, MeterDTO.class, "dTOType");
        xStream.alias(MEASUREMENT_ALIAS, Measurement.class);
        xStream.alias(PERIOD_DATA_ALIAS, PeriodData.class);
        xStream.alias(MEASURED_DATA_HISTORY_ALIAS, MeasurementHistory.class);

        //measured data aliases
        xStream.alias(TRAFFIC_VOLUME_ALIAS, TrafficVolume.class);
        xStream.alias(DENSITY_ALIAS, Density.class);
        xStream.alias(FLOW_ALIAS, Flow.class);
        xStream.alias(SPEED_ALIAS, Speed.class);
        xStream.alias(TIME_ALIAS, Time.class);
        xStream.alias(DENSITY_FLOW_ALIAS, DensityFlow.class);
        xStream.alias(VEHICLE_APPEARANCE_ALIAS, VehicleAppearanceHeadwayHistogram.class);
        xStream.alias(LINK_LOCATION_DTO_ALIAS, LinkLocationData.class);
        xStream.alias(NODE_LOCATION_DTO_ALIAS, NodeLocationData.class);
//        xStream.alias(ROAD_NETWORK_DTO_ALIAS, RoadNetworkLocationDTO.class);

        xStream.omitField(AbstractDTO.class, "dTOType");
    }
}
