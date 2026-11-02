package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AccessFlags;
import com.allatori.lib.bcel.classfile.Attribute;
import java.util.ArrayList;
import java.util.List;

public abstract class FieldGenOrMethodGen extends AccessFlags implements NamedAndTyped, Cloneable {

  public ArrayList arrayList;

  public List attribute_vec = new ArrayList();

  public ConstantPoolGen cp;

  public Type type;

  public String name;

  public void setConstantPool(ConstantPoolGen constantPoolGen) {
    this.cp = constantPoolGen;
  }

  public void removeAttribute(Attribute attribute) {
    this.attribute_vec.remove(attribute);
  }

  public void addAttribute(Attribute attribute) {
    this.attribute_vec.add(attribute);
  }

  @Override
  public void setType(Type type2) {
    if (type2.getType() == 16) {
      throw new IllegalArgumentException(("Type can not be " + type2));
    }
    this.type = type2;
  }

  public void processAnnotationEntryGen(AnnotationEntryGen annotationEntryGen) {
    this.arrayList.add(annotationEntryGen);
  }

  public FieldGenOrMethodGen() {
    this.arrayList = new ArrayList();
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return null;
    }
  }

  public AnnotationEntryGen[] getAnnotationEntryGens() {
    AnnotationEntryGen[] annotationEntryGens = new AnnotationEntryGen[this.arrayList.size()];
    this.arrayList.toArray(annotationEntryGens);
    return annotationEntryGens;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Type getType() {
    return this.type;
  }

  public Attribute[] getAttributes() {
    Attribute[] attributes = new Attribute[this.attribute_vec.size()];
    this.attribute_vec.toArray(attributes);
    return attributes;
  }

  public void removeAttributes() {
    this.arrayList.clear();
  }

  public void removeAttributes2() {
    this.attribute_vec.clear();
  }

  public void processAnnotationEntryGen2(AnnotationEntryGen annotationEntryGen) {
    this.arrayList.remove(annotationEntryGen);
  }

  public ConstantPoolGen getConstantPool() {
    return this.cp;
  }

  public abstract String getText();
}
