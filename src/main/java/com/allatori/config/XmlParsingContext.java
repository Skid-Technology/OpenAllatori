package com.allatori.config;

import java.io.FileReader;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class XmlParsingContext implements ConfigurationSource {

  public String text;

  @Override
  public void parse() throws ConfigurationException {
    try {
      FileReader fileReader = new FileReader(this.text);
      XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      xMLReader.setContentHandler(new ConfigurationHandler(this, null));
      xMLReader.parse(new InputSource(fileReader));
    } catch (ConfigurationSaxException configurationSaxException) {
      throw new ConfigurationException(configurationSaxException.getMessage());
    } catch (Exception exception) {
      throw new ConfigurationException(exception);
    }
  }

  public XmlParsingContext(String text) {
    super();
    this.text = text;
  }

  public static String getText(XmlParsingContext xmlParsingContext) {
    return xmlParsingContext.text;
  }
}
