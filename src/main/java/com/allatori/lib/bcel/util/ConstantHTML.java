package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public final class ConstantHTML implements Constants {

  public PrintWriter file;

  public String class_name;

  public Method[] methods;

  public String[] constant_ref;

  public String class_package;

  public ConstantPool constant_pool;

  public Constant[] constants;

  public String referenceConstant(int value) {
    return this.constant_ref[value];
  }

  public final int getMethodNumber(String text) {
    int value;
    for (int value2 = value = 0; value2 < this.methods.length; value2 = ++value) {
      if (("" + this.methods[value].getText() + this.methods[value].getText2()).equals(text)) {
        return value;
      }
    }
    return -1;
  }

  public ConstantHTML(
      String text,
      String class_name,
      String class_package,
      Method[] methods2,
      ConstantPool constantPool)
      throws IOException {
    super();
    this.class_name = class_name;
    this.class_package = class_package;
    this.constant_pool = constantPool;
    this.methods = methods2;
    this.constants = constantPool.getConstantPool();
    this.file = new PrintWriter(new FileOutputStream(("" + text + class_name + "_cp.html")));
    this.constant_ref = new String[this.constants.length];
    int value = 1;
    this.constant_ref[0] = "&lt;unknown&gt;";
    this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
    for (int index = 1; value < this.constants.length; value = index) {
      ConstantHTML constantHTML;
      if (index % 2 == 0) {
        constantHTML = this;
        this.file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
      } else {
        constantHTML = this;
        this.file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
      }
      if (constantHTML.constants[index] != null) {
        this.writeConstant(index);
      }
      PrintWriter printWriter = this.file;
      index++;
      printWriter.print("</TD></TR>\n");
    }
    this.file.println("</TABLE></BODY></HTML>");
    this.file.close();
  }

  public void writeConstant(int value) {
    byte tag = this.constants[value].getTag();
    this.file.println("<H4> <A NAME=cp" + value + ">" + value + "</A> " + TEXTS7[tag] + "</H4>");
    switch (tag) {
      case 7:
        int nameIndex =
            ((ConstantClass) this.constant_pool.getConstant(value, (byte) 7)).getNameIndex();
        String text;
        String text2 =
            Utility.compactClassName(
                Utility.getText(text = this.constant_pool.getText(value, tag)),
                "" + this.class_package + ".",
                true);
        String text3 = "<A HREF=\"" + text + ".html\" TARGET=_top>" + text2 + "</A>";
        this.constant_ref[value] =
            "<A HREF=\""
                + this.class_name
                + "_cp.html#cp"
                + value
                + "\" TARGET=ConstantPool>"
                + text2
                + "</A>";
        this.file.println(
            "<P><TT>"
                + text3
                + "</TT><UL>"
                + "<LI><A HREF=\"#cp"
                + nameIndex
                + "\">Name index("
                + nameIndex
                + ")</A></UL>\n");
        return;
      case 8:
        int stringIndex =
            ((ConstantString) this.constant_pool.getConstant(value, (byte) 8)).getStringIndex();
        String text4 = Class2HTML.createText2(this.constant_pool.getText(value, tag));
        this.file.println(
            "<P><TT>"
                + text4
                + "</TT><UL>"
                + "<LI><A HREF=\"#cp"
                + stringIndex
                + "\">Name index("
                + stringIndex
                + ")</A></UL>\n");
        return;
      case 9:
        ConstantFieldref constantFieldref =
            (ConstantFieldref) this.constant_pool.getConstant(value, (byte) 9);
        int class_index = constantFieldref.getClass_index();
        int name_and_type_index = constantFieldref.getName_and_type_index();
        String text5;
        String text6 =
            Utility.compactClassName(
                Utility.getText(text5 = this.constant_pool.getText(class_index, (byte) 7)),
                "" + this.class_package + ".",
                true);
        String text7 = this.constant_pool.getText(name_and_type_index, (byte) 12);
        boolean matches = text5.equals(this.class_name);
        StringBuilder stringBuilder = new StringBuilder();
        String text8;
        ConstantHTML constantHTML;
        if (matches) {
          text8 =
              stringBuilder
                  .insert(0, "<A HREF=\"")
                  .append(text5)
                  .append("_methods.html#field")
                  .append(text7)
                  .append("\" TARGET=Methods>")
                  .append(text7)
                  .append("</A>")
                  .toString();
          constantHTML = this;
        } else {
          text8 =
              stringBuilder
                  .insert(0, "<A HREF=\"")
                  .append(text5)
                  .append(".html\" TARGET=_top>")
                  .append(text6)
                  .append("</A>.")
                  .append(text7)
                  .append("\n")
                  .toString();
          constantHTML = this;
        }
        constantHTML.constant_ref[value] =
            "<A HREF=\""
                + this.class_name
                + "_cp.html#cp"
                + class_index
                + "\" TARGET=Constants>"
                + text6
                + "</A>.<A HREF=\""
                + this.class_name
                + "_cp.html#cp"
                + value
                + "\" TARGET=ConstantPool>"
                + text7
                + "</A>";
        this.file.println(
            "<P><TT>"
                + text8
                + "</TT><BR>\n"
                + "<UL>"
                + "<LI><A HREF=\"#cp"
                + class_index
                + "\">Class("
                + class_index
                + ")</A><BR>\n"
                + "<LI><A HREF=\"#cp"
                + name_and_type_index
                + "\">NameAndType("
                + name_and_type_index
                + ")</A></UL>");
        return;
      case 10:
      case 11:
        int value2;
        int value3;
        ConstantHTML constantHTML2;
        if (tag == 10) {
          ConstantMethodref constantMethodref =
              (ConstantMethodref) this.constant_pool.getConstant(value, (byte) 10);
          constantHTML2 = this;
          value3 = constantMethodref.getClass_index();
          value2 = constantMethodref.getName_and_type_index();
        } else {
          ConstantInterfaceMethodref constantInterfaceMethodref =
              (ConstantInterfaceMethodref) this.constant_pool.getConstant(value, (byte) 11);
          constantHTML2 = this;
          value3 = constantInterfaceMethodref.getClass_index();
          value2 = constantInterfaceMethodref.getName_and_type_index();
        }
        String text9;
        String text10 =
            Class2HTML.createText2(text9 = constantHTML2.constant_pool.getText(value2, (byte) 12));
        String text11;
        String text12 = text11 = this.constant_pool.getText(value3, (byte) 7);
        Utility.getText(text12);
        String text13 = Utility.getText(text12);
        String text14 = Utility.compactClassName(text13, this.class_package + ".", true);
        ConstantNameAndType constantNameAndType =
            (ConstantNameAndType) this.constant_pool.getConstant(value2, (byte) 12);
        String text15;
        String text16 =
            text15 = this.constant_pool.getText(constantNameAndType.getSignature_index(), (byte) 1);
        String[] texts = Utility.methodSignatureArgumentTypes(text16, false);
        String text17 = Class2HTML.createText(Utility.createText(text16, false));
        StringBuffer stringBuffer = new StringBuffer("(");
        int value4;
        for (int value5 = value4 = 0; value5 < texts.length; value5 = ++value4) {
          stringBuffer.append(Class2HTML.createText(texts[value4]));
          if (value4 < texts.length - 1) {
            stringBuffer.append(",&nbsp;");
          }
        }
        stringBuffer.append(")");
        String text18 = stringBuffer.toString();
        boolean matches2 = text11.equals(this.class_name);
        StringBuilder stringBuilder2 = new StringBuilder();
        String text19;
        if (matches2) {
          text19 =
              stringBuilder2
                  .insert(0, "<A HREF=\"")
                  .append(this.class_name)
                  .append("_code.html#method")
                  .append(this.getMethodNumber("" + text9 + text15))
                  .append("\" TARGET=Code>")
                  .append(text10)
                  .append("</A>")
                  .toString();
          constantHTML2 = this;
        } else {
          text19 =
              stringBuilder2
                  .insert(0, "<A HREF=\"")
                  .append(text11)
                  .append(".html")
                  .append("\" TARGET=_top>")
                  .append(text14)
                  .append("</A>.")
                  .append(text10)
                  .toString();
          constantHTML2 = this;
        }
        constantHTML2.constant_ref[value] =
            ""
                + text17
                + "&nbsp;<A HREF=\""
                + this.class_name
                + "_cp.html#cp"
                + value3
                + "\" TARGET=Constants>"
                + text14
                + "</A>.<A HREF=\""
                + this.class_name
                + "_cp.html#cp"
                + value
                + "\" TARGET=ConstantPool>"
                + text10
                + "</A>&nbsp;"
                + text18;
        this.file.println(
            "<P><TT>"
                + text17
                + "&nbsp;"
                + text19
                + text18
                + "&nbsp;</TT>\n<UL>"
                + "<LI><A HREF=\"#cp"
                + value3
                + "\">Class index("
                + value3
                + ")</A>\n"
                + "<LI><A HREF=\"#cp"
                + value2
                + "\">NameAndType index("
                + value2
                + ")</A></UL>");
        return;
      case 12:
        ConstantNameAndType constantNameAndType2 =
            (ConstantNameAndType) this.constant_pool.getConstant(value, (byte) 12);
        int name_index = constantNameAndType2.getName_index();
        int signature_index = constantNameAndType2.getSignature_index();
        this.file.println(
            "<P><TT>"
                + Class2HTML.createText2(this.constant_pool.getText(value, tag))
                + "</TT><UL>"
                + "<LI><A HREF=\"#cp"
                + name_index
                + "\">Name index("
                + name_index
                + ")</A>\n"
                + "<LI><A HREF=\"#cp"
                + signature_index
                + "\">Signature index("
                + signature_index
                + ")</A></UL>\n");
        return;
      default:
        this.file.println(
            "<P><TT>" + Class2HTML.createText2(this.constant_pool.getText(value, tag)) + "</TT>\n");
    }
  }
}
