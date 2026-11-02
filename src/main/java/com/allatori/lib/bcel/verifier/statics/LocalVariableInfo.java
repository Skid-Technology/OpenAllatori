package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.exc.ClassConstraintException;
import java.util.Hashtable;

public class LocalVariableInfo {

  public Hashtable types = new Hashtable();

  public Hashtable names = new Hashtable();

  public Type getType(int value) {
    return (Type) this.types.get(Integer.toString(value));
  }

  public void add(int value, String text, Type type) {
    if (this.getName(value) != null && !this.getName(value).equals(text)) {
      throw new ClassConstraintException(
          ("At bytecode offset '"
              + value
              + "' a local variable has two different names: '"
              + this.getName(value)
              + "' and '"
              + text
              + "'."));
    }
    if (this.getType(value) != null && !this.getType(value).equals(type)) {
      throw new ClassConstraintException(
          ("At bytecode offset '"
              + value
              + "' a local variable has two different types: '"
              + this.getType(value)
              + "' and '"
              + type
              + "'."));
    }
    this.setName(value, text);
    this.setType(value, type);
  }

  public void setName(int value, String text) {
    this.names.put(Integer.toString(value), text);
  }

  public void setType(int value, Type type) {
    this.types.put(Integer.toString(value), type);
  }

  public void add(String text, int value, int value2, Type type) {
    int value3;
    for (int value4 = value3 = value; value4 <= value + value2; value4 = value3) {
      int value5 = value3;
      value3++;
      this.add(value5, text, type);
    }
  }

  public String getName(int value) {
    return (String) this.names.get(Integer.toString(value));
  }
}
