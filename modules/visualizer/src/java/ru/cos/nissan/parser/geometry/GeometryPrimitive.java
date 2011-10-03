package ru.cos.nissan.parser.geometry;


public abstract class GeometryPrimitive {
	
	public enum Geometrytype {
		Arc,
		Line
	}
	
	private Geometrytype geometrytype;
	
	public GeometryPrimitive(Geometrytype type)
	{
		this.geometrytype = type;
	}

	public Geometrytype getGeometrytype() {
		return geometrytype;
	}
	


}
