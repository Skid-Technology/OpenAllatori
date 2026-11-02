package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class MethodRemovalOptions {

  public String testString;

  public RuleSet ruleSet;

  public final Configuration configuration;

  public void setTestString(String testString) {
    this.testString = testString;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public MethodRemovalOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(0);
    this.testString = null;
  }

  public String getTestString() {
    return this.testString;
  }

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    if (classStorage == null) {
      return false;
    } else {
      return this.ruleSet.getValues() == 0
          ? false
          : this.ruleSet.calculateCount(classStorage, classGen, method) > 0;
    }
  }
}
