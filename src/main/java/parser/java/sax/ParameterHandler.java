package parser.java.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parser.java.model.VKparameters;

public class ParameterHandler extends DefaultHandler {
	private List<VKparameters> vkparameters;
	private VKparameters parameter;
	boolean bOwner_id = false;
	boolean bMessage = false;
	boolean bAccess_token = false;

	public List<VKparameters> getVkparameters() {
		return vkparameters;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Parameter")) {
			String id = attributes.getValue("id");
			parameter = new VKparameters();
			parameter.setId(Integer.parseInt(id));
			if (vkparameters == null) {
				vkparameters = new ArrayList<>();
			}
		} else if (qName.equalsIgnoreCase("owner_id")) {
			bOwner_id = true;
		} else if (qName.equalsIgnoreCase("message")) {
			bMessage = true;
		} else if (qName.equalsIgnoreCase("access_token")) {
			bAccess_token = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("Parameter")) {
			vkparameters.add(parameter);
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (bOwner_id) {
			parameter.setOwner_id(new String(ch, start, length));
			bOwner_id = false;
		} else if (bMessage) {
			parameter.setMessage(new String(ch, start, length));
			bMessage = false;
		} else if (bAccess_token) {
			parameter.setAccess_token(new String(ch, start, length));
			bAccess_token = false;
		}
	}
}
