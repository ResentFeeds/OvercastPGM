package overcast.pgm.map;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.module.modules.info.InfoBuilder;
import overcast.pgm.module.modules.info.InfoModule;
import overcast.pgm.util.DocUtil;
import overcast.pgm.util.StringUtils;
import overcast.pgm.util.XMLUtils;

public class Map {

	private File source;
	private File xml;
	private Document document;
	private InfoModule info;   
	
	// TODO 
	private String[] objTags = {"wools", "wool", "cores", "core"};
	
	 
	
	public Map(File source) {
		this.source = source;
		this.xml = new File(source, "map.xml"); 
		this.document = DocUtil.parse(this.getXML()); 
		this.info = (InfoModule) new InfoBuilder().build(this.document).get(0);
	}
	
	
	public InfoModule getInfo(){
		return this.info;
	}
	
	
	public Document getDocument(){
		return this.document;
	}

	public File getSource() {
		return this.source;
	}

	public File getXML() {
		return this.xml;
	}
	
	
	public String getShortDescription(){
		String name = getInfo().getName();
		ChatColor gold = ChatColor.GOLD;
		ChatColor color = ChatColor.DARK_PURPLE;
		Set<String> names = getInfo().getAuthorNames().keySet();
		Collection<String> all = Collections.checkedSet(names, String.class);
		return gold + name + color + " by " + StringUtils.listToEnglishCompound(all);
	}

	
	// TODO 
	public boolean isObjectives(){
		Element root = getDocument().getDocumentElement();
		
		List<Element> childrens = XMLUtils.getChildElements(root);
		
		for(Element child : childrens){
			for(String objTag : objTags ){
			    if(child.getTagName().equalsIgnoreCase(objTag)){
					return true;
				}
			}
		} 
		return false;
	}
}
