package com.allatori.stacktrace;

import java.io.IOException;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class MappingXmlReader {

  public static StackTraceMappings createStackTraceMappings(Reader reader)
      throws ParserConfigurationException, SAXException, IOException {
    XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    StackTraceMappings stackTraceMappings;
    StackTraceMappings stackTraceMappings2 = stackTraceMappings = new StackTraceMappings();
    xMLReader.setContentHandler(new MappingXmlHandler(stackTraceMappings));
    xMLReader.parse(new InputSource(reader));
    return stackTraceMappings2;
  }
}
