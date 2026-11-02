package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;

public class DebugInfoOptions {

  public String text;

  public boolean enabled;

  public final Configuration configuration;

  public String text2;

  public RuleSet ruleSet;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public String getText() {
    return this.text;
  }

  public String getText2() {
    return this.text2;
  }

  public DebugInfoOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(0);
  }

  public boolean matchesMethodGen(
      ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    return this.ruleSet.calculateCount(classStorage, classGen, methodGen) == 1;
  }

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    return this.ruleSet.calculateCount(classStorage, classGen) == 1;
  }

  public void setText2(String text2) {
    this.text2 = text2;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }
}
