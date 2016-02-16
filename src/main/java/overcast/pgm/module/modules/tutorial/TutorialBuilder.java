package overcast.pgm.module.modules.tutorial;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.tutorial.parser.TutorialStageParser;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;
@BuilderInfo(stage = ModuleStage.START)
public class TutorialBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();

		Element root = doc.getDocumentElement();

		Node node = root.getElementsByTagName("tutorial").item(0);
		List<TutorialStage> stages = new ArrayList<>();
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
			Element stagesElement = (Element) node;

			stages.add(parseTutorialStage(stagesElement, "stage"));
			TutorialModule tutorial = new TutorialModule(stages);
			modules.add(tutorial);
		}
		return modules;
	}

	public static TutorialStage parseTutorialStage(Element parent, String stageTag) {
		if (parent != null) {
			List<Element> children = XMLUtils.getChildElements(parent);

			for (Element child : children) {
				if (child != null) {
					if (child.getTagName().equals(stageTag)) {
						TutorialStage stage = new TutorialStage(new TutorialStageParser(child));
						return stage;
					}
				}
			}
		}
		return null;
	}

}
