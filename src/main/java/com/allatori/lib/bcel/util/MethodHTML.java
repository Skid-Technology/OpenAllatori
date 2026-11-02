package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public final class MethodHTML implements Constants {

  public ConstantHTML constant_html;

  public String class_name;

  public AttributeHTML attribute_html;

  public PrintWriter file;

  public final void writeMethod(Method method, int value) throws IOException {
    String text2 = method.getText2();
    String[] texts = Utility.methodSignatureArgumentTypes(text2, false);
    String text = Utility.createText(text2, false);
    String text3 = method.getText();
    String text22 = Utility.getText2(method.getAccess_flags2());
    Attribute[] attributes = method.getAttributes();
    String text4 = Utility.replace(text22, " ", "&nbsp;");
    String text5 = Class2HTML.createText2(text3);
    this.file.print(
        "<TR VALIGN=TOP><TD><FONT COLOR=\"#FF0000\"><A NAME=method"
            + value
            + ">"
            + text4
            + "</A></FONT></TD>");
    PrintWriter printWriter = this.file;
    StringBuilder stringBuilder = new StringBuilder();
    int value2 = 0;
    printWriter.print(
        stringBuilder
            .insert(0, "<TD>")
            .append(Class2HTML.createText(text))
            .append("</TD><TD>")
            .append("<A HREF=")
            .append(this.class_name)
            .append("_code.html#method")
            .append(value)
            .append(" TARGET=Code>")
            .append(text5)
            .append("</A></TD>\n<TD>(")
            .toString());
    for (int index = 0; value2 < texts.length; value2 = ++index) {
      this.file.print(Class2HTML.createText(texts[index]));
      if (index < texts.length - 1) {
        this.file.print(", ");
      }
    }
    this.file.print(")</TD></TR>");
    int value3;
    for (int value4 = value3 = 0; value4 < attributes.length; value4 = ++value3) {
      this.attribute_html.writeAttribute(
          attributes[value3], "method" + value + "@" + value3, value);
      byte value5;
      if ((value5 = attributes[value3].getTag()) != 3) {
        if (value5 == 2) {
          Attribute[] attributes2 = ((Code) attributes[value3]).getAttributes();
          int value6;
          for (int value7 = value6 = 0; value7 < attributes2.length; value7 = value6) {
            AttributeHTML attributeHTML = this.attribute_html;
            Attribute attribute = attributes2[value6];
            StringBuilder stringBuilder2 =
                new StringBuilder()
                    .insert(0, "method")
                    .append(value)
                    .append("@")
                    .append(value3)
                    .append("@")
                    .append(value6);
            value6++;
            attributeHTML.writeAttribute(attribute, stringBuilder2.toString(), value);
          }
        }
      } else {
        this.file.print("<TR VALIGN=TOP><TD COLSPAN=2></TD><TH ALIGN=LEFT>throws</TH><TD>");
        int[] exceptionIndexTable = ((ExceptionTable) attributes[value3]).getExceptionIndexTable();
        int value8;
        for (int value9 = value8 = 0; value9 < exceptionIndexTable.length; value9 = ++value8) {
          this.file.print(this.constant_html.referenceConstant(exceptionIndexTable[value8]));
          if (value8 < exceptionIndexTable.length - 1) {
            this.file.print(", ");
          }
        }
        this.file.println("</TD></TR>");
      }
    }
  }

  public void writeField(Field field) throws IOException {
    MethodHTML methodHTML;
    String text2 = Utility.getText2(field.getText2());
    String text = field.getText();
    String text3 = Utility.replace(Utility.getText2(field.getAccess_flags2()), " ", "&nbsp;");
    this.file.print(
        "<TR><TD><FONT COLOR=\"#FF0000\">"
            + text3
            + "</FONT></TD>\n<TD>"
            + Class2HTML.createText(text2)
            + "</TD><TD><A NAME=\"field"
            + text
            + "\">"
            + text
            + "</A></TD>");
    Attribute[] attributes = field.getAttributes();
    int value;
    for (int value2 = value = 0; value2 < attributes.length; value2 = value) {
      AttributeHTML attributeHTML = this.attribute_html;
      Attribute attribute = attributes[value];
      StringBuilder stringBuilder = new StringBuilder().insert(0, text).append("@");
      int value3 = value;
      value++;
      attributeHTML.writeAttribute(attribute, stringBuilder.append(value3).toString());
    }
    int value4;
    int value5 = value4 = 0;
    while (true) {
      if (value5 >= attributes.length) {
        methodHTML = this;
        break;
      }
      if (attributes[value4].getTag() == 1) {
        String text4 = ((ConstantValue) attributes[value4]).toString();
        PrintWriter printWriter = this.file;
        StringBuilder stringBuilder2 = new StringBuilder().insert(0, "<TD>= <A HREF=\"");
        methodHTML = this;
        printWriter.print(
            stringBuilder2
                .append(this.class_name)
                .append("_attributes.html#")
                .append(text)
                .append("@")
                .append(value4)
                .append("\" TARGET=\"Attributes\">")
                .append(text4)
                .append("</TD>\n")
                .toString());
        break;
      }
      value5 = ++value4;
    }
    methodHTML.file.println("</TR>");
  }

  public MethodHTML(
      String text,
      String class_name,
      Method[] methods,
      Field[] fields,
      ConstantHTML constantHTML,
      AttributeHTML attributeHTML)
      throws IOException {
    super();
    int value = 0;
    this.class_name = class_name;
    this.attribute_html = attributeHTML;
    this.constant_html = constantHTML;
    this.file = new PrintWriter(new FileOutputStream(("" + text + class_name + "_methods.html")));
    this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
    this.file.println(
        "<TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Type</TH><TH"
            + " ALIGN=LEFT>Field&nbsp;name</TH></TR>");
    for (int index = 0; value < fields.length; value = index) {
      int value2 = index;
      index++;
      this.writeField(fields[value2]);
    }
    this.file.println("</TABLE>");
    this.file.println(
        "<TABLE BORDER=0><TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH"
            + " ALIGN=LEFT>Return&nbsp;type</TH><TH ALIGN=LEFT>Method&nbsp;name</TH><TH"
            + " ALIGN=LEFT>Arguments</TH></TR>");
    int value3;
    for (int value4 = value3 = 0; value4 < methods.length; value4 = value3) {
      Method method = methods[value3];
      int value5 = value3;
      value3++;
      this.writeMethod(method, value5);
    }
    this.file.println("</TABLE></BODY></HTML>");
    this.file.close();
  }
}
