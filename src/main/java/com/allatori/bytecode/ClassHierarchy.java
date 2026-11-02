package com.allatori.bytecode;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen$2;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class ClassHierarchy {

  public Hashtable entries;

  public Hashtable entries2;

  public Vector values;

  public static final String TEXT = "-=404=-";

  public static final Vector VALUES = new Vector();

  public static final Set VALUES2 = new OrderedClassSet();

  public ClassStorage classStorage;

  public Set values2;

  public Set createValues(Set values) {
    if (values.contains("-=404=-")) {
      return VALUES2;
    }
    TreeSet treeSet = new TreeSet();
    for (String text : ((Iterable<String>) ((Iterable<?>) values))) {
      if (this.classStorage.getClassGen(text) == null) {
        try {
          Method[] methods;
          for (int value =
                  (methods =
                              Class.forName(text, false, this.classStorage.getClassLoader())
                                  .getDeclaredMethods())
                          .length
                      - 1;
              value >= 0;
              value--) {
            Method method = methods[value];
            if (this.matchesMethod(method)) {
              treeSet.add(method.getName() + "&" + Type.getSignature(method));
            }
          }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (NoClassDefFoundError noClassDefFoundError) {
          String text2;
          if ((text2 = noClassDefFoundError.getMessage()).length() > 0) {
            String text3 = text2.replace('/', '.');
            if (!this.entries.containsKey(text3)) {
              this.entries.put(text3, "");
              Log.warn("Class '" + text3 + "' cannot be found.");
            }
          }
        } catch (VerifyError verifyError) {
          Log.warn(
              "Cannot read class. Possible JVM compile and runtime versions mismatch: "
                  + verifyError.getMessage());
        }
      } else {
        int value2;
        if (Configuration.getIgnoreClassesOptions()
            .matchesClassGen(this.classStorage, this.classStorage.getClassGen(text))) {
          com.allatori.lib.bcel.classfile.Method[] methods2;
          for (int value3 =
                  value2 = (methods2 = this.classStorage.getClassGen(text).getMethods()).length - 1;
              value3 >= 0;
              value3 = --value2) {
            com.allatori.lib.bcel.classfile.Method method2;
            if (!(method2 = methods2[value2]).isEnabled9() && !method2.isEnabled11()) {
              treeSet.add("" + method2.getText() + "&" + method2.getText2());
            }
          }
        }
      }
    }
    return treeSet;
  }

  public void processClassGen(ClassGen classGen) {
    this.values.remove(classGen);
    this.values.add(0, classGen);
    String superclassName = classGen.getSuperclassName();
    ClassGen classGen2 = this.classStorage.getClassGen(superclassName);
    if (classGen2 != null) {
      this.processClassGen(classGen2);
    }
    String[] texts;
    int value;
    for (int value2 = value = (texts = classGen.getInterfaceNames()).length - 1;
        value2 >= 0;
        value2 = --value) {
      ClassGen classGen3;
      if ((classGen3 = this.classStorage.getClassGen(texts[value])) != null) {
        this.processClassGen(classGen3);
      }
    }
  }

  public void update() {
    Hashtable hashtable = new Hashtable();
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      ClassGen classGen = (ClassGen) this.values.get(value);
      hashtable.put(classGen, "");
      Vector values2 = this.getValues(classGen.getClassName());
      int value3 = 0;
      for (int value4 = value3; value4 < values2.size(); value4 = ++value3) {
        ClassGen classGen2 = (ClassGen) values2.get(value3);
        if (hashtable.containsKey(classGen2)) {
          Log.error("Incorrect classes order");
        }
      }
    }
  }

  public boolean matchesText(String text, String text2, String text3) {
    return this.values2.contains("" + text + "&" + text2 + "&" + text3);
  }

  public void processClassGen2(ClassGen classGen) {
    Set values = this.createValues(classGen);
    Set values3;
    Set values4 = this.createValues(values3 = this.createValues2(classGen));
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.createValues(values, values4).iterator();
    while (iterator3.hasNext()) {
      Set values5 = this.values2;
      StringBuilder stringBuilder =
          new StringBuilder().insert(0, classGen.getClassName()).append("&");
      iterator3 = iterator2;
      values5.add(stringBuilder.append(iterator2.next()).toString());
    }
    this.processValues(values3, values4);
  }

  public void update2() {
    Vector vector = new Vector();
    Iterator iterator;
    Iterator iterator2 = iterator = this.classStorage.getClassesByName2();
    while (iterator2.hasNext()) {
      iterator2 = iterator;
      vector.add(iterator.next());
    }
    Collections.sort(vector, new ClassHierarchyComparator(this));
    this.values = new Vector();
    for (ClassGen classGen : ((Iterable<ClassGen>) ((Iterable<?>) vector))) {
      if (this.getValues(classGen.getClassName()).size() == 0) {
        this.processClassGen(classGen);
      }
    }
    Collections.sort(this.values, new MethodGen$2(this));
    this.update();
  }

  public Set createValues(Set values, Set values2) {
    TreeSet treeSet = new TreeSet();
    Set values3 = values.size() < values2.size() ? values : values2;
    Set values4 = values.size() < values2.size() ? values2 : values;
    for (Object result : values3) {
      if (values4.contains(result)) {
        treeSet.add(result);
      }
    }
    return treeSet;
  }

  public void processValues(Set values, Set values3) {
    Iterator iterator2 = values.iterator();
    label36:
    while (true) {
      for (Iterator iterator3 = iterator2; iterator3.hasNext(); iterator3 = iterator2) {
        String text = (String) iterator2.next();
        if (!"-=404=-".equals(text)) {
          ClassGen classGen;
          if ((classGen = this.classStorage.getClassGen(text)) != null && classGen.isEnabled5()) {
            Iterator iterator4;
            iterator3 =
                iterator4 = this.createValues(this.createValues(classGen), values3).iterator();
            while (iterator3.hasNext()) {
              Set values4 = this.values2;
              StringBuilder stringBuilder =
                  new StringBuilder().insert(0, classGen.getClassName()).append("&");
              iterator3 = iterator4;
              values4.add(stringBuilder.append(iterator4.next()).toString());
            }
          }
          continue label36;
        }
      }
      return;
    }
  }

  public Vector getValues() {
    return this.values;
  }

  public void processClassGen3(ClassGen classGen) {
    this.processClassGen(classGen.getSuperclassName(), classGen);
    int value;
    String[] texts;
    for (int value2 = value = (texts = classGen.getInterfaceNames()).length - 1;
        value2 >= 0;
        value2 = value) {
      int value3 = value;
      String[] texts2 = texts;
      value--;
      this.processClassGen(texts2[value3], classGen);
    }
  }

  public Set createValues(ClassGen classGen) {
    TreeSet treeSet = new TreeSet();
    com.allatori.lib.bcel.classfile.Method[] methods;
    int value;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      com.allatori.lib.bcel.classfile.Method method = methods[value];
      if (this.matchesMethod(method)) {
        treeSet.add(method.getText() + "&" + method.getText2());
      }
    }
    return treeSet;
  }

  public ClassHierarchy(ClassStorage classStorage2) {
    super();
    this.entries2 = new Hashtable();
    this.values2 = new TreeSet();
    this.entries = new Hashtable();
    this.classStorage = classStorage2;
  }

  public void processText(Set values, String text) {
    values.add(text);
    ClassGen classGen = this.classStorage.getClassGen(text);
    if (classGen != null) {
      this.processText(values, classGen.getSuperclassName());
      int value;
      String[] texts;
      for (int value2 = value = (texts = classGen.getInterfaceNames()).length - 1;
          value2 >= 0;
          value2 = value) {
        int value3 = value;
        String[] texts2 = texts;
        value--;
        this.processText(values, texts2[value3]);
      }
    } else {
      try {
        Class classValue;
        if ((classValue = Class.forName(text, false, this.classStorage.getClassLoader()))
                .getSuperclass()
            != null) {
          this.processText(values, classValue.getSuperclass().getName());
        }
        Class[] classs;
        int value4 = (classs = classValue.getInterfaces()).length - 1;
        while (value4 >= 0) {
          int value5 = value4;
          Class[] classs2 = classs;
          value4--;
          this.processText(values, classs2[value5].getName());
        }
      } catch (Exception exception) {
        values.add("-=404=-");
        if (!this.entries.containsKey(text)) {
          this.entries.put(text, "");
          Log.warn("Class '" + text + "' cannot be found.");
        }
      }
    }
  }

  public boolean matchesMethod(com.allatori.lib.bcel.classfile.Method method) {
    return !method.isEnabled9() && !method.isEnabled11();
  }

  public Vector getValues(String text) {
    Vector vector;
    return (vector = (Vector) this.entries2.get(text)) != null ? vector : VALUES;
  }

  public Set createValues2(ClassGen classGen) {
    TreeSet treeSet = new TreeSet();
    this.processText(treeSet, classGen.getClassName());
    return treeSet;
  }

  public boolean matchesMethod(Method method) {
    return !Modifier.isPrivate(method.getModifiers()) && !Modifier.isStatic(method.getModifiers());
  }

  public void update3() {
    Iterator iterator;
    Iterator iterator2 = iterator = this.classStorage.getClassesByName2();
    while (iterator2.hasNext()) {
      ClassGen classGen = (ClassGen) iterator.next();
      iterator2 = iterator;
      this.processClassGen3(classGen);
      this.processClassGen2(classGen);
    }
    if (this.entries.size() > 0) {
      Log.warn(
          "Some classes weren't found. It may result in weaker obfuscation. Add needed jars to the"
              + " 'classpath' element of the configuration file.");
    }
    this.update2();
  }

  public void processClassGen(String text, ClassGen classGen) {
    if (this.classStorage.getClassGen(text) != null) {
      Vector vector;
      if ((vector = (Vector) this.entries2.get(text)) == null) {
        vector = new Vector();
      }
      vector.add(classGen);
      this.entries2.put(text, vector);
    }
  }
}
