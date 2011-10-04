package ru.cos.sim.visualizer.traffic.information;


import java.util.ArrayList;
import java.util.List;

public class TreeItem
{
	private String name;
	public String value;
	private List<TreeItem> children = new ArrayList<TreeItem>();
	
	public TreeItem() 
	{
	}
	
	public TreeItem( String name,String value) 
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return value;
	}
	
	public List<TreeItem> getChildren() 
	{
		return children;
	}
	
	public String toString()
	{
		return "MyTreeNode: " + name + ", ";
	}
}