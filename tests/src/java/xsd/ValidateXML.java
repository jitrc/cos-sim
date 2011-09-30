package xsd;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * 
 */

/**
 * @author zroslaw
 *
 */
public class ValidateXML {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		SchemaFactory schemaFactory = 
		    SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		factory.setSchema(schemaFactory.newSchema(
		    new Source[] {new StreamSource(ValidateXML.class.getResourceAsStream("TrafficModel.xsd"))}));
		
		SAXParser parser = factory.newSAXParser();

		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(new SimpleErrorHandler());
		reader.parse(new InputSource(ValidateXML.class.getResourceAsStream("SampleTrafficModel.xml")));
	}

	@Test
	public void test() throws IOException, SAXException, ParserConfigurationException{
		main(null);
	}
	
}

 class SimpleErrorHandler implements ErrorHandler {

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		throw new RuntimeException(arg0);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		throw new RuntimeException(arg0);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		System.out.println(arg0);
	}

}