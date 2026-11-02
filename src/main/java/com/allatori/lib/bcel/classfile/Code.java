package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Code extends Attribute {

  public int max_locals;

  public Attribute[] attributes;

  public int attributes_count;

  public CodeException[] exception_table;

  public int max_stack;

  public int exception_table_length;

  public int code_length;

  public byte[] code;

  @Override
  public void accept(Visitor visitor) {
    visitor.visitCode(this);
  }

  public final void setMax_locals(int max_locals) {
    this.max_locals = max_locals;
  }

  public final void setCode(byte[] code) {
    this.code = code;
    this.code_length = code == null ? 0 : code.length;
  }

  public final byte[] getCode() {
    return this.code;
  }

  public Code(
      int value,
      int value2,
      int max_stack,
      int max_locals,
      byte[] values,
      CodeException[] codeExceptions,
      Attribute[] attributes,
      ConstantPool constantPool) {
    super((byte) 2, value, value2, constantPool);
    this.max_stack = max_stack;
    this.max_locals = max_locals;
    this.setCode(values);
    this.setExceptionTable(codeExceptions);
    this.setAttributes(attributes);
  }

  public Code(int value, int length, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(
        value,
        length,
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        (byte[]) null,
        (CodeException[]) null,
        (Attribute[]) null,
        constantPool);
    this.code_length = dataInputStream.readInt();
    this.code = new byte[this.code_length];
    dataInputStream.readFully(this.code);
    this.exception_table_length = dataInputStream.readUnsignedShort();
    this.exception_table = new CodeException[this.exception_table_length];
    int value3;
    for (int value4 = value3 = 0; value4 < this.exception_table_length; value4 = value3) {
      CodeException[] codeExceptions = this.exception_table;
      CodeException codeException = new CodeException(dataInputStream);
      int value5 = value3;
      value3++;
      codeExceptions[value5] = codeException;
    }
    this.attributes_count = dataInputStream.readUnsignedShort();
    this.attributes = new Attribute[this.attributes_count];
    int value6;
    for (int value7 = value6 = 0; value7 < this.attributes_count; value7 = value6) {
      Attribute[] attributes2 = this.attributes;
      Attribute attribute = Attribute.readAttribute(dataInputStream, constantPool);
      int value8 = value6;
      value6++;
      attributes2[value8] = attribute;
    }
    this.length = length;
  }

  public final void setMax_stack(int max_stack) {
    this.max_stack = max_stack;
  }

  public final int getMax_stack() {
    return this.max_stack;
  }

  public final CodeException[] getExceptionTable() {
    return this.exception_table;
  }

  public final int calculateCount() {
    return 8 + this.code_length + 2 + 8 * this.exception_table_length + 2;
  }

  public LineNumberTable getLineNumberTable() {
    int value;
    for (int value2 = value = 0; value2 < this.attributes_count; value2 = ++value) {
      if (this.attributes[value] instanceof LineNumberTable) {
        return (LineNumberTable) this.attributes[value];
      }
    }
    return null;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    Code code2 = (Code) this.clone();
    if (this.code != null) {
      code2.code = new byte[this.code.length];
      System.arraycopy(this.code, 0, code2.code, 0, this.code.length);
    }
    code2.constant_pool = constantPool;
    code2.exception_table = new CodeException[this.exception_table_length];
    int value;
    for (int value2 = value = 0; value2 < this.exception_table_length; value2 = value) {
      CodeException[] codeExceptions = code2.exception_table;
      CodeException codeException = this.exception_table[value].copy();
      int value3 = value;
      value++;
      codeExceptions[value3] = codeException;
    }
    code2.attributes = new Attribute[this.attributes_count];
    int value4;
    for (int value5 = value4 = 0; value5 < this.attributes_count; value5 = value4) {
      Attribute[] attributes2 = code2.attributes;
      Attribute attribute = this.attributes[value4].copy(constantPool);
      int value6 = value4;
      value4++;
      attributes2[value6] = attribute;
    }
    return code2;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.max_stack);
    dataOutputStream.writeShort(this.max_locals);
    dataOutputStream.writeInt(this.code_length);
    dataOutputStream.write(this.code, 0, this.code_length);
    dataOutputStream.writeShort(this.exception_table_length);
    for (int index = 0; value < this.exception_table_length; value = index) {
      int value2 = index;
      CodeException[] codeExceptions = this.exception_table;
      index++;
      codeExceptions[value2].dump(dataOutputStream);
    }
    dataOutputStream.writeShort(this.attributes_count);
    int value3;
    for (int value4 = value3 = 0; value4 < this.attributes_count; value4 = value3) {
      int value5 = value3;
      Attribute[] attributes2 = this.attributes;
      value3++;
      attributes2[value5].dump(dataOutputStream);
    }
  }

  public final Attribute[] getAttributes() {
    return this.attributes;
  }

  public final int calculateCount2() {
    int value = 0;
    int value2;
    for (int value3 = value2 = 0; value3 < this.attributes_count; value3 = value2) {
      int value4 = this.attributes[value2].length;
      int value5 = value;
      value2++;
      value = value5 + value4 + (byte) 6;
    }
    return value + this.calculateCount();
  }

  public LocalVariableTable getLocalVariableTable() {
    int value;
    for (int value2 = value = 0; value2 < this.attributes_count; value2 = ++value) {
      if (this.attributes[value] instanceof LocalVariableTable) {
        return (LocalVariableTable) this.attributes[value];
      }
    }
    return null;
  }

  public Code(Code code) {
    this(
        code.getName_index(),
        code.getLength(),
        code.getMax_stack(),
        code.getMax_locals(),
        code.getCode(),
        code.getExceptionTable(),
        code.getAttributes(),
        code.getConstantPool());
  }

  public final void setAttributes(Attribute[] attributes2) {
    this.attributes = attributes2;
    this.attributes_count = attributes2 == null ? 0 : attributes2.length;
    this.length = this.calculateCount2();
  }

  public final String toString(boolean matches) {
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer(100))
        .append("Code(max_stack = ")
        .append(this.max_stack)
        .append(", max_locals = ")
        .append(this.max_locals)
        .append(", code_length = ")
        .append(this.code_length)
        .append(")\n")
        .append(Utility.codeToString(this.code, this.constant_pool, 0, -1, matches));
    if (this.exception_table_length > 0) {
      stringBuffer.append("\nException handler(s) = \n").append("From\tTo\tHandler\tType\n");
      int value = 0;
      for (int value2 = value; value2 < this.exception_table_length; value2 = value) {
        StringBuffer stringBuffer2 =
            stringBuffer.append(this.exception_table[value].toString(this.constant_pool, matches));
        value++;
        stringBuffer2.append("\n");
      }
    }
    if (this.attributes_count > 0) {
      stringBuffer.append("\nAttribute(s) = \n");
      int value3 = 0;
      for (int value4 = value3; value4 < this.attributes_count; value4 = value3) {
        StringBuffer stringBuffer3 = stringBuffer.append(this.attributes[value3].toString());
        value3++;
        stringBuffer3.append("\n");
      }
    }
    return stringBuffer.toString();
  }

  public final int getMax_locals() {
    return this.max_locals;
  }

  public final void setExceptionTable(CodeException[] codeExceptions) {
    this.exception_table = codeExceptions;
    this.exception_table_length = codeExceptions == null ? 0 : codeExceptions.length;
  }
}
