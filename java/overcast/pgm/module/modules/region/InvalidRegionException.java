package overcast.pgm.module.modules.region;

import org.w3c.dom.Element;

public class InvalidRegionException extends Exception {

	private Element el;

	public InvalidRegionException(String string, Element el) {
		super(string);
		this.el = el;
	}

	public Element getElement() {
		return this.el;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9134895457361232628L;

}
