package com.allatori.config.rule;

import com.allatori.config.ConfigurationException;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassRule {

  public Vector values;

  public String[] texts;

  public int count;

  public boolean enabled;

  public boolean enabled2;

  public String text;

  public boolean enabled3;

  public String text2;

  public String text3;

  public String text4;

  public Vector values2;

  public boolean enabled4 = false;

  public ClassRule(
      String text, boolean matches, boolean matches2, boolean enabled4, boolean enabled3)
      throws ConfigurationException {
    this(text, matches, matches2);
    this.enabled4 = enabled4;
    this.enabled3 = enabled3;
  }

  public boolean matches(MethodGen methodGen) {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      MethodRule methodRule;
      if ((methodRule = (MethodRule) this.values.get(value)).matches(methodGen)) {
        if (!methodRule.matches()) {
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public boolean matches() {
    return this.enabled2;
  }

  public ClassRule(String text, boolean enabled, boolean enabled2) throws ConfigurationException {
    super();
    this.enabled4 = false;
    this.enabled3 = false;
    this.values2 = new Vector();
    this.values = new Vector();
    this.enabled = enabled;
    this.enabled2 = enabled2;
    this.processText(text);
  }

  public void processText(String text5) throws ConfigurationException {
    Matcher matcher2;
    if ((matcher2 =
            Pattern.compile(
                    "(.*?(?:class|interface))\\s+(.+?)(?:\\s+extends\\s+(.+?))?(?:\\s+implements\\s+(.+?))?(?:\\s+instanceof\\s+(.+?))?")
                .matcher(text5))
        .matches()) {
      String text6 = matcher2.group(1);
      this.count = TemplateUtils.calculateCount(text6);
      Matcher matcher3;
      if (TemplateUtils.matches2(text6)) {
        int value;
        if ((value = text6.indexOf(32)) == -1) {
          value = text6.length();
        }
        this.text = TemplateUtils.getText2(text6.substring(1, value));
        this.text = 'L' + this.text.replaceAll("\\\\\\.", "/") + ';';
        matcher3 = matcher2;
      } else {
        this.text = null;
        matcher3 = matcher2;
      }
      String text7 = matcher3.group(2);
      this.text2 = TemplateUtils.getText2(text7);
      this.text3 = TemplateUtils.getText2(matcher2.group(3));
      this.texts = TemplateUtils.getTexts(matcher2.group(4));
      this.text4 = TemplateUtils.getText2(matcher2.group(5));
    } else {
      throw new ConfigurationException("Invalid template.");
    }
  }

  public ClassRule() {
    this.enabled3 = false;
    this.values2 = new Vector();
    this.values = new Vector();
  }

  public boolean matches(ClassStorage classStorage, ClassGen classGen) {
    if (!TemplateUtils.matches(classGen, this.count)) {
      return this.enabled3;
    } else if (!classGen.getClassName().matches(this.text2)) {
      return this.enabled3;
    } else if (!classGen.getSuperclassName().matches(this.text3)) {
      return this.enabled3;
    } else if (!TemplateUtils.matches(classGen.getInterfaceNames(), this.texts)) {
      return this.enabled3;
    } else if (!TemplateUtils.matches(classStorage, classGen.getClassName(), this.text4)) {
      return this.enabled3;
    } else {
      return this.text != null && !TemplateUtils.matches(classGen.getAnnotationEntries(), this.text)
          ? this.enabled3
          : !this.enabled3;
    }
  }

  public void processMethodRule(MethodRule methodRule) {
    this.values.add(methodRule);
  }

  public boolean matches2() {
    return this.enabled;
  }

  public void processFieldRule(FieldRule fieldRule) {
    this.values2.add(fieldRule);
  }

  public boolean matches(ClassStorage classStorage, Field field) {
    int value;
    for (int value2 = value = 0; value2 < this.values2.size(); value2 = ++value) {
      FieldRule fieldRule;
      if ((fieldRule = (FieldRule) this.values2.get(value)).matches(classStorage, field)) {
        if (!fieldRule.matches()) {
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public boolean matches(Method method) {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = ++value) {
      MethodRule methodRule;
      if ((methodRule = (MethodRule) this.values.get(value)).matches(method)) {
        if (!methodRule.matches()) {
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public boolean matches3() {
    return this.enabled4;
  }

  public ClassRule createClassRule() {
    ClassRule classRule = new ClassRule();
    classRule.enabled = this.enabled;
    classRule.enabled2 = this.enabled2;
    classRule.enabled4 = this.enabled4;
    classRule.count = this.count;
    classRule.text2 = this.text2;
    classRule.text3 = this.text3;
    classRule.text4 = this.text4;
    classRule.texts = this.texts;
    classRule.text = this.text;
    return classRule;
  }

  public boolean matches4() {
    return this.values2.size() > 0;
  }

  public boolean matches5() {
    return this.values.size() > 0;
  }
}
