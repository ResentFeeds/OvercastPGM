package overcast.pgm.module.modules.timelimit;
 

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.TimeUtil; 
import overcast.pgm.xml.XMLParseException;
@BuilderInfo()
public class TimeBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		
		Node timeNode = root.getElementsByTagName("time").item(0);
		if(timeNode != null){
			if(timeNode.getNodeType() == Node.ELEMENT_NODE){
				Element timeElement = (Element) timeNode;
				int time = TimeUtil.parseTime(timeElement.getTextContent());
				
				TimeModule timeModule = new TimeModule(time);
				
				modules.add(timeModule);
			}
		}
		return modules;
	}

}
