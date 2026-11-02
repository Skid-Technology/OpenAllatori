package com.allatori.config;

import org.xml.sax.SAXException;

public class ConfigurationSaxException extends SAXException {

  public final XmlParsingContext xmlParsingContext;

  public ConfigurationSaxException(XmlParsingContext xmlParsingContext2, String text) {
    super(text);
    this.xmlParsingContext = xmlParsingContext2;
  }
}
