package overcast.pgm.module.modules.region;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.InvalidXMLException;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.START)
public class RegionBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc)
			throws XMLParseException {
		return null;
	}

	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory factory)
			throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		RegionParser parser = factory.getRegionContext().getParser();
		Element root = doc.getDocumentElement();
		Element regions = XMLUtils.getUniqueChild(root, "regions");
		if(regions != null){
			try {
				parser.parseSubRegions(regions);
			} catch (InvalidXMLException e) { 
				e.printStackTrace();
			}
		}
		
		modules.add(new RegionModule(new RFAContext()));
		return modules;
	}
}
