package overcast.pgm.module.modules.region.parsers;

import org.bukkit.util.Vector;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.parser.ElementParser;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;

public class PointParser extends ElementParser {

	private Vector point;
	private float yaw;
	private float pitch;

	public PointParser(Element element) {
		super(element); 
		
		
		Node pointNode = element.getElementsByTagName("point").item(0);
		
		if(pointNode != null){
			if(pointNode.getNodeType() == Node.ELEMENT_NODE){
				Element pointElement = (Element) pointNode;
				this.point = XMLUtils.parseVector(pointElement);
				this.yaw = NumberUtils.parseFloat(pointElement.getAttribute("yaw"));
				this.pitch = NumberUtils.parseFloat(pointElement.getAttribute("pitch"));
			}
		}
	}
	
	
	
	public Vector getPoint(){
		return this.point;
	}
	
	
	public float getYaw(){
		return this.yaw;
	}

	public float getPitch(){
		return this.pitch;
	}
}
