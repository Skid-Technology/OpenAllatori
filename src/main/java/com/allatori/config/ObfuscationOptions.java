package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ElementValuePairGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;
import java.util.Hashtable;
import java.util.List;

public class ObfuscationOptions {

  public RuleSet ruleSet;

  public static final int COUNT = 1;

  public char stringEncryptionSeed;

  public boolean enabled;

  public static final int COUNT2 = 2;

  public static final int COUNT3 = 1;

  public RuleSet ruleSet2;

  public boolean outputJarCommentEnabled;

  public String decryptorExcludePattern;

  public boolean enabled3;

  public static final int COUNT4 = 0;

  public RuleSet ruleSet3;

  public static final int COUNT5 = 100;

  public static final int COUNT6 = 0;

  public boolean java6Enabled;

  public String stringEncryptionIgnoredStrings;

  public boolean packageStringEncryptionEnabled;

  public Hashtable entries;

  public String decryptorIncludePattern;

  public static final int COUNT7 = 0;

  public RuleSet ruleSet4;

  public String randomSeed;

  public String outputJarComment;

  public boolean methodsNamingEnabled;

  public boolean globalStringEncryptionEnabled;

  public String text6;

  public int stringEncryptionVersion;

  public static final int COUNT8 = 1000;

  public static final int COUNT9 = 3;

  public final Configuration configuration;

  public static final int COUNT10 = 1;

  public RuleSet ruleSet5;

  public static final int COUNT11 = 0;

  public static final int COUNT12 = 101;

  public static final int COUNT13 = 1;

  public RuleSet ruleSet6;

  public boolean finalizeEnabled;

  public RuleSet ruleSet7;

  public void setDecryptorExcludePattern(String decryptorExcludePattern) {
    this.decryptorExcludePattern = decryptorExcludePattern;
  }

  public void setEnabled7(boolean globalStringEncryptionEnabled) {
    this.globalStringEncryptionEnabled = globalStringEncryptionEnabled;
  }

  public void setCharacter(char stringEncryptionSeed) {
    this.stringEncryptionSeed = stringEncryptionSeed;
  }

  public boolean isOutputJarCommentEnabled() {
    return this.outputJarCommentEnabled;
  }

  public String getRandomSeed() {
    return this.randomSeed;
  }

  public void setEnabled3(boolean enabled3) {
    this.enabled3 = enabled3;
  }

