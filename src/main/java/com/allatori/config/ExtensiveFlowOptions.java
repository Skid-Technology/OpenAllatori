package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.RuleSet;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ElementValuePairGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;
import java.util.List;

public class ExtensiveFlowOptions {

  public static final int COUNT = 0;

  public static final int COUNT2 = 2;

  public static final int COUNT3 = 1;

  public final Configuration configuration;

  public RuleSet ruleSet;

  public ExtensiveFlowOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.ruleSet = new RuleSet(1);
  }

  public int calculateCount(ClassStorage classStorage, ClassGen classGen, MethodGen methodGen) {
    if (!Configuration.getObfuscationOptions().isEnabled()
        && methodGen.getName().startsWith("Allatori")) {
      return 1;
    }
    byte value = 1;
    AnnotationEntryGen[] annotationEntryGens = methodGen.getAnnotationEntryGens();
    while (true) {
      AnnotationEntryGen[] annotationEntryGens2 = annotationEntryGens;
      int value2 = annotationEntryGens2.length;
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
        AnnotationEntryGen annotationEntryGen = annotationEntryGens2[value3];
        if ("Lcom/allatori/annotations/DisableExtensiveFlowObfuscation;"
            .equals(annotationEntryGen.getText2())) {
          return 0;
        }
        if ("Lcom/allatori/annotations/NormalExtensiveFlowObfuscation;"
            .equals(annotationEntryGen.getText2())) {
          return 1;
        }
        if ("Lcom/allatori/annotations/MaximumExtensiveFlowObfuscation;"
            .equals(annotationEntryGen.getText2())) {
          return 2;
        }
        List values;
        if ("Lcom/allatori/annotations/ExtensiveFlowObfuscation;"
                .equals(annotationEntryGen.getText2())
            && (values = annotationEntryGen.getValues()) != null
            && values.size() > 0
            && ((ElementValuePairGen) values.get(0)).getValue() != null) {
          if ("maximum".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
            return 2;
          }
          if ("normal".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
            return 1;
          }
          if ("disable".equals(((ElementValuePairGen) values.get(0)).getValue().stringifyValue())) {
            return 0;
          }
        }
      }
      if (value == 0) {
        if (Configuration.getObfuscationOptions().isEnabled3()
            && Configuration.getObfuscationOptions().isMethodsNamingEnabled()) {
          return 0;
        }
        return this.ruleSet.calculateCount(classStorage, classGen, methodGen);
      }
      value = 0;
      annotationEntryGens = classGen.getAnnotationEntries();
    }
  }

  public void processRuleEntry(RuleEntry ruleEntry) {
    this.ruleSet.processRuleEntry(ruleEntry);
  }
}
