package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Vector;

public class FieldRemovalOptions {

  public Vector values;

  public final Configuration configuration;

  public RuleSet ruleSet;

  public boolean isValues() {
    return this.values.size() > 0;
  }

  public FieldRemovalOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(0);
    this.values = new Vector();
  }

  public void processText(String text) {
    this.values.add(text);
  }

  public boolean matchesText(String text) {
    return this.values.contains(text);
  }

  public boolean matchesField(ClassStorage classStorage, ClassGen classGen, Field field) {
    if (classStorage == null) {
      return false;
    } else {
      return this.ruleSet.getValues() == 0
          ? false
          : this.ruleSet.calculateCount(classStorage, classGen, field) > 0;
    }
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }
}