  public ObfuscationOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.entries = new Hashtable();
    this.ruleSet6 = new RuleSet(1);
    this.ruleSet5 = new RuleSet(0);
    this.ruleSet3 = new RuleSet(1);
    this.ruleSet = new RuleSet(1);
    this.ruleSet7 = new RuleSet(0);
    this.ruleSet2 = new RuleSet(0);
    this.ruleSet4 = new RuleSet(0);
    this.randomSeed = null;
    this.finalizeEnabled = false;
    this.enabled = false;
    this.enabled3 = false;
    this.methodsNamingEnabled = true;
    this.outputJarCommentEnabled = true;
    this.outputJarComment = null;
    this.stringEncryptionSeed = '\uffff';
    this.stringEncryptionVersion = 1;
    this.globalStringEncryptionEnabled = false;
    this.packageStringEncryptionEnabled = false;
    this.decryptorIncludePattern = "";
    this.decryptorExcludePattern = "";
    this.java6Enabled = false;
    this.text6 = "org.springframework.boot.";
  }

  public boolean matchesMethod(ClassStorage classStorage, ClassGen classGen, Method method) {
    return classStorage == null
        ? false
        : this.ruleSet4.calculateCount(classStorage, classGen, method) > 0;
  }

  public void setEnabled8(boolean finalizeEnabled) {
    this.finalizeEnabled = finalizeEnabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen) {
    if (!this.packageStringEncryptionEnabled && classGen.getClassName().startsWith(this.text6)) {
      return 0;
    }
    AnnotationEntryGen[] annotationEntryGens;
    int value = (annotationEntryGens = classGen.getAnnotationEntries()).length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
      AnnotationEntryGen annotationEntryGen = annotationEntryGens[value2];
      if ("Lcom/allatori/annotations/DisableStringEncryption;"
          .equals(annotationEntryGen.getText2())) {
        return 0;
      }
      if ("Lcom/allatori/annotations/EnableStringEncryption;"
          .equals(annotationEntryGen.getText2())) {
        return 1;
      }
      if ("Lcom/allatori/annotations/MaximumStringEncryption;"
          .equals(annotationEntryGen.getText2())) {
        return 3;
      }
      List values;
      if ("Lcom/allatori/annotations/StringEncryption;".equals(annotationEntryGen.getText2())
          && (values = annotationEntryGen.getValues()) != null
          && values.size() > 0
          && ((ElementValuePairGen) values.get(0)).getValue() != null) {
        if ("enable".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 1;
        }
        if ("disable".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 0;
        }
        if ("maximum".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 2;
        }
        if ("maximum-with-warnings"
            .equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 3;
        }
      }
    }
    return this.ruleSet6.calculateCount(classStorage, classGen);
  }

  public boolean isGlobalStringEncryptionEnabled() {
    return this.globalStringEncryptionEnabled;
  }

  public void setEnabled5(boolean packageStringEncryptionEnabled) {
    this.packageStringEncryptionEnabled = packageStringEncryptionEnabled;
  }

  public boolean isMethodsNamingEnabled() {
    return this.methodsNamingEnabled;
  }

  public void setRandomSeed(String randomSeed) {
    this.randomSeed = randomSeed;
  }

  public String getDecryptorIncludePattern() {
    return this.decryptorIncludePattern;
  }

  public String getDecryptorExcludePattern() {
    return this.decryptorExcludePattern;
  }

  public int calculateCount2(ClassStorage classStorage, ClassGen classGen) {
    return this.ruleSet3.calculateCount(classStorage, classGen);
  }

  public Hashtable getEntries() {
    return this.entries;
  }

  public String getStringEncryptionIgnoredStrings() {
    return this.stringEncryptionIgnoredStrings;
  }

  public void setStringEncryptionVersion(int stringEncryptionVersion) {
    this.stringEncryptionVersion = stringEncryptionVersion;
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet7.processRuleEntry(ruleEntry);
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    if (this.enabled) {
      return 0;
    }
    AnnotationEntryGen[] annotationEntryGens;
    int value = (annotationEntryGens = classGen.getAnnotationEntries()).length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
      AnnotationEntryGen annotationEntryGen = annotationEntryGens[value2];
      if ("Lcom/allatori/annotations/StrongStringEncryptionType;"
          .equals(annotationEntryGen.getText2())) {
        return 1;
      }
      if ("Lcom/allatori/annotations/FastStringEncryptionType;"
          .equals(annotationEntryGen.getText2())) {
        return 0;
      }
      List values;
      if ("Lcom/allatori/annotations/StringEncryptionType;".equals(annotationEntryGen.getText2())
          && (values = annotationEntryGen.getValues()) != null
          && values.size() > 0
          && ((ElementValuePairGen) values.get(0)).getValue() != null) {
        if ("fast".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 0;
        }
        if ("strong".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return 1;
        }
      }
    }
    return this.ruleSet5.calculateCount(classStorage, classGen, methodGen);
  }

  public void setEnabled2(boolean outputJarCommentEnabled) {
    this.outputJarCommentEnabled = outputJarCommentEnabled;
  }

  public void processRuleEntry2(RuleEntry ruleEntry) {
    this.ruleSet4.processRuleEntry(ruleEntry);
  }

  public boolean isPackageStringEncryptionEnabled() {
    return this.packageStringEncryptionEnabled;
  }

  public void processRuleEntry3(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }

  public void setStringEncryptionIgnoredStrings(String stringEncryptionIgnoredStrings) {
    this.stringEncryptionIgnoredStrings = stringEncryptionIgnoredStrings;
  }

  public void processRuleEntry4(RuleEntry ruleEntry) {
    this.ruleSet6.processRuleEntry(ruleEntry);
  }

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    return classStorage == null ? false : this.ruleSet7.calculateCount(classStorage, classGen) > 0;
  }

  public void setEnabled6(boolean methodsNamingEnabled) {
    this.methodsNamingEnabled = methodsNamingEnabled;
  }

  public char getCharacter() {
    return this.stringEncryptionSeed;
  }

  public int getStringEncryptionVersion() {
    return this.stringEncryptionVersion;
  }

  public boolean isJava6Enabled() {
    return this.java6Enabled;
  }

  public void processRuleEntry5(RuleEntry ruleEntry) {
    this.ruleSet5.processRuleEntry(ruleEntry);
  }

  public void setDecryptorIncludePattern(String decryptorIncludePattern) {
    this.decryptorIncludePattern = decryptorIncludePattern;
  }

  public boolean isFinalizeEnabled() {
    return this.finalizeEnabled;
  }

  public String getOutputJarComment() {
    return this.outputJarComment;
  }

  public void processRuleEntry6(RuleEntry ruleEntry) {
    this.ruleSet3.processRuleEntry(ruleEntry);
  }

  public void setOutputJarComment(String outputJarComment) {
    this.outputJarComment = outputJarComment;
  }

  public boolean isEnabled3() {
    return this.enabled3;
  }

  public void setEnabled4(boolean java6Enabled) {
    this.java6Enabled = java6Enabled;
  }

  public boolean matchesMethodGen(
      ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    if (methodGen.getName().startsWith("Allatori")) {
      return !Configuration.getObfuscationOptions().isEnabled();
    }
    AnnotationEntryGen[] annotationEntryGens;
    int value = (annotationEntryGens = methodGen.getAnnotationEntryGens()).length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
      AnnotationEntryGen annotationEntryGen = annotationEntryGens[value2];
      if ("Lcom/allatori/annotations/DisableControlFlowObfuscation;"
          .equals(annotationEntryGen.getText2())) {
        return false;
      }
      if ("Lcom/allatori/annotations/EnableControlFlowObfuscation;"
          .equals(annotationEntryGen.getText2())) {
        return true;
      }
      List values;
      if ("Lcom/allatori/annotations/ControlFlowObfuscation;".equals(annotationEntryGen.getText2())
          && (values = annotationEntryGen.getValues()) != null
          && values.size() > 0
          && ((ElementValuePairGen) values.get(0)).getValue() != null) {
        if ("enable".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return true;
        }
        if ("disable".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
          return false;
        }
      }
    }
    AnnotationEntryGen[] annotationEntryGens2;
    int value4 = (annotationEntryGens2 = classGen.getAnnotationEntries()).length;
    int value5;
    for (int value6 = value5 = 0; value6 < value4; value6 = ++value5) {
      AnnotationEntryGen annotationEntryGen2 = annotationEntryGens2[value5];
      if ("Lcom/allatori/annotations/DisableControlFlowObfuscation;"
          .equals(annotationEntryGen2.getText2())) {
        return false;
      }
      if ("Lcom/allatori/annotations/EnableControlFlowObfuscation;"
          .equals(annotationEntryGen2.getText2())) {
        return true;
      }
      List values2;
      if ("Lcom/allatori/annotations/ControlFlowObfuscation;".equals(annotationEntryGen2.getText2())
          && (values2 = annotationEntryGen2.getValues()) != null
          && values2.size() > 0
          && ((ElementValuePairGen) values2.get(0)).getValue() != null) {
        if ("enable".equals(((ElementValuePairGen) values2.get(0)).getValue().stringifyValue())) {
          return true;
        }
        if ("disable".equals(((ElementValuePairGen) values2.get(0)).getValue().stringifyValue())) {
          return false;
        }
      }
    }
    return this.ruleSet.calculateCount(classStorage, classGen, methodGen) > 0;
  }

  public boolean matchesClassGen2(ClassStorage classStorage, ClassGen classGen) {
    return classStorage == null ? false : this.ruleSet2.calculateCount(classStorage, classGen) > 0;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void processRuleEntry7(RuleEntry ruleEntry) {
    this.ruleSet2.processRuleEntry(ruleEntry);
  }
}
