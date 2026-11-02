package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ClassSet implements Serializable {

  public Map _map = new HashMap();

  public JavaClass[] toArray() {
    Collection values2;
    JavaClass[] javaClasss = new JavaClass[(values2 = this._map.values()).size()];
    values2.toArray(javaClasss);
    return javaClasss;
  }

  public boolean empty() {
    return this._map.isEmpty();
  }

  public String[] getClassNames() {
    return (String[]) this._map.keySet().toArray(new String[this._map.keySet().size()]);
  }

  public boolean add(JavaClass javaClass) {
    byte value = 0;
    if (!this._map.containsKey(javaClass.getText2())) {
      value = 1;
      this._map.put(javaClass.getText2(), javaClass);
    }
    return value != 0;
  }

  public void remove(JavaClass javaClass) {
    this._map.remove(javaClass.getText2());
  }
}
