package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantCP;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConstantPoolGen implements Serializable {

  public Map utf8_table;

  public static final String TEXT = ":";

  public static final String TEXT4 = "#";

  public static final String TEXT2 = "&";

  public static final String TEXT3 = "%";

  public Map n_a_t_table;

  public int index;

  public Map class_table;

  public Constant[] constants;

  public Map cp_table;

  public Map string_table;

  public int size = 1024;

  public void setConstant(int value, Constant constant) {
    this.constants[value] = constant;
  }

  public int lookupInteger(int value) {
    int value2;
    for (int value3 = value2 = 1; value3 < this.index; value3 = ++value2) {
      if (this.constants[value2] instanceof ConstantInteger
          && ((ConstantInteger) this.constants[value2]).getBytes() == value) {
        return value2;
      }
    }
    return -1;
  }

  public int lookupUtf8(String text) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index = (ConstantPoolGen$Index) this.utf8_table.get(text)) != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public int addMethodref(String text, String text2, String text3) {
    int value;
    if ((value = this.calculateCount3(text, text2, text3)) != -1) {
      return value;
    }
    this.adjustSize();
    int value2 = this.addNameAndType(text2, text3);
    int value3 = this.addClass(text);
    int value4 = this.index;
    this.constants[this.index++] = new ConstantMethodref(value3, value2);
    String text4 = "" + text + ":" + text2 + ":" + text3;
    if (!this.cp_table.containsKey(text4)) {
      this.cp_table.put(text4, new ConstantPoolGen$Index(value4));
    }
    return value4;
  }

  public int addClass_(String text) {
    int value;
    if ((value = this.lookupClass(text)) != -1) {
      return value;
    }
    this.adjustSize();
    ConstantClass constantClass = new ConstantClass(this.addUtf8(text));
    int value2 = this.index;
    this.constants[this.index++] = constantClass;
    if (!this.class_table.containsKey(text)) {
      this.class_table.put(text, new ConstantPoolGen$Index(value2));
    }
    return value2;
  }

  public int addInteger(int value) {
    int value2;
    if ((value2 = this.lookupInteger(value)) != -1) {
      return value2;
    }
    this.adjustSize();
    int value3 = this.index;
    this.constants[this.index++] = new ConstantInteger(value);
    return value3;
  }

  public ConstantPool getFinalConstantPool() {
    Constant[] constants2 = new Constant[this.index];
    System.arraycopy(this.constants, 0, constants2, 0, this.index);
    return new ConstantPool(constants2);
  }

  public int addDouble(double value) {
    int value2;
    if ((value2 = this.lookupDouble(value)) != -1) {
      return value2;
    }
    this.adjustSize();
    int value3 = this.index;
    this.constants[this.index] = new ConstantDouble(value);
    this.index += 2;
    return value3;
  }

  public int addNameAndType(String text, String text2) {
    int value;
    if ((value = this.lookupNameAndType(text, text2)) != -1) {
      return value;
    }
    this.adjustSize();
    int value2 = this.addUtf8(text);
    int value3 = this.addUtf8(text2);
    int value4 = this.index;
    this.constants[this.index++] = new ConstantNameAndType(value2, value3);
    String text3 = "" + text + "%" + text2;
    if (!this.n_a_t_table.containsKey(text3)) {
      this.n_a_t_table.put(text3, new ConstantPoolGen$Index(value4));
    }
    return value4;
  }

  public ConstantPoolGen(Constant[] constants2) {
    this.constants = new Constant[this.size];
    this.index = 1;
    this.string_table = new HashMap();
    this.class_table = new HashMap();
    this.utf8_table = new HashMap();
    this.n_a_t_table = new HashMap();
    this.cp_table = new HashMap();
    if (constants2.length > this.size) {
      this.size = constants2.length;
      this.constants = new Constant[this.size];
    }
    System.arraycopy(constants2, 0, this.constants, 0, constants2.length);
    if (constants2.length > 0) {
      this.index = constants2.length;
    }
    int value;
    for (int value2 = value = 1; value2 < this.index; value2 = ++value) {
      Constant constant;
      if ((constant = this.constants[value]) instanceof ConstantString) {
        ConstantString constantString = (ConstantString) constant;
        String bytes = ((ConstantUtf8) this.constants[constantString.getStringIndex()]).getBytes();
        if (!this.string_table.containsKey(bytes)) {
          this.string_table.put(bytes, new ConstantPoolGen$Index(value));
        }
      } else if (constant instanceof ConstantClass) {
        ConstantClass constantClass = (ConstantClass) constant;
        String bytes2 = ((ConstantUtf8) this.constants[constantClass.getNameIndex()]).getBytes();
        if (!this.class_table.containsKey(bytes2)) {
          this.class_table.put(bytes2, new ConstantPoolGen$Index(value));
        }
      } else if (constant instanceof ConstantNameAndType) {
        ConstantNameAndType constantNameAndType = (ConstantNameAndType) constant;
        ConstantUtf8 constantUtf8 =
            (ConstantUtf8) this.constants[constantNameAndType.getName_index()];
        ConstantUtf8 constantUtf82 =
            (ConstantUtf8) this.constants[constantNameAndType.getSignature_index()];
        String text = "" + constantUtf8.getBytes() + "%" + constantUtf82.getBytes();
        if (!this.n_a_t_table.containsKey(text)) {
          this.n_a_t_table.put(text, new ConstantPoolGen$Index(value));
        }
      } else if (constant instanceof ConstantUtf8) {
        String bytes3 = ((ConstantUtf8) constant).getBytes();
        if (!this.utf8_table.containsKey(bytes3)) {
          this.utf8_table.put(bytes3, new ConstantPoolGen$Index(value));
        }
      } else if (constant instanceof ConstantCP) {
        ConstantCP constantCP = (ConstantCP) constant;
        ConstantClass constantClass2 = (ConstantClass) this.constants[constantCP.getClass_index()];
        ConstantNameAndType constantNameAndType2 =
            (ConstantNameAndType) this.constants[constantCP.getName_and_type_index()];
        String text2 =
            ((ConstantUtf8) this.constants[constantClass2.getNameIndex()])
                .getBytes()
                .replace('/', '.');
        String bytes4 =
            ((ConstantUtf8) this.constants[constantNameAndType2.getName_index()]).getBytes();
        String bytes5 =
            ((ConstantUtf8) this.constants[constantNameAndType2.getSignature_index()]).getBytes();
        String text3 = ":";
        if (constant instanceof ConstantInterfaceMethodref) {
          text3 = "#";
        } else if (constant instanceof ConstantFieldref) {
          text3 = "&";
        }
        String text4 = "" + text2 + text3 + bytes4 + text3 + bytes5;
        if (!this.cp_table.containsKey(text4)) {
          this.cp_table.put(text4, new ConstantPoolGen$Index(value));
        }
      }
    }
  }

  public int lookupDouble(double value) {
    long value2 = Double.doubleToLongBits(value);
    int value3;
    for (int value4 = value3 = 1; value4 < this.index; value4 = ++value3) {
      if (this.constants[value3] instanceof ConstantDouble
          && Double.doubleToLongBits(((ConstantDouble) this.constants[value3]).getBytes())
              == value2) {
        return value3;
      }
    }
    return -1;
  }

  public int calculateCount(MethodGen methodGen) {
    return this.calculateCount3(
        methodGen.getClass_name(), methodGen.getName(), methodGen.getText());
  }

  public int lookupClass(String text) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index =
                (ConstantPoolGen$Index) this.class_table.get(text.replace((char) 46, (char) 47)))
            != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public ConstantPool getConstantPool() {
    return new ConstantPool(this.constants);
  }

  public int addArrayClass(ArrayType arrayType) {
    return this.addClass_(arrayType.getSignature());
  }

  public int addInterfaceMethodref(String text, String text2, String text3) {
    int value;
    if ((value = this.calculateCount2(text, text2, text3)) != -1) {
      return value;
    }
    this.adjustSize();
    int value2 = this.addClass(text);
    int value3 = this.addNameAndType(text2, text3);
    int value4 = this.index;
    this.constants[this.index++] = new ConstantInterfaceMethodref(value2, value3);
    String text4 = "" + text + "#" + text2 + "#" + text3;
    if (!this.cp_table.containsKey(text4)) {
      this.cp_table.put(text4, new ConstantPoolGen$Index(value4));
    }
    return value4;
  }

  public int lookupLong(long value) {
    int value2;
    for (int value3 = value2 = 1; value3 < this.index; value3 = ++value2) {
      if (this.constants[value2] instanceof ConstantLong
          && ((ConstantLong) this.constants[value2]).getBytes() == value) {
        return value2;
      }
    }
    return -1;
  }

  public int lookupString(String text) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index = (ConstantPoolGen$Index) this.string_table.get(text)) != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public Constant getConstant(int value) {
    return this.constants[value];
  }

  public int lookupNameAndType(String text, String text2) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index =
                (ConstantPoolGen$Index) this.n_a_t_table.get("" + text + "%" + text2))
            != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public int calculateCount2(MethodGen methodGen) {
    return this.addMethodref(methodGen.getClass_name(), methodGen.getName(), methodGen.getText());
  }

  public int calculateCount3(MethodGen methodGen) {
    return this.addInterfaceMethodref(
        methodGen.getClass_name(), methodGen.getName(), methodGen.getText());
  }

  public int addLong(long value) {
    int value2;
    if ((value2 = this.lookupLong(value)) != -1) {
      return value2;
    }
    this.adjustSize();
    int value3 = this.index;
    this.constants[this.index] = new ConstantLong(value);
    this.index += 2;
    return value3;
  }

  public ConstantPoolGen() {
    this.constants = new Constant[this.size];
    this.index = 1;
    this.string_table = new HashMap();
    this.class_table = new HashMap();
    this.utf8_table = new HashMap();
    this.n_a_t_table = new HashMap();
    this.cp_table = new HashMap();
  }

  public void adjustSize() {
    if (this.index + 3 >= this.size) {
      Constant[] constants2 = this.constants;
      this.size *= 2;
      this.constants = new Constant[this.size];
      System.arraycopy(constants2, 0, this.constants, 0, this.index);
    }
  }

  public int addFieldref(String text, String text2, String text3) {
    int value;
    if ((value = this.calculateCount(text, text2, text3)) != -1) {
      return value;
    }
    this.adjustSize();
    int value2 = this.addClass(text);
    int value3 = this.addNameAndType(text2, text3);
    int value4 = this.index;
    this.constants[this.index++] = new ConstantFieldref(value2, value3);
    String text4 = "" + text + "&" + text2 + "&" + text3;
    if (!this.cp_table.containsKey(text4)) {
      this.cp_table.put(text4, new ConstantPoolGen$Index(value4));
    }
    return value4;
  }

  public int calculateCount(String text, String text2, String text3) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index =
                (ConstantPoolGen$Index) this.cp_table.get("" + text + "&" + text2 + "&" + text3))
            != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public int addString(String text) {
    int value;
    if ((value = this.lookupString(text)) != -1) {
      return value;
    }
    int value2 = this.addUtf8(text);
    this.adjustSize();
    ConstantString constantString = new ConstantString(value2);
    int value3 = this.index;
    this.constants[this.index++] = constantString;
    if (!this.string_table.containsKey(text)) {
      this.string_table.put(text, new ConstantPoolGen$Index(value3));
    }
    return value3;
  }

  public ConstantPoolGen(ConstantPool constantPool) {
    this(constantPool.getConstantPool());
  }

  public int lookupFloat(float value) {
    int value2 = Float.floatToIntBits(value);
    int value3;
    for (int value4 = value3 = 1; value4 < this.index; value4 = ++value3) {
      if (this.constants[value3] instanceof ConstantFloat
          && Float.floatToIntBits(((ConstantFloat) this.constants[value3]).getBytes()) == value2) {
        return value3;
      }
    }
    return -1;
  }

  public int addFloat(float value) {
    int value2;
    if ((value2 = this.lookupFloat(value)) != -1) {
      return value2;
    }
    this.adjustSize();
    int value3 = this.index;
    this.constants[this.index++] = new ConstantFloat(value);
    return value3;
  }

  public int addClass(ObjectType objectType) {
    return this.addClass(objectType.getClassName());
  }

  public int calculateCount4(MethodGen methodGen) {
    return this.calculateCount2(
        methodGen.getClass_name(), methodGen.getName(), methodGen.getText());
  }

  public int calculateCount2(String text, String text2, String text3) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index =
                (ConstantPoolGen$Index) this.cp_table.get("" + text + "#" + text2 + "#" + text3))
            != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public int addConstant(Constant constant, ConstantPoolGen constantPoolGen) {
    Constant[] constantPool = constantPoolGen.getConstantPool().getConstantPool();
    switch (constant.getTag()) {
      case 1:
        return this.addUtf8(((ConstantUtf8) constant).getBytes());
      case 2:
      default:
        throw new RuntimeException(("Unknown constant type " + constant));
      case 3:
        return this.addInteger(((ConstantInteger) constant).getBytes());
      case 4:
        return this.addFloat(((ConstantFloat) constant).getBytes());
      case 5:
        return this.addLong(((ConstantLong) constant).getBytes());
      case 6:
        return this.addDouble(((ConstantDouble) constant).getBytes());
      case 7:
        ConstantClass constantClass = (ConstantClass) constant;
        ConstantUtf8 constantUtf8 = (ConstantUtf8) constantPool[constantClass.getNameIndex()];
        return this.addClass(constantUtf8.getBytes());
      case 8:
        ConstantString constantString = (ConstantString) constant;
        ConstantUtf8 constantUtf82 = (ConstantUtf8) constantPool[constantString.getStringIndex()];
        return this.addString(constantUtf82.getBytes());
      case 9:
      case 10:
      case 11:
        ConstantCP constantCP = (ConstantCP) constant;
        ConstantClass constantClass2 = (ConstantClass) constantPool[constantCP.getClass_index()];
        ConstantNameAndType constantNameAndType =
            (ConstantNameAndType) constantPool[constantCP.getName_and_type_index()];
        String text =
            ((ConstantUtf8) constantPool[constantClass2.getNameIndex()])
                .getBytes()
                .replace('/', '.');
        String bytes =
            ((ConstantUtf8) constantPool[constantNameAndType.getName_index()]).getBytes();
        String bytes2 =
            ((ConstantUtf8) constantPool[constantNameAndType.getSignature_index()]).getBytes();
        switch (constant.getTag()) {
          case 9:
            return this.addFieldref(text, bytes, bytes2);
          case 10:
            return this.addMethodref(text, bytes, bytes2);
          case 11:
            return this.addInterfaceMethodref(text, bytes, bytes2);
          default:
            throw new RuntimeException(("Unknown constant type " + constant));
        }
      case 12:
        ConstantNameAndType constantNameAndType2 = (ConstantNameAndType) constant;
        ConstantUtf8 constantUtf83 =
            (ConstantUtf8) constantPool[constantNameAndType2.getName_index()];
        ConstantUtf8 constantUtf84 =
            (ConstantUtf8) constantPool[constantNameAndType2.getSignature_index()];
        return this.addNameAndType(constantUtf83.getBytes(), constantUtf84.getBytes());
    }
  }

  public int getSize() {
    return this.index;
  }

  public int calculateCount3(String text, String text2, String text3) {
    ConstantPoolGen$Index constantPoolGen$Index;
    return (constantPoolGen$Index =
                (ConstantPoolGen$Index) this.cp_table.get("" + text + ":" + text2 + ":" + text3))
            != null
        ? constantPoolGen$Index.index
        : -1;
  }

  public int addClass(String text) {
    return this.addClass_(text.replace((char) 46, (char) 47));
  }

  public int addUtf8(String text) {
    int value;
    if ((value = this.lookupUtf8(text)) != -1) {
      return value;
    }
    this.adjustSize();
    int value2 = this.index;
    this.constants[this.index++] = new ConstantUtf8(text);
    if (!this.utf8_table.containsKey(text)) {
      this.utf8_table.put(text, new ConstantPoolGen$Index(value2));
    }
    return value2;
  }
}
