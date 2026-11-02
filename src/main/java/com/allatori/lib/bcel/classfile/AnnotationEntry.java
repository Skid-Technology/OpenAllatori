package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnnotationEntry implements Node, Constants {

  public List element_value_pairs;

  public ConstantPool constant_pool;

  public int count;

  public int count2;

  public boolean isRuntimeVisible;

  public ElementValuePair[] getElementValuePairs() {
    return (ElementValuePair[])
        this.element_value_pairs.toArray(new ElementValuePair[this.element_value_pairs.size()]);
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    dataOutputStream.writeShort(this.count2);
    dataOutputStream.writeShort(this.element_value_pairs.size());
    for (int index = 0; value < this.element_value_pairs.size(); value = index) {
      Object result = this.element_value_pairs.get(index);
      index++;
      ((ElementValuePair) result).dump(dataOutputStream);
    }
  }

  public String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.count2, (byte) 1)).getBytes();
  }

  public int getCount2() {
    return this.count2;
  }

  public static AnnotationEntry createAnnotationEntry(
      DataInputStream dataInputStream, ConstantPool constantPool, boolean matches)
      throws IOException {
    AnnotationEntry annotationEntry =
        new AnnotationEntry(dataInputStream.readUnsignedShort(), constantPool, matches);
    int value = 0;
    annotationEntry.count = dataInputStream.readUnsignedShort();
    for (int index = 0; value < annotationEntry.count; value = index) {
      List values = annotationEntry.element_value_pairs;
      int value2 = dataInputStream.readUnsignedShort();
      ElementValue elementValue = ElementValue.createElementValue(dataInputStream, constantPool);
      index++;
      values.add(new ElementValuePair(value2, elementValue, constantPool));
    }
    return annotationEntry;
  }

  public String createText() {
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer()).append("@");
    stringBuffer.append(this.getText());
    if (this.getElementValuePairs().length > 0) {
      stringBuffer.append("(");
      int value = 0;
      for (int value2 = value; value2 < this.getElementValuePairs().length; value2 = value) {
        ElementValuePair elementValuePair = this.getElementValuePairs()[value];
        StringBuffer stringBuffer2 = stringBuffer;
        value++;
        stringBuffer2.append(elementValuePair.createText());
      }
      stringBuffer.append(")");
    }
    return stringBuffer.toString();
  }

  public void addElementNameValuePair(ElementValuePair elementValuePair) {
    if (this.element_value_pairs == null) {
      this.element_value_pairs = new ArrayList();
    }
    this.element_value_pairs.add(elementValuePair);
  }

  public AnnotationEntry(int count2, ConstantPool constantPool, boolean isRuntimeVisible) {
    super();
    this.count2 = count2;
    this.constant_pool = constantPool;
    this.isRuntimeVisible = isRuntimeVisible;
    this.element_value_pairs = new ArrayList();
  }

  @Override
  public void accept(Visitor visitor) {}

  public final int getCount() {
    return this.count;
  }

  public boolean isRuntimeVisible() {
    return this.isRuntimeVisible;
  }
}
