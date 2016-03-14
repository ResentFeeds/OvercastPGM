package overcast.pgm.module.modules.timelimit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.result.MatchResult;
import overcast.pgm.result.MatchResults;
import overcast.pgm.util.TimeUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.NORMAL)
public class TimeBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();

		Node timeNode = root.getElementsByTagName("time").item(0);
		if (timeNode != null) {
			boolean show = true;
			if (timeNode.getNodeType() == Node.ELEMENT_NODE) {
				Element timeElement = (Element) timeNode;
				int time = TimeUtil.parseTime(timeElement.getTextContent());
				if (timeElement.hasAttribute("show")) {
					show = XMLUtils.parseBoolean(timeElement.getAttribute("show"));
				}
				MatchResult result = MatchResults.parse(timeElement.getAttribute("result"));
				
				TimeModule timeModule = new TimeModule(time, show, result);
				modules.add(timeModule);
			}
		}
		return modules;
	}
}
