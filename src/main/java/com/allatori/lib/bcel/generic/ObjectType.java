package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;

public class ObjectType extends ReferenceType {

  public String class_name;

  @Override
  public boolean equals(Object result) {
    return result instanceof ObjectType
        ? ((ObjectType) result).class_name.equals(this.class_name)
        : false;
  }

  public boolean matchesObjectType(ObjectType objectType) throws ClassNotFoundException {
    return !this.isEnabled2() && !objectType.isEnabled2()
        ? Repository.matchesText(this.class_name, objectType.class_name)
        : false;
  }

  public ObjectType(String text) {
    super((byte) 14, ("L" + text.replace('.', '/') + ";"));
    this.class_name = text.replace('/', '.');
  }

  @Override
  public int hashCode() {
    return this.class_name.hashCode();
  }

  public boolean isEnabled() throws ClassNotFoundException {
    return !Repository.lookupClass(this.class_name).isEnabled17();
  }

  public String getClassName() {
    return this.class_name;
  }

  /**
   * @deprecated
   */
  public boolean isEnabled2() {
    try {
      return !Repository.lookupClass(this.class_name).isEnabled17();
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    }
  }

  public boolean isClass_name() throws ClassNotFoundException {
    return Repository.lookupClass(this.class_name).isEnabled17();
  }

  /**
   * @deprecated
   */
  public boolean isClass_name2() {
    try {
      return Repository.lookupClass(this.class_name).isEnabled17();
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    }
  }

  public boolean matchesObjectType2(ObjectType objectType) throws ClassNotFoundException {
    JavaClass javaClass;
    return (javaClass = Repository.lookupClass(this.class_name)).isEnabled6()
        ? true
        : Repository.lookupClass(objectType.class_name).getText4().equals(javaClass.getText4());
  }
}
