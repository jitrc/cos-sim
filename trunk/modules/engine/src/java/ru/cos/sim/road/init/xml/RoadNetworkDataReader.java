/**
 * 
 */
package ru.cos.sim.road.init.xml;

import java.util.Map;
import java.util.TreeMap;


import org.jdom.Element;
import org.jdom.Namespace;

import ru.cos.sim.mdf.MDFReader;
import ru.cos.sim.road.init.data.AbstractNodeData;
import ru.cos.sim.road.init.data.LinkData;
import ru.cos.sim.road.init.data.RoadNetworkData;

/**
 * 
 * @author zroslaw
 */
public class RoadNetworkDataReader {

	public static final Namespace NS = MDFReader.MDF_NAMESPACE;
	
	public static final String ROAD_NETWORK = "RoadNetwork";
	public static final String LINKS = "Links";
	public static final String LINK = "Link";
	public static final String NODES = "Nodes";

	public static RoadNetworkData read(Element roadNetworkXmlElement){
		RoadNetworkData result = new RoadNetworkData();

		// read and set links data
		Map<Integer,LinkData> linksData = new TreeMap<Integer, LinkData>();
		Element links = roadNetworkXmlElement.getChild(LINKS, NS);
		for (Object linkObj:links.getChildren(LINK, NS)){
			Element linkElement = (Element) linkObj;
			LinkData linkData = LinkDataReader.read(linkElement);
			linksData.put(linkData.getId(),linkData);
		}
		result.setLinks(linksData);

		// read and set abstractNodes data 
		Map<Integer,AbstractNodeData> nodesData = new TreeMap<Integer, AbstractNodeData>();
		Element nodes = roadNetworkXmlElement.getChild(NODES, NS);
		for (Object nodeObj:nodes.getChildren()){
			Element nodeElement = (Element) nodeObj;
			AbstractNodeData abstractNodeData = NodeDataReader.read(nodeElement);
			nodesData.put(abstractNodeData.getId(),abstractNodeData);
		}
		result.setNodes(nodesData);
		
		return result;
	}
	
}
