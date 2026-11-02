package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class FieldOrMethod extends AccessFlags implements Cloneable, Node {

  public boolean enabled;

  public AnnotationEntry[] annotationEntries;

  public int count;

  public String signatureAttributeString = null;

  public Attribute[] attributes;

  public boolean enabled2;

  public ConstantPool constant_pool;

  public int count2;

  public int count3;

  public final String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.count3, (byte) 1)).getBytes();
  }

  public FieldOrMethod() {
    this.enabled = false;
    this.enabled2 = true;
  }

  public final int getCount() {
    return this.count;
  }

  public void update() {
    if (this.enabled2) {
      Attribute[] attributes = this.getAttributes();
      ArrayList values = new ArrayList();
      int value;
      for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
        Attribute attribute;
        if ((attribute = attributes[value]) instanceof Annotations) {
          Annotations annotations = (Annotations) attribute;
          int value3;
          for (int value4 = value3 = 0;
              value4 < annotations.getAnnotationEntries().length;
              value4 = value3) {
            AnnotationEntry[] annotationEntries2 = annotations.getAnnotationEntries();
            int value5 = value3;
            value3++;
            values.add(annotationEntries2[value5]);
          }
        }
      }
      this.annotationEntries =
          (AnnotationEntry[]) values.toArray(new AnnotationEntry[values.size()]);
      this.enabled2 = false;
    }
  }

  public final int getCount3() {
    return this.count3;
  }

  public FieldOrMethod(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        null,
        constantPool);
    this.count2 = dataInputStream.readUnsignedShort();
    this.attributes = new Attribute[this.count2];
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = value) {
      Attribute[] attributes2 = this.attributes;
      Attribute attribute = Attribute.readAttribute(dataInputStream, constantPool);
      int value3 = value;
      value++;
      attributes2[value3] = attribute;
    }
  }

  public FieldOrMethod(
      int access_flags, int count3, int count, Attribute[] attributes, ConstantPool constantPool) {
    super();
    this.signatureAttributeString = null;
    this.enabled = false;
    this.enabled2 = true;
    this.access_flags = access_flags;
    this.count3 = count3;
    this.count = count;
    this.constant_pool = constantPool;
    this.setAttributes(attributes);
  }

  public void processAnnotationEntry(AnnotationEntry annotationEntry) {
    this.update();
    AnnotationEntry[] annotationEntrys;
    int value;
    AnnotationEntry[] annotationEntrys2 =
        annotationEntrys = new AnnotationEntry[(value = this.annotationEntries.length) + 1];
    System.arraycopy(this.annotationEntries, 0, annotationEntrys, 0, value);
    annotationEntrys2[value] = annotationEntry;
    this.annotationEntries = annotationEntrys2;
  }

  public FieldOrMethod copy_(ConstantPool constantPool) {
    FieldOrMethod fieldOrMethod = null;
    FieldOrMethod fieldOrMethod2;
    label22:
    {
      try {
        fieldOrMethod = (FieldOrMethod) this.clone();
      } catch (CloneNotSupportedException cloneNotSupportedException) {
        fieldOrMethod2 = fieldOrMethod;
        break label22;
      }
      fieldOrMethod2 = fieldOrMethod;
    }
    fieldOrMethod2.constant_pool = this.constant_pool;
    fieldOrMethod.attributes = new Attribute[this.count2];
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = value) {
      Attribute[] attributes2 = fieldOrMethod.attributes;
      Attribute attribute = this.attributes[value].copy(this.constant_pool);
      int value3 = value;
      value++;
      attributes2[value3] = attribute;
    }
    return fieldOrMethod;
  }

  public final ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public final String getText2() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.count, (byte) 1)).getBytes();
  }

  public FieldOrMethod(FieldOrMethod fieldOrMethod) {
    this(
        fieldOrMethod.getAccess_flags2(),
        fieldOrMethod.getCount3(),
        fieldOrMethod.getCount(),
        fieldOrMethod.getAttributes(),
        fieldOrMethod.getConstantPool());
  }

  public AnnotationEntry[] getAnnotationEntries() {
    this.update();
    return this.annotationEntries;
  }

  public final void setCount3(int count3) {
    this.count3 = count3;
  }

  public final void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeShort(this.access_flags);
    dataOutputStream.writeShort(this.count3);
    dataOutputStream.writeShort(this.count);
    dataOutputStream.writeShort(this.count2);
    for (int index = 0; value < this.count2; value = index) {
      int value2 = index;
      Attribute[] attributes2 = this.attributes;
      index++;
      attributes2[value2].dump(dataOutputStream);
    }
  }

  public final void setAttributes(Attribute[] attributes2) {
    this.attributes = attributes2;
    this.count2 = attributes2 == null ? 0 : attributes2.length;
  }

  public final void setCount(int count) {
    this.count = count;
  }

  public final Attribute[] getAttributes() {
    return this.attributes;
  }

  public final String getText3() {
    if (!this.enabled) {
      byte value = 0;
      int value2 = 0;
      for (byte value3 = value; value3 == 0 && value2 < this.count2; value3 = value) {
        if (this.attributes[value2] instanceof Signature) {
          this.signatureAttributeString = ((Signature) this.attributes[value2]).getText();
          value = 1;
        }
        value2++;
      }
      this.enabled = true;
    }
    return this.signatureAttributeString;
  }
}
