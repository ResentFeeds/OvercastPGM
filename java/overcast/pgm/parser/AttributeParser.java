package overcast.pgm.parser;

import org.w3c.dom.Attr;

public class AttributeParser {

	private Attr attribute;

	public AttributeParser(Attr attribute) {
		this.attribute = attribute;
	}

	public Attr getAttribute() {
		return this.attribute;
	}
}
