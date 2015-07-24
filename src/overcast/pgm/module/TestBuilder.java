package overcast.pgm.module;

 

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;

@BuilderInfo(documentable = false, stage = ModuleStage.LOAD)
public class TestBuilder extends Builder {

	@Override
	public Module build(Document doc) {
		Element root = doc.getDocumentElement();
		
		Element element = (Element) root.getElementsByTagName("name").item(0);
		
		String name = element.getTextContent();
		return new TestModule(name);
	}
}
