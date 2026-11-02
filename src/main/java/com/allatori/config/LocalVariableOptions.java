package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;

public class LocalVariableOptions {

  public RuleSet ruleSet;

  public static final int COUNT = 4;

  public static final int COUNT2 = 5;

  public final RenamingOptions renamingOptions;

  public static final int COUNT3 = 3;

  public static final int COUNT4 = 1;

  public static final int COUNT5 = 6;

  public String localVariablesNaming;

  public static final int COUNT6 = 2;

  public int localVariablesNamingMode;

  public void setLocalVariablesNaming(String localVariablesNaming) {
    this.localVariablesNaming = localVariablesNaming;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    return Configuration.getObfuscationOptions().isEnabled3()
            && Configuration.getObfuscationOptions().isMethodsNamingEnabled()
        ? 4
        : this.ruleSet.calculateCount(classStorage, classGen, methodGen);
  }

  public void setLocalVariablesNamingMode(int localVariablesNamingMode) {
    this.localVariablesNamingMode = localVariablesNamingMode;
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, Method method) {
    return Configuration.getObfuscationOptions().isEnabled3()
            && Configuration.getObfuscationOptions().isMethodsNamingEnabled()
        ? 4
        : this.ruleSet.calculateCount(classStorage, classGen, method);
  }

  public LocalVariableOptions(RenamingOptions renamingOptions2) {
    super();
    this.renamingOptions = renamingOptions2;
    this.localVariablesNamingMode = 6;
    this.ruleSet = new RuleSet(6);
    this.localVariablesNaming = "a";
  }

  public String getLocalVariablesNaming() {
    return this.localVariablesNaming;
  }

  public int calculateCount() {
    return Configuration.getObfuscationOptions().isEnabled3()
            && Configuration.getObfuscationOptions().isMethodsNamingEnabled()
        ? 4
        : this.localVariablesNamingMode;
  }
}
