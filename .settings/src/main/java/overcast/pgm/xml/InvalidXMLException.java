package overcast.pgm.xml;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class InvalidXMLException extends Exception {



    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidXMLException() {
        super();
    }

    public InvalidXMLException(String message) {
        super(message);
    }

    public InvalidXMLException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidXMLException(Throwable cause) {
        super(cause);
    }

    public InvalidXMLException(String message, Element element) {
        super(generateMessage(message, element));
    }

    public InvalidXMLException(String message, Element element, Throwable cause) {
        super(generateMessage(message, element), cause);
    }

    public InvalidXMLException(String message, Attr attribute) {
        super(generateMessage(message, attribute));
    }

    public InvalidXMLException(String message, Attr attribute, Throwable cause) {
        super(generateMessage(message, attribute), cause);
    }

    private static String generateMessage(Element element) { 
    	return element.getTagName();
    }

    private static String generateMessage(Attr  attribute) {
        return "'" + attribute.getName() + "' attribute of " + generateMessage(attribute.getOwnerElement());
    }

    private static String generateMessage(String message, Element element) {
        return message + " [" + generateMessage(element) + "]";
    }

    private static String generateMessage(String message, Attr attribute) {
        return message + " [" + generateMessage(attribute) + "]";
    }
} 
