package overcast.pgm.match;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import overcast.pgm.map.Map;

public class Match {

	public int id;
	private MatchState state;
	private Map map;
	private Document document;

	public Match(int id, Map map) {
		this.id = id;
		this.state = MatchState.LOAD;
		this.map = map;

		SAXBuilder builder = new SAXBuilder();
		try {
			this.document = builder.build(this.map.getXML());
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public MatchState getState() {
		return this.state;
	}

	public Document getDocument() {
		return this.document;
	}

	public Map getMap() {
		return this.map;
	}
}
