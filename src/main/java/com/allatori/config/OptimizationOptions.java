package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Vector;

public class OptimizationOptions {

  public RuleSet ruleSet;

  public Vector values;

  public RuleSet ruleSet2;

  public final Configuration configuration;

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    if (classStorage == null) {
      return false;
    } else {
      return this.ruleSet.getValues() == 0
          ? false
          : this.ruleSet.calculateCount(classStorage, classGen) > 0;
    }
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public void processRuleEntry2(RuleEntry ruleEntry) {
    this.ruleSet2.processRuleEntry(ruleEntry);
  }

  public boolean matchesText(String text) {
    return this.values.contains(text);
  }

  public OptimizationOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(0);
    this.ruleSet2 = new RuleSet(0);
    this.values = new Vector();
  }

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    if (classStorage == null) {
      return false;
    } else {
      return this.ruleSet2.getValues() == 0
          ? false
          : this.ruleSet2.calculateCount(classStorage, classGen, method) > 0;
    }
  }

  public void processText(String text) {
    this.values.add(text);
  }
}
