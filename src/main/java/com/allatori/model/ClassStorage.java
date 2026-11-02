package com.allatori.model;

import com.allatori.io.ClassStorageClassLoader;
import com.allatori.lib.bcel.generic.ClassGen;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class ClassStorage {

  public Hashtable classesByName;

  public Hashtable classEntryNames;

  public Vector inputClasses;

  public ClassLoader classLoader;

  public Hashtable entryPrefixes;

  public HashSet duplicateClasses;

  public Hashtable sourceFiles;

  public void processText(ClassGen classGen, String text, String text2) {
    this.processText(classGen, text);
    this.classEntryNames.put("" + text + '#' + text2, classGen.getClassName());
    String text3 = "" + classGen.getClassName().replace('.', '/') + ".class";
    if (text2.endsWith(text3)) {
      String text4 = text2.substring(0, text2.length() - text3.length());
      this.entryPrefixes.put("" + text + '#' + classGen.getClassName(), text4);
    }
  }

  public void processClassGen(ClassGen classGen) {
    this.classesByName.put(classGen.getClassName(), classGen);
    this.inputClasses.add(classGen);
  }

  public String getText(ClassGen classGen) {
    return (String) this.sourceFiles.get(classGen.getClassName());
  }

  public void processText(ClassGen classGen, String text) {
    if (!"java.lang.Object".equals(classGen.getClassName())) {
      if (this.classesByName.containsKey(classGen.getClassName())) {
        this.duplicateClasses.add(classGen);
      } else {
        this.classesByName.put(classGen.getClassName(), classGen);
        this.sourceFiles.put(classGen.getClassName(), text);
      }
    }
  }

  public Vector getInputClasses() {
    return this.inputClasses;
  }

  public boolean matchesClassGen(ClassGen classGen) {
    return this.duplicateClasses.contains(classGen);
  }

  public int getClassesByName() {
    return this.classesByName.size();
  }

  public ClassGen getClassGen(String text) {
    return (ClassGen) this.classesByName.get(text);
  }

  public ClassLoader getClassLoader() {
    return this.classLoader;
  }

  public Iterator getClassesByName2() {
    return this.classesByName.values().iterator();
  }

  public String getText(String text) {
    return (String) this.classEntryNames.get(text);
  }

  public ClassStorage(ClassStorageClassLoader classStorageClassLoader) {
    super();
    this.classesByName = new Hashtable();
    this.sourceFiles = new Hashtable();
    this.duplicateClasses = new HashSet();
    this.inputClasses = new Vector();
    this.classEntryNames = new Hashtable();
    this.entryPrefixes = new Hashtable();
    this.classLoader = classStorageClassLoader;
    classStorageClassLoader.setClassStorage(this);
  }

  public String getText2(String text) {
    return (String) this.entryPrefixes.get(text);
  }
}
