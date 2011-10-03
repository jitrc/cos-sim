package road.init.xml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RoadNetworkData;
import ru.cos.sim.road.init.xml.RoadNetworkDataReader;

public class RoadNetworkDataReaderTest {
	
	Element roadNetworkElement;
	
	@Before
	public void setUp() throws Exception {
		InputStream is = this.getClass().getResourceAsStream("testRoadNetwork.xml"); 
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(is);
		roadNetworkElement = doc.getRootElement();
	}

	@Test
	public void testRead() throws JDOMException, IOException {
		RoadNetworkData rnd = RoadNetworkDataReader.read(roadNetworkElement);
		
		Map<Integer, LinkData> linksData = rnd.getLinks();		
		Map<Integer, AbstractNodeData> nodesData = rnd.getNodes(); 
		
		assertTrue(linksData.size()==1);
		assertTrue(nodesData.size()==1);
		
		LinkData linkData = linksData.get(1);
		assertTrue(linkData!=null);
		
		AbstractNodeData abstractNodeData = nodesData.get(3);
		assertTrue(abstractNodeData!=null);
		
		// ...
		// TODO complete test properly
	}

}
