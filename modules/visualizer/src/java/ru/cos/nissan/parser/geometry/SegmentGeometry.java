package ru.cos.nissan.parser.geometry;

import org.jdom.Element;

import ru.cos.math.Vector2f;

public class SegmentGeometry extends Geometry {

	public Side beginSide;
	public Side endSide;
	
	public Side leftSide;
	public Side rightSide;
	
	public SegmentGeometry(Element e) {
		super(e);
		
		
		//Enhance accoridng to new XSD !!!
		leftSide = getSides().get(0);
		rightSide = getSides().get(1);
		
		this.beginSide = new Side(leftSide.startX, leftSide.startY, rightSide.startX, rightSide.startY);
		this.endSide = new Side(leftSide.endX, leftSide.endY, rightSide.endX, rightSide.endY);
		
	}
	
}
