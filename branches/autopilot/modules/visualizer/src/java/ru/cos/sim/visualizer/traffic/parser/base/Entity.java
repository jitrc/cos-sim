package ru.cos.sim.visualizer.traffic.parser.base;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Entity {

	protected int uid;
	protected static String UID = "id";
	
	public Entity(Element e) {
		if (e.getChild(UID,Parser.getCurrentNamespace()) != null){
			this.uid = ItemParser.getInteger(e, UID);
		}
	}
	
	public Entity(){		
	}
	
	public Entity(int uid) {
		this.uid  = uid;
	}
	
	public int id() {
		return this.uid;
	}
}
