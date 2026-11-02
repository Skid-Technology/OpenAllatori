package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class RenamingOptions {

  public String defaultPackage;

  public boolean enabled;

  public NameGenerationOptions nameGenerationOptions;

  public boolean compatibleRenamingEnabled;

  public KeepNamesOptions keepNamesOptions;

  public boolean randomizeNamesEnabled;

  public LocalVariableOptions localVariableOptions;

  public String incrementalObfuscation;

  public boolean classesNamingEnabled;

  public boolean skipRenamingEnabled;

  public RuleSet ruleSet;

  public boolean fieldsNamingEnabled;

  public final Configuration configuration;

  public LineNumberOptions lineNumberOptions;

  public ResourceRenamingOptions resourceRenamingOptions;

  public boolean forceDefaultPackageEnabled;

  public boolean enabled8;

  public boolean isSkipRenamingEnabled() {
    return this.skipRenamingEnabled;
  }

  public void setIncrementalObfuscation(String incrementalObfuscation) {
    this.incrementalObfuscation = incrementalObfuscation;
  }

  public void setEnabled2(boolean compatibleRenamingEnabled) {
    this.compatibleRenamingEnabled = compatibleRenamingEnabled;
  }

  public boolean isForceDefaultPackageEnabled() {
    return this.forceDefaultPackageEnabled;
  }

  public RenamingOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.skipRenamingEnabled = false;
    this.forceDefaultPackageEnabled = false;
    this.enabled8 = false;
    this.fieldsNamingEnabled = false;
    this.randomizeNamesEnabled = true;
    this.enabled = false;
    this.compatibleRenamingEnabled = false;
    this.classesNamingEnabled = false;
    this.ruleSet = new RuleSet(0);
    this.keepNamesOptions = new KeepNamesOptions(this, null);
    this.lineNumberOptions = new LineNumberOptions(this);
    this.nameGenerationOptions = new NameGenerationOptions(this, null);
    this.localVariableOptions = new LocalVariableOptions(this);
    this.resourceRenamingOptions = new ResourceRenamingOptions(this);
  }

  public KeepNamesOptions getKeepNamesOptions() {
    return this.keepNamesOptions;
  }

  public NameGenerationOptions getNameGenerationOptions() {
    return this.nameGenerationOptions;
  }

  public LocalVariableOptions getLocalVariableOptions() {
    return this.localVariableOptions;
  }

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    return this.ruleSet.calculateCount(classStorage, classGen) == 1;
  }

  public LineNumberOptions getLineNumberOptions() {
    return this.lineNumberOptions;
  }

  public void setEnabled3(boolean randomizeNamesEnabled) {
    this.randomizeNamesEnabled = randomizeNamesEnabled;
  }

  public boolean isClassesNamingEnabled() {
    return this.classesNamingEnabled;
  }

  public static boolean matchesRenamingOptions(RenamingOptions renamingOptions) {
    return renamingOptions.skipRenamingEnabled;
  }

  public ResourceRenamingOptions getResourceRenamingOptions() {
    return this.resourceRenamingOptions;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled4(boolean classesNamingEnabled) {
    this.classesNamingEnabled = classesNamingEnabled;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public boolean isText2() {
    return this.incrementalObfuscation != null;
  }

  public void setEnabled8(boolean enabled8) {
    this.enabled8 = enabled8;
  }

  public void setEnabled7(boolean forceDefaultPackageEnabled) {
    this.forceDefaultPackageEnabled = forceDefaultPackageEnabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setDefaultPackage(String defaultPackage) {
    this.defaultPackage = defaultPackage;
  }

  public boolean isFieldsNamingEnabled() {
    return this.fieldsNamingEnabled;
  }

  public static LocalVariableOptions getLocalVariableOptions(RenamingOptions renamingOptions) {
    return renamingOptions.localVariableOptions;
  }

  public void setEnabled6(boolean fieldsNamingEnabled) {
    this.fieldsNamingEnabled = fieldsNamingEnabled;
  }

  public boolean isEnabled8() {
    return this.enabled8;
  }

  public String getDefaultPackage() {
    return this.defaultPackage;
  }

  public boolean isCompatibleRenamingEnabled() {
    return this.compatibleRenamingEnabled;
  }

  public boolean isRandomizeNamesEnabled() {
    return this.randomizeNamesEnabled;
  }

  public String getIncrementalObfuscation() {
    return this.incrementalObfuscation;
  }

  public boolean isText() {
    return this.defaultPackage != null;
  }

  public void setEnabled5(boolean skipRenamingEnabled) {
    this.skipRenamingEnabled = skipRenamingEnabled;
  }
}
