/**
 * 
 */
package xsd;

import java.io.InputStream;

import javax.xml.bind.*;

import org.junit.Test;

/**
 * @author zroslaw
 *
 */
public class UnmarshallingMDF {

	/**
	 * @param args
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("mdf.jaxb.data");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		InputStream is = UnmarshallingMDF.class.getResourceAsStream("SampleTrafficModel.xml");
		unmarshaller.unmarshal(is);
	}
	
	@Test
	public void test() throws JAXBException{
		
	}

}
