package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;
import com.allatori.transform.DistributedStringEncryptionTransformer;
import java.util.Iterator;
import java.util.Vector;

public class MethodCallRemovalOptions {

  public Vector values;

  public boolean enabled;

  public final Configuration configuration;

  public boolean isEnabled() {
    return this.enabled;
  }

  public MethodCallRemovalOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.enabled = false;
    this.values = new Vector();
  }

  public boolean matchesText(
      ClassStorage classStorage,
      ClassGen classGen,
      MethodGen methodGen,
      String text3,
      String text5,
      String text6) {
    if (classStorage != null && text3 != null && text5 != null) {
      if (text5.startsWith(DistributedStringEncryptionTransformer.text4)) {
        return false;
      }
      if ("<init>".equals(text5)) {
        return false;
      }
      if ("<clinit>".equals(text5)) {
        return false;
      }
      if (this.values.size() == 0) {
        return false;
      }
      Iterator iterator2 = this.values.iterator();
      while (iterator2.hasNext()) {
        MethodCallRemovalRule methodCallRemovalRule;
        if ((methodCallRemovalRule = (MethodCallRemovalRule) iterator2.next())
                .ruleEntry.classRule.matches(classStorage, classGen)
            && methodCallRemovalRule.ruleEntry.classRule.matches(methodGen)
            && text3.matches(methodCallRemovalRule.text)
            && text5.matches(methodCallRemovalRule.text2)) {
          return true;
        }
      }
      return Configuration.getOptimizationOptions().matchesText("" + text3 + "." + text5 + text6);
    } else {
      return false;
    }
  }

  public void processText(RuleEntry ruleEntry, String text, String text2) {
    this.enabled = true;
    String text3 = text.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
    String text4 = text2.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
    this.values.add(new MethodCallRemovalRule(this, ruleEntry, text3, text4));
  }
}
