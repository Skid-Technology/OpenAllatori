package com.allatori.config;

import com.allatori.config.rule.RuleEntry;

public class PackageNameRule {

  public RuleEntry ruleEntry;

  public final PackageRenamingOptions packageRenamingOptions;

  public String text;

  public PackageNameRule(
      PackageRenamingOptions packageRenamingOptions2, RuleEntry ruleEntry2, String text) {
    super();
    this.packageRenamingOptions = packageRenamingOptions2;
    this.ruleEntry = ruleEntry2;
    this.text = text;
  }
}
