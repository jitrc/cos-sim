/**
 * 
 */
package road.init;


import static org.junit.Assert.*;

import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.*;

import ru.cos.sim.road.RoadNetwork;
import ru.cos.sim.road.init.RoadNetworkBuilder;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.xml.RoadNetworkDataReader;

/**
 * Read road network data from XML and build road network instance
 * @author zroslaw
 */
public class ReadAndBuildTest {

	@Test
	public void integrationTest() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("simpleRoadNetwork.xml"); 
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(is);
		Element roadNetworkElement = doc.getRootElement();
		
		RoadNetworkData roadNetworkData = RoadNetworkDataReader.read(roadNetworkElement);
		
		RoadNetwork roadNetwork = RoadNetworkBuilder.build(roadNetworkData);
		assertTrue(roadNetwork!=null);

		// ...
		// TODO complete test properly
	}
	

	@Test
	public void originDestinationNodesTest() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("destinationRoadNetwork.xml"); 
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(is);
		Element roadNetworkElement = doc.getRootElement().getChild("RoadNetwork",doc.getRootElement().getNamespace());
		
		RoadNetworkData roadNetworkData = RoadNetworkDataReader.read(roadNetworkElement);
		
		RoadNetwork roadNetwork = RoadNetworkBuilder.build(roadNetworkData);
		assertTrue(roadNetwork!=null);

		// ...
		// TODO complete test properly
	}

}
