package overcast.pgm.parser;

import org.w3c.dom.Element;
public class ElementParser  {

    Element element;

	public ElementParser(Element element) {
		this.element = element;
	}

	
	public Element getElement(){
		return this.element;
	}
}
