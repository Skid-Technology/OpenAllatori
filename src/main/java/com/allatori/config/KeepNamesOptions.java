package com.allatori.config;

import com.allatori.config.rule.ClassRule;
import com.allatori.config.rule.FieldRule;
import com.allatori.config.rule.MethodRule;
import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Vector;

public class KeepNamesOptions {

  public final RenamingOptions renamingOptions;

  public Vector values;

  public ClassRule classRule;

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    if (this.matchesMethod2(classStorage, classGen, method)) {
      return true;
    }
    if (RenamingOptions.matchesRenamingOptions(this.renamingOptions)) {
      return false;
    }
    AnnotationEntry[] annotationEntries = method.getAnnotationEntries();
    int value;
    for (int value2 = value = 0; value2 < annotationEntries.length; value2 = ++value) {
      AnnotationEntry annotationEntry = annotationEntries[value];
      if ("Lcom/allatori/annotations/Rename;".equals(annotationEntry.getText())) {
        return true;
      }
      if ("Lcom/allatori/annotations/DoNotRename;".equals(annotationEntry.getText())) {
        return false;
      }
      if ("Lcom/allatori/annotations/KeepName;".equals(annotationEntry.getText())) {
        return false;
      }
    }
    int value3;
    for (int value4 = value3 = 0; value4 < this.values.size(); value4 = ++value3) {
      ClassRule classRule2;
      if ((classRule2 = (ClassRule) this.values.get(value3)).matches5()
          && classRule2.matches(classStorage, classGen)
          && classRule2.matches(method)) {
        return false;
      }
      if (classRule2.matches() && classRule2.matches(classStorage, classGen)) {
        return true;
      }
    }
    return !this.classRule.matches5() || !this.classRule.matches(method);
  }

  public boolean matchesMethod2(ClassStorage classStorage, ClassGen classGen, Method method) {
    return method.getText().startsWith("Allatori") || this.matchesClassGen2(classStorage, classGen);
  }

  public void processFieldRule(FieldRule fieldRule) {
    this.classRule.processFieldRule(fieldRule);
  }

  public KeepNamesOptions(
      RenamingOptions renamingOptions,
      NamingOptionsConstructorToken namingOptionsConstructorToken) {
    this(renamingOptions);
  }

  public boolean matchesField(ClassStorage classStorage, ClassGen classGen, Field field) {
    if (this.matchesField2(classStorage, classGen, field)) {
      return true;
    }
    if (RenamingOptions.matchesRenamingOptions(this.renamingOptions)) {
      return false;
    }
    AnnotationEntry[] annotationEntries = field.getAnnotationEntries();
    int value;
    for (int value2 = value = 0; value2 < annotationEntries.length; value2 = ++value) {
      AnnotationEntry annotationEntry = annotationEntries[value];
      if ("Lcom/allatori/annotations/Rename;".equals(annotationEntry.getText())) {
        return true;
      }
      if ("Lcom/allatori/annotations/DoNotRename;".equals(annotationEntry.getText())) {
        return false;
      }
      if ("Lcom/allatori/annotations/KeepName;".equals(annotationEntry.getText())) {
        return false;
      }
    }
    int value3;
    for (int value4 = value3 = 0; value4 < this.values.size(); value4 = ++value3) {
      ClassRule classRule2;
      if ((classRule2 = (ClassRule) this.values.get(value3)).matches4()
          && classRule2.matches(classStorage, classGen)
          && classRule2.matches(classStorage, field)) {
        return false;
      }
      if (classRule2.matches() && classRule2.matches(classStorage, classGen)) {
        return true;
      }
    }
    return !this.classRule.matches4() || !this.classRule.matches(classStorage, field);
  }

  public boolean matchesField2(ClassStorage classStorage, ClassGen classGen, Field field) {
    return this.matchesClassGen2(classStorage, classGen);
  }

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    if (this.matchesClassGen2(classStorage, classGen)) {
      return true;
    }
    if (RenamingOptions.matchesRenamingOptions(this.renamingOptions)) {
      return false;
    }
    AnnotationEntryGen[] annotationEntries = classGen.getAnnotationEntries();
    int value;
    for (int value2 = value = 0; value2 < annotationEntries.length; value2 = ++value) {
      AnnotationEntryGen annotationEntryGen = annotationEntries[value];
      if ("Lcom/allatori/annotations/Rename;".equals(annotationEntryGen.getText2())) {
        return true;
      }
      if ("Lcom/allatori/annotations/DoNotRename;".equals(annotationEntryGen.getText2())) {
        return false;
      }
      if ("Lcom/allatori/annotations/KeepName;".equals(annotationEntryGen.getText2())) {
        return false;
      }
    }
    int value3;
    for (int value4 = value3 = 0; value4 < this.values.size(); value4 = ++value3) {
      ClassRule classRule;
      if ((!(classRule = (ClassRule) this.values.get(value3)).matches2() || classRule.matches())
          && classRule.matches(classStorage, classGen)) {
        if (classRule.matches()) {
          return true;
        }
        if (!classRule.matches3()) {
          return false;
        }
        int value5;
        Field[] fields;
        for (int value6 = value5 = (fields = classGen.getFields()).length - 1;
            value6 >= 0;
            value6 = --value5) {
          Field field = fields[value5];
          if (!Configuration.getRenamingOptions()
              .getKeepNamesOptions()
              .matchesField(classStorage, classGen, field)) {
            return false;
          }
        }
        int value7;
        Method[] methods;
        for (int value8 = value7 = (methods = classGen.getMethods()).length - 1;
            value8 >= 0;
            value8 = --value7) {
          Method method = methods[value7];
          if (!Configuration.getRenamingOptions()
              .getKeepNamesOptions()
              .matchesMethod(classStorage, classGen, method)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public void processClassRule(ClassRule classRule) {
    this.values.add(classRule);
  }

  public KeepNamesOptions(RenamingOptions renamingOptions2) {
    super();
    this.renamingOptions = renamingOptions2;
    this.values = new Vector();
    try {
      this.classRule = new ClassRule("class *", true, false);
      ClassRule classRule2 = new ClassRule("class * extends java.lang.Enum", true, false);
      classRule2.processMethodRule(new MethodRule("values(**)", false, false));
      classRule2.processMethodRule(new MethodRule("valueOf(**)", false, false));
      this.processClassRule(classRule2);
      this.processClassRule(new ClassRule("interface *.package-info", false, false));
    } catch (ConfigurationException configurationException) {
      configurationException.printStackTrace();
    }
  }

  public void processMethodRule(MethodRule methodRule) {
    this.classRule.processMethodRule(methodRule);
  }

  public boolean matchesClassGen2(ClassStorage classStorage, ClassGen classGen) {
    return classStorage.getInputClasses().contains(classGen);
  }
}
