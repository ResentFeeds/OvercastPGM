package overcast.pgm.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class XMLParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // Do nothing with this

	private String message;
	private Attr attribute;

	private Element element;

	public XMLParseException(String message, Attr attr) {
		this.message = message;
		this.attribute = attr;
	}

	public XMLParseException(String message) {
		this(message, null);
	}

	public XMLParseException(Element element, String message) { 
		this.element = element;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public Attr getAttribute() {
		return this.attribute;
	}
	
	
	public Element getElement(){
		return this.element;
	}
}
