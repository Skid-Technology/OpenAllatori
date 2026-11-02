package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;

public final class CodeHTML implements Constants {

  public String class_name;

  public Method[] methods;

  public PrintWriter file;

  public ConstantHTML constant_html;

  public static boolean wide = false;

  public ConstantPool constant_pool;

  public BitSet goto_set;

  public final String codeToHTML(ByteSequence byteSequence, int value) throws IOException {
    short value2 = (short) byteSequence.readUnsignedByte();
    int value3 = 0;
    byte value4 = 0;
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer(256))
        .append("<TT>")
        .append(TEXTS8[value2])
        .append("</TT></TD><TD>");
    if (value2 == 170 || value2 == 171) {
      int value5;
      value4 = (byte) ((value5 = byteSequence.getIndex() % 4) == 0 ? 0 : 4 - value5);
      int value6;
      for (int value7 = value6 = 0; value7 < value4; value7 = value6) {
        value6++;
        byteSequence.readByte();
      }
      value3 = byteSequence.readInt();
    }
    StringBuffer stringBuffer2;
    switch (value2) {
      case 18:
        int value8 = byteSequence.readUnsignedByte();
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<A HREF=\"")
            .append(this.class_name)
            .append("_cp.html#cp")
            .append(value8)
            .append("\" TARGET=\"ConstantPool\">")
            .append(
                Class2HTML.createText2(
                    this.constant_pool.getText(
                        value8, this.constant_pool.getConstant(value8).getTag())))
            .append("</a>");
        break;
      case 19:
      case 20:
        short value9 = byteSequence.readShort();
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<A HREF=\"")
            .append(this.class_name)
            .append("_cp.html#cp")
            .append(value9)
            .append("\" TARGET=\"ConstantPool\">")
            .append(
                Class2HTML.createText2(
                    this.constant_pool.getText(
                        value9, this.constant_pool.getConstant(value9).getTag())))
            .append("</a>");
        break;
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 169:
        int value10;
        if (wide) {
          value10 = byteSequence.readShort();
          wide = false;
          stringBuffer2 = stringBuffer;
        } else {
          value10 = byteSequence.readUnsignedByte();
          stringBuffer2 = stringBuffer;
        }
        stringBuffer2.append("%").append(value10);
        stringBuffer2 = stringBuffer;
        break;
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 96:
      case 97:
      case 98:
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
      case 186:
      case 190:
      case 191:
      case 194:
      case 195:
      default:
        int value11;
        if (NO_OF_OPERANDS[value2] > 0) {
          for (int value12 = value11 = 0;
              value12 < TYPE_OF_OPERANDS[value2].length;
              value12 = value11) {
            switch (TYPE_OF_OPERANDS[value2][value11]) {
              case 8:
                stringBuffer2 = stringBuffer;
                stringBuffer.append(byteSequence.readUnsignedByte());
                break;
              case 9:
                stringBuffer2 = stringBuffer;
                stringBuffer.append(byteSequence.readShort());
                break;
              case 10:
                stringBuffer2 = stringBuffer;
                stringBuffer.append(byteSequence.readInt());
                break;
              default:
                System.err.println("Unreachable default case reached!");
                System.exit(-1);
                stringBuffer2 = stringBuffer;
            }
            StringBuffer stringBuffer3 = stringBuffer2;
            value11++;
            stringBuffer3.append("&nbsp;");
          }
        }
        stringBuffer2 = stringBuffer;
        break;
      case 132:
        int value13;
        short value14;
        if (wide) {
          value13 = byteSequence.readShort();
          stringBuffer2 = stringBuffer;
          value14 = byteSequence.readShort();
          wide = false;
        } else {
          value13 = byteSequence.readUnsignedByte();
          stringBuffer2 = stringBuffer;
          value14 = byteSequence.readByte();
        }
        stringBuffer2.append("%").append(value13).append(" ").append(value14);
        stringBuffer2 = stringBuffer;
        break;
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 167:
      case 168:
      case 198:
      case 199:
        int value15 = byteSequence.getIndex() + byteSequence.readShort() - 1;
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<A HREF=\"#code")
            .append(value)
            .append("@")
            .append(value15)
            .append("\">")
            .append(value15)
            .append("</A>");
        break;
      case 170:
        int value16 = byteSequence.readInt();
        int value17 = byteSequence.readInt();
        int value18 = byteSequence.getIndex() - 12 - value4 - 1;
        int value19 = value3 + value18;
        stringBuffer.append("<TABLE BORDER=1><TR>");
        int[] values = new int[value17 - value16 + 1];
        int value20 = 0;
        for (int value21 = value20; value21 < values.length; value21 = value20) {
          values[value20] = value18 + byteSequence.readInt();
          StringBuffer stringBuffer4 = stringBuffer.append("<TH>").append(value16 + value20);
          value20++;
          stringBuffer4.append("</TH>");
        }
        stringBuffer.append("<TH>default</TH></TR>\n<TR>");
        int value22 = 0;
        for (int value23 = value22; value23 < values.length; value23 = value22) {
          StringBuffer stringBuffer5 =
              stringBuffer
                  .append("<TD><A HREF=\"#code")
                  .append(value)
                  .append("@")
                  .append(values[value22])
                  .append("\">")
                  .append(values[value22]);
          value22++;
          stringBuffer5.append("</A></TD>");
        }
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<TD><A HREF=\"#code")
            .append(value)
            .append("@")
            .append(value19)
            .append("\">")
            .append(value19)
            .append("</A></TD></TR>\n</TABLE>\n");
        break;
      case 171:
        int value24 = byteSequence.readInt();
        int value25 = byteSequence.getIndex() - 8 - value4 - 1;
        int[] values2 = new int[value24];
        int value26 = value3 + value25;
        stringBuffer.append("<TABLE BORDER=1><TR>");
        int value27 = 0;
        for (int value28 = value27; value28 < value24; value28 = value27) {
          int value29 = byteSequence.readInt();
          values2[value27] = value25 + byteSequence.readInt();
          StringBuffer stringBuffer6 = stringBuffer.append("<TH>").append(value29);
          value27++;
          stringBuffer6.append("</TH>");
        }
        stringBuffer.append("<TH>default</TH></TR>\n<TR>");
        int value30 = 0;
        for (int value31 = value30; value31 < value24; value31 = value30) {
          StringBuffer stringBuffer7 =
              stringBuffer
                  .append("<TD><A HREF=\"#code")
                  .append(value)
                  .append("@")
                  .append(values2[value30])
                  .append("\">")
                  .append(values2[value30]);
          value30++;
          stringBuffer7.append("</A></TD>");
        }
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<TD><A HREF=\"#code")
            .append(value)
            .append("@")
            .append(value26)
            .append("\">")
            .append(value26)
            .append("</A></TD></TR>\n</TABLE>\n");
        break;
      case 178:
      case 179:
      case 180:
      case 181:
        short value32 = byteSequence.readShort();
        ConstantFieldref constantFieldref =
            (ConstantFieldref) this.constant_pool.getConstant(value32, (byte) 9);
        int class_index = constantFieldref.getClass_index();
        String text = Utility.getText(this.constant_pool.createText(class_index, (byte) 7), false);
        int name_and_type_index = constantFieldref.getName_and_type_index();
        String text2 = this.constant_pool.getText(name_and_type_index, (byte) 12);
        if (text.equals(this.class_name)) {
          stringBuffer
              .append("<A HREF=\"")
              .append(this.class_name)
              .append("_methods.html#field")
              .append(text2)
              .append("\" TARGET=Methods>")
              .append(text2)
              .append("</A>\n");
          stringBuffer2 = stringBuffer;
        } else {
          stringBuffer
              .append(this.constant_html.referenceConstant(class_index))
              .append(".")
              .append(text2);
          stringBuffer2 = stringBuffer;
        }
        break;
      case 182:
      case 183:
      case 184:
      case 185:
        short value33 = byteSequence.readShort();
        int value34;
        int value35;
        if (value2 == 185) {
          byteSequence.readUnsignedByte();
          byteSequence.readUnsignedByte();
          ConstantInterfaceMethodref constantInterfaceMethodref =
              (ConstantInterfaceMethodref) this.constant_pool.getConstant(value33, (byte) 11);
          int class_index2 = constantInterfaceMethodref.getClass_index();
          this.constant_pool.constantToString(constantInterfaceMethodref);
          value34 = constantInterfaceMethodref.getName_and_type_index();
          value35 = class_index2;
        } else {
          ConstantMethodref constantMethodref =
              (ConstantMethodref) this.constant_pool.getConstant(value33, (byte) 10);
          int class_index3 = constantMethodref.getClass_index();
          this.constant_pool.constantToString(constantMethodref);
          value34 = constantMethodref.getName_and_type_index();
          value35 = class_index3;
        }
        String text3 = Class2HTML.referenceClass(value35);
        String text4 =
            Class2HTML.createText2(
                this.constant_pool.constantToString(
                    this.constant_pool.getConstant(value34, (byte) 12)));
        ConstantNameAndType constantNameAndType =
            (ConstantNameAndType) this.constant_pool.getConstant(value34, (byte) 12);
        String text5 =
            this.constant_pool.getText(constantNameAndType.getSignature_index(), (byte) 1);
        String[] texts = Utility.methodSignatureArgumentTypes(text5, false);
        String text6 = Utility.createText(text5, false);
        stringBuffer
            .append(text3)
            .append(".<A HREF=\"")
            .append(this.class_name)
            .append("_cp.html#cp")
            .append(value33)
            .append("\" TARGET=ConstantPool>")
            .append(text4)
            .append("</A>")
            .append("(");
        int value36 = 0;
        for (int value37 = value36; value37 < texts.length; value37 = ++value36) {
          stringBuffer.append(Class2HTML.createText(texts[value36]));
          if (value36 < texts.length - 1) {
            stringBuffer.append(", ");
          }
        }
        stringBuffer2 = stringBuffer;
        stringBuffer.append("):").append(Class2HTML.createText(text6));
        break;
      case 187:
      case 192:
      case 193:
        short value38 = byteSequence.readShort();
        stringBuffer2 = stringBuffer;
        stringBuffer.append(this.constant_html.referenceConstant(value38));
        break;
      case 188:
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<FONT COLOR=\"#00FF00\">")
            .append(TEXTS[byteSequence.readByte()])
            .append("</FONT>");
        break;
      case 189:
        short value39 = byteSequence.readShort();
        stringBuffer2 = stringBuffer;
        stringBuffer.append(this.constant_html.referenceConstant(value39));
        break;
      case 196:
        wide = true;
        stringBuffer2 = stringBuffer;
        stringBuffer.append("(wide)");
        break;
      case 197:
        short value40 = byteSequence.readShort();
        byte value41 = byteSequence.readByte();
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append(this.constant_html.referenceConstant(value40))
            .append(":")
            .append(value41)
            .append("-dimensional");
        break;
      case 200:
      case 201:
        int value42 = byteSequence.getIndex() + byteSequence.readInt() - 1;
        stringBuffer2 = stringBuffer;
        stringBuffer
            .append("<A HREF=\"#code")
            .append(value)
            .append("@")
            .append(value42)
            .append("\">")
            .append(value42)
            .append("</A>");
    }
    stringBuffer2.append("</TD>");
    return stringBuffer.toString();
  }

  public void writeMethod(Method method, int value) throws IOException {
    String text2 = method.getText2();
    String[] texts = Utility.methodSignatureArgumentTypes(text2, false);
    String text = Utility.createText(text2, false);
    String text3 = Class2HTML.createText2(method.getText());
    String text4 = Utility.replace(Utility.getText2(method.getAccess_flags2()), " ", "&nbsp;");
    Attribute[] attributes = method.getAttributes();
    PrintWriter printWriter = this.file;
    StringBuilder stringBuilder = new StringBuilder();
    int value2 = 0;
    printWriter.print(
        stringBuilder
            .insert(0, "<P><B><FONT COLOR=\"#FF0000\">")
            .append(text4)
            .append("</FONT>&nbsp;")
            .append("<A NAME=method")
            .append(value)
            .append(">")
            .append(Class2HTML.createText(text))
            .append("</A>&nbsp<A HREF=\"")
            .append(this.class_name)
            .append("_methods.html#method")
            .append(value)
            .append("\" TARGET=Methods>")
            .append(text3)
            .append("</A>(")
            .toString());
    for (int index = 0; value2 < texts.length; value2 = ++index) {
      this.file.print(Class2HTML.createText(texts[index]));
      if (index < texts.length - 1) {
        this.file.print(",&nbsp;");
      }
    }
    this.file.println(")</B></P>");
    Code code = null;
    byte[] values = null;
    if (attributes.length > 0) {
      int value3 = 0;
      this.file.print("<H4>Attributes</H4><UL>\n");
      for (int index2 = 0; value3 < attributes.length; value3 = ++index2) {
        byte value4;
        byte value5;
        if ((value4 = attributes[index2].getTag()) != -1) {
          value5 = value4;
          this.file.print(
              "<LI><A HREF=\""
                  + this.class_name
                  + "_attributes.html#method"
                  + value
                  + "@"
                  + index2
                  + "\" TARGET=Attributes>"
                  + TEXTS4[value4]
                  + "</A></LI>\n");
        } else {
          this.file.print("<LI>" + attributes[index2] + "</LI>");
          value5 = value4;
        }
        if (value5 == 2) {
          Code code2 = code = (Code) attributes[index2];
          Attribute[] attributes2 = code2.getAttributes();
          values = code2.getCode();
          int value6 = 0;
          this.file.print("<UL>");
          for (int index3 = 0; value6 < attributes2.length; value6 = index3) {
            byte tag = attributes2[index3].getTag();
            printWriter = this.file;
            StringBuilder stringBuilder2 =
                new StringBuilder()
                    .insert(0, "<LI><A HREF=\"")
                    .append(this.class_name)
                    .append("_attributes.html#")
                    .append("method")
                    .append(value)
                    .append("@")
                    .append(index2)
                    .append("@")
                    .append(index3)
                    .append("\" TARGET=Attributes>")
                    .append(TEXTS4[tag]);
            PrintWriter printWriter2 = printWriter;
            index3++;
            printWriter2.print(stringBuilder2.append((String) "</A></LI>\n").toString());
          }
          this.file.print("</UL>");
        }
      }
      this.file.println("</UL>");
    }
    if (values != null) {
      ByteSequence byteSequence = new ByteSequence(values);
      ByteSequence byteSequence2 = byteSequence;
      byteSequence.mark(byteSequence.available());
      this.findGotos(byteSequence, method, code);
      byteSequence.reset();
      this.file.println(
          "<TABLE BORDER=0><TR><TH ALIGN=LEFT>Byte<BR>offset</TH><TH ALIGN=LEFT>Instruction</TH><TH"
              + " ALIGN=LEFT>Argument</TH>");
      int value7 = 0;
      while (byteSequence2.available() > 0) {
        int index4 = byteSequence.getIndex();
        String text5 = this.codeToHTML(byteSequence, value);
        String text6 = "";
        if (this.goto_set.get(index4)) {
          text6 = "<A NAME=code" + value + "@" + index4 + "></A>";
        }
        String text7;
        CodeHTML codeHTML;
        if (byteSequence.getIndex() == values.length) {
          text7 = "<A NAME=code" + value + "@" + values.length + ">" + index4 + "</A>";
          codeHTML = this;
        } else {
          text7 = "" + index4;
          codeHTML = this;
        }
        printWriter = codeHTML.file;
        StringBuilder stringBuilder3 =
            new StringBuilder()
                .insert(0, "<TR VALIGN=TOP><TD>")
                .append(text7)
                .append("</TD><TD>")
                .append(text6)
                .append(text5);
        PrintWriter printWriter3 = printWriter;
        value7++;
        printWriter3.println(stringBuilder3.append((String) "</TR>").toString());
        byteSequence2 = byteSequence;
      }
      this.file.println("<TR><TD> </A></TD></TR>");
      this.file.println("</TABLE>");
    }
  }

  public final void findGotos(ByteSequence byteSequence, Method method, Code code)
      throws IOException {
    this.goto_set = new BitSet(byteSequence.available());
    if (code != null) {
      CodeException[] codeExceptions;
      int value = (codeExceptions = code.getExceptionTable()).length;
      int value2;
      for (int value3 = value2 = 0; value3 < value; value3 = value2) {
        this.goto_set.set(codeExceptions[value2].getStart_pc());
        this.goto_set.set(codeExceptions[value2].getEnd_pc());
        int value4 = value2;
        CodeException[] codeExceptions2 = codeExceptions;
        BitSet bitSet = this.goto_set;
        value2++;
        bitSet.set(codeExceptions2[value4].getHandler_pc());
      }
      Attribute[] attributes = code.getAttributes();
      int value5;
      for (int value6 = value5 = 0; value6 < attributes.length; value6 = ++value5) {
        if (attributes[value5].getTag() == 5) {
          LocalVariable[] localVariableTable =
              ((LocalVariableTable) attributes[value5]).getLocalVariableTable();
          int value7;
          for (int value8 = value7 = 0; value8 < localVariableTable.length; value8 = value7) {
            int value9;
            int value10 =
                (value9 = localVariableTable[value7].getStart_pc())
                    + localVariableTable[value7].getLength();
            this.goto_set.set(value9);
            value7++;
            this.goto_set.set(value10);
          }
          break;
        }
      }
    }
    int value11 = 0;
    for (ByteSequence byteSequence2 = byteSequence;
        byteSequence2.available() > 0;
        byteSequence2 = byteSequence) {
      int value12;
      switch (value12 = byteSequence.readUnsignedByte()) {
        case 153:
        case 154:
        case 155:
        case 156:
        case 157:
        case 158:
        case 159:
        case 160:
        case 161:
        case 162:
        case 163:
        case 164:
        case 165:
        case 166:
        case 167:
        case 168:
        case 198:
        case 199:
          int value13 = byteSequence.getIndex() + byteSequence.readShort() - 1;
          this.goto_set.set(value13);
          break;
        case 169:
        case 172:
        case 173:
        case 174:
        case 175:
        case 176:
        case 177:
        case 178:
        case 179:
        case 180:
        case 181:
        case 182:
        case 183:
        case 184:
        case 185:
        case 186:
        case 187:
        case 188:
        case 189:
        case 190:
        case 191:
        case 192:
        case 193:
        case 194:
        case 195:
        case 196:
        case 197:
        default:
          byteSequence.unreadByte();
          this.codeToHTML(byteSequence, 0);
          break;
        case 170:
        case 171:
          int value14;
          int value15 = (value14 = byteSequence.getIndex() % 4) == 0 ? 0 : 4 - value14;
          int value16;
          for (int value17 = value16 = 0; value17 < value15; value17 = value16) {
            value16++;
            byteSequence.readByte();
          }
          int value18 = byteSequence.readInt();
          if (value12 == 170) {
            int value19 = byteSequence.readInt();
            int value20 = byteSequence.readInt();
            int value21 = byteSequence.getIndex() - 12 - value15 - 1;
            int value22 = value18 + value21;
            this.goto_set.set(value22);
            int value23;
            for (int value24 = value23 = 0; value24 < value20 - value19 + 1; value24 = value23) {
              int value25 = value21 + byteSequence.readInt();
              value23++;
              this.goto_set.set(value25);
            }
          } else {
            int value26 = byteSequence.readInt();
            int value27 = byteSequence.getIndex() - 8 - value15 - 1;
            int value28 = value18 + value27;
            int value29 = 0;
            this.goto_set.set(value28);
            for (int index = 0; value29 < value26; value29 = index) {
              byteSequence.readInt();
              int value30 = value27 + byteSequence.readInt();
              index++;
              this.goto_set.set(value30);
            }
          }
          break;
        case 200:
        case 201:
          int value31 = byteSequence.getIndex() + byteSequence.readInt() - 1;
          this.goto_set.set(value31);
      }
      value11++;
    }
  }

  public CodeHTML(
      String text,
      String class_name,
      Method[] methods2,
      ConstantPool constantPool,
      ConstantHTML constantHTML)
      throws IOException {
    super();
    int value = 0;
    this.class_name = class_name;
    this.methods = methods2;
    this.constant_pool = constantPool;
    this.constant_html = constantHTML;
    this.file = new PrintWriter(new FileOutputStream(("" + text + class_name + "_code.html")));
    this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\">");
    for (int index = 0; value < methods2.length; value = index) {
      Method method = methods2[index];
      int value2 = index;
      index++;
      this.writeMethod(method, value2);
    }
    this.file.println("</BODY></HTML>");
    this.file.close();
  }
}
