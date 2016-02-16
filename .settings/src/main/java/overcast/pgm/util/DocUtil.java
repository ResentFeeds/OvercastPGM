package overcast.pgm.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DocUtil {

	public static Document parse(File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			return factory.newDocumentBuilder().parse(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
