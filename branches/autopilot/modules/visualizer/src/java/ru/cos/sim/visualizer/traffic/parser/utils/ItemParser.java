package ru.cos.sim.visualizer.traffic.parser.utils;

import org.jdom.Element;

import ru.cos.sim.visualizer.traffic.parser.Parser;

public class ItemParser {
	
	public static Float getFloat(Element e, String name)
	{
		Float result;
		
		try {
			result = Float.parseFloat(e.getChildText(name,Parser.getCurrentNamespace()));
		} catch (NumberFormatException ex)
		{
			return null;
		}
		return result;
		
		
	}
	
	public static Long getLong(Element e, String name)
	{
		Long result;
		try {
			result = Long.parseLong(e.getChildText(name,Parser.getCurrentNamespace()));
		} catch (NumberFormatException ex)
		{
			return null;
		}
		return result;
	}
	
	public static Integer getInteger(Element e, String name){
		Integer result;
		
		try {
			result =  Integer.parseInt(e.getChildText(name,Parser.getCurrentNamespace()));
		} catch (NumberFormatException ex)
		{
			return null;
		}
		
		return result;
	}
}
