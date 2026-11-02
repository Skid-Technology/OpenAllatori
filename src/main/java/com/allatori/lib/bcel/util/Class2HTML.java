package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Class2HTML implements Constants {

  public static String text;

  public String dir;

  public static ConstantPool constant_pool;

  public static String text2;

  public JavaClass java_class;

  public static final String createText(String text) {
    String text3 = Utility.getText(text);
    String text4 = Utility.compactClassName(text3, text2 + ".", true);
    int value = text.indexOf(91);
    String text5 = text;
    if (value > -1) {
      text5 = text.substring(0, value);
    }
    return !text5.equals("int")
            && !text5.equals("short")
            && !text5.equals("boolean")
            && !text5.equals("void")
            && !text5.equals("char")
            && !text5.equals("byte")
            && !text5.equals("long")
            && !text5.equals("double")
            && !text5.equals("float")
        ? ("<A HREF=\"" + text5 + ".html\" TARGET=_top>" + text4 + "</A>")
        : ("<FONT COLOR=\"#00FF00\">" + text + "</FONT>");
  }

  public void writeMainHTML(AttributeHTML attributeHTML) throws IOException {
    PrintWriter printWriter =
        new PrintWriter(new FileOutputStream(("" + this.dir + text + ".html")));
    Attribute[] attributes = this.java_class.getAttributes();
    int value = 0;
    printWriter.println(
        "<HTML>\n<HEAD><TITLE>Documentation for "
            + text
            + "</TITLE>"
            + "</HEAD>\n"
            + "<FRAMESET BORDER=1 cols=\"30%,*\">\n"
            + "<FRAMESET BORDER=1 rows=\"80%,*\">\n"
            + "<FRAME NAME=\"ConstantPool\" SRC=\""
            + text
            + "_cp.html"
            + "\"\n MARGINWIDTH=\"0\" "
            + "MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n"
            + "<FRAME NAME=\"Attributes\" SRC=\""
            + text
            + "_attributes.html"
            + "\"\n MARGINWIDTH=\"0\" "
            + "MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n"
            + "</FRAMESET>\n"
            + "<FRAMESET BORDER=1 rows=\"80%,*\">\n"
            + "<FRAME NAME=\"Code\" SRC=\""
            + text
            + "_code.html\"\n MARGINWIDTH=0 "
            + "MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n"
            + "<FRAME NAME=\"Methods\" SRC=\""
            + text
            + "_methods.html\"\n MARGINWIDTH=0 "
            + "MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n"
            + "</FRAMESET></FRAMESET></HTML>");
    printWriter.close();
    for (int index = 0; value < attributes.length; value = index) {
      Attribute attribute = attributes[index];
      StringBuilder stringBuilder = new StringBuilder().insert(0, "class");
      int value2 = index;
      index++;
      attributeHTML.writeAttribute(attribute, stringBuilder.append(value2).toString());
    }
  }

  public static String referenceClass(int value) {
    String text3 = Utility.getText(constant_pool.createText(value, (byte) 7));
    String text4 = Utility.compactClassName(text3, text2 + ".", true);
    return "<A HREF=\"" + text + "_cp.html#cp" + value + "\" TARGET=ConstantPool>" + text4 + "</A>";
  }

  public static String createText2(String text) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      for (int index = 0; index < text.length(); index++) {
        char value;
        switch (value = text.charAt(index)) {
          case '\n':
            stringBuffer.append("\\n");
            break;
          case '\r':
            stringBuffer.append("\\r");
            break;
          case '<':
            stringBuffer.append("&lt;");
            break;
          case '>':
            stringBuffer.append("&gt;");
            break;
          default:
            stringBuffer.append(value);
        }
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
    }
    return stringBuffer.toString();
  }

  public Class2HTML(JavaClass javaClass, String dir) throws IOException {
    Method[] methods = javaClass.getMethods();
    this.java_class = javaClass;
    this.dir = dir;
    text = javaClass.getText2();
    constant_pool = javaClass.getConstantPool();
    int value;
    if ((value = text.lastIndexOf(46)) > -1) {
      text2 = text.substring(0, value);
    } else {
      text2 = "";
    }
    ConstantHTML constantHTML = new ConstantHTML(dir, text, text2, methods, constant_pool);
    AttributeHTML attributeHTML = new AttributeHTML(dir, text, constant_pool, constantHTML);
    new MethodHTML(dir, text, methods, javaClass.getFields(), constantHTML, attributeHTML);
    this.writeMainHTML(attributeHTML);
    new CodeHTML(dir, text, methods, constant_pool, constantHTML);
    attributeHTML.close();
  }

  public static void main(String[] texts) throws IOException {
    String[] texts2 = new String[texts.length];
    int value = 0;
    String text = null;
    char value2 = System.getProperty("file.separator").toCharArray()[0];
    String text2 = "." + value2;
    try {
      int value3;
      for (int value4 = value3 = 0; value4 < texts.length; value4 = ++value3) {
        if (texts[value3].charAt(0) == '-') {
          if (texts[value3].equals("-d")) {
            text2 = texts[++value3];
            if (!text2.endsWith("" + value2)) {
              text2 = "" + text2 + value2;
            }
            new File(text2).mkdirs();
          } else if (texts[value3].equals("-zip")) {
            text = texts[++value3];
          } else {
            System.out.println("Unknown option " + texts[value3]);
          }
        } else {
          int value5 = value3;
          int value6 = value;
          value++;
          texts2[value6] = texts[value5];
        }
      }
      if (value == 0) {
        System.err.println("Class2HTML: No input files specified.");
        return;
      }
      int value7 = 0;
      while (value7 < value) {
        System.out.print("Processing " + texts2[value7] + "...");
        ClassParser classParser;
        if (text == null) {
          classParser = new ClassParser(texts2[value7]);
        } else {
          classParser = new ClassParser(text, texts2[value7]);
        }
        JavaClass javaClass = classParser.parse();
        new Class2HTML(javaClass, text2);
        PrintStream printStream = System.out;
        value7++;
        printStream.println("Done.");
      }
    } catch (Exception exception) {
      System.out.println(exception);
      exception.printStackTrace(System.out);
    }
  }
}
