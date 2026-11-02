package com.allatori.stacktrace;

import com.allatori.util.LookupTable;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class MappingXmlHandler extends DefaultHandler {

  public StackTraceMappings stackTraceMappings;

  public String text;

  public String text2;

  public String text3;

  public String text4;

  public String text5;

  public String text6;

  public String text7;

  public String text8;

  public String text9;

  public String text10;

  public String text11;

  public String text12;

  public String text13;

  public MappingXmlHandler(StackTraceMappings stackTraceMappings2) {
    super();
    this.text5 = "old";
    this.text8 = "new";
    this.text = "class";
    this.text9 = "method";
    this.text3 = "source";
    this.text11 = "line";
    this.text10 = "l";
    this.text2 = "<init>";
    this.text6 = "<clinit>";
    this.text7 = "s";
    this.text13 = "e";
    this.stackTraceMappings = stackTraceMappings2;
    stackTraceMappings2.lookupTable2 = new LookupTable();
    stackTraceMappings2.lookupTable = new LookupTable();
    stackTraceMappings2.values = new Vector();
  }

  @Override
  public void startElement(String text14, String text15, String text16, Attributes attributes) {
    if (this.text.equals(text16)) {
      String value = attributes.getValue(this.text5);
      String value2 = attributes.getValue(this.text8);
      this.stackTraceMappings.lookupTable2.processText(value2, value);
      this.text4 = value + ".";
      this.text12 = "" + value2 + ".";
      this.stackTraceMappings.processText(
          "" + this.text12 + this.text2, "" + this.text4 + this.text2);
      this.stackTraceMappings.processText(
          "" + this.text12 + this.text6, "" + this.text4 + this.text6);
    } else if (this.text9.equals(text16)) {
      String value3 = attributes.getValue(this.text5);
      String value4 = attributes.getValue(this.text8);
      String text17 = value3.substring(0, value3.indexOf(40));
      if (attributes.getValue(this.text7) != null && attributes.getValue(this.text13) != null) {
        int value5 = Integer.valueOf(attributes.getValue(this.text7));
        int value6 = Integer.valueOf(attributes.getValue(this.text13));
        this.stackTraceMappings.processCount(
            "" + this.text12 + value4, "" + this.text4 + text17, value5, value6);
      } else {
        this.stackTraceMappings.processText("" + this.text12 + value4, "" + this.text4 + text17);
      }
    } else if (this.text3.equals(text16)) {
      String value7 = attributes.getValue(this.text5);
      String value8 = attributes.getValue(this.text8);
      this.stackTraceMappings.lookupTable.processText(value8, value7);
    } else {
      if (this.text11.equals(text16)) {
        Integer value9 = Integer.valueOf(attributes.getValue(this.text10));
        this.stackTraceMappings.values.add(value9);
      }
    }
  }
}
