package ru.cos.nissan.gui;



import org.jdom.Element;
import org.jdom.Namespace;


public class ExportData extends Element {

	public ExportData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportData(String name, Namespace namespace) {
		super(name, namespace);
		// TODO Auto-generated constructor stub
	}

	public ExportData(String name, String prefix, String uri) {
		super(name, prefix, uri);
		// TODO Auto-generated constructor stub
	}

	public ExportData(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public ExportData(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/*
	public ExportData(String name , SceneObjectData data)
	{
		super(name);
		data.attachToElement(this);
	}*/
	/*
	public ExportData(String name, CLMData data){
		super(name);
		Element id = new Element("ID");
		Element type = new Element("LocalMeterType");
		Element segmentId = new Element("SegmentId");
		Element linkId = new Element("LinkId");
		Element measurements = new Element("Measurements");

		type.setText(data.getLocalMeterType().toString());
		id.setText(String.valueOf(data.getId()));
		linkId.setText(String.valueOf(data.getLinkId()));
		segmentId.setText(String.valueOf(data.getSegmentId()));
		for (float tempMeasurement:data.getData().values()){
			Element measurement = new Element("Measurement");
			measurement.setText(String.valueOf(tempMeasurement));
			measurements.addContent(measurement);
		}
		
		super.addContent(id);
		linkId.addContent(segmentId);
		super.addContent(linkId);
		super.addContent(type);
		super.addContent(measurements);
	}*/
/*
	private ArrayList<ExportData> children;
	private ArrayList<SceneObjectData> textchildren;
	private String name;
	public ExportData(String name) {
		super();
		this.name = name;
		children = new ArrayList<ExportData>();
		textchildren = new ArrayList<SceneObjectData>();
	}
	
	public Element toXml()
	{
		Element result = new Element(name) ;
		for (ExportData data : children)
		{
			result.addContent(data.toXml());
		}
		
		return result;
	}
	*/
	
}
