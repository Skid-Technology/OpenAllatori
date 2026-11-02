package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class SyntheticOptions {

  public RuleSet ruleSet;

  public RuleSet ruleSet2;

  public final Configuration configuration;

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    if (classStorage == null) {
      return false;
    }
    AnnotationEntry[] annotationEntries = method.getAnnotationEntries();
    int value;
    for (int value2 = value = 0; value2 < annotationEntries.length; value2 = ++value) {
      AnnotationEntry annotationEntry = annotationEntries[value];
      if ("Lcom/allatori/annotations/Synthetize;".equals(annotationEntry.getText())) {
        return true;
      }
    }
    return this.ruleSet2.getValues() == 0
        ? method.isEnabled9()
        : this.ruleSet2.calculateCount(classStorage, classGen, method) > 0;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public SyntheticOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(0);
    this.ruleSet2 = new RuleSet(0);
  }

  public void processRuleEntry2(RuleEntry ruleEntry) {
    this.ruleSet2.processRuleEntry(ruleEntry);
  }

  public boolean matchesField(ClassStorage classStorage, ClassGen classGen, Field field) {
    if (classStorage == null) {
      return false;
    }
    AnnotationEntry[] annotationEntries = field.getAnnotationEntries();
    int value;
    for (int value2 = value = 0; value2 < annotationEntries.length; value2 = ++value) {
      AnnotationEntry annotationEntry = annotationEntries[value];
      if ("Lcom/allatori/annotations/Synthetize;".equals(annotationEntry.getText())) {
        return true;
      }
    }
    return this.ruleSet.getValues() == 0
        ? false
        : this.ruleSet.calculateCount(classStorage, classGen, field) > 0;
  }
}
