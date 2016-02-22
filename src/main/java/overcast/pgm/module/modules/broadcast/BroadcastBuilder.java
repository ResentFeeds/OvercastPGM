package overcast.pgm.module.modules.broadcast;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.TimeUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo
public class BroadcastBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		Node node = root.getElementsByTagName("broadcasts").item(0);
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				List<Element> children = XMLUtils.getChildElements(element);

				for (Element child : children) {
					if (child != null) {
						switch (child.getTagName()) {
						case "tip":
							int count = 1;
							int after = 1;
							int every = -1;
							if (child.hasAttribute("after")) {
								after = TimeUtil.parseTime(child.getAttribute("after"));
							}

							if (child.hasAttribute("count")) {
								count = NumberUtils.parseInteger(child.getAttribute("count"));
							}

							if (child.hasAttribute("every")) {
								every = TimeUtil.parseTime(child.getAttribute("every"));
							}
							modules.add(new BroadcastModule(BroadcastType.TIP, after, every, count,
									child.getTextContent()));
							break; 
						case "alert":
							int count1 = 1;
							int after1 = 1;
							int every1 = -1;

							if (child.hasAttribute("after")) {
								after1 = TimeUtil.parseTime(child.getAttribute("after"));
							}

							if (child.hasAttribute("count")) {
								count1 = NumberUtils.parseInteger(child.getAttribute("count"));
							}

							if (child.hasAttribute("every")) {
								every1 = TimeUtil.parseTime(child.getAttribute("every"));
							}
							modules.add(new BroadcastModule(BroadcastType.ALERT, after1, every1, count1,
									child.getTextContent()));
							break;
						}
					}
				}
			}
		}
		return modules;
	}
}
