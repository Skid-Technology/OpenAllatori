package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.util.LookupTable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class MappingImportHandler extends DefaultHandler {

  public LookupTable lookupTable;

  public String text;

  public String text2;

  public String text3;

  public String text4;

  public String text5;

  public final NameRenamer nameRenamer;

  public String text6;

  public String text7;

  public String text8;

  public String text9;

  public String text10;

  public String text11;

  public String text12;

  public String text13;

  public String text14;

  public String text15;

  @Override
  public void startElement(String text16, String text17, String text18, Attributes attributes) {
    if (this.text15.equals(text18)) {
      this.text11 = attributes.getValue(this.text8);
      this.text14 = attributes.getValue(this.text);
      if (NameRenamer.isEnabled()) {
        String text22 = NameUtils.getText2(this.text14);
        if (NameRenamer.getLookupTable().matchesText(text22)) {
          this.text14 =
              NameRenamer.getLookupTable().getText(text22) + this.text14.substring(text22.length());
        }
      }
      ParameterNameTable.getLookupTable(
              NamingContext.getParameterNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
          .processText(this.text11, this.text14);
      ParameterNameTable.getLookupTable3(
              NamingContext.getParameterNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
          .processText(this.text14, this.text11);
      this.update3();
    } else {
      if (this.text7.equals(text18)) {
        String value = attributes.getValue(this.text8);
        String value2 = attributes.getValue(this.text);
        String text19 = value.substring(0, value.indexOf("("));
        String text20 = value.substring(value.indexOf("("));
        MethodNameTable.getLookupTable2(
                NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText("" + this.text11 + "&" + text19 + "&" + text20, value2);
        String text21 = "" + this.text11 + "&" + value2 + "&" + NameUtils.getText(text20);
        if (MethodNameTable.getLookupTable3(
                NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .matchesText(text21)) {
          this.lookupTable.processText(text21, this.text11);
        }
        MethodNameTable.getLookupTable3(
                NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText(text21, "&");
        if (Configuration.getRenamingOptions().isEnabled8() && !value2.equals(text19)) {
          MethodNameTable.getLookupTable4(
                  NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
              .processText("" + text19 + "&" + NameUtils.getText(text20), value2);
          return;
        }
      } else {
        if (this.text3.equals(text18)) {
          String value3 = attributes.getValue(this.text8);
          String value4 = attributes.getValue(this.text);
          String text23 = value3.substring(0, value3.indexOf(this.text12));
          String text24 = value3.substring(value3.indexOf(this.text12) + 1);
          FieldNameTable.getLookupTable(
                  NamingContext.getFieldNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
              .processText("" + this.text11 + "&" + text23 + "&" + text24, value4);
          FieldNameTable.getLookupTable3(
                  NamingContext.getFieldNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
              .processText("" + this.text11 + "&" + value4 + "&" + text24, "&");
          return;
        }
        if (this.text2.equals(text18)) {
          String value5 = attributes.getValue(this.text8);
          String value6 = attributes.getValue(this.text);
          NameRenamer.getNameMappingEntry(this.nameRenamer).processText(value5, value6);
          return;
        }
        if (this.text4.equals(text18)) {
          Integer value7 = Integer.valueOf(attributes.getValue(this.text10));
          NameRenamer.getNameMappingEntry(this.nameRenamer).getValues().add(value7);
          return;
        }
        if (this.text13.equals(text18)) {
          String value8 = attributes.getValue(this.text6);
          String value9 = attributes.getValue(this.text9);
          String value10 = attributes.getValue(this.text5);
          MethodNameTable.getLookupTable5(
                  NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
              .processText("" + value8 + "&" + value9, value10);
        }
      }
    }
  }

  public void update() {
    try {
      Vector vector = new Vector(this.lookupTable.getEntries2());
      int value;
      for (int value2 = value = 0; value2 < vector.size(); value2 = ++value) {
        String text = (String) vector.get(value);
        String text2 = this.lookupTable.getText(text);
        ClassGen classGen;
        if ((classGen = NameRenamer.getClassStorage(this.nameRenamer).getClassGen(text2)) != null) {
          String[] interfaceNames = classGen.getInterfaceNames();
          int value3;
          for (int value4 = value3 = 0; value4 < interfaceNames.length; value4 = value3) {
            String text3 = interfaceNames[value3];
            LookupTable lookupTable2 = this.lookupTable;
            StringBuilder stringBuilder =
                new StringBuilder().insert(0, text3).append(text.substring(text.indexOf("&")));
            value3++;
            lookupTable2.processText(stringBuilder.toString(), text3);
          }
        }
      }
      Set entries2 = this.lookupTable.getEntries2();
      Vector vector2 =
          new Vector(
              MethodNameTable.getLookupTable2(
                      NamingContext.getMethodNameTable(
                          NameRenamer.getNamingContext(this.nameRenamer)))
                  .getEntries2());
      for (String text4 : ((Iterable<String>) ((Iterable<?>) entries2))) {
        String text5 = this.lookupTable.getText(text4);
        Iterator iterator2 = vector2.iterator();
        while (iterator2.hasNext()) {
          String text6;
          if ((text6 = (String) iterator2.next()).startsWith(text5)) {
            String text7 =
                MethodNameTable.getLookupTable2(
                        NamingContext.getMethodNameTable(
                            NameRenamer.getNamingContext(this.nameRenamer)))
                    .getText(text6);
            if (text4.substring(text4.indexOf("&") + 1, text4.lastIndexOf("&")).equals(text7)) {
              String text8 = text4.substring(text4.lastIndexOf("&") + 1);
              String text9 = text6.substring(text6.lastIndexOf("&") + 1);
              if (text8.equals(NameUtils.getText(text9))) {
                MethodNameTable.getLookupTable2(
                        NamingContext.getMethodNameTable(
                            NameRenamer.getNamingContext(this.nameRenamer)))
                    .processText(text6);
                MethodNameTable.getLookupTable3(
                        NamingContext.getMethodNameTable(
                            NameRenamer.getNamingContext(this.nameRenamer)))
                    .processText(text4);
              }
            }
          }
        }
      }
    } catch (Exception exception) {
    }
  }

  public String createText(String text, LookupTable lookupTable) {
    String text2 = "";
    while (text.length() > 0) {
      int value = text.indexOf(76);
      int value2 = text.indexOf(59);
      if (value != -1 && value2 != -1 && value < value2) {
        String text3 = text.substring(value + 1, value2).replace('/', '.');
        if (lookupTable.getText(text3) != null) {
          text3 = lookupTable.getText(text3);
        }
        String text4 = text3.replace('.', '/');
        text2 = "" + text2 + text.substring(0, value + 1) + text4 + ";";
        text = text.substring(value2 + 1);
      } else {
        text2 = "" + text2 + text;
        text = "";
      }
    }
    return text2;
  }

  public static void processMappingImportHandler(MappingImportHandler mappingImportHandler)
      throws ParserConfigurationException,
          SAXException,
          FileNotFoundException,
          UnsupportedEncodingException,
          IOException {
    mappingImportHandler.update4();
  }

  public void update2() {
    for (String text :
        ((Iterable<String>)
            ((Iterable<?>)
                FieldNameTable.getLookupTable(
                        NamingContext.getFieldNameTable(
                            NameRenamer.getNamingContext(this.nameRenamer)))
                    .getEntries2()))) {
      String text2;
      String text3 =
          this.createText(
              text2 = text.substring(text.lastIndexOf("&") + 1),
              ParameterNameTable.getLookupTable(
                  NamingContext.getParameterNameTable(
                      NameRenamer.getNamingContext(this.nameRenamer))));
      if (!text2.equals(text3)) {
        FieldNameTable.getLookupTable2(
                NamingContext.getFieldNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText(text2, text3);
      }
    }
    for (String text4 :
        ((Iterable<String>)
            ((Iterable<?>)
                MethodNameTable.getLookupTable2(
                        NamingContext.getMethodNameTable(
                            NameRenamer.getNamingContext(this.nameRenamer)))
                    .getEntries2()))) {
      String text5;
      String text6 =
          this.createText(
              text5 = text4.substring(text4.lastIndexOf("&") + 1),
              ParameterNameTable.getLookupTable(
                  NamingContext.getParameterNameTable(
                      NameRenamer.getNamingContext(this.nameRenamer))));
      if (!text5.equals(text6)) {
        MethodNameTable.getLookupTable(
                NamingContext.getMethodNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText(text5, text6);
      }
    }
  }

  public MappingImportHandler(NameRenamer nameRenamer2) {
    super();
    this.nameRenamer = nameRenamer2;
    this.lookupTable = new LookupTable();
    this.text8 = "old";
    this.text = "new";
    this.text15 = "class";
    this.text7 = "method";
    this.text3 = "field";
    this.text2 = "source";
    this.text4 = "line";
    this.text10 = "l";
    this.text13 = "annotation";
    this.text6 = "oldClassName";
    this.text9 = "oldMethodName";
    this.text5 = "newMethodName";
    this.text12 = " ";
  }

  public void update3() {
    String text2 = NameUtils.getText2(this.text11);
    String text22 = NameUtils.getText2(this.text14);
    if (!Configuration.getRenamingOptions().isText()
        || !Configuration.getRenamingOptions().getDefaultPackage().equals(text22)) {
      int value = 0;
      int value2;
      for (int value3 = value2 = text2.length() - 1; value3 > 0; value3 = --value2) {
        if (text2.charAt(value2) == '.') {
          value++;
        }
      }
      int value4;
      for (int value5 = value4 = text22.length() - 1; value5 > 0; value5 = --value4) {
        if (text22.charAt(value4) == '.') {
          value--;
        }
      }
      if (value == 0) {
        ClassNameTable.getLookupTable2(
                NamingContext.getClassNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText(text2, text22);
        ClassNameTable.getLookupTable(
                NamingContext.getClassNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
            .processText(text22, "&");
        String text = text2;
        while (text.lastIndexOf(46) > 0) {
          text2 = text2.substring(0, text2.lastIndexOf(46));
          text22 = text22.substring(0, text22.lastIndexOf(46));
          LookupTable lookupTable2 =
              ClassNameTable.getLookupTable2(
                  NamingContext.getClassNameTable(NameRenamer.getNamingContext(this.nameRenamer)));
          text = text2;
          lookupTable2.processText(text2, text22);
          ClassNameTable.getLookupTable(
                  NamingContext.getClassNameTable(NameRenamer.getNamingContext(this.nameRenamer)))
              .processText(text22, "&");
        }
      }
    }
  }

  public MappingImportHandler(
      NameRenamer nameRenamer, NamingConstructorToken namingConstructorToken) {
    this(nameRenamer);
  }

  public void update4()
      throws ParserConfigurationException,
          SAXException,
          FileNotFoundException,
          UnsupportedEncodingException,
          IOException {
    XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    xMLReader.setContentHandler(this);
    xMLReader.parse(
        new InputSource(
            new InputStreamReader(
                new FileInputStream(Configuration.getRenamingOptions().getIncrementalObfuscation()),
                "UTF-8")));
    this.update2();
    this.update();
  }
}
