package overcast.pgm.module.modules.filter;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.xml.InvalidXMLException;

@BuilderInfo(stage = ModuleStage.START)
public class FilterBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) {
		return null;
	}

	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory fac) {
		ModuleCollection<Module> modules = new ModuleCollection<>();

		Element root = doc.getDocumentElement();
		FilterParser parser = fac.getFilterContext().getParser();
		List<Filter> children = null;
		Node filtersNode = root.getElementsByTagName("filters").item(0);
		if (filtersNode != null) {
			if (filtersNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) filtersNode;
				try {
					 children = parser.parseChildFilters(element);;
				} catch (InvalidXMLException e1) {
					e1.printStackTrace();
				}
			}
			
			FilterModule filter = new FilterModule(null, children);
			modules.add(filter);
		}
		return modules;
	}
}
