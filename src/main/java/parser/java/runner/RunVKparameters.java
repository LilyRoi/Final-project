package parser.java.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import parser.java.model.VKparameters;
import parser.java.sax.ParameterHandler;

public class RunVKparameters {

	private static final String VKPARAMETERS_XML = "vkparameters.xml";

	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, XMLStreamException {
		System.out.println(" ========================= SAX parser ==============================");
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		ParameterHandler parameterHandler = new ParameterHandler();
		saxParser.parse(new File(VKPARAMETERS_XML), parameterHandler);
		List<VKparameters> vkparameters = parameterHandler.getVkparameters();
		for (VKparameters parameter : vkparameters) {
			System.out.println(parameter);
		}

		System.out.println(" ============================== STAX parser =========================");
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(VKPARAMETERS_XML));
		vkparameters = new parser.java.stax.StaxParser().parse(xmlEventReader);
		for (VKparameters parameter : vkparameters) {
			System.out.println(parameter);
		}
		System.out.println(" ============================== DOM parser =========================");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(VKPARAMETERS_XML);
		vkparameters = new parser.java.dom.DomParser().parse(document);
		for (VKparameters parameter : vkparameters) {
			System.out.println(parameter);
		}
	}

}
