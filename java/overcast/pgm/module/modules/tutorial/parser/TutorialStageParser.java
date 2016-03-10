package overcast.pgm.module.modules.tutorial.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.module.modules.region.parsers.PointParser;
import overcast.pgm.module.modules.region.types.PointProvider;
import overcast.pgm.parser.ElementParser;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.XMLUtils;

public class TutorialStageParser extends ElementParser {

	private String title;
	private List<String> lines;
	private PointProvider provider;
	
	
	public TutorialStageParser(Element element) {
		super(element); 
		this.lines = new ArrayList<>();
		if(element != null){
			this.title = element.getAttribute("title") != null ? BukkitUtils.colorize(element.getAttribute("title")) : null; 
			

			Node node = element.getElementsByTagName("message").item(0);
			
			if(node != null){
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element messageElement = (Element) node;
					
					List<Element> children = XMLUtils.getChildElements(messageElement);
					
					
					for(Element child : children){
						if(child != null){
							switch(child.getTagName()){
							case "line":
								this.lines.add(BukkitUtils.colorize(child.getTextContent()));
								break;
							}
						}
					}
				}
			}
			 
			
			Node teleport = element.getElementsByTagName("teleport").item(0);
			
			if(teleport != null){
				if(teleport.getNodeType() == Node.ELEMENT_NODE){
					Element teleportElement = (Element) teleport;
					PointParser parser = new PointParser(teleportElement);
					
					this.provider = new PointProvider(parser);
				}
			}
		}
	}
	
	
	
	public String getTitle(){
		return this.title;
	}
	
	
	public PointProvider getPointProvider(){
		return this.provider;
	}
	
	
	public List<String> getMessage(){
		return this.lines;
	}
}
