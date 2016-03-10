package overcast.pgm.module.modules.kits.parsers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
 
import overcast.pgm.module.modules.kits.KitParser;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.StringUtils;
import overcast.pgm.util.XMLUtils;

public class BookKitParser extends KitParser {
 
	
	private String title;
	private String author;
	private List<String> pages;
	private int slot;

	public BookKitParser(Element element) {
		super(element); 
		if(element != null){ 
			parseBook(element);
		}
	}

	public void parseBook(Element element) {
		Element titleEl = XMLUtils.getChildElement(element, "title");
		if (StringUtils.hasLessCharacters(titleEl.getTextContent(), 16)) {
			this.title = BukkitUtils.colorize(titleEl.getTextContent());
		}

		Element authorEl = XMLUtils.getChildElement(element, "author");
		this.author = BukkitUtils.colorize(authorEl.getTextContent());
		this.pages  = new ArrayList<>();
		this.slot = NumberUtils.parseInteger(element.getAttribute("slot"));
		List<Element> children = XMLUtils.getChildElements(element);
		
		for(Element child : children){
			 if(child != null){
				 if(child.getTagName().equals("pages")){
					 List<Element> pages = XMLUtils.getChildElements(child);
					 
					 for(Element page : pages){
						 if(page != null && page.getTagName().equals("page")){
							 String text = page.getTextContent();
							 
							 if(text != null){
								 String colored = BukkitUtils.colorize(text) != null ? BukkitUtils.colorize(text) : text;
								 
								 this.pages.add(colored);
							 }
						 }
					 }
				 }
			 }
		}
	} 
	
	
	public List<String> getPages(){
		return this.pages;
	}
	
	
	public String getAuthor(){
		return this.author;
	}
	
	public String getTitle(){
		return this.title;
	}

	public int getSlot() {
		return this.slot;
	}
}
