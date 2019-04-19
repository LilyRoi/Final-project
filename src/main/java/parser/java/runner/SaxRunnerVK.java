package parser.java.runner;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import parser.java.model.VKparameters;
import parser.java.sax.ParameterHandler;

public class SaxRunnerVK {
	private static final String VKPARAMETERS_XML = "vkparameters.xml";

	public static VKparameters getVKParameters(int index) throws Exception {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		ParameterHandler parameterHandler = new ParameterHandler();
		saxParser.parse(new File(VKPARAMETERS_XML), parameterHandler);
		List<VKparameters> vkparameters = parameterHandler.getVkparameters();
		VKparameters parameters = vkparameters.get(index);
		return parameters;
	}

}
