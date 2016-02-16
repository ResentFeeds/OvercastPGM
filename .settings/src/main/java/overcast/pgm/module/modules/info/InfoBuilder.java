package overcast.pgm.module.modules.info;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.MojangUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo()
public class InfoBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();

		Version proto = Version.parse(root.getAttribute("proto"));

		if (!root.hasAttribute("proto")) {
			try {
				throw new XMLParseException("proto='' attribute is not there");
			} catch (XMLParseException e) {
				e.printStackTrace();
			}
		}

		String name = root.getElementsByTagName("name").item(0)
				.getTextContent();

		String objective = root.getElementsByTagName("objective").item(0)
				.getTextContent();

		Version version = null;
		try {
			version = parseVersion(root, "version");
		} catch (XMLParseException e) {
			e.printStackTrace();
		}

		List<Author> authors = parseAuthors(root, "authors", "author");

		if (authors.size() == 0) {
			try {
				throw new XMLParseException(
						"At least one map author is required.");
			} catch (XMLParseException e) {
				e.printStackTrace();
			}
		}

		List<Contributor> contributors = parseContributors(root,
				"contributors", "contributor");

		List<Rule> rules = parseRules(root, "rules", "rule");

		InfoModule info = new InfoModule(proto, name, objective, version,
				authors, contributors, rules);
		modules.add(info);

		return modules;
	}

	private Version parseVersion(Element root, String versionTag)
			throws XMLParseException {
		Node node = root.getElementsByTagName(versionTag).item(0);
		if (node == null) {
			throw new XMLParseException("<version> -- tag missing on a map");
		}

		String text = node.getTextContent();

		Version version = Version.parse(text);
		return version;
	}

	private List<Rule> parseRules(Element root, String rulesTag, String ruleTag) {
		List<Rule> rules = new ArrayList<>();
		Node rulesNode = root.getElementsByTagName(rulesTag).item(0);
		if (rulesNode == null) {
			rules.add(new Rule(ChatColor.RED + "No Rules"));
		} else {
			if (rulesNode.getNodeType() == Node.ELEMENT_NODE) {
				Element rulesElement = (Element) rulesNode;
				NodeList rulesList = rulesElement.getChildNodes();
				for (int i = 0; i < rulesList.getLength(); i++) {
					Node ruleNode = rulesList.item(i);
					if (ruleNode.getNodeType() == Node.ELEMENT_NODE
							&& ruleNode.getNodeName().equals(ruleTag)) {
						Element ruleElement = (Element) ruleNode;
						String ruleText = BukkitUtils.colorize(ruleElement
								.getTextContent());
						rules.add(new Rule(ruleText));
					}
				}
			}
		}
		return rules;
	}

	private List<Author> parseAuthors(Element root, String authorsTag,
			String authorTag) {
		List<Author> authors = new ArrayList<>();
		Node authorsNode = root.getElementsByTagName(authorsTag).item(0);
		if (authorsNode.getNodeType() == Node.ELEMENT_NODE) {
			Element authorsElement = (Element) authorsNode;

			NodeList authorList = authorsElement.getChildNodes();

			for (int i = 0; i < authorList.getLength(); i++) {
				Node node = authorList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE
						&& node.getNodeName().equals(authorTag)) {
					Element element = (Element) node;

					String contribution = null;

					UUID uuid = MojangUtils.getUUID(element
							.getAttribute("uuid"));

					if (element.hasAttribute("contribution")) {
						contribution = element.getAttribute("contribution");
					}

					authors.add(new Author(uuid, contribution));
				}
			}

		}
		return authors;
	}

	public List<Contributor> parseContributors(Element root,
			String contribsTag, String contribTag) {
		List<Contributor> contributors = new ArrayList<>();
		Node authorsNode = root.getElementsByTagName(contribsTag).item(0);
		if (authorsNode != null) {
			if (authorsNode.getNodeType() == Node.ELEMENT_NODE) {
				Element authorsElement = (Element) authorsNode;

				NodeList authorList = authorsElement.getChildNodes();

				for (int i = 0; i < authorList.getLength(); i++) {
					Node node = authorList.item(i);

					if (node.getNodeType() == Node.ELEMENT_NODE
							&& node.getNodeName().equals(contribTag)) {
						Element element = (Element) node;

						String contribution = null;

						UUID uuid = MojangUtils.getUUID(element
								.getAttribute("uuid"));

						if (element.hasAttribute("contribution")) {
							contribution = element.getAttribute("contribution");
						}

						contributors.add(new Contributor(uuid, contribution));
					}
				}

			}
		} else {
			return null;
		}
		return contributors;
	}

}