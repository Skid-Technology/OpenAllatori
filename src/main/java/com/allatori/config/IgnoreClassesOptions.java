package com.allatori.config;

import com.allatori.config.rule.ClassRule;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Iterator;
import java.util.Vector;

public class IgnoreClassesOptions {

  public final Configuration configuration;

  public boolean enabled;

  public Vector values;

  public boolean matchesClassGen(ClassStorage classStorage, ClassGen classGen) {
    if (!this.enabled) {
      return false;
    }
    Iterator iterator2 = this.values.iterator();
    while (iterator2.hasNext()) {
      ClassRule classRule;
      if ((classRule = (ClassRule) iterator2.next()).matches(classStorage, classGen)) {
        if (!classRule.matches()) {
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public void processClassRule(ClassRule classRule) {
    this.values.add(classRule);
    this.enabled = true;
  }

  public IgnoreClassesOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.values = new Vector();
    this.enabled = false;
  }
}
