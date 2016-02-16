package overcast.pgm.module.modules.filter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.util.Log;
import overcast.pgm.xml.InvalidXMLException;

@BuilderInfo(stage = ModuleStage.LOAD)
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
		Node filtersNode = root.getElementsByTagName("filters").item(0);
		if (filtersNode != null) {
			if (filtersNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) filtersNode;
				try {
					modules.add(new FilterModule(new FilterNode(null, parser.parseChildFilters(element))));
				} catch (InvalidXMLException e) {
					e.printStackTrace();
				}
			}
		}else{
			Log.info("its null");
			return null;
		}
		return modules;
	}
}
