package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class InnerClass implements Cloneable, Node, Serializable {

  public int outer_class_index;

  public int inner_access_flags;

  public int inner_class_index;

  public int inner_name_index;

  public InnerClass(InnerClass innerClass) {
    this(
        innerClass.getInner_class_index(),
        innerClass.getOuter_class_index(),
        innerClass.getInner_name_index(),
        innerClass.getInner_access_flags());
  }

  public InnerClass(
      int inner_class_index, int outer_class_index, int inner_name_index, int inner_access_flags) {
    super();
    this.inner_class_index = inner_class_index;
    this.outer_class_index = outer_class_index;
    this.inner_name_index = inner_name_index;
    this.inner_access_flags = inner_access_flags;
  }

  public final int getOuter_class_index() {
    return this.outer_class_index;
  }

  public final void setInner_class_index(int inner_class_index) {
    this.inner_class_index = inner_class_index;
  }

  public final int getInner_class_index() {
    return this.inner_class_index;
  }

  public final void setOuter_class_index(int outer_class_index) {
    this.outer_class_index = outer_class_index;
  }

  public InnerClass(DataInputStream dataInputStream) throws IOException {
    this(
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort());
  }

  public final void setInner_access_flags(int inner_access_flags) {
    this.inner_access_flags = inner_access_flags;
  }

  public final int getInner_access_flags() {
    return this.inner_access_flags;
  }

  public InnerClass copy() {
    try {
      return (InnerClass) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitInnerClass(this);
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.inner_class_index);
    dataOutputStream.writeShort(this.outer_class_index);
    dataOutputStream.writeShort(this.inner_name_index);
    dataOutputStream.writeShort(this.inner_access_flags);
  }

  public final int getInner_name_index() {
    return this.inner_name_index;
  }

  public final void setInner_name_index(int inner_name_index) {
    this.inner_name_index = inner_name_index;
  }

  public final String toString(ConstantPool constantPool) {
    String text = Utility.getText(constantPool.createText(this.inner_class_index, (byte) 7));
    String text2;
    InnerClass innerClass;
    if (this.outer_class_index != 0) {
      text2 = Utility.getText(constantPool.createText(this.outer_class_index, (byte) 7));
      innerClass = this;
    } else {
      text2 = "<not a member>";
      innerClass = this;
    }
    String text3;
    if (innerClass.inner_name_index != 0) {
      text3 = ((ConstantUtf8) constantPool.getConstant(this.inner_name_index, (byte) 1)).getBytes();
      innerClass = this;
    } else {
      text3 = "<anonymous>";
      innerClass = this;
    }
    String text4;
    String text5 =
        (text4 = Utility.accessToString(innerClass.inner_access_flags, true)).equals("")
            ? ""
            : ("" + text4 + " ");
    return "InnerClass:" + text5 + text + "(\"" + text2 + "\", \"" + text3 + "\")";
  }
}
