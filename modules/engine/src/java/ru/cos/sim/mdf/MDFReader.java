package ru.cos.sim.mdf;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import ru.cos.sim.agents.AgentsDataReader;
import ru.cos.sim.agents.TrafficAgentData;
import ru.cos.sim.engine.TrafficModelDefinition;
import ru.cos.sim.exceptions.TrafficSimulationException;
import ru.cos.sim.meters.data.MeterData;
import ru.cos.sim.meters.xml.MetersDataReader;
import ru.cos.sim.parameters.data.ModelParametersData;
import ru.cos.sim.parameters.xml.ModelParametersDataReader;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.xml.RoadNetworkDataReader;

/**
 * Reader for MDF files.
 * MDF - Model Definition File, xml files that completely describes traffic model
 * @author zroslaw
 */
public class MDFReader {

	public static final String ROAD_NETWORK = "RoadNetwork";
	public static final String AGENTS = "Agents";
	public static final String METERS = "Meters";
	public static final String MODEL_PARAMETERS = "ModelParameters";
	
	public static final Namespace MDF_NAMESPACE = Namespace.getNamespace("mdf","http://traffic.cos.ru/cossim/TrafficModelDefinitionFile0.1");
	
	/**
	 * Reading MDF file as input stream.
	 * @param mdfInputStream input stream of MDF XML file
	 * @return object that defines traffic model
	 */
	public static TrafficModelDefinition read(byte[] mdfFile){
		
		// validate incoming mdf according to xsd
//		validate(new ByteArrayInputStream(mdfFile));

		// Retrieve root TrafficModel JDOM element
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		try {
			doc = builder.build(new ByteArrayInputStream(mdfFile));
		} catch (Throwable throwable) {
			throw new TrafficSimulationException("Unexpected error while reading MDF file.", throwable);
		}
		Element trafficModel = doc.getRootElement();

		// Read it and return traffic model definition object
		return read(trafficModel); 
	}

	private static void validate(InputStream mdfInputStream) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		InputStream xsdInputStream = 
			MDFReader.class.getResourceAsStream("TrafficModel.xsd");
		SchemaFactory schemaFactory = 
			SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		SAXParser parser;
		XMLReader reader;
		try {
			factory.setSchema(schemaFactory.newSchema(
					new Source[] {new StreamSource(xsdInputStream)}));
			parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setErrorHandler(new MDFErrorHandler());
		} catch (Throwable throwable) {
			throw new TrafficSimulationException("Unexpected error while reading MDF XSD schema.", throwable);
		}
		try {
			reader.parse(new InputSource(mdfInputStream));
		} catch (Throwable throwable) {
			throw new TrafficSimulationException("MDF file is incorrect and was not validated by XSD schema.", throwable);
		}

	}

	private static TrafficModelDefinition read(Element trafficModel){
		TrafficModelDefinition def = new TrafficModelDefinition();

		Element roadNetworkElement = trafficModel.getChild(ROAD_NETWORK, MDF_NAMESPACE);
		RoadNetworkData roadNetworkData = RoadNetworkDataReader.read(roadNetworkElement);

		Element agentsElement = trafficModel.getChild(AGENTS, MDF_NAMESPACE);
		Set<TrafficAgentData> agentsData = AgentsDataReader.read(agentsElement);

		Element metersElement = trafficModel.getChild(METERS, MDF_NAMESPACE);
		Set<MeterData> metersData = MetersDataReader.read(metersElement);

		Element modelParametersElement = trafficModel.getChild(MODEL_PARAMETERS, MDF_NAMESPACE);
		ModelParametersData modelParametersData = ModelParametersDataReader.read(modelParametersElement);
		
		def.setRoadNetworkData(roadNetworkData);
		def.setAgentsData(agentsData);
		def.setMetersData(metersData);
		def.setModelParametersData(modelParametersData);
		
		return def;
	}

}

class MDFErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		throw new TrafficSimulationException(arg0);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		throw new TrafficSimulationException(arg0);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		System.out.println(arg0);
	}

}
