package com.allatori.config;

import com.allatori.config.rule.RuleEntry;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Iterator;
import java.util.Vector;

public class PackageRenamingOptions {

  public Vector values;

  public boolean enabled;

  public final Configuration configuration;

  public boolean isEnabled() {
    return this.enabled;
  }

  public boolean matchesText(ClassStorage classStorage, ClassGen classGen, String text2) {
    if (classStorage != null && classGen != null && text2 != null) {
      if (this.values.size() == 0) {
        return false;
      }
      Iterator iterator2 = this.values.iterator();
      while (iterator2.hasNext()) {
        PackageNameRule packageNameRule;
        if ((packageNameRule = (PackageNameRule) iterator2.next())
                .ruleEntry.classRule.matches(classStorage, classGen)
            && text2.matches(packageNameRule.text)) {
          return true;
        }
      }
      return false;
    } else {
      return false;
    }
  }

  public void processText(RuleEntry ruleEntry, String text) {
    this.enabled = true;
    String text2 = text.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
    this.values.add(new PackageNameRule(this, ruleEntry, text2));
  }

  public PackageRenamingOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.enabled = false;
    this.values = new Vector();
  }
}
