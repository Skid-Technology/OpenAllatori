package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class InnerClasses extends Attribute {

  public int number_of_classes;

  public InnerClass[] inner_classes;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.number_of_classes);
    for (int index = 0; value < this.number_of_classes; value = index) {
      int value2 = index;
      InnerClass[] innerClasss = this.inner_classes;
      index++;
      innerClasss[value2].dump(dataOutputStream);
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitInnerClasses(this);
  }

  public final InnerClass[] getInnerClasses() {
    return this.inner_classes;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    InnerClasses innerClasses;
    (innerClasses = (InnerClasses) this.clone()).inner_classes =
        new InnerClass[this.number_of_classes];
    int value;
    for (int value2 = value = 0; value2 < this.number_of_classes; value2 = value) {
      InnerClass[] innerClasss = innerClasses.inner_classes;
      InnerClass innerClass = this.inner_classes[value].copy();
      int value3 = value;
      value++;
      innerClasss[value3] = innerClass;
    }
    innerClasses.constant_pool = constantPool;
    return innerClasses;
  }

  public InnerClasses(int value, int value2, InnerClass[] innerClasss, ConstantPool constantPool) {
    super((byte) 6, value, value2, constantPool);
    this.setInnerClasses(innerClasss);
  }

  public InnerClasses(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (InnerClass[]) null, constantPool);
    this.number_of_classes = dataInputStream.readUnsignedShort();
    this.inner_classes = new InnerClass[this.number_of_classes];
    int value3;
    for (int value4 = value3 = 0; value4 < this.number_of_classes; value4 = value3) {
      InnerClass[] innerClasss = this.inner_classes;
      InnerClass innerClass = new InnerClass(dataInputStream);
      int value5 = value3;
      value3++;
      innerClasss[value5] = innerClass;
    }
  }

  public InnerClasses(InnerClasses innerClasses) {
    this(
        innerClasses.getName_index(),
        innerClasses.getLength(),
        innerClasses.getInnerClasses(),
        innerClasses.getConstantPool());
  }

  public final void setInnerClasses(InnerClass[] innerClasss) {
    this.inner_classes = innerClasss;
    this.number_of_classes = innerClasss == null ? 0 : innerClasss.length;
  }
}
