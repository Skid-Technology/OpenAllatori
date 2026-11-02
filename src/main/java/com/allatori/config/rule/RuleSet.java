package com.allatori.config.rule;

import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;
import java.util.Vector;

public class RuleSet {

  public Vector values;

  public int count;

  public int getValues() {
    return this.values.size();
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, Field field) {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      RuleEntry ruleEntry;
      if ((ruleEntry = (RuleEntry) this.values.get(value))
          .classRule.matches(classStorage, classGen)) {
        if (field == null) {
          return ruleEntry.count;
        }
        if (ruleEntry.classRule.matches(classStorage, field)) {
          return ruleEntry.count;
        }
      }
    }
    return this.count;
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, Method method) {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      RuleEntry ruleEntry;
      if ((ruleEntry = (RuleEntry) this.values.get(value))
          .classRule.matches(classStorage, classGen)) {
        if (method == null) {
          return ruleEntry.count;
        }
        if (ruleEntry.classRule.matches(method)) {
          return ruleEntry.count;
        }
      }
    }
    return this.count;
  }

  public RuleSet(int count) {
    super();
    this.values = new Vector();
    this.count = count;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.values.add(ruleEntry);
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      RuleEntry ruleEntry;
      if ((ruleEntry = (RuleEntry) this.values.get(value))
          .classRule.matches(classStorage, classGen)) {
        if (methodGen == null) {
          return ruleEntry.count;
        }
        if (ruleEntry.classRule.matches(methodGen)) {
          return ruleEntry.count;
        }
      }
    }
    return this.count;
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen) {
    return this.calculateCount(classStorage, classGen, (MethodGen) null);
  }
}
