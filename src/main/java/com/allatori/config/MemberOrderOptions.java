package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class MemberOrderOptions {

  public final Configuration configuration;

  public RuleSet ruleSet;

  public RuleSet ruleSet2;

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet2.processRuleEntry(ruleEntry);
  }

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    return classStorage == null || this.ruleSet.getValues() == 0
        ? false
        : this.ruleSet.calculateCount(classStorage, classGen, method) > 0;
  }

  public boolean matchesField(ClassStorage classStorage, ClassGen classGen, Field field) {
    return classStorage == null || this.ruleSet2.getValues() == 0
        ? false
        : this.ruleSet2.calculateCount(classStorage, classGen, field) > 0;
  }

  public void processRuleEntry2(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public MemberOrderOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet2 = new RuleSet(0);
    this.ruleSet = new RuleSet(0);
  }
}
