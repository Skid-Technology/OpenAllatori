package com.allatori.config;

import com.allatori.config.rule.RuleEntry;

public class MethodCallRemovalRule {

  public String text;

  public RuleEntry ruleEntry;

  public final MethodCallRemovalOptions methodCallRemovalOptions;

  public String text2;

  public MethodCallRemovalRule(
      MethodCallRemovalOptions methodCallRemovalOptions2,
      RuleEntry ruleEntry2,
      String text,
      String text2) {
    super();
    this.methodCallRemovalOptions = methodCallRemovalOptions2;
    this.ruleEntry = ruleEntry2;
    this.text = text;
    this.text2 = text2;
  }
}
