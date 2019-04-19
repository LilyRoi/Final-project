package parser.java.stax;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import parser.java.model.VKparameters;


public class StaxParser {
	private static final String ID = "id";
	private static final String OWNER_ID = "owner_id";
	private static final String MESSAGE = "message";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String PARAMETER = "Parameter";

	private VKparameters parameter;
	List<VKparameters> vkparameters = new ArrayList<>();

	public List<VKparameters> parse(XMLEventReader xmlEventReader) throws FileNotFoundException, XMLStreamException {
		while (xmlEventReader.hasNext()) {
			XMLEvent xmlEvent = xmlEventReader.nextEvent();
			proceedStartElement(xmlEvent, xmlEventReader);
			proceedEndElement(xmlEvent);
		}
		return vkparameters;
	}

	private void proceedStartElement(XMLEvent xmlEvent, XMLEventReader xmlEventReader) throws XMLStreamException {
		if (xmlEvent.isStartElement()) {
			StartElement startElement = xmlEvent.asStartElement();
			if (isTagNameEqual(startElement, PARAMETER)) {
				parameter = new VKparameters();
				Attribute attribute = startElement.getAttributeByName(new QName(ID));
				if (attribute != null) {
					parameter.setId(Integer.parseInt(attribute.getValue()));
				}
			}
			// set the other varibles from xml elements
			else if (isTagNameEqual(startElement, OWNER_ID)) {
				parameter.setOwner_id(xmlEventReader.nextEvent().asCharacters().getData());
			} else if (isTagNameEqual(startElement, MESSAGE)) {
				parameter.setMessage(xmlEventReader.nextEvent().asCharacters().getData());
			} else if (isTagNameEqual(startElement, ACCESS_TOKEN)) {
				parameter.setAccess_token(xmlEventReader.nextEvent().asCharacters().getData());
			}
		}
	}

	private void proceedEndElement(XMLEvent xmlEvent) {
		if (xmlEvent.isEndElement()) {
			EndElement endElement = xmlEvent.asEndElement();
			if (endElement.getName().getLocalPart().equals(PARAMETER)) {
				vkparameters.add(parameter);
			}
		}
	}

	private boolean isTagNameEqual(StartElement startElement, String tagName) {
		return startElement.getName().getLocalPart().equals(tagName);
	}
}
