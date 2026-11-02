package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ElementValuePair;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnnotationEntryGen {

  public boolean isRuntimeVisible;

  public List evs;

  public int typeIndex;

  public ConstantPoolGen cpool;

  public int getTypeIndex() {
    return this.typeIndex;
  }

  public boolean isRuntimeVisible() {
    return this.isRuntimeVisible;
  }

  public List getValues() {
    return this.evs;
  }

  public AnnotationEntry getAnnotation() {
    AnnotationEntry annotationEntry =
        new AnnotationEntry(this.typeIndex, this.cpool.getConstantPool(), this.isRuntimeVisible);
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.evs.iterator();
    while (iterator3.hasNext()) {
      ElementValuePairGen elementValuePairGen = (ElementValuePairGen) iterator2.next();
      iterator3 = iterator2;
      annotationEntry.addElementNameValuePair(elementValuePairGen.getElementNameValuePair());
    }
    return annotationEntry;
  }

  public void isRuntimeVisible(boolean isRuntimeVisible) {
    this.isRuntimeVisible = isRuntimeVisible;
  }

  public final String getText() {
    return this.getText2();
  }

  public AnnotationEntryGen(ConstantPoolGen constantPoolGen) {
    super();
    this.isRuntimeVisible = false;
    this.cpool = constantPoolGen;
  }

  public final String getText2() {
    return ((ConstantUtf8) this.cpool.getConstant(this.typeIndex)).getBytes();
  }

  public String toShortString() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("@" + this.getText() + "(");
    int value = 0;
    for (int value2 = value; value2 < this.evs.size(); value2 = ++value) {
      stringBuffer.append(this.evs.get(value));
      if (value + 1 < this.evs.size()) {
        stringBuffer.append(",");
      }
    }
    stringBuffer.append(")");
    return stringBuffer.toString();
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeShort(this.typeIndex);
    dataOutputStream.writeShort(this.evs.size());
    for (int index = 0; value < this.evs.size(); value = index) {
      Object result = this.evs.get(index);
      index++;
      ((ElementValuePairGen) result).dump(dataOutputStream);
    }
  }

  public AnnotationEntryGen(
      AnnotationEntry annotationEntry, ConstantPoolGen constantPoolGen, boolean matches) {
    super();
    this.isRuntimeVisible = false;
    this.cpool = constantPoolGen;
    AnnotationEntryGen annotationEntryGen;
    if (matches) {
      annotationEntryGen = this;
      this.typeIndex = constantPoolGen.addUtf8(annotationEntry.getText());
    } else {
      annotationEntryGen = this;
      this.typeIndex = annotationEntry.getCount2();
    }
    annotationEntryGen.isRuntimeVisible = annotationEntry.isRuntimeVisible();
    this.evs = this.copyValues(annotationEntry.getElementValuePairs(), constantPoolGen, matches);
  }

  public void addElementNameValuePair(ElementValuePairGen elementValuePairGen) {
    if (this.evs == null) {
      this.evs = new ArrayList();
    }
    this.evs.add(elementValuePairGen);
  }

  public void setTypeIndex(int typeIndex) {
    this.typeIndex = typeIndex;
  }

  public AnnotationEntryGen(
      ObjectType objectType, List evs, boolean isRuntimeVisible, ConstantPoolGen constantPoolGen) {
    super();
    this.isRuntimeVisible = false;
    this.cpool = constantPoolGen;
    this.typeIndex = constantPoolGen.addUtf8(objectType.getSignature());
    this.evs = evs;
    this.isRuntimeVisible = isRuntimeVisible;
  }

  public List copyValues(
      ElementValuePair[] elementValuePairs, ConstantPoolGen constantPoolGen, boolean matches) {
    ArrayList values = new ArrayList();
    int value = elementValuePairs.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      ElementValuePair elementValuePair = elementValuePairs[value2];
      value2++;
      values.add(new ElementValuePairGen(elementValuePair, constantPoolGen, matches));
    }
    return values;
  }

  public static AnnotationEntryGen createAnnotationEntryGen(
      DataInputStream dataInputStream, ConstantPoolGen constantPoolGen, boolean matches)
      throws IOException {
    AnnotationEntryGen annotationEntryGen;
    (annotationEntryGen = new AnnotationEntryGen(constantPoolGen)).typeIndex =
        dataInputStream.readUnsignedShort();
    int value = dataInputStream.readUnsignedShort();
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = dataInputStream.readUnsignedShort();
      ElementValueGen elementValueGen =
          ElementValueGen.createElementValueGen(dataInputStream, constantPoolGen);
      value2++;
      annotationEntryGen.addElementNameValuePair(
          new ElementValuePairGen(value4, elementValueGen, constantPoolGen));
    }
    annotationEntryGen.isRuntimeVisible(matches);
    return annotationEntryGen;
  }
}
