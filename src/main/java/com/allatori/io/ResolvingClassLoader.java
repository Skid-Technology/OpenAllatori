package com.allatori.io;

import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.LookupTable;
import java.net.URL;
import java.net.URLClassLoader;

public class ResolvingClassLoader extends URLClassLoader {

  public ClassStorage classStorage;

  public LookupTable lookupTable;

  public ResolvingClassLoader(ClassStorage classStorage2, LookupTable lookupTable2) {
    super(new URL[0], classStorage2.getClassLoader());
    this.classStorage = classStorage2;
    this.lookupTable = lookupTable2;
  }

  public Class getType(String text) {
    ClassGen classGen = this.classStorage.getClassGen(text);
    byte[] bytes = classGen.getJavaClass().getBytes();
    return this.defineClass(classGen.getClassName(), bytes, 0, bytes.length);
  }

  @Override
  public Class findClass(String text) throws ClassNotFoundException {
    String text2 = text;
    try {
      if (this.lookupTable.matchesText(text2)) {
        text2 = this.lookupTable.getText(text2);
      }
      if (this.classStorage.getClassGen(text2) != null) {
        return this.getType(text2);
      }
    } catch (Exception exception) {
    }
    return super.findClass(text2);
  }
}
