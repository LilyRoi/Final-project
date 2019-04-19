package parser.java.dom;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import parser.java.model.VKparameters;

public class DomParser {
	public List<VKparameters> parse(Document document) throws FileNotFoundException, XMLStreamException {
		NodeList nodeList = document.getElementsByTagName("Parameter");
		List<VKparameters> vkparameters = new ArrayList<VKparameters>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			vkparameters.add(getParameter(nodeList.item(i)));
		}
		return vkparameters;
	}

	private static VKparameters getParameter(Node node) {
		VKparameters parameter = new VKparameters();
		Element element = (Element) node;
		parameter.setId(Integer.parseInt(element.getAttribute("id")));
		parameter.setOwner_id(getTagValue("owner_id", element));
		parameter.setMessage(getTagValue("message", element));
		parameter.setAccess_token(getTagValue("access_token", element));
		return parameter;
	}

	private static String getTagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}
}
