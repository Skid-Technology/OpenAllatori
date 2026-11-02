package com.allatori.lib.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public class MethodParameter implements Cloneable, Node {

  public int nameIndex;

  public int accessFlags;

  public MethodParameter() {}

  public boolean isEnabled() {
    return (this.accessFlags & 16) != 0;
  }

  public MethodParameter(DataInput dataInput) throws IOException {
    super();
    this.nameIndex = dataInput.readUnsignedShort();
    this.accessFlags = dataInput.readUnsignedShort();
  }

  public int getNameIndex() {
    return this.nameIndex;
  }

  public MethodParameter copy() {
    try {
      return (MethodParameter) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public boolean isEnabled2() {
    return (this.accessFlags & -32768) != 0;
  }

  @Override
  public void accept(Visitor visitor) {}

  public void setNameIndex(int nameIndex) {
    this.nameIndex = nameIndex;
  }

  public int getAccessFlags() {
    return this.accessFlags;
  }

  public String getParameterName(ConstantPool constantPool) {
    return this.nameIndex == 0 ? null : constantPool.createText(this.nameIndex, (byte) 1);
  }

  public boolean isEnabled3() {
    return (this.accessFlags & 4096) != 0;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.nameIndex);
    dataOutputStream.writeShort(this.accessFlags);
  }

  public void setAccessFlags(int accessFlags) {
    this.accessFlags = accessFlags;
  }
}
