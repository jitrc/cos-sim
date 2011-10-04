package ru.cos.sim.visualizer.traffic.parser.trace.staff;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.jdom.Element;

import ru.cos.sim.visualizer.math.Vector2f;
import ru.cos.sim.visualizer.traffic.parser.Parser;
import ru.cos.sim.visualizer.traffic.parser.trace.base.Staff;
import ru.cos.sim.visualizer.traffic.parser.utils.ItemParser;

public class Background extends Staff {

	public static String Name = "Background";
	
	public static enum Fields {
		Tile,
		Rectangle,
		Start,
		End,
		Saturation,
		Image,
		type,
		binaryData,
		x,
		y
	}
	private static enum ImageType {
		JPEG,
		PNG,
		GIF,
		BMP,
		TIFF,
		UNKNOWN
	}
	
	public ByteArrayInputStream stream;
	public ImageType imageType;
	public Vector2f start;
	public Vector2f end;
	
	public Background(Element e) {
		super();
		
		this.setType(StaffType.Background);

		Element image = e.getChild(Fields.Image.name(),Parser.getCurrentNamespace());
		imageType = ImageType.valueOf(image.getChildText(Fields.type.name(),Parser.getCurrentNamespace()));
		String data = image.getChildText(Fields.binaryData.name(),Parser.getCurrentNamespace());
		stream = new ByteArrayInputStream(Base64.decodeBase64(data));
		
		byte[] img = Base64.decodeBase64(data);

		Element rectangle = e.getChild(Fields.Rectangle.name(),Parser.getCurrentNamespace());
		
		start = new Vector2f();
		end = new Vector2f();
		start.x = ItemParser.getFloat(rectangle.getChild(Fields.Start.name(),Parser.getCurrentNamespace()), Fields.x.name() );
		start.y = ItemParser.getFloat(rectangle.getChild(Fields.Start.name(),Parser.getCurrentNamespace()), Fields.y.name() );
		
		end.x = ItemParser.getFloat(rectangle.getChild(Fields.End.name(),Parser.getCurrentNamespace()), Fields.x.name() );
		end.y = ItemParser.getFloat(rectangle.getChild(Fields.End.name(),Parser.getCurrentNamespace()), Fields.y.name() );
	}
	
}
