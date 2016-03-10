package overcast.pgm.module.modules.maxheight;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.NumberUtils;
@BuilderInfo()
public class MaxBuildHeightBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		
		Node node = root.getElementsByTagName("maxbuildheight").item(0);
		
		int height = 100;
		
		if(node != null){
			height = NumberUtils.parseInteger(node.getTextContent());
		}
		
		MaxHeightModule maxheightModule = new MaxHeightModule(height);
		
		modules.add(maxheightModule);
		
		return modules;
		
	}

}
