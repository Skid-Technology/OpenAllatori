package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.LineNumber;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public final class AttributeHTML implements Constants {

  public int attr_count;

  public ConstantPool constant_pool;

  public String class_name;

  public ConstantHTML constant_html;

  public PrintWriter file;

  public final void writeAttribute(Attribute attribute, String text) throws IOException {
    this.writeAttribute(attribute, text, 0);
  }

  public final String codeLink(int value, int value2) {
    return "<A HREF=\""
        + this.class_name
        + "_code.html#code"
        + value2
        + "@"
        + value
        + "\" TARGET=Code>"
        + value
        + "</A>";
  }

  public AttributeHTML(
      String text, String class_name, ConstantPool constantPool, ConstantHTML constantHTML)
      throws IOException {
    super();
    this.attr_count = 0;
    this.class_name = class_name;
    this.constant_pool = constantPool;
    this.constant_html = constantHTML;
    this.file =
        new PrintWriter(new FileOutputStream(("" + text + class_name + "_attributes.html")));
    this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
  }

  public final void writeAttribute(Attribute attribute, String text, int value) throws IOException {
    byte value2;
    if ((value2 = attribute.getTag()) != -1) {
      this.attr_count++;
      AttributeHTML attributeHTML;
      if (this.attr_count % 2 == 0) {
        attributeHTML = this;
        this.file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
      } else {
        attributeHTML = this;
        this.file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
      }
      label82:
      {
        attributeHTML.file.println(
            "<H4><A NAME=\"" + text + "\">" + this.attr_count + " " + TEXTS4[value2] + "</A></H4>");
        switch (value2) {
          case 0:
            int sourceFileIndex = ((SourceFile) attribute).getSourceFileIndex();
            PrintWriter printWriter = this.file;
            StringBuilder stringBuilder = new StringBuilder().insert(0, "<UL><LI><A HREF=\"");
            attributeHTML = this;
            printWriter.print(
                stringBuilder
                    .append(this.class_name)
                    .append("_cp.html#cp")
                    .append(sourceFileIndex)
                    .append("\" TARGET=\"ConstantPool\">Source file index(")
                    .append(sourceFileIndex)
                    .append(")</A></UL>\n")
                    .toString());
            break label82;
          case 1:
            int constantValueIndex = ((ConstantValue) attribute).getConstantValueIndex();
            PrintWriter printWriter2 = this.file;
            StringBuilder stringBuilder2 = new StringBuilder().insert(0, "<UL><LI><A HREF=\"");
            attributeHTML = this;
            printWriter2.print(
                stringBuilder2
                    .append(this.class_name)
                    .append("_cp.html#cp")
                    .append(constantValueIndex)
                    .append("\" TARGET=\"ConstantPool\">Constant value index(")
                    .append(constantValueIndex)
                    .append(")</A></UL>\n")
                    .toString());
            break label82;
          case 2:
            Code code = (Code) attribute;
            this.file.print(
                "<UL><LI>Maximum stack size = "
                    + code.getMax_stack()
                    + "</LI>\n<LI>Number of local variables = "
                    + code.getMax_locals()
                    + "</LI>\n<LI><A HREF=\""
                    + this.class_name
                    + "_code.html#method"
                    + value
                    + "\" TARGET=Code>Byte code</A></LI></UL>\n");
            CodeException[] codeExceptions;
            int value3;
            if ((value3 = (codeExceptions = code.getExceptionTable()).length) > 0) {
              int value4 = 0;
              this.file.print("<P><B>Exceptions handled</B><UL>");
              for (int index = 0; value4 < value3; value4 = index) {
                int catch_type = codeExceptions[index].getCatch_type();
                this.file.print("<LI>");
                if (catch_type != 0) {
                  this.file.print(this.constant_html.referenceConstant(catch_type));
                  attributeHTML = this;
                } else {
                  this.file.print("Any Exception");
                  attributeHTML = this;
                }
                PrintWriter printWriter3 = attributeHTML.file;
                StringBuilder stringBuilder3 =
                    new StringBuilder()
                        .insert(0, "<BR>(Ranging from lines ")
                        .append(this.codeLink(codeExceptions[index].getStart_pc(), value))
                        .append(" to ")
                        .append(this.codeLink(codeExceptions[index].getEnd_pc(), value))
                        .append(", handled at line ")
                        .append(this.codeLink(codeExceptions[index].getHandler_pc(), value));
                index++;
                printWriter3.print(stringBuilder3.append((String) ")</LI>").toString());
              }
              attributeHTML = this;
              this.file.print("</UL>");
              break label82;
            }
            break;
          case 3:
            int[] exceptionIndexTable = ((ExceptionTable) attribute).getExceptionIndexTable();
            int value5 = 0;
            this.file.print("<UL>");
            for (int index2 = 0; value5 < exceptionIndexTable.length; value5 = index2) {
              PrintWriter printWriter4 = this.file;
              StringBuilder stringBuilder4 =
                  new StringBuilder()
                      .insert(0, "<LI><A HREF=\"")
                      .append(this.class_name)
                      .append("_cp.html#cp")
                      .append(exceptionIndexTable[index2])
                      .append("\" TARGET=\"ConstantPool\">Exception class index(")
                      .append(exceptionIndexTable[index2]);
              index2++;
              printWriter4.print(stringBuilder4.append((String) ")</A>\n").toString());
            }
            attributeHTML = this;
            this.file.print("</UL>\n");
            break label82;
          case 4:
            LineNumber[] lineNumberTable = ((LineNumberTable) attribute).getLineNumberTable();
            int value6 = 0;
            this.file.print("<P>");
            for (int index3 = 0; value6 < lineNumberTable.length; value6 = ++index3) {
              this.file.print(
                  "("
                      + lineNumberTable[index3].getStart_pc()
                      + ",&nbsp;"
                      + lineNumberTable[index3].getLine_number()
                      + ")");
              if (index3 < lineNumberTable.length - 1) {
                this.file.print(", ");
              }
            }
            break;
          case 5:
            LocalVariable[] localVariableTable =
                ((LocalVariableTable) attribute).getLocalVariableTable();
            int value7 = 0;
            this.file.print("<UL>");
            for (int index4 = 0; value7 < localVariableTable.length; value7 = index4) {
              int signature_index = localVariableTable[index4].getSignature_index();
              String text2 =
                  Utility.signatureToString(
                      ((ConstantUtf8) this.constant_pool.getConstant(signature_index, (byte) 1))
                          .getBytes(),
                      false);
              int value8;
              int value9 =
                  (value8 = localVariableTable[index4].getStart_pc())
                      + localVariableTable[index4].getLength();
              PrintWriter printWriter5 = this.file;
              StringBuilder stringBuilder5 =
                  new StringBuilder()
                      .insert(0, "<LI>")
                      .append(Class2HTML.createText(text2))
                      .append("&nbsp;<B>")
                      .append(localVariableTable[index4].getText())
                      .append("</B> in slot %")
                      .append(localVariableTable[index4].getIndex())
                      .append("<BR>Valid from lines ")
                      .append("<A HREF=\"")
                      .append(this.class_name)
                      .append("_code.html#code")
                      .append(value)
                      .append("@")
                      .append(value8)
                      .append("\" TARGET=Code>")
                      .append(value8)
                      .append("</A> to ")
                      .append("<A HREF=\"")
                      .append(this.class_name)
                      .append("_code.html#code")
                      .append(value)
                      .append("@")
                      .append(value9)
                      .append("\" TARGET=Code>")
                      .append(value9);
              index4++;
              printWriter5.println(stringBuilder5.append((String) "</A></LI>").toString());
            }
            attributeHTML = this;
            this.file.print("</UL>\n");
            break label82;
          case 6:
            InnerClass[] innerClasses = ((InnerClasses) attribute).getInnerClasses();
            int value10 = 0;
            this.file.print("<UL>");
            for (int index5 = 0; value10 < innerClasses.length; value10 = index5) {
              String text3;
              int value11;
              InnerClass[] innerClasss;
              if ((value11 = innerClasses[index5].getInner_name_index()) > 0) {
                text3 =
                    ((ConstantUtf8) this.constant_pool.getConstant(value11, (byte) 1)).getBytes();
                innerClasss = innerClasses;
              } else {
                text3 = "&lt;anonymous&gt;";
                innerClasss = innerClasses;
              }
              String text22 = Utility.getText2(innerClasss[index5].getInner_access_flags());
              PrintWriter printWriter6 = this.file;
              StringBuilder stringBuilder6 =
                  new StringBuilder()
                      .insert(0, "<LI><FONT COLOR=\"#FF0000\">")
                      .append(text22)
                      .append("</FONT> ")
                      .append(
                          this.constant_html.referenceConstant(
                              innerClasses[index5].getInner_class_index()))
                      .append(" in&nbsp;class ")
                      .append(
                          this.constant_html.referenceConstant(
                              innerClasses[index5].getOuter_class_index()))
                      .append(" named ")
                      .append(text3);
              index5++;
              printWriter6.print(stringBuilder6.append((String) "</LI>\n").toString());
            }
            attributeHTML = this;
            this.file.print("</UL>\n");
            break label82;
          default:
            this.file.print("<P>" + attribute.toString());
        }
        attributeHTML = this;
      }
      attributeHTML.file.println("</TD></TR>");
      this.file.flush();
    }
  }

  public final void close() {
    this.file.println("</TABLE></BODY></HTML>");
    this.file.close();
  }
}
